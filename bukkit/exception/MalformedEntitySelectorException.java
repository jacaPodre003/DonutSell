package revxrsal.commands.bukkit.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.exception.InvalidValueException;

public class MalformedEntitySelectorException extends InvalidValueException {
   private final @NotNull String errorMessage;

   public MalformedEntitySelectorException(String input, @NotNull String errorMessage) {
      super(input);
      this.errorMessage = errorMessage;
   }

   public @NotNull String errorMessage() {
      return this.errorMessage;
   }
}
