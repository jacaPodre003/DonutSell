package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;

public class InvalidIntegerException extends InvalidNumberException {
   public InvalidIntegerException(@NotNull String input) {
      super(input);
   }
}
