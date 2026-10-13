package revxrsal.commands.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.exception.context.ErrorContext;
import revxrsal.commands.node.ExecutionContext;

public interface Potential<A extends CommandActor> extends Comparable<Potential<A>> {
   boolean successful();

   boolean failed();

   @NotNull ExecutionContext<A> context();

   @Nullable Throwable error();

   @Nullable ErrorContext<A> errorContext();

   void handleException();

   void execute();
}
