package revxrsal.commands.process;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ParameterNode;

@FunctionalInterface
public interface ParameterValidator<A extends CommandActor, T> {
   void validate(@NotNull A var1, T var2, @NotNull ParameterNode<A, T> var3, @NotNull Lamp<A> var4);
}
