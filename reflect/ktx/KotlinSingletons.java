package revxrsal.commands.reflect.ktx;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.reflect.MethodCaller;
import revxrsal.commands.reflect.MethodCallerFactory;
import revxrsal.commands.util.Collections;
import revxrsal.commands.util.Preconditions;

public final class KotlinSingletons {
   private static final String COMPANION_NAME = "Companion";

   private KotlinSingletons() {
   }

   public static @NotNull MethodCaller getCallerForNonDefault(@NotNull Method method) {
      Class<?>[] parameterTypes = method.getParameterTypes();
      MethodCaller caller = wrapMethod(method);
      boolean isStatic = Modifier.isStatic(method.getModifiers());
      if (isStatic) {
         return caller;
      } else {
         return parameterTypes.length > 0 && parameterTypes[0] == method.getDeclaringClass() ? (instance, arguments) -> {
            Object[] boundArgs = Collections.insertAtBeginning(arguments, instance);
            return caller.call(instance, boundArgs);
         } : caller;
      }
   }

   public static @Nullable Object findCompanion(Class<?> type) {
      Map<String, Field> fields = (Map)Arrays.stream(type.getDeclaredFields()).filter((field) -> Modifier.isPublic(field.getModifiers()) && KotlinConstants.isStaticFinal(field.getModifiers())).collect(Collectors.toMap(Field::getName, (f) -> f));

      try {
         Class<?> companion = Class.forName(type.getName() + "$" + "Companion");
         Field companionField = (Field)fields.get(companion.getSimpleName());
         if (companionField != null) {
            return fetch(companionField);
         }
      } catch (ClassNotFoundException var9) {
      }

      for(Class<?> declaredClass : type.getDeclaredClasses()) {
         String name = declaredClass.getSimpleName();
         Field companionField = (Field)fields.get(name);
         if (companionField != null) {
            Object singleton = fetch(companionField);
            if (singleton != null) {
               return singleton;
            }
         }
      }

      return null;
   }

   private static void makeAccessible(@NotNull AccessibleObject accessibleObject) {
      if (!accessibleObject.isAccessible()) {
         accessibleObject.setAccessible(true);
      }

   }

   private static Object fetch(@NotNull Field field) {
      try {
         makeAccessible(field);
         return field.get((Object)null);
      } catch (IllegalAccessException e) {
         throw new IllegalStateException("Failed to access the field", e);
      }
   }

   static MethodCaller wrapMethod(@NotNull Method method) {
      try {
         Preconditions.notNull(method, "method");
         return MethodCallerFactory.methodHandles().createFor(method);
      } catch (Throwable $ex) {
         throw $ex;
      }
   }
}
