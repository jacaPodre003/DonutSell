package revxrsal.commands.node;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;

public interface LiteralNode<A extends CommandActor> extends CommandNode<A> {
   default boolean isLiteral() {
      return true;
   }

   @Contract(
      value = "-> this",
      pure = true
   )
   default @NotNull LiteralNode<A> requireLiteralNode() {
      return this;
   }

   default boolean isParameter() {
      return false;
   }

   default @NotNull String representation() {
      return this.name();
   }

   @Contract("-> fail")
   default <T> @NotNull ParameterNode<A, T> requireParameterNode() {
      throw new IllegalStateException("Expected a ParameterNode, found a LiteralNode");
   }
}
