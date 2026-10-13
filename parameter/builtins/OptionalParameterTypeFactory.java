package revxrsal.commands.parameter.builtins;

import java.lang.reflect.Type;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.util.Classes;

public enum OptionalParameterTypeFactory implements ParameterType.Factory<CommandActor> {
   INSTANCE;

   public <T> @Nullable ParameterType<CommandActor, T> create(@NotNull Type parameterType, @NotNull AnnotationList annotations, @NotNull Lamp<CommandActor> lamp) {
      Class<?> rawType = Classes.getRawType(parameterType);
      if (rawType != Optional.class) {
         return null;
      } else {
         Type delegateType = Classes.getFirstGeneric(parameterType, Object.class);
         ParameterType<?, ?> delegate = lamp.resolver(delegateType, annotations).requireParameterType();
         return (input, context) -> Optional.of(delegate.parse(input, context));
      }
   }
}
