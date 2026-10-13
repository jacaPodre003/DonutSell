package revxrsal.commands.exception.context;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.stream.StringStream;

final class ParsingParameterContext<A extends CommandActor> implements ErrorContext.ParsingParameter<A> {
   private final @NotNull ExecutionContext<A> context;
   private final @NotNull ParameterNode<A, ?> parameter;
   private final @NotNull StringStream input;

   ParsingParameterContext(@NotNull ExecutionContext<A> context, @NotNull ParameterNode<A, ?> parameter, @NotNull StringStream input) {
      this.context = context;
      this.parameter = parameter;
      this.input = input;
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

   public @NotNull ParameterNode<A, ?> parameter() {
      return this.parameter;
   }

   public @NotNull StringStream input() {
      return this.input;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         ParsingParameterContext that = (ParsingParameterContext)obj;
         return Objects.equals(this.context, that.context) && Objects.equals(this.parameter, that.parameter) && Objects.equals(this.input, that.input);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.context, this.parameter, this.input});
   }

   public String toString() {
      return "ParsingParameterContext[context=" + this.context + ", parameter=" + this.parameter + ", input=" + this.input + ']';
   }
}
