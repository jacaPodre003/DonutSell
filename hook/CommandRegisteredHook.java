package revxrsal.commands.hook;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;

@FunctionalInterface
public interface CommandRegisteredHook<A extends CommandActor> extends Hook {
   void onRegistered(@NotNull ExecutableCommand<A> var1, @NotNull CancelHandle var2);
}
