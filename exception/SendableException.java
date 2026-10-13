package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;

@ThrowableFromCommand
public abstract class SendableException extends RuntimeException {
   public SendableException() {
      this("");
   }

   public SendableException(String message) {
      super(message);
   }

   public abstract void sendTo(@NotNull CommandActor var1);
}
