package revxrsal.commands.reflect;

import java.lang.reflect.Method;
import org.jetbrains.annotations.NotNull;

public interface MethodCallerFactory {
   static @NotNull MethodCallerFactory methodHandles() {
      return MethodHandlesCallerFactory.INSTANCE;
   }

   static @NotNull MethodCallerFactory kotlinFunctions() {
      return KotlinMethodCallerFactory.INSTANCE;
   }

   static @NotNull MethodCallerFactory defaultFactory() {
      return DefaultMethodCallerFactory.INSTANCE;
   }

   @NotNull MethodCaller createFor(@NotNull Method var1) throws Throwable;
}
