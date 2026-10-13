package revxrsal.commands.reflect;

import java.lang.reflect.Method;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.reflect.ktx.KotlinConstants;

final class DefaultMethodCallerFactory implements MethodCallerFactory {
   public static final DefaultMethodCallerFactory INSTANCE = new DefaultMethodCallerFactory();

   public @NotNull MethodCaller createFor(@NotNull Method method) throws Throwable {
      return KotlinConstants.isKotlinClass(method.getDeclaringClass()) ? MethodCallerFactory.kotlinFunctions().createFor(method) : MethodCallerFactory.methodHandles().createFor(method);
   }
}
