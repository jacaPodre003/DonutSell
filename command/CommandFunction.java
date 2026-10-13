package revxrsal.commands.command;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.reflect.MethodCaller;
import revxrsal.commands.response.ResponseHandler;

public interface CommandFunction {
   <A extends CommandActor> Lamp<A> lamp();

   @NotNull String name();

   @NotNull AnnotationList annotations();

   @NotNull Method method();

   @NotNull @Unmodifiable Map<String, CommandParameter> parametersByName();

   @NotNull CommandParameter parameter(String var1);

   MethodCaller.@NotNull BoundMethodCaller caller();

   <T> T call(Object... var1);

   <T> @NotNull ResponseHandler<?, T> responseHandler();

   default boolean hasAnnotation(@NotNull Class<? extends Annotation> annotationType) {
      return this.annotations().contains(annotationType);
   }

   default <T extends Annotation> @Nullable T getAnnotation(@NotNull Class<T> type) {
      return (T)this.annotations().get(type);
   }
}
