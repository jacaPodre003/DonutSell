package revxrsal.commands.node.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.InputParseException;
import revxrsal.commands.exception.UnknownParameterException;
import revxrsal.commands.exception.context.ErrorContext;
import revxrsal.commands.node.MutableExecutionContext;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.reflect.ktx.KotlinConstants;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.stream.StringStream;
import revxrsal.commands.util.Collections;
import revxrsal.commands.util.Strings;

final class FlagParser<A extends CommandActor> {
   private final MutableExecutionContext<A> context;
   private final List<ParameterNode<A, Object>> parametersLeft;
   private final MutableStringStream input;
   private final List<Strings.StringRange> rangesToRemove = new ArrayList();
   private Throwable error;
   private ErrorContext<A> errorContext;

   public FlagParser(MutableExecutionContext<A> context, MutableStringStream input) {
      this.context = context;
      this.parametersLeft = Collections.<ParameterNode<A, Object>>filter(context.command().parameters().values(), (parameter) -> parameter.isSwitch() || parameter.isFlag());
      this.input = input;
   }

   public boolean tryParse() {
      try {
         while(this.input.hasRemaining()) {
            int start = this.input.position();
            if (this.input.peek() == ' ') {
               this.input.skipWhitespace();
            }

            String next = this.input.peekUnquotedString();
            if (next.startsWith("--")) {
               String flagName = next.substring("--".length());
               ParameterNode<A, Object> parameter = this.removeParameterNamed(flagName);
               this.input.readUnquotedString();
               this.parseNext(this.context, parameter);
               int end = this.input.position();
               this.rangesToRemove.add(new Strings.StringRange(start, end));
            } else if (!next.startsWith("-")) {
               this.input.moveForward(next.length());
            } else {
               this.input.readUnquotedString();
               char[] flags = next.substring("-".length()).toCharArray();

               for(char flag : flags) {
                  ParameterNode<A, Object> parameter = this.removeParameterWithShorthand(flag);
                  this.parseNext(this.context, parameter);
               }

               int end = this.input.position();
               this.rangesToRemove.add(new Strings.StringRange(start, end));
            }
         }

         for(ParameterNode<A, Object> parameter : this.parametersLeft) {
            if (parameter.isSwitch()) {
               if (!KotlinConstants.isKotlinClass(parameter.command().function().method().getDeclaringClass())) {
                  this.context.addResolvedArgument(parameter.name(), false);
               }
            } else if (parameter.isFlag()) {
               this.parseFlag(this.context, parameter, StringStream.createMutable(""));
            }
         }

         return true;
      } catch (Throwable t) {
         this.error = t;
         return false;
      }
   }

   private void parseNext(MutableExecutionContext<A> context, ParameterNode<A, Object> parameter) {
      if (parameter.isSwitch()) {
         context.addResolvedArgument(parameter.name(), true);
      } else {
         if (this.input.hasFinished() || this.input.peek() != ' ') {
            throw new InputParseException(InputParseException.Cause.EXPECTED_WHITESPACE);
         }

         this.input.skipWhitespace();
         this.parseFlag(context, parameter, this.input);
      }

   }

   private <T> void parseFlag(MutableExecutionContext<A> context, ParameterNode<A, T> parameter, MutableStringStream input) {
      try {
         T value = parameter.parse(input, context);
         context.addResolvedArgument(parameter.name(), value);
      } catch (Throwable t) {
         this.errorContext = ErrorContext.<A>parsingParameter(context, parameter, input);
         throw t;
      }
   }

   private @NotNull ParameterNode<A, Object> removeParameterWithShorthand(char c) {
      Iterator<ParameterNode<A, Object>> iterator = this.parametersLeft.iterator();

      while(iterator.hasNext()) {
         ParameterNode<A, Object> value = (ParameterNode)iterator.next();
         Character shorthand = value.shorthand();
         if (shorthand != null && shorthand == c) {
            iterator.remove();
            return value;
         }
      }

      this.errorContext = ErrorContext.<A>unknownParameter(this.context);
      throw new UnknownParameterException(Character.toString(c), true);
   }

   private @NotNull ParameterNode<A, Object> removeParameterNamed(String name) {
      Iterator<ParameterNode<A, Object>> iterator = this.parametersLeft.iterator();

      while(iterator.hasNext()) {
         ParameterNode<A, Object> value = (ParameterNode)iterator.next();
         if (value.isFlag() && Objects.equals(value.flagName(), name)) {
            iterator.remove();
            return value;
         }

         if (value.isSwitch() && Objects.equals(value.switchName(), name)) {
            iterator.remove();
            return value;
         }
      }

      this.errorContext = ErrorContext.<A>unknownParameter(this.context);
      throw new UnknownParameterException(name, false);
   }

   public @NotNull MutableStringStream strippedInput() {
      String string = Strings.removeRanges(this.input.source(), this.rangesToRemove);
      return StringStream.createMutable(string);
   }

   public ErrorContext<A> errorContext() {
      return this.errorContext;
   }

   public Throwable error() {
      return this.error;
   }
}
