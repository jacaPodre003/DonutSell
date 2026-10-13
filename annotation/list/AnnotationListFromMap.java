package revxrsal.commands.annotation.list;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import revxrsal.commands.annotation.DistributeOnMethods;
import revxrsal.commands.annotation.dynamic.AnnotationReplacer;
import revxrsal.commands.util.Classes;

final class AnnotationListFromMap implements AnnotationList {
   private final Map<Class<? extends Annotation>, Annotation> annotations;

   public AnnotationListFromMap(Map<Class<? extends Annotation>, Annotation> annotations) {
      this.annotations = annotations;
   }

   public static @NotNull AnnotationList createFrom(@NotNull Collection<Annotation> annotations) {
      return (AnnotationList)(annotations.isEmpty() ? AnnotationList.empty() : new AnnotationListFromMap(toMap(annotations)));
   }

   public static @NotNull AnnotationList createFor(@NotNull AnnotatedElement element) {
      Map<Class<? extends Annotation>, Annotation> annotations = toMap(element.getAnnotations());
      return (AnnotationList)(annotations.isEmpty() ? AnnotationList.empty() : new AnnotationListFromMap(annotations));
   }

   public static @NotNull Map<Class<? extends Annotation>, Annotation> toMap(@NotNull Iterable<Annotation> annotations) {
      Map<Class<? extends Annotation>, Annotation> map = new LinkedHashMap();

      for(Annotation annotation : annotations) {
         map.put(annotation.annotationType(), annotation);
      }

      return map;
   }

   public static @NotNull Map<Class<? extends Annotation>, Annotation> toMap(@NotNull Annotation[] annotations) {
      Map<Class<? extends Annotation>, Annotation> map = new LinkedHashMap();

      for(Annotation annotation : annotations) {
         map.put(annotation.annotationType(), annotation);
      }

      return map;
   }

   private static void distributeAnnotations(@NotNull Map<Class<? extends Annotation>, Annotation> annotations, @NotNull Method element, @NotNull Map<Class<? extends Annotation>, Set<AnnotationReplacer<?>>> replacers) {
      for(Class<?> top = element.getDeclaringClass(); top != null; top = top.getDeclaringClass()) {
         for(Annotation annotation : AnnotationList.create(top).replaceAnnotations(top, replacers)) {
            if (annotation.annotationType().isAnnotationPresent(DistributeOnMethods.class)) {
               annotations.putIfAbsent(annotation.annotationType(), annotation);
            }
         }
      }

   }

   public <T extends Annotation> @Nullable T get(@NotNull Class<T> type) {
      Classes.checkRetention(type);
      return (T)(this.annotations.get(type));
   }

   public <R, T extends Annotation> @Nullable R map(@NotNull Class<T> type, Function<T, R> function) {
      T annotation = this.get(type);
      return (R)(annotation != null ? function.apply(annotation) : null);
   }

   public <R, T extends Annotation> R mapOr(@NotNull Class<T> type, Function<T, R> function, R defaultValue) {
      T annotation = this.get(type);
      return (R)(annotation != null ? function.apply(annotation) : defaultValue);
   }

   public <R, T extends Annotation> R mapOrGet(@NotNull Class<T> type, @NotNull Function<T, R> function, @NotNull Supplier<R> defaultValue) {
      T annotation = this.get(type);
      return (R)(annotation != null ? function.apply(annotation) : defaultValue.get());
   }

   public <T extends Annotation> @NotNull T require(@NotNull Class<T> type, @NotNull String errorMessage) {
      T annotation = this.get(type);
      return annotation;
   }

   public <T extends Annotation> boolean contains(@NotNull Class<T> type) {
      Classes.checkRetention(type);
      return this.annotations.containsKey(type);
   }

   public @NotNull AnnotationList replaceAnnotations(@NotNull AnnotatedElement element, @NotNull Map<Class<? extends Annotation>, Set<AnnotationReplacer<?>>> replacers) {
      Map<Class<? extends Annotation>, Annotation> annotations = new LinkedHashMap(this.annotations);

      for(Annotation annotation : this.annotations.values()) {
         for(AnnotationReplacer replacer : (Set)replacers.getOrDefault(annotation.annotationType(), Collections.emptySet())) {
            Collection<Annotation> newAnnotations = replacer.replaceAnnotation(element, annotation);
            if (newAnnotations != null) {
               annotations.putAll(toMap(newAnnotations));
            }
         }
      }

      if (element instanceof Method) {
         Method method = (Method)element;
         distributeAnnotations(annotations, method, replacers);
      }

      return new AnnotationListFromMap(annotations);
   }

   public @NotNull Map<Class<?>, Annotation> toMutableMap() {
      return new LinkedHashMap(this.annotations);
   }

   public boolean isEmpty() {
      return this.annotations.isEmpty();
   }

   public @NotNull @UnmodifiableView Iterator<Annotation> iterator() {
      return revxrsal.commands.util.Collections.<Annotation>unmodifiableIterator(this.annotations.values().iterator());
   }

   public boolean any(@NotNull Predicate<Annotation> predicate) {
      for(Annotation value : this.annotations.values()) {
         if (predicate.test(value)) {
            return true;
         }
      }

      return false;
   }

   @NotNull
   public AnnotationList withAnnotations(boolean overrideExisting, @NotNull Annotation... annotations) {
      LinkedHashMap<Class<? extends Annotation>, Annotation> map = new LinkedHashMap(this.annotations);

      for(Annotation annotation : annotations) {
         if (overrideExisting) {
            map.put(annotation.annotationType(), annotation);
         } else {
            map.putIfAbsent(annotation.annotationType(), annotation);
         }
      }

      return new AnnotationListFromMap(map);
   }
}
