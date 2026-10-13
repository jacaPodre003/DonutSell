package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;

@ThrowableFromCommand
public class NumberNotInRangeException extends RuntimeException {
   private final @NotNull Number input;
   private final double minimum;
   private final double maximum;

   public NumberNotInRangeException(@NotNull Number input, double minimum, double maximum) {
      this.input = input;
      this.minimum = minimum;
      this.maximum = maximum;
   }

   public @NotNull Number input() {
      return this.input;
   }

   public double minimum() {
      return this.minimum;
   }

   public double maximum() {
      return this.maximum;
   }
}
