package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;

@ThrowableFromCommand
public class CommandInvocationException extends RuntimeException {
   private final @NotNull Throwable cause;

   public CommandInvocationException(@NotNull Throwable cause) {
      this.cause = cause;
   }

   public synchronized Throwable getCause() {
      return super.getCause();
   }

   public @NotNull Throwable cause() {
      return this.cause;
   }
}
