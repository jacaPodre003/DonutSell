package revxrsal.commands.bukkit;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

public final class BukkitLamp {
   public static Lamp.Builder<BukkitCommandActor> builder(@NotNull JavaPlugin plugin) {
      return builder(BukkitLampConfig.createDefault(plugin));
   }

   public static <A extends BukkitCommandActor> Lamp.Builder<A> builder(@NotNull BukkitLampConfig<A> config) {
      return Lamp.<A>builder().accept(config);
   }
}
