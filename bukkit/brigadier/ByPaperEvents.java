package revxrsal.commands.bukkit.brigadier;

import com.destroystokyo.paper.event.brigadier.CommandRegisteredEvent;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.command.UnknownCommandEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.brigadier.BrigadierConverter;
import revxrsal.commands.brigadier.BrigadierParser;
import revxrsal.commands.brigadier.types.ArgumentTypes;
import revxrsal.commands.bukkit.actor.ActorFactory;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.hooks.LampCommandExecutor;
import revxrsal.commands.bukkit.util.PluginCommands;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.stream.StringStream;
import revxrsal.commands.util.Strings;

final class ByPaperEvents<A extends BukkitCommandActor> implements BukkitBrigadierBridge<A>, BrigadierConverter<A, Object>, Listener {
   private final RootCommandNode<Object> rootNode = new RootCommandNode();
   private final String fallbackPrefix;
   private final ArgumentTypes<A> types;
   private final ActorFactory<A> actorFactory;
   private final JavaPlugin plugin;
   private final BrigadierParser<Object, A> parser = new BrigadierParser<Object, A>(this);
   private boolean unknownCommandListenerRegistered = false;

   ByPaperEvents(@NotNull JavaPlugin plugin, ArgumentTypes<A> types, @NotNull ActorFactory<A> actorFactory) {
      this.plugin = plugin;
      this.fallbackPrefix = plugin.getName().toLowerCase().trim();
      this.types = types;
      this.actorFactory = actorFactory;
      this.registerListener(plugin);
   }

   private void registerListener(Plugin plugin) {
      Bukkit.getPluginManager().registerEvents(new CommandRegisterListener(), plugin);
   }

   public @NotNull ArgumentType<?> getArgumentType(@NotNull ParameterNode<A, ?> parameter) {
      return this.types.type(parameter);
   }

   public @NotNull A createActor(@NotNull Object sender, @NotNull Lamp<A> lamp) {
      return this.actorFactory.create(BrigadierUtil.getBukkitSender(sender), lamp);
   }

   public void register(ExecutableCommand<A> command) {
      Objects.requireNonNull(command, "command");
      if (!this.unknownCommandListenerRegistered) {
         Bukkit.getPluginManager().registerEvents(new UnknownCommandListener(command.lamp()), this.plugin);
         this.unknownCommandListenerRegistered = true;
      }

      LiteralCommandNode<Object> node = this.parser.createNode(command);
      Collection<String> aliases = BukkitBrigadierBridge.getAliases(PluginCommands.getCommand(this.plugin, command.firstNode().name()));
      if (!aliases.contains(node.getLiteral())) {
         node = BrigadierUtil.<Object>renameLiteralNode(node, command.firstNode().name());
      }

      for(String alias : aliases) {
         if (node.getLiteral().equals(alias)) {
            BrigadierParser.addChild(this.rootNode, node);
         } else {
            LiteralCommandNode<Object> redirectNode = ((LiteralArgumentBuilder)LiteralArgumentBuilder.literal(alias).redirect(node)).build();
            BrigadierParser.addChild(this.rootNode, redirectNode);
         }
      }

   }

   public final class UnknownCommandListener implements Listener {
      private final Lamp<A> lamp;

      public UnknownCommandListener(Lamp<A> lamp) {
         this.lamp = lamp;
      }

      @EventHandler
      public void onUnknownCommand(UnknownCommandEvent event) {
         if (!event.getCommandLine().isEmpty()) {
            MutableStringStream input = StringStream.createMutable(Strings.stripNamespace(ByPaperEvents.this.fallbackPrefix, event.getCommandLine()));
            if (ByPaperEvents.this.rootNode.getChild(input.peekUnquotedString()) != null) {
               event.setMessage((String)null);
               A actor = ByPaperEvents.this.actorFactory.create(event.getSender(), this.lamp);
               this.lamp.dispatch(actor, (StringStream)input);
            }

         }
      }
   }

   public final class CommandRegisterListener implements Listener {
      @EventHandler
      public void onCommandRegistered(CommandRegisteredEvent<?> event) {
         if (event.getCommand() instanceof PluginCommand) {
            PluginCommand pCommand = (PluginCommand)event.getCommand();
            if (pCommand.getExecutor() instanceof LampCommandExecutor) {
               LiteralCommandNode node = (LiteralCommandNode)ByPaperEvents.this.rootNode.getChild(event.getCommandLabel());
               if (node != null) {
                  event.setLiteral(node);
               }

            }
         }
      }
   }
}
