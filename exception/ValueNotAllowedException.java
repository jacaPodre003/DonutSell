package revxrsal.commands.exception;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public class ValueNotAllowedException extends InvalidValueException {
   private final @Unmodifiable List<String> allowedValues;
   private final boolean caseSensitive;

   public ValueNotAllowedException(@NotNull String input, @NotNull @Unmodifiable List<String> allowedValues, boolean caseSensitive) {
      super(input);
      this.allowedValues = allowedValues;
      this.caseSensitive = caseSensitive;
   }

   public boolean caseSensitive() {
      return this.caseSensitive;
   }

   public @Unmodifiable @NotNull List<String> allowedValues() {
      return this.allowedValues;
   }
}
