package revxrsal.commands.response;

import java.lang.reflect.Type;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.util.Classes;

public enum OptionalResponseHandler implements ResponseHandler.Factory<CommandActor> {
   INSTANCE;

   public <T> @Nullable ResponseHandler<CommandActor, T> create(@NotNull Type type, @NotNull AnnotationList annotations, @NotNull Lamp<CommandActor> lamp) {
      Class<?> rawType = Classes.getRawType(type);
      if (rawType != Optional.class) {
         return null;
      } else {
         Type suppliedType = Classes.getFirstGeneric(type, Object.class);
         ResponseHandler<CommandActor, Object> delegate = lamp.responseHandler(suppliedType, AnnotationList.empty());
         return (response, context) -> {
            Optional<Object> optional = (Optional)response;
            optional.ifPresent((value) -> delegate.handleResponse(value, context));
         };
      }
   }
}
