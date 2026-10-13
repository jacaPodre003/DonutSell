package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.context.ErrorContext;

@FunctionalInterface
public interface CommandExceptionHandler<A extends CommandActor> {
   void handleException(@NotNull Throwable var1, @NotNull ErrorContext<A> var2);
}
