package revxrsal.commands.bukkit.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.exception.InvalidValueException;

public class NonPlayerEntitiesException extends InvalidValueException {
   public NonPlayerEntitiesException(@NotNull String input) {
      super(input);
   }
}
