package revxrsal.commands.bukkit.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.exception.InvalidValueException;

public class MoreThanOneEntityException extends InvalidValueException {
   public MoreThanOneEntityException(@NotNull String input) {
      super(input);
   }
}
