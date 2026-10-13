package revxrsal.commands.node;

import java.lang.reflect.Parameter;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.util.BuiltInNamingStrategies;

@FunctionalInterface
public interface ParameterNamingStrategy {
   static ParameterNamingStrategy identity() {
      return Parameter::getName;
   }

   static ParameterNamingStrategy lowerCaseWithSeparator(String separator) {
      return (parameter) -> BuiltInNamingStrategies.separateCamelCase(parameter.getName(), separator).toLowerCase(Locale.ENGLISH);
   }

   static ParameterNamingStrategy lowerCaseWithSpace() {
      return lowerCaseWithSeparator(" ");
   }

   static ParameterNamingStrategy upperCamelCase() {
      return (parameter) -> BuiltInNamingStrategies.upperCaseFirstLetter(parameter.getName());
   }

   static ParameterNamingStrategy upperCamelCaseWithSpace() {
      return upperCamelCaseWithSeparator(" ");
   }

   static ParameterNamingStrategy upperCamelCaseWithSeparator(String separator) {
      return (parameter) -> BuiltInNamingStrategies.upperCaseFirstLetter(BuiltInNamingStrategies.separateCamelCase(parameter.getName(), separator));
   }

   @NotNull String getName(@NotNull Parameter var1);
}
