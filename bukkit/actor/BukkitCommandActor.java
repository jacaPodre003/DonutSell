package revxrsal.commands.bukkit.actor;

import java.util.Optional;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.exception.SenderNotConsoleException;
import revxrsal.commands.bukkit.exception.SenderNotPlayerException;
import revxrsal.commands.command.CommandActor;

public interface BukkitCommandActor extends CommandActor {
   @NotNull CommandSender sender();

   default boolean isPlayer() {
      return this.sender() instanceof Player;
   }

   default boolean isConsole() {
      return this.sender() instanceof ConsoleCommandSender;
   }

   default @Nullable Player asPlayer() {
      return this.isPlayer() ? (Player)this.sender() : null;
   }

   default @NotNull Player requirePlayer() throws SenderNotPlayerException {
      if (!this.isPlayer()) {
         throw new SenderNotPlayerException();
      } else {
         return (Player)this.sender();
      }
   }

   default @NotNull ConsoleCommandSender requireConsole() throws SenderNotConsoleException {
      if (!this.isConsole()) {
         throw new SenderNotConsoleException();
      } else {
         return (ConsoleCommandSender)this.sender();
      }
   }

   default void sendRawMessage(@NotNull String message) {
      this.sender().sendMessage(message);
   }

   default void sendRawError(@NotNull String message) {
      this.sender().sendMessage(ChatColor.RED + message);
   }

   void reply(@NotNull ComponentLike var1);

   @NotNull Optional<Audience> audience();

   Lamp<BukkitCommandActor> lamp();

   default @NotNull String name() {
      return this.sender().getName();
   }
}
