package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;

public abstract class InvalidNumberException extends InvalidValueException {
   public InvalidNumberException(@NotNull String input) {
      super(input);
   }
}
