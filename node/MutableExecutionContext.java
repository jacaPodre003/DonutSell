package revxrsal.commands.node;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;

public interface MutableExecutionContext<A extends CommandActor> extends ExecutionContext<A> {
   void addResolvedArgument(@NotNull String var1, Object var2);

   void clearResolvedArguments();
}
