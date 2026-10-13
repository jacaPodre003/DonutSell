package revxrsal.commands.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import revxrsal.commands.autocomplete.BaseSuggestionProvider;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SuggestWith {
   Class<? extends BaseSuggestionProvider> value();
}
