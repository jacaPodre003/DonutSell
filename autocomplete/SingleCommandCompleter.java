package revxrsal.commands.autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.node.CommandNode;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.node.LiteralNode;
import revxrsal.commands.node.MutableExecutionContext;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.stream.MutableStringStream;

final class SingleCommandCompleter<A extends CommandActor> {
   private final ExecutableCommand<A> command;
   private final MutableStringStream input;
   private final MutableExecutionContext<A> context;
   private final List<String> suggestions = new ArrayList();
   private int positionBeforeParsing = -1;

   public SingleCommandCompleter(A actor, ExecutableCommand<A> command, MutableStringStream input) {
      this.command = command;
      this.input = input;
      this.context = ExecutionContext.<A>createMutable(command, actor, input.toImmutableView());
   }

   private void rememberPosition() {
      if (this.positionBeforeParsing != -1) {
         throw new IllegalArgumentException("You already have a position remembered that you did not consume.");
      } else {
         this.positionBeforeParsing = this.input.position();
      }
   }

   private String restorePosition() {
      if (this.positionBeforeParsing == -1) {
         throw new IllegalArgumentException("You forgot to call rememberPosition() when trying to restore position.");
      } else {
         int positionAfterParsing = this.input.position();
         this.input.setPosition(this.positionBeforeParsing);
         this.positionBeforeParsing = -1;
         return this.input.peek(positionAfterParsing - this.positionBeforeParsing);
      }
   }

   public void complete() {
      Map<String, ParameterNode<A, Object>> remainingFlags = null;

      for(CommandNode<A> node : this.command.nodes()) {
         if (node.isLiteral()) {
            CompletionResult result = this.completeLiteral(node.requireLiteralNode());
            if (result == SingleCommandCompleter.CompletionResult.HALT) {
               break;
            }
         } else {
            ParameterNode<A, Object> parameter = node.requireParameterNode();
            if (!parameter.isFlag() && !parameter.isSwitch()) {
               CompletionResult result = this.completeParameter(parameter);
               if (result == SingleCommandCompleter.CompletionResult.HALT) {
                  break;
               }
            } else {
               (remainingFlags == null ? (remainingFlags = new HashMap()) : remainingFlags).put(this.universalFlagName(parameter), parameter);
            }
         }
      }

      if (this.command.containsFlags() && remainingFlags != null) {
         this.completeFlags(remainingFlags);
      }
   }

   private CompletionResult completeParameter(@NotNull ParameterNode<A, Object> parameter) {
      this.rememberPosition();
      if (parameter.isSwitch()) {
         this.context.addResolvedArgument(parameter.name(), true);
         return SingleCommandCompleter.CompletionResult.CONTINUE;
      } else {
         try {
            Object value = parameter.parse(this.input, this.context);
            this.context.addResolvedArgument(parameter.name(), value);
            int positionAfterParsing = this.input.position();
            String consumed = this.restorePosition();
            Collection<String> parameterSuggestions = parameter.complete(this.context);
            this.input.setPosition(positionAfterParsing);
            if (this.input.hasFinished()) {
               this.filterSuggestions(consumed, parameterSuggestions);
               return SingleCommandCompleter.CompletionResult.HALT;
            } else {
               if (this.input.peek() == ' ') {
                  this.input.skipWhitespace();
               }

               return SingleCommandCompleter.CompletionResult.CONTINUE;
            }
         } catch (Throwable var6) {
            String consumed = this.restorePosition();
            this.filterSuggestions(consumed, parameter.complete(this.context));
            return SingleCommandCompleter.CompletionResult.HALT;
         }
      }
   }

   @Contract(
      mutates = "param1"
   )
   private void completeFlags(@NotNull Map<String, ParameterNode<A, Object>> remainingFlags) {
      boolean lastWasShort = false;

      while(this.input.hasRemaining()) {
         if (this.input.peek() == ' ') {
            this.input.skipWhitespace();
         }

         String next = this.input.peekUnquotedString();
         if (next.startsWith("--")) {
            lastWasShort = false;
            String flagName = next.substring("--".length());
            ParameterNode<A, Object> targetFlag = (ParameterNode)remainingFlags.remove(flagName);
            if (targetFlag == null) {
               for(ParameterNode<A, Object> value : remainingFlags.values()) {
                  if (this.universalFlagName(value).startsWith(flagName)) {
                     this.suggestions.add("--" + this.universalFlagName(value));
                  }
               }

               return;
            }

            this.input.readUnquotedString();
            if (this.input.hasFinished()) {
               return;
            }

            if (this.input.remaining() == 1 && this.input.peek() == ' ') {
               Collection<String> parameterSuggestions = targetFlag.complete(this.context);
               this.suggestions.addAll(parameterSuggestions);
               return;
            }

            this.input.skipWhitespace();
            CompletionResult result = this.completeParameter(targetFlag);
            if (result == SingleCommandCompleter.CompletionResult.HALT) {
               return;
            }

            if (this.input.hasRemaining() && this.input.peek() == ' ') {
               this.input.skipWhitespace();
            }
         } else if (next.startsWith("-")) {
            lastWasShort = true;
            String shortenedString = next.substring("-".length());
            char[] spec = shortenedString.toCharArray();
            this.input.moveForward("-".length());

            for(char flag : spec) {
               this.input.moveForward();
               ParameterNode<A, Object> targetFlag = this.removeParameterWithShorthand(remainingFlags, flag);
               if (targetFlag != null) {
                  if (targetFlag.isSwitch()) {
                     this.context.addResolvedArgument(targetFlag.name(), true);
                  }

                  if (this.input.hasFinished()) {
                     if (targetFlag.isFlag()) {
                        return;
                     }

                     for(ParameterNode<A, Object> remFlag : remainingFlags.values()) {
                        if (remFlag.shorthand() != null) {
                           String flagCompletion = "-" + shortenedString + remFlag.shorthand();
                           this.suggestions.add(remFlag.isFlag() ? flagCompletion + ' ' : flagCompletion);
                        }
                     }

                     return;
                  }

                  if (!targetFlag.isSwitch()) {
                     if (this.input.remaining() == 1 && this.input.peek() == ' ') {
                        Collection<String> parameterSuggestions = targetFlag.complete(this.context);
                        this.suggestions.addAll(parameterSuggestions);
                        return;
                     }

                     if (this.input.hasRemaining() && this.input.peek() == ' ') {
                        this.input.skipWhitespace();
                     }

                     CompletionResult result = this.completeParameter(targetFlag);
                     if (result == SingleCommandCompleter.CompletionResult.HALT) {
                        return;
                     }
                  }
               }
            }
         }
      }

      for(ParameterNode<A, Object> c : remainingFlags.values()) {
         if (lastWasShort) {
            this.suggestions.add("-" + c.shorthand());
         } else {
            this.suggestions.add("--" + (c.isSwitch() ? c.switchName() : c.flagName()));
         }
      }

   }

   private @Nullable ParameterNode<A, Object> removeParameterWithShorthand(Map<String, ParameterNode<A, Object>> parametersLeft, char c) {
      Iterator<Map.Entry<String, ParameterNode<A, Object>>> iterator = parametersLeft.entrySet().iterator();

      while(iterator.hasNext()) {
         Map.Entry<String, ParameterNode<A, Object>> entry = (Map.Entry)iterator.next();
         Character shorthand = ((ParameterNode)entry.getValue()).shorthand();
         if (shorthand != null && shorthand == c) {
            iterator.remove();
            return (ParameterNode)entry.getValue();
         }
      }

      return null;
   }

   private CompletionResult completeLiteral(@NotNull LiteralNode<A> node) {
      String nextWord = this.input.readUnquotedString();
      if (this.input.hasFinished()) {
         if (node.name().startsWith(nextWord)) {
            this.suggestions.add(node.name());
         }

         return SingleCommandCompleter.CompletionResult.HALT;
      } else if (!node.name().equalsIgnoreCase(nextWord)) {
         return SingleCommandCompleter.CompletionResult.HALT;
      } else if (this.input.hasRemaining() && this.input.peek() == ' ') {
         this.input.skipWhitespace();
         return SingleCommandCompleter.CompletionResult.CONTINUE;
      } else {
         return SingleCommandCompleter.CompletionResult.HALT;
      }
   }

   private void filterSuggestions(String consumed, @NotNull Collection<String> parameterSuggestions) {
      for(String parameterSuggestion : parameterSuggestions) {
         if (parameterSuggestion.toLowerCase().startsWith(consumed.toLowerCase())) {
            this.suggestions.add(getRemainingContent(parameterSuggestion, consumed));
         }
      }

   }

   private String universalFlagName(@NotNull ParameterNode<A, Object> parameter) {
      if (parameter.isSwitch()) {
         return parameter.switchName();
      } else {
         return parameter.isFlag() ? parameter.flagName() : parameter.name();
      }
   }

   public @NotNull List<String> suggestions() {
      return this.suggestions;
   }

   private static String getRemainingContent(String suggestion, String consumed) {
      int matchIndex = consumed.length();
      int spaceIndex = suggestion.lastIndexOf(32, matchIndex - 1);
      return suggestion.substring(spaceIndex + 1);
   }

   private static enum CompletionResult {
      HALT,
      CONTINUE;
   }
}
