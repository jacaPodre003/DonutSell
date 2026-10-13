package revxrsal.commands.bukkit.brigadier;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.brigadier.BrigadierConverter;
import revxrsal.commands.brigadier.BrigadierParser;
import revxrsal.commands.brigadier.types.ArgumentTypes;
import revxrsal.commands.bukkit.actor.ActorFactory;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.util.BukkitVersion;
import revxrsal.commands.bukkit.util.PluginCommands;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.node.ParameterNode;

final class ByReflection<A extends BukkitCommandActor> implements BukkitBrigadierBridge<A>, BrigadierConverter<A, Object> {
   private static final Field CONSOLE_FIELD;
   private static final Method GET_COMMAND_DISPATCHER_METHOD;
   private static final Method GET_BRIGADIER_DISPATCHER_METHOD;
   private final JavaPlugin plugin;
   private final ArgumentTypes<A> types;
   private final ActorFactory<A> factory;
   private final BrigadierParser<Object, A> parser = new BrigadierParser<Object, A>(this);
   private final RootCommandNode<Object> registeredNodes = new RootCommandNode();

   ByReflection(JavaPlugin plugin, ArgumentTypes<A> types, ActorFactory<A> factory) {
      this.plugin = plugin;
      this.types = types;
      this.factory = factory;
      this.plugin.getServer().getPluginManager().registerEvents(new ServerReloadListener(), this.plugin);
   }

   private CommandDispatcher<?> getDispatcher() {
      try {
         Object mcServerObject = CONSOLE_FIELD.get(Bukkit.getServer());
         Object commandDispatcherObject = GET_COMMAND_DISPATCHER_METHOD.invoke(mcServerObject);
         return (CommandDispatcher)GET_BRIGADIER_DISPATCHER_METHOD.invoke(commandDispatcherObject);
      } catch (ReflectiveOperationException e) {
         throw new RuntimeException(e);
      }
   }

   private void register(LiteralCommandNode<Object> node) {
      Objects.requireNonNull(node, "node");
      CommandDispatcher dispatcher = this.getDispatcher();
      RootCommandNode root = dispatcher.getRoot();
      BrigadierUtil.removeChild(root, node.getName());
      BrigadierParser.addChild(root, node);
      BrigadierParser.addChild(this.registeredNodes, node);
   }

   public void register(ExecutableCommand<A> command) {
      Objects.requireNonNull(command, "command");
      LiteralCommandNode<Object> node = this.parser.createNode(command);
      PluginCommand bCommand = PluginCommands.getCommand(this.plugin, command.firstNode().name());
      Collection<String> aliases = BukkitBrigadierBridge.getAliases(bCommand);
      if (!aliases.contains(node.getLiteral())) {
         node = BrigadierUtil.<Object>renameLiteralNode(node, command.firstNode().name());
      }

      for(String alias : aliases) {
         if (node.getLiteral().equals(alias)) {
            this.register(node);
         } else {
            this.register(((LiteralArgumentBuilder)LiteralArgumentBuilder.literal(alias).redirect(node)).build());
         }
      }

      this.plugin.getServer().getPluginManager().registerEvents(new CommandDataSendListener(bCommand), this.plugin);
   }

   public @NotNull ArgumentType<?> getArgumentType(@NotNull ParameterNode<A, ?> parameter) {
      return this.types.type(parameter);
   }

   public @NotNull A createActor(@NotNull Object sender, @NotNull Lamp<A> lamp) {
      return this.factory.create(BrigadierUtil.getBukkitSender(sender), lamp);
   }

   static {
      try {
         Class<?> minecraftServer = BukkitVersion.findNmsClass("server.MinecraftServer", "MinecraftServer");
         Class<?> commandDispatcher = BukkitVersion.findNmsClass("commands.CommandDispatcher", "command.CommandDispatcher", "CommandDispatcher");
         Class<?> craftServer = BukkitVersion.findOcbClass("CraftServer");
         CONSOLE_FIELD = craftServer.getDeclaredField("console");
         CONSOLE_FIELD.setAccessible(true);
         GET_COMMAND_DISPATCHER_METHOD = (Method)Arrays.stream(minecraftServer.getDeclaredMethods()).filter((method) -> method.getParameterCount() == 0).filter((method) -> commandDispatcher.isAssignableFrom(method.getReturnType())).findFirst().orElseThrow(NoSuchMethodException::new);
         GET_COMMAND_DISPATCHER_METHOD.setAccessible(true);
         GET_BRIGADIER_DISPATCHER_METHOD = (Method)Arrays.stream(commandDispatcher.getDeclaredMethods()).filter((method) -> method.getParameterCount() == 0).filter((method) -> CommandDispatcher.class.isAssignableFrom(method.getReturnType())).findFirst().orElseThrow(NoSuchMethodException::new);
         GET_BRIGADIER_DISPATCHER_METHOD.setAccessible(true);
      } catch (ReflectiveOperationException e) {
         throw new ExceptionInInitializerError(e);
      }
   }

   private static final class CommandDataSendListener implements Listener {
      private final Set<String> minecraftPrefixedAliases;

      CommandDataSendListener(Command pluginCommand) {
         this.minecraftPrefixedAliases = (Set)BukkitBrigadierBridge.getAliases(pluginCommand).stream().map((alias) -> "minecraft:" + alias).collect(Collectors.toSet());
      }

      @EventHandler
      public void onCommandSend(PlayerCommandSendEvent e) {
         e.getCommands().removeAll(this.minecraftPrefixedAliases);
      }
   }

   private final class ServerReloadListener implements Listener {
      private ServerReloadListener() {
      }

      @EventHandler
      public void onLoad(ServerLoadEvent e) {
         CommandDispatcher dispatcher = ByReflection.this.getDispatcher();
         RootCommandNode root = dispatcher.getRoot();

         for(CommandNode<?> node : ByReflection.this.registeredNodes.getChildren()) {
            BrigadierUtil.removeChild(root, node.getName());
            BrigadierParser.addChild(root, node);
         }

      }

      @EventHandler
      public void onPluginDisable(PluginDisableEvent e) {
         if (ByReflection.this.plugin == e.getPlugin()) {
            CommandDispatcher dispatcher = ByReflection.this.getDispatcher();
            RootCommandNode root = dispatcher.getRoot();

            for(CommandNode<?> node : ByReflection.this.registeredNodes.getChildren()) {
               BrigadierUtil.removeChild(root, node.getName());
            }

         }
      }
   }
}
