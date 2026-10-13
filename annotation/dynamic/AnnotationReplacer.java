package revxrsal.commands.annotation.dynamic;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface AnnotationReplacer<T extends Annotation> {
   @Nullable Collection<Annotation> replaceAnnotation(@NotNull AnnotatedElement var1, @NotNull T var2);
}
