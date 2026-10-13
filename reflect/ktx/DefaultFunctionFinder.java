package revxrsal.commands.reflect.ktx;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.reflect.MethodCaller;
import revxrsal.commands.util.Collections;

public final class DefaultFunctionFinder {
   private DefaultFunctionFinder() {
   }

   public static @Nullable CallableMethod findDefaultFunction(@NotNull Method method) {
      try {
         if (method.getParameterCount() == 0) {
            throw new IllegalArgumentException("Method has no parameters!");
         } else {
            List<Class<?>> syntheticParams = getSyntheticParameters(method);
            String name = getDefaultMethodName(method);
            Method defaultMethod = getDeclaredMethodOrNull(name, method.getDeclaringClass(), syntheticParams);
            if (defaultMethod != null) {
               return new CallableMethod(defaultMethod, KotlinSingletons.wrapMethod(defaultMethod));
            } else {
               syntheticParams.add(0, method.getDeclaringClass());
               defaultMethod = getDeclaredMethodOrNull(name, method.getDeclaringClass(), syntheticParams);
               if (defaultMethod != null) {
                  MethodCaller callerForDefault = bindInstanceParameter(defaultMethod);
                  return new CallableMethod(defaultMethod, callerForDefault);
               } else {
                  Object companion = KotlinSingletons.findCompanion(method.getDeclaringClass());
                  if (companion == null) {
                     return null;
                  } else {
                     syntheticParams.set(0, companion.getClass());
                     defaultMethod = getDeclaredMethodOrNull(name, companion.getClass(), syntheticParams);
                     if (defaultMethod == null) {
                        return null;
                     } else {
                        MethodCaller callerForDefault = bindInstanceParameter(defaultMethod);
                        return new CallableMethod(defaultMethod, callerForDefault);
                     }
                  }
               }
            }
         }
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   private static @NotNull MethodCaller bindInstanceParameter(Method method) {
      try {
         MethodCaller caller = KotlinSingletons.wrapMethod(method);
         return (instance, arguments) -> {
            Object[] boundArgs = Collections.insertAtBeginning(arguments, instance);
            return caller.call(instance, boundArgs);
         };
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   private static @Nullable Method getDeclaredMethodOrNull(@NotNull String name, @NotNull Class<?> type, @NotNull List<Class<?>> parameterTypes) {
      try {
         return type.getDeclaredMethod(name, (Class[])parameterTypes.toArray(new Class[0]));
      } catch (NoSuchMethodException var4) {
         return null;
      }
   }

   private static @NotNull String getDefaultMethodName(@NotNull Method method) {
      return method.getName() + "$default";
   }

   private static @NotNull List<Class<?>> getSyntheticParameters(Method method) {
      List<Class<?>> parameters = new ArrayList(method.getParameterCount() + 3);
      java.util.Collections.addAll(parameters, method.getParameterTypes());
      parameters.add(Integer.TYPE);
      parameters.add(Object.class);
      return parameters;
   }
}
