package revxrsal.commands.bukkit.actor;

import java.util.Optional;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.process.MessageSender;

final class BasicActorFactory implements ActorFactory<BukkitCommandActor> {
   private final Plugin plugin;
   private final Optional<BukkitAudiences> bukkitAudiences;
   private final MessageSender<BukkitCommandActor, ComponentLike> messageSender;

   public BasicActorFactory(Plugin plugin, Optional<BukkitAudiences> bukkitAudiences) {
      this(plugin, bukkitAudiences, (MessageSender)null);
   }

   public BasicActorFactory(Plugin plugin, Optional<BukkitAudiences> bukkitAudiences, MessageSender<BukkitCommandActor, ComponentLike> messageSender) {
      this.plugin = plugin;
      this.bukkitAudiences = bukkitAudiences;
      this.messageSender = messageSender;
   }

   public @NotNull BukkitCommandActor create(@NotNull CommandSender sender, @NotNull Lamp<BukkitCommandActor> lamp) {
      return new BasicBukkitActor(sender, this.plugin, this.bukkitAudiences, this.messageSender, lamp);
   }
}
