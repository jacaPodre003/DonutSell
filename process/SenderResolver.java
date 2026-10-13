package revxrsal.commands.process;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.command.ExecutableCommand;

public interface SenderResolver<A extends CommandActor> {
   boolean isSenderType(@NotNull CommandParameter var1);

   @NotNull Object getSender(@NotNull Class<?> var1, @NotNull A var2, @NotNull ExecutableCommand<A> var3);
}
