package revxrsal.commands.node.parser;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.StringJoiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.CommandPriority;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.SecretCommand;
import revxrsal.commands.annotation.Usage;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandFunction;
import revxrsal.commands.command.CommandPermission;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.command.Potential;
import revxrsal.commands.exception.ExpectedLiteralException;
import revxrsal.commands.exception.InputParseException;
import revxrsal.commands.exception.context.ErrorContext;
import revxrsal.commands.help.Help;
import revxrsal.commands.node.CommandNode;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.node.MutableExecutionContext;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.process.CommandCondition;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.util.Collections;

final class Execution<A extends CommandActor> implements ExecutableCommand<A> {
   private final CommandFunction function;
   private final List<CommandNode<A>> nodes;
   private final @Unmodifiable Map<String, ParameterNode<A, Object>> parameters;
   private final CommandPermission<A> permission;
   private final int size;
   private final boolean isSecret;
   private final String description;
   private final String usage;
   private final OptionalInt priority;
   private final String siblingPath;
   private final String path;
   private final int flagCount;
   private final boolean lowPriority;
   private int optionalParameters;
   private int requiredInput;

   public Execution(CommandFunction function, List<CommandNode<A>> nodes) {
      this.function = function;
      this.nodes = nodes;
      this.parameters = this.computeParameters();
      this.size = nodes.size();
      this.permission = function.<A>lamp().createPermission(function.annotations());

      for(CommandNode<A> node : nodes) {
         if (isOptional(node)) {
            ++this.optionalParameters;
         } else {
            ++this.requiredInput;
         }
      }

      this.isSecret = function.annotations().contains(SecretCommand.class);
      this.description = (String)function.annotations().map(Description.class, Description::value);
      this.path = this.computePath();
      this.usage = (String)function.annotations().mapOrGet(Usage.class, Usage::value, this::path);
      this.priority = (OptionalInt)function.annotations().mapOr(CommandPriority.class, (c) -> OptionalInt.of(c.value()), OptionalInt.empty());
      this.siblingPath = this.computeSiblingPath();
      this.flagCount = Collections.count(nodes, (n) -> n instanceof ParameterNode && (((ParameterNode)n).isFlag() || ((ParameterNode)n).isSwitch()));
      this.lowPriority = function.annotations().contains(CommandPriority.Low.class);
      if (this.lowPriority && this.priority.isPresent()) {
         throw new IllegalArgumentException("You cannot have @CommandPriority and @CommandPriority.Low on the same function!");
      }
   }

   private static boolean isOptional(@NotNull CommandNode<? extends CommandActor> node) {
      return node instanceof ParameterNodeImpl && ((ParameterNode)node).isOptional();
   }

   private @Unmodifiable Map<String, ParameterNode<A, Object>> computeParameters() {
      Map<String, ParameterNode<A, Object>> parameters = new LinkedHashMap();

      for(CommandNode<A> node : this.nodes) {
         if (node instanceof ParameterNode) {
            ParameterNode parameter = (ParameterNode)node;
            parameters.put(parameter.name(), parameter);
         }
      }

      return java.util.Collections.unmodifiableMap(parameters);
   }

   private String computePath() {
      StringJoiner joiner = new StringJoiner(" ");

      for(CommandNode<A> n : this.nodes) {
         joiner.add(n.representation());
      }

      return joiner.toString();
   }

   private @NotNull String computeSiblingPath() {
      StringJoiner joiner = new StringJoiner(" ");
      int index = 0;

      for(int i = this.nodes.size() - 1; i >= 0; --i) {
         CommandNode<A> n = (CommandNode)this.nodes.get(i);
         if (n.isLiteral()) {
            index = i;
            break;
         }
      }

      for(int i = 0; i < index; ++i) {
         CommandNode<A> n = (CommandNode)this.nodes.get(i);
         joiner.add(n.representation());
      }

      return joiner.toString();
   }

   public @NotNull Lamp<A> lamp() {
      return this.function.<A>lamp();
   }

   public int size() {
      return this.size;
   }

   public int optionalParameters() {
      return this.optionalParameters;
   }

   public int requiredInput() {
      return this.requiredInput;
   }

   public @NotNull String path() {
      return this.path;
   }

   public @NotNull String usage() {
      return this.usage;
   }

   public @NotNull CommandPermission<A> permission() {
      return this.permission;
   }

   public @NotNull CommandFunction function() {
      return this.function;
   }

   public @NotNull CommandNode<A> lastNode() {
      return (CommandNode)this.nodes.get(this.nodes.size() - 1);
   }

   public @NotNull LiteralNodeImpl<A> firstNode() {
      return (LiteralNodeImpl)this.nodes.get(0);
   }

   public @NotNull Potential<A> test(@NotNull A actor, @NotNull MutableStringStream input) {
      return new ParseResult<A>(this, actor, input);
   }

   public void unregister() {
      this.lamp().unregister(this);
   }

   public void execute(@NotNull ExecutionContext<A> context) {
      try {
         for(CommandCondition<? super A> condition : context.lamp().commandConditions()) {
            condition.test(context);
         }

         this.action().execute(context);
      } catch (Throwable t) {
         this.lamp().handleException(t, ErrorContext.executingFunction(context));
      }

   }

   public Help.@NotNull RelatedCommands<A> relatedCommands(@Nullable A filterFor) {
      return new HelpImpl.RelatedCommandsImpl<A>(Collections.filter(this.lamp().registry().commands(), (command) -> command != this && !command.isSecret() && this.isRelatedTo(command) && (filterFor == null || command.permission().isExecutableBy(filterFor))));
   }

   public Help.@NotNull ChildrenCommands<A> childrenCommands(@Nullable A filterFor) {
      return new HelpImpl.ChildrenCommandsImpl<A>(Collections.filter(this.lamp().registry().commands(), (command) -> command != this && !command.isSecret() && this.isParentOf(command) && (filterFor == null || command.permission().isExecutableBy(filterFor))));
   }

   public Help.@NotNull SiblingCommands<A> siblingCommands(@Nullable A filterFor) {
      return new HelpImpl.SiblingCommandsImpl<A>(Collections.filter(this.lamp().registry().commands(), (command) -> command != this && !command.isSecret() && this.isSiblingOf(command) && (filterFor == null || command.permission().isExecutableBy(filterFor))));
   }

   public @NotNull @Unmodifiable Map<String, ParameterNode<A, Object>> parameters() {
      return this.parameters;
   }

   public boolean isSiblingOf(@NotNull ExecutableCommand<A> command) {
      String otherPath = ((Execution)command).siblingPath;
      return command != this && otherPath.startsWith(this.siblingPath) || this.siblingPath.startsWith(otherPath);
   }

   public boolean isChildOf(@NotNull ExecutableCommand<A> command) {
      if (this.size() <= command.size()) {
         return false;
      } else {
         for(int i = 0; i < command.size(); ++i) {
            CommandNode<A> ourNode = (CommandNode)this.nodes.get(i);
            CommandNode<A> otherNode = (CommandNode)command.nodes().get(i);
            if (!otherNode.representation().equals(ourNode.representation())) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean containsFlags() {
      return this.flagCount > 0;
   }

   public boolean isSecret() {
      return this.isSecret;
   }

   public String toString() {
      return "ExecutableCommand(path='" + this.path() + "')";
   }

   public @Nullable String description() {
      return this.description;
   }

   public @Range(
   from = 0L,
   to = 2147483647L
) int flagCount() {
      return this.flagCount;
   }

   public @NotNull @Unmodifiable List<CommandNode<A>> nodes() {
      return java.util.Collections.unmodifiableList(this.nodes);
   }

   public @NotNull OptionalInt commandPriority() {
      return this.priority;
   }

   public int compareTo(@NotNull ExecutableCommand<A> o) {
      if (!(o instanceof Execution)) {
         return 0;
      } else {
         Execution<A> exec = (Execution)o;
         if (this.lowPriority != exec.lowPriority) {
            return this.lowPriority ? 1 : -1;
         } else if (this.commandPriority().isPresent() && o.commandPriority().isPresent()) {
            return Integer.compare(this.commandPriority().getAsInt(), o.commandPriority().getAsInt());
         } else {
            int sizeComparison = Integer.compare(this.size, exec.size);
            if (sizeComparison != 0) {
               return sizeComparison;
            } else if (isOptional(this.lastNode()) != isOptional(o.lastNode())) {
               return isOptional(this.lastNode()) ? 1 : -1;
            } else {
               return this.lastNode().compareTo(o.lastNode());
            }
         }
      }
   }

   public @NotNull Iterator<CommandNode<A>> iterator() {
      return Collections.<CommandNode<A>>unmodifiableIterator(this.nodes.iterator());
   }

   static final class ParseResult<A extends CommandActor> implements Potential<A> {
      private final Execution<A> execution;
      private final MutableExecutionContext<A> context;
      private final boolean testResult;
      private MutableStringStream input;
      private boolean consumedAllInput = false;
      private @Nullable Throwable error;
      private @Nullable ErrorContext<A> errorContext;

      public ParseResult(Execution<A> execution, A actor, MutableStringStream input) {
         this.execution = execution;
         this.context = ExecutionContext.<A>createMutable(execution, actor, input.toImmutableCopy());
         this.input = input;
         this.testResult = this.test();
      }

      private boolean test() {
         if (this.execution.flagCount > 0) {
            MutableStringStream original = this.input.toMutableCopy();
            if (!this.tryParseFlags()) {
               this.input = original;
               return false;
            }
         }

         for(CommandNode<A> node : this.execution.nodes) {
            if (!(node instanceof ParameterNode) || !((ParameterNode)node).isFlag() && !((ParameterNode)node).isSwitch()) {
               if (!this.tryParse(node, this.input, this.context)) {
                  this.context.clearResolvedArguments();
                  return false;
               }
            } else {
               ParameterNode<?, ?> p = (ParameterNode)node;
            }
         }

         if (!this.testConditions()) {
            return false;
         } else {
            this.consumedAllInput = this.input.hasFinished();
            return true;
         }
      }

      private boolean tryParseFlags() {
         FlagParser<A> flagParser = new FlagParser<A>(this.context, this.input);
         if (!flagParser.tryParse()) {
            this.error = flagParser.error();
            this.errorContext = flagParser.errorContext();
            this.context.clearResolvedArguments();
            return false;
         } else {
            this.input = flagParser.strippedInput();
            return true;
         }
      }

      private boolean testConditions() {
         try {
            for(CommandCondition<? super A> condition : this.context.lamp().commandConditions()) {
               condition.test(this.context);
            }

            return true;
         } catch (Throwable t) {
            this.error = t;
            this.errorContext = ErrorContext.<A>executingFunction(this.context);
            return false;
         }
      }

      public boolean successful() {
         return this.testResult;
      }

      public @NotNull ExecutionContext<A> context() {
         return this.context;
      }

      public boolean failed() {
         return !this.testResult;
      }

      public void handleException() {
         if (this.error != null && this.errorContext != null) {
            this.context().lamp().handleException(this.error, this.errorContext);
         }

      }

      public @Nullable Throwable error() {
         return this.error;
      }

      public @Nullable ErrorContext<A> errorContext() {
         return this.errorContext;
      }

      public void execute() {
         if (this.error == null && this.execution.lamp().hooks().onCommandExecuted(this.execution, this.context)) {
            this.execution.lastNode().execute(this.context, this.input);
         }

      }

      public int compareTo(@NotNull Potential<A> o) {
         if (o.getClass() != this.getClass()) {
            return 0;
         } else {
            ParseResult<A> result = (ParseResult)o;
            if (this.consumedAllInput != result.consumedAllInput) {
               return this.consumedAllInput ? -1 : 1;
            } else {
               return this.execution.compareTo(result.execution);
            }
         }
      }

      private boolean tryParse(CommandNode<A> node, MutableStringStream input, MutableExecutionContext<A> context) {
         if (input.hasRemaining() && input.peek() == ' ') {
            input.skipWhitespace();
         }

         int pos = input.position();
         if (node instanceof LiteralNodeImpl) {
            LiteralNodeImpl<A> l = (LiteralNodeImpl)node;
            String value = input.readUnquotedString();
            if (node.name().equalsIgnoreCase(value)) {
               this.checkForSpace(input);
               return true;
            } else {
               input.setPosition(pos);
               this.error = new ExpectedLiteralException(value, l);
               this.errorContext = ErrorContext.<A>parsingLiteral(context, l);
               return false;
            }
         } else {
            ParameterNodeImpl<A, Object> parameter = (ParameterNodeImpl)node;

            try {
               Object value = parameter.parse(input, context);
               Lamp<A> lamp = this.execution.function().<A>lamp();
               context.addResolvedArgument(parameter.name(), value);
               this.checkForSpace(input);
               return true;
            } catch (Throwable t) {
               input.setPosition(pos);
               this.error = t;
               this.errorContext = ErrorContext.<A>parsingParameter(context, parameter, input);
               return false;
            }
         }
      }

      private void checkForSpace(MutableStringStream input) {
         if (input.hasRemaining() && input.peek() != ' ') {
            throw new InputParseException(InputParseException.Cause.EXPECTED_WHITESPACE);
         }
      }

      public String toString() {
         return this.successful() ? "Potential(path=" + this.execution.path() + ", success=true)" : "Potential(path=" + this.execution.path() + ", success=false, error=" + this.error + ")";
      }
   }
}
