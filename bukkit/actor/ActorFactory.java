package revxrsal.commands.bukkit.actor;

import java.util.Optional;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.process.MessageSender;

@FunctionalInterface
public interface ActorFactory<A extends BukkitCommandActor> {
   static @NotNull ActorFactory<BukkitCommandActor> defaultFactory(@NotNull Plugin plugin, @NotNull Optional<BukkitAudiences> audiences) {
      return new BasicActorFactory(plugin, audiences);
   }

   static <A extends BukkitCommandActor> Object defaultFactory(@NotNull JavaPlugin plugin, @NotNull Optional<BukkitAudiences> audiences, @Nullable MessageSender<? super A, ComponentLike> messageSender) {
      return new BasicActorFactory(plugin, audiences, messageSender);
   }

   @NotNull A create(@NotNull CommandSender var1, @NotNull Lamp<A> var2);
}
