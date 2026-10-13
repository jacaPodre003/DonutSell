package revxrsal.commands.process;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;

@FunctionalInterface
public interface MessageSender<A extends CommandActor, T> {
   void send(@NotNull A var1, @NotNull T var2);
}
