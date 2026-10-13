package revxrsal.commands.node;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.stream.MutableStringStream;

public interface CommandNode<A extends CommandActor> extends Comparable<CommandNode<A>> {
   @NotNull Lamp<A> lamp();

   @NotNull ExecutableCommand<A> command();

   @NotNull String name();

   @Contract(
      pure = true
   )
   @Nullable CommandAction<A> action();

   boolean isLast();

   default boolean hasAction() {
      return this.action() != null;
   }

   void execute(@NotNull ExecutionContext<A> var1, @NotNull MutableStringStream var2);

   boolean isLiteral();

   @NotNull LiteralNode<A> requireLiteralNode();

   boolean isParameter();

   <T> @NotNull ParameterNode<A, T> requireParameterNode();

   @NotNull String representation();
}
