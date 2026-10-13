package revxrsal.commands.exception.context;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;

final class UnknownCommandContext<A extends CommandActor> implements ErrorContext.UnknownCommand<A> {
   private final @NotNull A actor;

   UnknownCommandContext(@NotNull A actor) {
      this.actor = actor;
   }

   public boolean hasExecutionContext() {
      return false;
   }

   public ExecutionContext<A> context() {
      return null;
   }

   public @NotNull A actor() {
      return this.actor;
   }

   public @NotNull Lamp<A> lamp() {
      return this.actor.lamp();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         UnknownCommandContext that = (UnknownCommandContext)obj;
         return Objects.equals(this.actor, that.actor);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.actor});
   }

   public String toString() {
      return "UnknownCommandContext[actor=" + this.actor + ']';
   }
}
