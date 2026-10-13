package revxrsal.commands.response;

import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;

public interface ResponseHandler<A extends CommandActor, T> {
   static <A extends CommandActor, T> @NotNull ResponseHandler<A, T> noOp() {
      return (response, context) -> {
      };
   }

   void handleResponse(T var1, ExecutionContext<A> var2);

   public interface Factory<A extends CommandActor> {
      static <A extends CommandActor, T> @NotNull Factory<A> forType(@NotNull Class<T> type, @NotNull ResponseHandler<A, T> responseHandler) {
         return new ClassResponseHandlerFactory(type, responseHandler, false);
      }

      static <A extends CommandActor, T> @NotNull Factory<A> forTypeAndSubclasses(@NotNull Class<T> type, @NotNull ResponseHandler<A, T> responseHandler) {
         return new ClassResponseHandlerFactory(type, responseHandler, true);
      }

      <T> @Nullable ResponseHandler<A, T> create(@NotNull Type var1, @NotNull AnnotationList var2, @NotNull Lamp<A> var3);
   }
}
