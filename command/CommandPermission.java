package revxrsal.commands.command;

import java.lang.annotation.Annotation;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.util.Classes;

@FunctionalInterface
public interface CommandPermission<A extends CommandActor> extends Predicate<A> {
   static <A extends CommandActor> CommandPermission<A> alwaysTrue() {
      return (actor) -> true;
   }

   boolean isExecutableBy(@NotNull A var1);

   default boolean test(A a) {
      return this.isExecutableBy(a);
   }

   @FunctionalInterface
   public interface Factory<A extends CommandActor> {
      static <A extends CommandActor, T extends Annotation> Factory<A> forAnnotation(@NotNull Class<T> annotationType, @NotNull Function<T, @Nullable CommandPermission<A>> permissionCreator) {
         Classes.checkRetention(annotationType);
         return (annotations, lamp) -> {
            T annotation = (T)annotations.get(annotationType);
            return annotation != null ? (CommandPermission)permissionCreator.apply(annotation) : null;
         };
      }

      @Nullable CommandPermission<A> create(@NotNull AnnotationList var1, @NotNull Lamp<A> var2);
   }
}
