package revxrsal.commands.response;

import java.lang.reflect.Type;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.util.Classes;

@Internal
public final class ClassResponseHandlerFactory<A extends CommandActor, T> implements ResponseHandler.Factory<A> {
   private final Class<?> type;
   private final ResponseHandler<A, T> responseHandler;
   private final boolean allowSubclasses;

   public ClassResponseHandlerFactory(Class<?> type, ResponseHandler<A, T> responseHandler, boolean allowSubclasses) {
      this.type = Classes.wrap(type);
      this.responseHandler = responseHandler;
      this.allowSubclasses = allowSubclasses;
   }

   public <L> ResponseHandler<A, L> create(@NotNull Type parameterType, @NotNull AnnotationList annotations, @NotNull Lamp<A> lamp) {
      Class<?> pType = Classes.wrap(Classes.getRawType(parameterType));
      if (this.allowSubclasses && this.type.isAssignableFrom(pType)) {
         return this.responseHandler;
      } else {
         return this.type == pType ? this.responseHandler : null;
      }
   }

   public Class<?> type() {
      return this.type;
   }

   public ResponseHandler<A, T> responseHandler() {
      return this.responseHandler;
   }

   public boolean allowSubclasses() {
      return this.allowSubclasses;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         ClassResponseHandlerFactory that = (ClassResponseHandlerFactory)obj;
         return Objects.equals(this.type, that.type) && Objects.equals(this.responseHandler, that.responseHandler) && this.allowSubclasses == that.allowSubclasses;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.type, this.responseHandler, this.allowSubclasses});
   }

   public String toString() {
      return "ClassResponseHandlerFactory[type=" + this.type + ", responseHandler=" + this.responseHandler + ", allowSubclasses=" + this.allowSubclasses + ']';
   }
}
