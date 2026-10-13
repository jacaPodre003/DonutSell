package revxrsal.commands.exception.context;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.node.LiteralNode;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.stream.StringStream;

public interface ErrorContext<A extends CommandActor> {
   static <A extends CommandActor> @NotNull ParsingLiteral<A> parsingLiteral(@NotNull ExecutionContext<A> context, @NotNull LiteralNode<A> node) {
      return new ParsingLiteralContext<A>(context, node);
   }

   static <A extends CommandActor> @NotNull ParsingParameter<A> parsingParameter(@NotNull ExecutionContext<A> context, @NotNull ParameterNode<A, ?> node, @NotNull StringStream input) {
      return new ParsingParameterContext<A>(context, node, input);
   }

   static <A extends CommandActor> @NotNull ExecutingFunction<A> executingFunction(@NotNull ExecutionContext<A> context) {
      return new ExecutingFunctionContext<A>(context);
   }

   static <A extends CommandActor> @NotNull UnknownCommand<A> unknownCommand(@NotNull A actor) {
      return new UnknownCommandContext<A>(actor);
   }

   static <A extends CommandActor> @NotNull UnknownParameter<A> unknownParameter(@NotNull ExecutionContext<A> context) {
      return new UnknownParameterContext<A>(context);
   }

   default boolean hasExecutionContext() {
      return this.context() != null;
   }

   ExecutionContext<A> context();

   @NotNull A actor();

   @NotNull Lamp<A> lamp();

   default boolean isParsingLiteral() {
      return this instanceof ParsingLiteral;
   }

   default boolean isParsingParameter() {
      return this instanceof ParsingParameter;
   }

   default boolean isExecutingFunction() {
      return this instanceof ExecutingFunctionContext;
   }

   public interface UnknownCommand<A extends CommandActor> extends ErrorContext<A> {
      @Contract("-> null")
      default @Nullable ExecutionContext<A> context() {
         return null;
      }
   }

   public interface ExecutingFunction<A extends CommandActor> extends ErrorContext<A> {
      @NotNull ExecutionContext<A> context();
   }

   public interface ParsingLiteral<A extends CommandActor> extends ErrorContext<A> {
      @NotNull LiteralNode<A> literal();

      @NotNull ExecutionContext<A> context();
   }

   public interface ParsingParameter<A extends CommandActor> extends ErrorContext<A> {
      @NotNull ParameterNode<A, ?> parameter();

      @NotNull ExecutionContext<A> context();
   }

   public interface UnknownParameter<A extends CommandActor> extends ErrorContext<A> {
      @NotNull ExecutionContext<A> context();
   }
}
