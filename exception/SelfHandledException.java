package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.context.ErrorContext;

public interface SelfHandledException<A extends CommandActor> {
   void handle(@NotNull ErrorContext<A> var1);
}
