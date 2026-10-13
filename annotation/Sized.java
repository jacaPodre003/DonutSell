package revxrsal.commands.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@NotSender.ImpliesNotSender
public @interface Sized {
   @org.jetbrains.annotations.Range(
   from = 0L,
   to = 2147483647L
) int min() default 0;

   @org.jetbrains.annotations.Range(
   from = 0L,
   to = 2147483647L
) int max() default Integer.MAX_VALUE;
}
