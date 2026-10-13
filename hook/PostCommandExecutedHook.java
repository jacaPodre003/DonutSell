package revxrsal.commands.hook;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.node.ExecutionContext;

@FunctionalInterface
public interface PostCommandExecutedHook<A extends CommandActor> extends Hook {
   void onPostExecuted(@NotNull ExecutableCommand<A> var1, @NotNull ExecutionContext<A> var2);
}
