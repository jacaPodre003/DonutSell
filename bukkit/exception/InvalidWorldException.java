package revxrsal.commands.bukkit.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.exception.InvalidValueException;

public class InvalidWorldException extends InvalidValueException {
   public InvalidWorldException(@NotNull String input) {
      super(input);
   }
}
