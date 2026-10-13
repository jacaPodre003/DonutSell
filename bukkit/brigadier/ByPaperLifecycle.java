package revxrsal.commands.bukkit.brigadier;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import java.lang.reflect.Method;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.brigadier.BrigadierConverter;
import revxrsal.commands.brigadier.BrigadierParser;
import revxrsal.commands.brigadier.types.ArgumentTypes;
import revxrsal.commands.bukkit.actor.ActorFactory;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.node.ParameterNode;

final class ByPaperLifecycle<A extends BukkitCommandActor> implements BukkitBrigadierBridge<A>, BrigadierConverter<A, CommandSourceStack> {
   private static final Method GET_LIFECYCLE_MANAGER;
   private final ArgumentTypes<A> types;
   private final ActorFactory<A> actorFactory;
   private final RootCommandNode<CommandSourceStack> root = new RootCommandNode();
   private final BrigadierParser<CommandSourceStack, A> parser = new BrigadierParser<CommandSourceStack, A>(this);

   public ByPaperLifecycle(JavaPlugin plugin, ArgumentTypes<A> types, ActorFactory<A> actorFactory) {
      this.types = types;
      this.actorFactory = actorFactory;
      getLifecycleManager(plugin).registerEventHandler(LifecycleEvents.COMMANDS, (event) -> {
         for(CommandNode<CommandSourceStack> node : this.root.getChildren()) {
            ((Commands)event.registrar()).register((LiteralCommandNode)node);
         }

      });
   }

   private static LifecycleEventManager<Plugin> getLifecycleManager(JavaPlugin plugin) {
      try {
         if (GET_LIFECYCLE_MANAGER == null) {
            throw new IllegalArgumentException("getLifecycleManager is not available.");
         } else {
            return (LifecycleEventManager)GET_LIFECYCLE_MANAGER.invoke(plugin);
         }
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   public void register(ExecutableCommand<A> command) {
      LiteralCommandNode<CommandSourceStack> node = this.parser.createNode(command);
      BrigadierParser.addChild(this.root, node);
   }

   public @NotNull ArgumentType<?> getArgumentType(@NotNull ParameterNode<A, ?> parameter) {
      return this.types.type(parameter);
   }

   public @NotNull A createActor(@NotNull CommandSourceStack sender, @NotNull Lamp<A> lamp) {
      return this.actorFactory.create((CommandSender)(sender.getExecutor() == null ? sender.getSender() : sender.getExecutor()), lamp);
   }

   static {
      Method getLifecycleManager;
      try {
         getLifecycleManager = Plugin.class.getDeclaredMethod("getLifecycleManager");
      } catch (NoSuchMethodException var2) {
         getLifecycleManager = null;
      }

      GET_LIFECYCLE_MANAGER = getLifecycleManager;
   }
}
