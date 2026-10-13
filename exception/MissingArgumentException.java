package revxrsal.commands.exception;

import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.node.ParameterNode;

@ThrowableFromCommand
public class MissingArgumentException extends RuntimeException {
   private final ParameterNode<CommandActor, Object> node;
   private final ExecutableCommand<CommandActor> command;

   public <A extends CommandActor> MissingArgumentException(ParameterNode<A, ?> node, ExecutableCommand<A> command) {
      this.node = node;
      this.command = command;
   }

   public <A extends CommandActor> ParameterNode<A, Object> node() {
      return this.node;
   }

   public ExecutableCommand<CommandActor> command() {
      return this.command;
   }
}
