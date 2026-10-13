package revxrsal.commands;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;

@FunctionalInterface
public interface LampBuilderVisitor<A extends CommandActor> {
   static <A extends CommandActor> @NotNull LampBuilderVisitor<A> nothing() {
      return (builder) -> {
      };
   }

   void visit(Lamp.@NotNull Builder<A> var1);
}
