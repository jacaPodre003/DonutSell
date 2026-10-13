package revxrsal.commands.command;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.annotation.list.AnnotationList;

public interface CommandParameter {
   @NotNull String name();

   int methodIndex();

   boolean isLastInMethod();

   @NotNull Parameter parameter();

   @NotNull Method method();

   @NotNull AnnotationList annotations();

   @NotNull Class<?> type();

   @NotNull Type fullType();

   @NotNull List<Type> generics();

   boolean isOptional();

   default boolean hasAnnotation(@NotNull Class<? extends Annotation> annotationType) {
      return this.annotations().contains(annotationType);
   }

   default <T extends Annotation> @Nullable T getAnnotation(@NotNull Class<T> type) {
      return (T)this.annotations().get(type);
   }
}
