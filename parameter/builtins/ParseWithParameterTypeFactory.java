package revxrsal.commands.parameter.builtins;

import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.ParseWith;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.parameter.BaseParameterType;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.util.InstanceCreator;

public enum ParseWithParameterTypeFactory implements ParameterType.Factory<CommandActor> {
   INSTANCE;

   public <T> @Nullable ParameterType<CommandActor, T> create(@NotNull Type parameterType, @NotNull AnnotationList annotations, @NotNull Lamp<CommandActor> lamp) {
      ParseWith parseWith = (ParseWith)annotations.get(ParseWith.class);
      if (parseWith == null) {
         return null;
      } else {
         BaseParameterType type = (BaseParameterType)InstanceCreator.create(parseWith.value());
         if (type instanceof ParameterType) {
            ParameterType<?, ?> pType = (ParameterType)type;
            return pType;
         } else if (type instanceof ParameterType.Factory) {
            ParameterType.Factory<?> factory = (ParameterType.Factory)type;
            return factory.create(parameterType, annotations, lamp);
         } else {
            throw new IllegalArgumentException("Don't know how to create a ParameterType from " + type);
         }
      }
   }
}
