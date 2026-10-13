package revxrsal.commands.parameter.builtins;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.EnumNotFoundException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.parameter.PrioritySpec;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.util.Classes;

@Internal
public enum EnumParameterTypeFactory implements ParameterType.Factory<CommandActor> {
   INSTANCE;

   public <T> ParameterType<CommandActor, T> create(@NotNull Type parameterType, @NotNull AnnotationList annotations, @NotNull Lamp<CommandActor> lamp) {
      Class<?> rawType = Classes.getRawType(parameterType);
      if (!rawType.isEnum()) {
         return null;
      } else {
         Enum<?>[] enumConstants = (Enum[])rawType.getEnumConstants();
         Map<String, Enum<?>> byKeys = new HashMap();
         List<String> suggestions = new ArrayList();

         for(Enum<?> enumConstant : enumConstants) {
            String name = enumConstant.name().toLowerCase();
            byKeys.put(name, enumConstant);
            suggestions.add(name);
         }

         return new EnumParameterType(rawType, byKeys, suggestions);
      }
   }

   private static final class EnumParameterType<E extends Enum<E>> implements ParameterType<CommandActor, E> {
      private final Class<E> enumType;
      private final Map<String, E> byKeys;
      private final List<String> suggestions;

      private EnumParameterType(Class<E> enumType, Map<String, E> byKeys, List<String> suggestions) {
         this.enumType = enumType;
         this.byKeys = byKeys;
         this.suggestions = suggestions;
      }

      public E parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<CommandActor> context) {
         String key = input.readUnquotedString();
         E value = (E)(this.byKeys.get(key.toLowerCase()));
         if (value != null) {
            return value;
         } else {
            throw new EnumNotFoundException(key, this.enumType);
         }
      }

      public @NotNull SuggestionProvider<CommandActor> defaultSuggestions() {
         return SuggestionProvider.of(this.suggestions);
      }

      public @NotNull PrioritySpec parsePriority() {
         return PrioritySpec.highest();
      }

      public Map<String, E> byKeys() {
         return this.byKeys;
      }

      public List<String> suggestions() {
         return this.suggestions;
      }

      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else if (obj != null && obj.getClass() == this.getClass()) {
            EnumParameterType that = (EnumParameterType)obj;
            return Objects.equals(this.byKeys, that.byKeys) && Objects.equals(this.suggestions, that.suggestions);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.byKeys, this.suggestions});
      }

      public String toString() {
         return "EnumParameterType[byKeys=" + this.byKeys + ", suggestions=" + this.suggestions + ']';
      }
   }
}
