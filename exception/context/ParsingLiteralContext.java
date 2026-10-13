package revxrsal.commands.exception.context;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.node.LiteralNode;

final class ParsingLiteralContext<A extends CommandActor> implements ErrorContext.ParsingLiteral<A> {
   private final @NotNull ExecutionContext<A> context;
   private final @NotNull LiteralNode<A> literal;

   ParsingLiteralContext(@NotNull ExecutionContext<A> context, @NotNull LiteralNode<A> literal) {
      this.context = context;
      this.literal = literal;
   }

   public @NotNull A actor() {
      return (A)this.context().actor();
   }

   public @NotNull Lamp<A> lamp() {
      return this.context.lamp();
   }

   public @NotNull ExecutionContext<A> context() {
      return this.context;
   }

   public @NotNull LiteralNode<A> literal() {
      return this.literal;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         ParsingLiteralContext that = (ParsingLiteralContext)obj;
         return Objects.equals(this.context, that.context) && Objects.equals(this.literal, that.literal);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.context, this.literal});
   }

   public String toString() {
      return "ParsingLiteralContext[context=" + this.context + ", literal=" + this.literal + ']';
   }
}
