package revxrsal.commands.bukkit.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.exception.InvalidValueException;

public class EmptyEntitySelectorException extends InvalidValueException {
   public EmptyEntitySelectorException(@NotNull String input) {
      super(input);
   }
}
