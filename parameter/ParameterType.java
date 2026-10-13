package revxrsal.commands.parameter;

import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.builtins.ClassParameterTypeFactory;
import revxrsal.commands.stream.MutableStringStream;

@FunctionalInterface
public interface ParameterType<A extends CommandActor, T> extends BaseParameterType {
   T parse(@NotNull MutableStringStream var1, @NotNull ExecutionContext<@NotNull A> var2);

   default @NotNull SuggestionProvider<@NotNull A> defaultSuggestions() {
      return SuggestionProvider.<A>empty();
   }

   default @NotNull PrioritySpec parsePriority() {
      return PrioritySpec.defaultPriority();
   }

   default boolean isGreedy() {
      return false;
   }

   public interface Factory<A extends CommandActor> extends ParameterFactory, BaseParameterType {
      static <A extends CommandActor, T> @NotNull Factory<A> forType(@NotNull Class<T> type, @NotNull ParameterType<A, T> parameterType) {
         return new ClassParameterTypeFactory(type, parameterType, false);
      }

      static <A extends CommandActor, T> @NotNull Factory<A> forTypeAndSubclasses(@NotNull Class<T> type, @NotNull ParameterType<A, T> parameterType) {
         return new ClassParameterTypeFactory(type, parameterType, true);
      }

      <T> @Nullable ParameterType<A, T> create(@NotNull Type var1, @NotNull AnnotationList var2, @NotNull Lamp<A> var3);
   }
}
