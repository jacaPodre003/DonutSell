package revxrsal.commands.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InstanceCreator {
   private InstanceCreator() {
      Preconditions.cannotInstantiate(InstanceCreator.class);
   }

   public static <T> @NotNull T create(@NotNull Class<? extends T> type) {
      if (type.isAnnotation()) {
         throw new IllegalArgumentException("Cannot construct annotation types");
      } else if (type.isArray()) {
         return (T)Array.newInstance(type, 0);
      } else if (type.isEnum()) {
         return (T)firstEnum(type.asSubclass(Enum.class));
      } else {
         if (type.isInterface()) {
            T singleton = (T)fromSingletonField(type);
            if (singleton != null) {
               return singleton;
            }

            singleton = (T)fromGetter(type);
            if (singleton == null) {
               throw new IllegalArgumentException("Attempted to construct an interface that has no getInstance()-like methods or INSTANCE-like fields");
            }
         }

         T t = (T)fromNoArgConstructor(type);
         if (t == null) {
            t = (T)fromSingletonField(type);
         }

         if (t != null) {
            return t;
         } else {
            t = (T)fromGetter(type);
            return t;
         }
      }
   }

   private static <T> @Nullable T fromNoArgConstructor(Class<? extends T> type) {
      try {
         Constructor<? extends T> constructor = type.getDeclaredConstructor();
         constructor.setAccessible(true);
         return (T)constructor.newInstance();
      } catch (ReflectiveOperationException var2) {
         return null;
      }
   }

   private static <T> T fromGetter(Class<? extends T> type) {
      for(Method method : type.getDeclaredMethods()) {
         if (type.isAssignableFrom(method.getReturnType()) && Modifier.isStatic(method.getModifiers()) && method.getParameterCount() == 0) {
            method.setAccessible(true);

            try {
               return (T)method.invoke((Object)null);
            } catch (InvocationTargetException | IllegalAccessException e) {
               throw new RuntimeException(e);
            }
         }
      }

      return null;
   }

   private static <T extends Enum> @NotNull T firstEnum(Class<? extends T> type) {
      T[] values = (T[])(type.getEnumConstants());
      if (values.length == 0) {
         throw new IllegalArgumentException("Attempted to construct an enum that has no fields");
      } else {
         return (T)values[0];
      }
   }

   private static <T> @Nullable T fromSingletonField(Class<? extends T> type) {
      for(Field field : type.getDeclaredFields()) {
         if (type.isAssignableFrom(field.getType()) && Modifier.isStatic(field.getModifiers())) {
            field.setAccessible(true);

            try {
               return (T)field.get((Object)null);
            } catch (IllegalAccessException e) {
               throw new RuntimeException(e);
            }
         }
      }

      return null;
   }
}
