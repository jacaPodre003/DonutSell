package revxrsal.commands.exception.context;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;

final class UnknownParameterContext<A extends CommandActor> implements ErrorContext.UnknownParameter<A> {
   private final @NotNull ExecutionContext<A> context;

   UnknownParameterContext(@NotNull ExecutionContext<A> context) {
      this.context = context;
   }

   public boolean hasExecutionContext() {
      return true;
   }

   public @NotNull A actor() {
      return this.context.actor();
   }

   public @NotNull Lamp<A> lamp() {
      return this.context.lamp();
   }

   public @NotNull ExecutionContext<A> context() {
      return this.context;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         UnknownParameterContext that = (UnknownParameterContext)obj;
         return Objects.equals(this.context, that.context);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.context});
   }

   public String toString() {
      return "UnknownParameterContext[context=" + this.context + ']';
   }
}
