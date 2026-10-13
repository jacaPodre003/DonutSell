package revxrsal.commands.annotation.list;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.annotation.dynamic.AnnotationReplacer;

public interface AnnotationList extends Iterable<Annotation> {
   static @NotNull AnnotationList create(@NotNull AnnotatedElement element) {
      return AnnotationListFromMap.createFor(element);
   }

   static @NotNull AnnotationList create(@NotNull Collection<Annotation> annotations) {
      return AnnotationListFromMap.createFrom(annotations);
   }

   static @NotNull AnnotationList create(@NotNull Map<Class<? extends Annotation>, Annotation> annotations) {
      return (AnnotationList)(annotations.isEmpty() ? empty() : new AnnotationListFromMap(annotations));
   }

   @Contract(
      pure = true
   )
   static @NotNull AnnotationList empty() {
      return EmptyAnnotationList.INSTANCE;
   }

   @Contract(
      pure = true
   )
   <T extends Annotation> @Nullable T get(@NotNull Class<T> var1);

   @Contract(
      pure = true
   )
   <R, T extends Annotation> @Nullable R map(@NotNull Class<T> var1, Function<T, @Nullable R> var2);

   @Contract(
      pure = true
   )
   <R, T extends Annotation> R mapOr(@NotNull Class<T> var1, Function<T, R> var2, R var3);

   @Contract(
      pure = true
   )
   <R, T extends Annotation> R mapOrGet(@NotNull Class<T> var1, @NotNull Function<T, R> var2, @NotNull Supplier<R> var3);

   @Contract(
      pure = true
   )
   <T extends Annotation> @NotNull T require(@NotNull Class<T> var1, @NotNull String var2);

   @Contract(
      pure = true
   )
   <T extends Annotation> boolean contains(@NotNull Class<T> var1);

   boolean isEmpty();

   @Contract(
      value = "-> new",
      pure = true
   )
   @NotNull Map<Class<?>, Annotation> toMutableMap();

   @Contract(
      pure = true
   )
   @NotNull AnnotationList replaceAnnotations(@NotNull AnnotatedElement var1, @NotNull Map<Class<? extends Annotation>, Set<AnnotationReplacer<?>>> var2);

   @Contract(
      pure = true
   )
   @NotNull @Unmodifiable Iterator<Annotation> iterator();

   @Contract(
      pure = true,
      value = "_, _ -> new"
   )
   @NotNull AnnotationList withAnnotations(boolean var1, Annotation... var2);

   @Contract(
      pure = true,
      value = "_ -> new"
   )
   default @NotNull AnnotationList withAnnotations(Annotation... annotations) {
      return this.withAnnotations(true, annotations);
   }

   @Contract(
      pure = true
   )
   boolean any(@NotNull Predicate<Annotation> var1);
}
