package revxrsal.commands.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class MethodHandlesCallerFactory implements MethodCallerFactory {
   public static final MethodHandlesCallerFactory INSTANCE = new MethodHandlesCallerFactory();

   public @NotNull MethodCaller createFor(@NotNull Method method) throws Throwable {
      if (!method.isAccessible()) {
         method.setAccessible(true);
      }

      final MethodHandle handle = MethodHandles.lookup().unreflect(method);
      final String methodString = method.toString();
      final boolean isStatic = Modifier.isStatic(method.getModifiers());
      return new MethodCaller() {
         public Object call(@Nullable Object instance, Object... arguments) {
            try {
               if (!isStatic) {
                  List<Object> args = new ArrayList();
                  args.add(instance);
                  Collections.addAll(args, arguments);
                  return handle.invokeWithArguments(args);
               } else {
                  return handle.invokeWithArguments(arguments);
               }
            } catch (Throwable $ex) {
               throw $ex;
            }
         }

         public String toString() {
            return "MethodHandlesCaller(" + methodString + ")";
         }
      };
   }

   public String toString() {
      return "MethodHandlesCallerFactory";
   }
}
