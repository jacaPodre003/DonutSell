package revxrsal.commands.hook;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;

@FunctionalInterface
public interface CommandUnregisteredHook<A extends CommandActor> extends Hook {
   void onUnregistered(@NotNull ExecutableCommand<A> var1, @NotNull CancelHandle var2);
}
