package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;

@ThrowableFromCommand
public abstract class InvalidValueException extends RuntimeException {
   private final @NotNull String input;

   public InvalidValueException(@NotNull String input) {
      this.input = input;
   }

   public @NotNull String input() {
      return this.input;
   }
}
