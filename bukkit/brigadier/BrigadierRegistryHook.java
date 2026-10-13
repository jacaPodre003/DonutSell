package revxrsal.commands.bukkit.brigadier;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.brigadier.types.ArgumentTypes;
import revxrsal.commands.bukkit.actor.ActorFactory;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.util.BukkitVersion;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.hook.CancelHandle;
import revxrsal.commands.hook.CommandRegisteredHook;

public final class BrigadierRegistryHook<A extends BukkitCommandActor> implements CommandRegisteredHook<A> {
   private final ActorFactory<A> actorFactory;
   private final ArgumentTypes<A> argumentTypes;
   private final BukkitBrigadierBridge<A> bridge;
   private final JavaPlugin plugin;

   public BrigadierRegistryHook(ArgumentTypes<A> argumentTypes, ActorFactory<A> actorFactory, JavaPlugin plugin) {
      this.actorFactory = actorFactory;
      this.argumentTypes = argumentTypes;
      this.plugin = plugin;
      this.bridge = this.createBridge();
   }

   private BukkitBrigadierBridge<A> createBridge() {
      if (BukkitVersion.isPaper()) {
         if (BukkitVersion.supports(1, 20, 6)) {
            return new ByPaperLifecycle<A>(this.plugin, this.argumentTypes, this.actorFactory);
         }

         if (BukkitVersion.supports(1, 19)) {
            return new ByPaperEvents<A>(this.plugin, this.argumentTypes, this.actorFactory);
         }
      }

      return new ByReflection<A>(this.plugin, this.argumentTypes, this.actorFactory);
   }

   public void onRegistered(@NotNull ExecutableCommand<A> command, @NotNull CancelHandle cancelHandle) {
      this.bridge.register(command);
   }
}
