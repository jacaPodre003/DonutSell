package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;

@ThrowableFromCommand
public class UnknownParameterException extends RuntimeException {
   private final @NotNull String name;
   private final boolean shorthand;

   public UnknownParameterException(@NotNull String name, boolean shorthand) {
      this.name = name;
      this.shorthand = shorthand;
   }

   public @NotNull String name() {
      return this.name;
   }

   public boolean shorthand() {
      return this.shorthand;
   }
}
