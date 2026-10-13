package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;

public class UnknownCommandException extends InvalidValueException {
   public UnknownCommandException(@NotNull String input) {
      super(input);
   }
}
