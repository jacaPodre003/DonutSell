package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.LiteralNode;

public class ExpectedLiteralException extends InvalidValueException {
   private final @NotNull LiteralNode<CommandActor> node;

   public ExpectedLiteralException(@NotNull String input, @NotNull LiteralNode<CommandActor> node) {
      super(input);
      this.node = node;
   }

   public <A extends CommandActor> @NotNull LiteralNode<A> node() {
      return this.node;
   }
}
