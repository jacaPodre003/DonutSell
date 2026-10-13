package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;

public class SendMessageException extends SendableException {
   public SendMessageException() {
   }

   public SendMessageException(String message) {
      super(message);
   }

   public void sendTo(@NotNull CommandActor actor) {
      if (!this.getMessage().isEmpty()) {
         actor.reply(this.getMessage());
      }
   }
}
