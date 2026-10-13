package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;

public class InvalidUUIDException extends InvalidValueException {
   public InvalidUUIDException(@NotNull String input) {
      super(input);
   }
}
