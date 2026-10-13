package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;

public class CommandErrorException extends SendableException {
   public CommandErrorException() {
   }

   public CommandErrorException(String message, Object... arguments) {
      super(message);
   }

   public void sendTo(@NotNull CommandActor actor) {
      if (!this.getMessage().isEmpty()) {
         actor.error(this.getMessage());
      }
   }
}
