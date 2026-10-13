package revxrsal.commands;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;

@FunctionalInterface
public interface LampVisitor<A extends CommandActor> {
   static <A extends CommandActor> @NotNull LampVisitor<A> nothing() {
      return (lamp) -> {
      };
   }

   void visit(@NotNull Lamp<A> var1);
}
