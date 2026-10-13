package revxrsal.commands.bukkit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.bukkit.permissions.PermissionDefault;
import revxrsal.commands.annotation.DistributeOnMethods;
import revxrsal.commands.annotation.NotSender;

@DistributeOnMethods
@NotSender.ImpliesNotSender
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandPermission {
   String value();

   PermissionDefault defaultAccess() default PermissionDefault.OP;
}
