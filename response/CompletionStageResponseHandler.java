package revxrsal.commands.response;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.context.ErrorContext;
import revxrsal.commands.util.Classes;

public enum CompletionStageResponseHandler implements ResponseHandler.Factory<CommandActor> {
   INSTANCE;

   public <T> @Nullable ResponseHandler<CommandActor, T> create(@NotNull Type type, @NotNull AnnotationList annotations, @NotNull Lamp<CommandActor> lamp) {
      Class<?> rawType = Classes.getRawType(type);
      if (rawType != CompletionStage.class && rawType != CompletableFuture.class) {
         return null;
      } else {
         Type suppliedType = Classes.getFirstGeneric(type, Object.class);
         ResponseHandler<CommandActor, Object> delegate = lamp.responseHandler(suppliedType, AnnotationList.empty());
         return (response, context) -> {
            CompletionStage<Object> future = (CompletionStage)response;
            future.whenComplete((o, throwable) -> {
               if (throwable != null) {
                  lamp.handleException(throwable, ErrorContext.executingFunction(context));
               } else {
                  delegate.handleResponse(o, context);
               }

            });
         };
      }
   }
}
