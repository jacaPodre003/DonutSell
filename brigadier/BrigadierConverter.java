package revxrsal.commands.brigadier;

import com.mojang.brigadier.arguments.ArgumentType;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ParameterNode;

public interface BrigadierConverter<A extends CommandActor, S> {
   @NotNull ArgumentType<?> getArgumentType(@NotNull ParameterNode<A, ?> var1);

   @NotNull A createActor(@NotNull S var1, @NotNull Lamp<A> var2);
}
