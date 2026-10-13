package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;

@ThrowableFromCommand
public final class InputParseException extends RuntimeException {
   private final @NotNull Cause cause;

   public InputParseException(@NotNull Cause cause) {
      this.cause = cause;
   }

   public @NotNull Cause cause() {
      return this.cause;
   }

   public static enum Cause {
      INVALID_ESCAPE_CHARACTER,
      UNCLOSED_QUOTE,
      EXPECTED_WHITESPACE;
   }
}
