package revxrsal.commands.response;

import java.lang.reflect.Type;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.util.Classes;

public enum SupplierResponseHandler implements ResponseHandler.Factory<CommandActor> {
   INSTANCE;

   public <T> @Nullable ResponseHandler<CommandActor, T> create(@NotNull Type type, @NotNull AnnotationList annotations, @NotNull Lamp<CommandActor> lamp) {
      Class<?> rawType = Classes.getRawType(type);
      if (rawType != Supplier.class) {
         return null;
      } else {
         Type optionalType = Classes.getFirstGeneric(type, Object.class);
         ResponseHandler<CommandActor, Object> delegate = lamp.responseHandler(optionalType, AnnotationList.empty());
         return (response, context) -> {
            Supplier<Object> supplier = (Supplier)response;
            Object v = supplier.get();
            delegate.handleResponse(v, context);
         };
      }
   }
}
