package revxrsal.commands.parameter.builtins;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.Values;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.ValueNotAllowedException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.parameter.PrioritySpec;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.util.Collections;

@Internal
public enum ValuesParameterTypeFactory implements ParameterType.Factory<CommandActor> {
   INSTANCE;

   public <T> ParameterType<CommandActor, T> create(@NotNull Type parameterType, @NotNull AnnotationList annotations, @NotNull Lamp<CommandActor> lamp) {
      final Values values = (Values)annotations.get(Values.class);
      if (values == null) {
         return null;
      } else {
         final ParameterType<CommandActor, Object> delegate = lamp.findNextResolver(parameterType, annotations, this).requireParameterType();
         final List<String> allowed = values.caseSensitive() ? Arrays.asList(values.value()) : Collections.map(values.value(), String::toUpperCase);
         if (allowed.isEmpty()) {
            throw new IllegalArgumentException("@Values() must contain at least 1 value!");
         } else {
            return new ParameterType<CommandActor, T>() {
               public T parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<@NotNull CommandActor> context) {
                  int start = input.position();
                  T value = (T)delegate.parse(input, context);
                  int end = input.position();
                  input.setPosition(start);
                  String consumed = input.peek(end - start);
                  input.setPosition(end);
                  if ((!values.caseSensitive() || !allowed.contains(consumed)) && (values.caseSensitive() || !allowed.contains(consumed.toUpperCase()))) {
                     throw new ValueNotAllowedException(consumed, Arrays.asList(values.value()), values.caseSensitive());
                  } else {
                     return value;
                  }
               }

               public @NotNull SuggestionProvider<@NotNull CommandActor> defaultSuggestions() {
                  return SuggestionProvider.of(values.value());
               }

               public @NotNull PrioritySpec parsePriority() {
                  return PrioritySpec.highest();
               }
            };
         }
      }
   }
}
