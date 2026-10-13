package revxrsal.commands.exception.context;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;

final class ExecutingFunctionContext<A extends CommandActor> implements ErrorContext.ExecutingFunction<A> {
   private final ExecutionContext<A> context;

   ExecutingFunctionContext(ExecutionContext<A> context) {
      this.context = context;
   }

   public @NotNull A actor() {
      return this.context.actor();
   }

   public @NotNull Lamp<A> lamp() {
      return this.context.lamp();
   }

   public ExecutionContext<A> context() {
      return this.context;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         ExecutingFunctionContext that = (ExecutingFunctionContext)obj;
         return Objects.equals(this.context, that.context);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.context});
   }

   public String toString() {
      return "ExecutingFunctionContext[context=" + this.context + ']';
   }
}
