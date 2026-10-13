package revxrsal.commands.bukkit.sender;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.process.SenderResolver;

public final class BukkitSenderResolver implements SenderResolver<BukkitCommandActor> {
   public boolean isSenderType(@NotNull CommandParameter parameter) {
      return CommandSender.class.isAssignableFrom(parameter.type());
   }

   public @NotNull Object getSender(@NotNull Class<?> customSenderType, @NotNull BukkitCommandActor actor, @NotNull ExecutableCommand<BukkitCommandActor> command) {
      if (Player.class.isAssignableFrom(customSenderType)) {
         return actor.requirePlayer();
      } else {
         return ConsoleCommandSender.class.isAssignableFrom(customSenderType) ? actor.requireConsole() : actor.sender();
      }
   }
}
