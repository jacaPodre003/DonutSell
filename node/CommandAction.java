package revxrsal.commands.node;

import revxrsal.commands.command.CommandActor;

@FunctionalInterface
public interface CommandAction<A extends CommandActor> {
   void execute(ExecutionContext<A> var1);
}
