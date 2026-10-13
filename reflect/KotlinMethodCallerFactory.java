package revxrsal.commands.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Switch;
import revxrsal.commands.reflect.ktx.KotlinConstants;
import revxrsal.commands.reflect.ktx.KotlinFunction;

final class KotlinMethodCallerFactory implements MethodCallerFactory {
   public static final KotlinMethodCallerFactory INSTANCE = new KotlinMethodCallerFactory();

   public @NotNull MethodCaller createFor(@NotNull Method method) {
      KotlinFunction function = KotlinFunction.wrap(method);
      return (instance, arguments) -> {
         List<Object> list = new ArrayList();
         Collections.addAll(list, arguments);
         list.replaceAll((v) -> v == KotlinConstants.ABSENT_VALUE ? null : v);
         return function.call(instance, list, (parameter) -> parameter.isAnnotationPresent(Optional.class) || parameter.isAnnotationPresent(Switch.class));
      };
   }
}
