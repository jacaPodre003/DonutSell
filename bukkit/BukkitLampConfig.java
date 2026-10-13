package revxrsal.commands.bukkit;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.LampBuilderVisitor;
import revxrsal.commands.brigadier.types.ArgumentTypes;
import revxrsal.commands.bukkit.actor.ActorFactory;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.brigadier.BukkitArgumentTypes;
import revxrsal.commands.bukkit.util.BukkitVersion;
import revxrsal.commands.process.MessageSender;
import revxrsal.commands.util.Lazy;
import revxrsal.commands.util.Preconditions;

public final class BukkitLampConfig<A extends BukkitCommandActor> implements LampBuilderVisitor<A> {
   private final ActorFactory<A> actorFactory;
   private final Supplier<ArgumentTypes<A>> argumentTypes;
   private final JavaPlugin plugin;
   private final String fallbackPrefix;
   private final boolean disableBrigadier;
   private final boolean disableAsyncCompletion;

   public static <A extends BukkitCommandActor> Builder<A> builder(@NotNull JavaPlugin plugin) {
      Preconditions.notNull(plugin, "plugin");
      return new Builder<A>(plugin);
   }

   public static BukkitLampConfig<BukkitCommandActor> createDefault(@NotNull JavaPlugin plugin) {
      Preconditions.notNull(plugin, "plugin");
      return new BukkitLampConfig<BukkitCommandActor>(ActorFactory.defaultFactory(plugin, Optional.empty()), () -> BukkitArgumentTypes.builder().build(), plugin, plugin.getName(), false, false);
   }

   public void visit(Lamp.@NotNull Builder<A> builder) {
      builder.accept(BukkitVisitors.legacyColorCodes()).accept(BukkitVisitors.bukkitSenderResolver()).accept(BukkitVisitors.bukkitParameterTypes(!this.disableBrigadier)).accept(BukkitVisitors.bukkitExceptionHandler()).accept(BukkitVisitors.bukkitPermissions()).accept(BukkitVisitors.registrationHooks(this.plugin, this.actorFactory, this.fallbackPrefix)).accept(BukkitVisitors.pluginContextParameters(this.plugin));
      if (!this.disableAsyncCompletion) {
         builder.accept(BukkitVisitors.asyncTabCompletion(this.plugin, this.actorFactory));
      }

      if (BukkitVersion.isBrigadierSupported() && !this.disableBrigadier) {
         builder.accept(BukkitVisitors.brigadier(this.plugin, (ArgumentTypes)this.argumentTypes.get(), this.actorFactory));
      }

   }

   public BukkitLampConfig(ActorFactory<A> actorFactory, Supplier<ArgumentTypes<A>> argumentTypes, JavaPlugin plugin, String fallbackPrefix, boolean disableBrigadier, boolean disableAsyncCompletion) {
      this.actorFactory = actorFactory;
      this.argumentTypes = argumentTypes;
      this.plugin = plugin;
      this.fallbackPrefix = fallbackPrefix;
      this.disableBrigadier = disableBrigadier;
      this.disableAsyncCompletion = disableAsyncCompletion;
   }

   public static class Builder<A extends BukkitCommandActor> {
      private final Supplier<ArgumentTypes.Builder<A>> argumentTypes = Lazy.<ArgumentTypes.Builder<A>>of(() -> BukkitArgumentTypes.builder());
      private final @NotNull JavaPlugin plugin;
      private ActorFactory<A> actorFactory;
      private boolean disableBrigadier;
      private String fallbackPrefix;
      private Optional<BukkitAudiences> audiences;
      private @Nullable MessageSender<A, ComponentLike> messageSender;
      private boolean disableAsyncCompletion = true;

      Builder(@NotNull JavaPlugin plugin) {
         this.plugin = plugin;
         this.fallbackPrefix = plugin.getName();
      }

      public @NotNull Builder<A> actorFactory(@NotNull ActorFactory<A> actorFactory) {
         this.actorFactory = actorFactory;
         return this;
      }

      public ArgumentTypes.@NotNull Builder<A> argumentTypes() {
         return (ArgumentTypes.Builder)this.argumentTypes.get();
      }

      public @NotNull Builder<A> argumentTypes(@NotNull Consumer<ArgumentTypes.Builder<A>> consumer) {
         consumer.accept(this.argumentTypes.get());
         return this;
      }

      public @NotNull Builder<A> disableBrigadier() {
         return this.disableBrigadier(true);
      }

      public @NotNull Builder<A> disableAsyncCompletion() {
         return this.disableAsyncCompletion(true);
      }

      public @NotNull Builder<A> enableAsyncCompletion() {
         return this.disableAsyncCompletion(false);
      }

      public @NotNull Builder<A> disableBrigadier(boolean disabled) {
         this.disableBrigadier = disabled;
         return this;
      }

      public @NotNull Builder<A> disableAsyncCompletion(boolean disabled) {
         this.disableAsyncCompletion = disabled;
         return this;
      }

      public @NotNull Builder<A> fallbackPrefix(String fallbackPrefix) {
         this.fallbackPrefix = fallbackPrefix;
         return this;
      }

      public @NotNull Builder<A> audiences(@NotNull BukkitAudiences audiences) {
         this.audiences = Optional.of(audiences);
         return this;
      }

      public @NotNull Builder<A> messageSender(@Nullable MessageSender<? super A, ComponentLike> messageSender) {
         this.messageSender = messageSender;
         return this;
      }

      @Contract("-> new")
      public @NotNull BukkitLampConfig<A> build() {
         this.actorFactory = (ActorFactory)ActorFactory.defaultFactory(this.plugin, this.audiences, this.messageSender);
         return new BukkitLampConfig<A>(this.actorFactory, Lazy.of(() -> ((ArgumentTypes.Builder)this.argumentTypes.get()).build()), this.plugin, this.fallbackPrefix, this.disableBrigadier, this.disableAsyncCompletion);
      }
   }
}
