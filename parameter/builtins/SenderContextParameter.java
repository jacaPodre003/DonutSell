package revxrsal.commands.parameter.builtins;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ContextParameter;
import revxrsal.commands.process.SenderResolver;
import revxrsal.commands.util.Preconditions;

public final class SenderContextParameter<A extends CommandActor, T> implements ContextParameter<A, T> {
   private final SenderResolver<A> resolver;

   public SenderContextParameter(SenderResolver<A> resolver) {
      this.resolver = resolver;
   }

   public T resolve(@NotNull CommandParameter parameter, @NotNull ExecutionContext<A> context) {
      A actor = context.actor();
      Object sender = this.resolver.getSender(parameter.type(), actor, context.command());
      Preconditions.notNull(sender, "SenderResolver#getSender()");
      return (T)sender;
   }

   public SenderResolver<A> resolver() {
      return this.resolver;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         SenderContextParameter that = (SenderContextParameter)obj;
         return Objects.equals(this.resolver, that.resolver);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.resolver});
   }

   public String toString() {
      return "SenderContextParameter[resolver=" + this.resolver + ']';
   }
}
