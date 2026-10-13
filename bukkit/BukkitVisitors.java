package revxrsal.commands.bukkit;

import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.LampBuilderVisitor;
import revxrsal.commands.brigadier.types.ArgumentTypes;
import revxrsal.commands.bukkit.actor.ActorFactory;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.brigadier.BrigadierRegistryHook;
import revxrsal.commands.bukkit.brigadier.BukkitArgumentTypes;
import revxrsal.commands.bukkit.exception.BukkitExceptionHandler;
import revxrsal.commands.bukkit.hooks.BukkitCommandHooks;
import revxrsal.commands.bukkit.listener.AsyncPaperTabListener;
import revxrsal.commands.bukkit.parameters.EntityParameterType;
import revxrsal.commands.bukkit.parameters.EntitySelectorParameterTypeFactory;
import revxrsal.commands.bukkit.parameters.LocationParameterType;
import revxrsal.commands.bukkit.parameters.OfflinePlayerParameterType;
import revxrsal.commands.bukkit.parameters.PlayerParameterType;
import revxrsal.commands.bukkit.parameters.WorldParameterType;
import revxrsal.commands.bukkit.sender.BukkitPermissionFactory;
import revxrsal.commands.bukkit.sender.BukkitSenderResolver;
import revxrsal.commands.bukkit.util.BukkitUtils;
import revxrsal.commands.bukkit.util.BukkitVersion;

public final class BukkitVisitors {
   public static <A extends BukkitCommandActor> @NotNull LampBuilderVisitor<A> legacyColorCodes() {
      return (builder) -> builder.defaultMessageSender((actor, message) -> actor.sendRawMessage(BukkitUtils.legacyColorize(message))).defaultErrorSender((actor, message) -> actor.sendRawMessage(BukkitUtils.legacyColorize("&c" + message)));
   }

   public static <A extends BukkitCommandActor> @NotNull LampBuilderVisitor<A> bukkitExceptionHandler() {
      return (builder) -> builder.exceptionHandler(new BukkitExceptionHandler());
   }

   public static <A extends BukkitCommandActor> @NotNull LampBuilderVisitor<A> bukkitSenderResolver() {
      return (builder) -> builder.senderResolver(new BukkitSenderResolver());
   }

   public static <A extends BukkitCommandActor> @NotNull LampBuilderVisitor<A> bukkitParameterTypes() {
      return bukkitParameterTypes(BukkitVersion.isBrigadierSupported());
   }

   public static <A extends BukkitCommandActor> @NotNull LampBuilderVisitor<A> bukkitParameterTypes(boolean brigadierEnabled) {
      return (builder) -> {
         builder.parameterTypes().addParameterTypeLast(Player.class, new PlayerParameterType(brigadierEnabled)).addParameterTypeLast(OfflinePlayer.class, new OfflinePlayerParameterType(brigadierEnabled)).addParameterTypeLast(World.class, new WorldParameterType()).addParameterTypeLast(Location.class, new LocationParameterType()).addParameterTypeFactoryLast(new EntitySelectorParameterTypeFactory());
         if (BukkitVersion.isBrigadierSupported()) {
            builder.parameterTypes().addParameterTypeLast(Entity.class, new EntityParameterType());
         }

      };
   }

   public static @NotNull LampBuilderVisitor<BukkitCommandActor> registrationHooks(@NotNull JavaPlugin plugin) {
      return registrationHooks(plugin, ActorFactory.defaultFactory(plugin, Optional.empty()));
   }

   public static <A extends BukkitCommandActor> @NotNull LampBuilderVisitor<A> registrationHooks(@NotNull JavaPlugin plugin, @NotNull ActorFactory<A> actorFactory) {
      return registrationHooks(plugin, actorFactory, plugin.getName());
   }

   public static <A extends BukkitCommandActor> @NotNull LampBuilderVisitor<A> registrationHooks(@NotNull JavaPlugin plugin, @NotNull ActorFactory<A> actorFactory, @NotNull String defaultFallbackPrefix) {
      BukkitCommandHooks<A> hooks = new BukkitCommandHooks<A>(plugin, actorFactory, defaultFallbackPrefix);
      return (builder) -> builder.hooks().onCommandRegistered(hooks);
   }

   public static <A extends BukkitCommandActor> @NotNull LampBuilderVisitor<A> pluginContextParameters(JavaPlugin plugin) {
      return (builder) -> {
         builder.parameterTypes().addContextParameterLast(Plugin.class, (parameter, context) -> plugin);
         builder.parameterTypes().addContextParameterLast(plugin.getClass(), (parameter, context) -> plugin);
         builder.dependency(Plugin.class, plugin);
         builder.dependency(plugin.getClass(), plugin);
      };
   }

   public static <A extends BukkitCommandActor> @NotNull LampBuilderVisitor<A> bukkitPermissions() {
      return (builder) -> builder.permissionFactory(BukkitPermissionFactory.INSTANCE);
   }

   public static @NotNull LampBuilderVisitor<BukkitCommandActor> brigadier(@NotNull JavaPlugin plugin) {
      ArgumentTypes.Builder<BukkitCommandActor> builder = BukkitArgumentTypes.<BukkitCommandActor>builder();
      return brigadier(plugin, builder.build(), ActorFactory.defaultFactory(plugin, Optional.empty()));
   }

   public static @NotNull LampBuilderVisitor<BukkitCommandActor> brigadier(@NotNull JavaPlugin plugin, @NotNull ArgumentTypes<? super BukkitCommandActor> argumentTypes) {
      return brigadier(plugin, argumentTypes, ActorFactory.defaultFactory(plugin, Optional.empty()));
   }

   public static <A extends BukkitCommandActor> @NotNull LampBuilderVisitor<A> brigadier(@NotNull JavaPlugin plugin, @NotNull ArgumentTypes<? super A> argumentTypes, @NotNull ActorFactory<A> actorFactory) {
      return BukkitVersion.isBrigadierSupported() ? (builder) -> builder.hooks().onCommandRegistered(new BrigadierRegistryHook(argumentTypes, actorFactory, plugin)) : LampBuilderVisitor.nothing();
   }

   public static <A extends BukkitCommandActor> @NotNull LampBuilderVisitor<A> asyncTabCompletion(final @NotNull JavaPlugin plugin, final @NotNull ActorFactory<A> actorFactory) {
      return BukkitVersion.supportsAsyncCompletion() ? new LampBuilderVisitor<A>() {
         private boolean registered = false;

         public void visit(Lamp.@NotNull Builder<A> builder) {
            builder.hooks().onCommandRegistered((command, cancelHandle) -> {
               if (!this.registered) {
                  this.registered = true;
                  Bukkit.getPluginManager().registerEvents(new AsyncPaperTabListener(command.lamp(), actorFactory), plugin);
               }
            });
         }
      } : LampBuilderVisitor.nothing();
   }
}
