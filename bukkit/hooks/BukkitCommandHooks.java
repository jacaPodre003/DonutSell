package revxrsal.commands.bukkit.hooks;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.actor.ActorFactory;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.FallbackPrefix;
import revxrsal.commands.bukkit.util.PluginCommands;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.hook.CancelHandle;
import revxrsal.commands.hook.CommandRegisteredHook;
import revxrsal.commands.hook.CommandUnregisteredHook;

public final class BukkitCommandHooks<A extends BukkitCommandActor> implements CommandRegisteredHook<A>, CommandUnregisteredHook<A> {
   private final Set<String> registeredRootNames = new HashSet();
   private final JavaPlugin plugin;
   private final ActorFactory<A> actorFactory;
   private final String defaultFallbackPrefix;

   public BukkitCommandHooks(JavaPlugin plugin, ActorFactory<A> actorFactory, @NotNull String defaultFallbackPrefix) {
      this.plugin = plugin;
      this.actorFactory = actorFactory;
      this.defaultFallbackPrefix = defaultFallbackPrefix;
   }

   public void onRegistered(@NotNull ExecutableCommand<A> command, @NotNull CancelHandle cancelHandle) {
      String name = command.firstNode().name();
      if (this.registeredRootNames.add(name)) {
         String fallbackPrefix = (String)command.annotations().mapOr(FallbackPrefix.class, FallbackPrefix::value, this.defaultFallbackPrefix);
         PluginCommand cmd = PluginCommands.create(fallbackPrefix, command.firstNode().name(), this.plugin);
         LampCommandExecutor<A> executor = new LampCommandExecutor<A>(command.lamp(), this.actorFactory);
         cmd.setExecutor(executor);
         cmd.setTabCompleter(executor);
         if (cmd.getDescription().isEmpty() && command.description() != null) {
            cmd.setDescription((String)Objects.requireNonNull(command.description()));
         }

         if (cmd.getUsage().isEmpty()) {
            cmd.setUsage(command.usage());
         }
      }

   }

   public void onUnregistered(@NotNull ExecutableCommand<A> command, @NotNull CancelHandle cancelHandle) {
      String label = command.firstNode().name();
      String fallbackPrefix = this.fallbackPrefix(command);
      PluginCommand cmd = Bukkit.getServer().getPluginCommand(fallbackPrefix + ':' + label);
      if (!command.lamp().registry().any((c) -> c != command && c.firstNode().name().equals(label) && this.fallbackPrefix(c).equals(fallbackPrefix)) && cmd != null) {
         PluginCommands.unregister(cmd, this.plugin);
      }

      if (!command.lamp().registry().any((c) -> c != command && c.firstNode().name().equals(label))) {
         cmd = PluginCommands.getCommand(this.plugin, label);
         if (cmd != null) {
            PluginCommands.unregister(cmd, this.plugin);
         }
      }

   }

   private @NotNull String fallbackPrefix(@NotNull ExecutableCommand<A> command) {
      return (String)command.annotations().mapOr(FallbackPrefix.class, FallbackPrefix::value, this.defaultFallbackPrefix);
   }
}
