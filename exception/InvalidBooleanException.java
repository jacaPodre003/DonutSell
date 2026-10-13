package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;

public class InvalidBooleanException extends InvalidValueException {
   public InvalidBooleanException(@NotNull String input) {
      super(input);
   }
}
