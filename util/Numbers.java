package revxrsal.commands.util;

import org.jetbrains.annotations.NotNull;

public final class Numbers {
   private Numbers() {
      Preconditions.cannotInstantiate(Numbers.class);
   }

   public static @NotNull Number getMinValue(@NotNull Class<?> type) {
      type = Classes.wrap(type);
      if (type == Byte.class) {
         return -128;
      } else if (type == Short.class) {
         return Short.MIN_VALUE;
      } else if (type == Integer.class) {
         return Integer.MIN_VALUE;
      } else if (type == Long.class) {
         return Long.MIN_VALUE;
      } else if (type == Float.class) {
         return Float.MIN_VALUE;
      } else if (type == Double.class) {
         return Double.MIN_VALUE;
      } else {
         throw new IllegalArgumentException("Unsupported type: " + type.getName());
      }
   }

   public static @NotNull Number getMaxValue(@NotNull Class<?> type) {
      type = Classes.wrap(type);
      if (type == Byte.class) {
         return 127;
      } else if (type == Short.class) {
         return 32767;
      } else if (type == Integer.class) {
         return Integer.MAX_VALUE;
      } else if (type == Long.class) {
         return Long.MAX_VALUE;
      } else if (type == Float.class) {
         return Float.MAX_VALUE;
      } else if (type == Double.class) {
         return Double.MAX_VALUE;
      } else {
         throw new IllegalArgumentException("Unsupported type: " + type.getName());
      }
   }

   public static boolean isDecimal(@NotNull Class<?> type) {
      type = Classes.wrap(type);
      return type == Float.class || type == Double.class;
   }
}
