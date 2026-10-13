package revxrsal.commands.bukkit.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.exception.InvalidValueException;

public class MissingLocationParameterException extends InvalidValueException {
   private final @NotNull MissingAxis missingAxis;

   public MissingLocationParameterException(@NotNull String input, @NotNull MissingAxis missingAxis) {
      super(input);
      this.missingAxis = missingAxis;
   }

   public @NotNull MissingAxis axis() {
      return this.missingAxis;
   }

   public static enum MissingAxis {
      X,
      Y,
      Z;
   }
}
