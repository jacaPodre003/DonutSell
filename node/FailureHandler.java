package revxrsal.commands.node;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.Potential;
import revxrsal.commands.stream.StringStream;

@FunctionalInterface
public interface FailureHandler<A extends CommandActor> {
   void handleFailedAttempts(@NotNull A var1, @NotNull @Unmodifiable List<Potential<A>> var2, @NotNull StringStream var3);
}
