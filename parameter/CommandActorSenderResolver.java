package revxrsal.commands.parameter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.process.SenderResolver;

@Internal
public enum CommandActorSenderResolver implements SenderResolver<CommandActor> {
   INSTANCE;

   public boolean isSenderType(@NotNull CommandParameter parameter) {
      return CommandActor.class.isAssignableFrom(parameter.type());
   }

   public @NotNull Object getSender(@NotNull Class<?> customSenderType, @NotNull CommandActor actor, @NotNull ExecutableCommand<CommandActor> command) {
      return actor;
   }
}
