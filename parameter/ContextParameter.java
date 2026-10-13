package revxrsal.commands.parameter;

import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.builtins.ClassContextParameterFactory;

@FunctionalInterface
public interface ContextParameter<A extends CommandActor, T> {
   T resolve(@NotNull CommandParameter var1, @NotNull ExecutionContext<A> var2);

   @FunctionalInterface
   public interface Factory<A extends CommandActor> extends ParameterFactory {
      static <A extends CommandActor, T> @NotNull Factory<A> forType(@NotNull Class<T> type, @NotNull ContextParameter<A, T> parameterType) {
         return new ClassContextParameterFactory(type, parameterType, false);
      }

      static <A extends CommandActor, T> @NotNull Factory<A> forTypeAndSubclasses(@NotNull Class<T> type, @NotNull ContextParameter<A, T> parameterType) {
         return new ClassContextParameterFactory(type, parameterType, true);
      }

      <T> @Nullable ContextParameter<A, T> create(@NotNull Type var1, @NotNull AnnotationList var2, @NotNull Lamp<A> var3);
   }
}
