package revxrsal.commands.reflect.ktx;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.util.Lazy;

public final class KotlinConstants {
   public static final Object ABSENT_VALUE = new Object();
   private static final Supplier<Class<?>> CONTINUATION = Lazy.<Class<?>>of(() -> findClass("kotlin.coroutines.Continuation"));
   private static final Supplier<Class<? extends Annotation>> METADATA = Lazy.<Class<? extends Annotation>>of(() -> {
      Class<?> metadata = findClass("kotlin.Metadata");
      return metadata == null ? null : metadata.asSubclass(Annotation.class);
   });

   private KotlinConstants() {
   }

   private static @Nullable Class<?> findClass(@NotNull String name) {
      try {
         return Class.forName(name);
      } catch (ClassNotFoundException var2) {
         return null;
      }
   }

   public static boolean isStaticFinal(int modifiers) {
      return Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
   }

   public static @Nullable Object defaultPrimitiveValue(Class<?> type) {
      if (type == Integer.TYPE) {
         return 0;
      } else if (type == Long.TYPE) {
         return 0L;
      } else if (type == Float.TYPE) {
         return 0.0F;
      } else if (type == Double.TYPE) {
         return (double)0.0F;
      } else if (type == Short.TYPE) {
         return 0;
      } else if (type == Byte.TYPE) {
         return 0;
      } else if (type == Boolean.TYPE) {
         return false;
      } else if (type == Character.TYPE) {
         return '\u0000';
      } else {
         return type == Optional.class ? Optional.empty() : null;
      }
   }

   public static @Nullable Class<?> continuation() {
      return (Class)CONTINUATION.get();
   }

   public static boolean isKotlinClass(@NotNull Class<?> cl) {
      return METADATA.get() != null && cl.isAnnotationPresent((Class)METADATA.get());
   }
}
