package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;

public class EnumNotFoundException extends InvalidValueException {
   private final Class<? extends Enum> enumType;

   public EnumNotFoundException(@NotNull String input, Class<? extends Enum> enumType) {
      super(input);
      this.enumType = enumType;
   }

   public Class<? extends Enum> enumType() {
      return this.enumType;
   }
}
