package revxrsal.commands.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import revxrsal.commands.parameter.BaseParameterType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@NotSender.ImpliesNotSender
public @interface ParseWith {
   Class<? extends BaseParameterType> value();
}
