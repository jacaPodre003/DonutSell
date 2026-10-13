package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;

public class InvalidDecimalException extends InvalidNumberException {
   public InvalidDecimalException(@NotNull String input) {
      super(input);
   }
}
