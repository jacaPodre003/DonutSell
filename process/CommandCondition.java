package revxrsal.commands.process;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;

public interface CommandCondition<A extends CommandActor> {
   void test(@NotNull ExecutionContext<A> var1);
}
