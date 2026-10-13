package revxrsal.commands.bukkit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.DistributeOnMethods;

@DistributeOnMethods
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface FallbackPrefix {
   @NotNull String value();
}
