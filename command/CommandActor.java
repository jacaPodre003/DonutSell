package revxrsal.commands.command;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.process.MessageSender;

public interface CommandActor {
   @NotNull String name();

   @NotNull UUID uniqueId();

   default void reply(@NotNull String message) {
      MessageSender messageSender = this.lamp().messageSender();
      messageSender.send(this, message);
   }

   void sendRawMessage(@NotNull String var1);

   default void error(@NotNull String message) {
      MessageSender messageSender = this.lamp().errorSender();
      messageSender.send(this, message);
   }

   void sendRawError(@NotNull String var1);

   Lamp<?> lamp();
}
