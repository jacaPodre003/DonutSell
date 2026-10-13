package revxrsal.commands.bukkit.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.exception.InvalidValueException;

public class InvalidPlayerException extends InvalidValueException {
   public InvalidPlayerException(@NotNull String input) {
      super(input);
   }
}
