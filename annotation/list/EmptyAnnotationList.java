package revxrsal.commands.annotation.list;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import revxrsal.commands.annotation.dynamic.AnnotationReplacer;
import revxrsal.commands.util.Classes;

final class EmptyAnnotationList implements AnnotationList {
   public static final EmptyAnnotationList INSTANCE = new EmptyAnnotationList();

   public <T extends Annotation> @Nullable T get(@NotNull Class<T> type) {
      Classes.checkRetention(type);
      return null;
   }

   public <R, T extends Annotation> @Nullable R map(@NotNull Class<T> type, Function<T, R> function) {
      Classes.checkRetention(type);
      return null;
   }

   public <R, T extends Annotation> R mapOr(@NotNull Class<T> type, Function<T, R> function, R defaultValue) {
      Classes.checkRetention(type);
      return defaultValue;
   }

   public <R, T extends Annotation> R mapOrGet(@NotNull Class<T> type, @NotNull Function<T, R> function, @NotNull Supplier<R> defaultValue) {
      Classes.checkRetention(type);
      return (R)defaultValue.get();
   }

   public <T extends Annotation> @NotNull T require(@NotNull Class<T> type, @NotNull String errorMessage) {
      Classes.checkRetention(type);
      throw new IllegalStateException(errorMessage);
   }

   public <T extends Annotation> boolean contains(@NotNull Class<T> type) {
      Classes.checkRetention(type);
      return false;
   }

   public boolean isEmpty() {
      return true;
   }

   public @NotNull AnnotationList replaceAnnotations(@NotNull AnnotatedElement element, @NotNull Map<Class<? extends Annotation>, Set<AnnotationReplacer<?>>> replacers) {
      return this;
   }

   public @NotNull @UnmodifiableView Iterator<Annotation> iterator() {
      return Collections.emptyIterator();
   }

   public @NotNull Map<Class<?>, Annotation> toMutableMap() {
      return new HashMap();
   }

   public @NotNull AnnotationList withAnnotations(boolean overrideExisting, Annotation... annotations) {
      Map<Class<? extends Annotation>, Annotation> map = AnnotationListFromMap.toMap(annotations);
      return new AnnotationListFromMap(map);
   }

   public boolean any(@NotNull Predicate<Annotation> predicate) {
      return false;
   }
}
