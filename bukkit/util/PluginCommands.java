package revxrsal.commands.bukkit.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.util.Preconditions;

@Internal
public final class PluginCommands {
   private static final Constructor<PluginCommand> COMMAND_CONSTRUCTOR;
   private static final @Nullable Field KNOWN_COMMANDS;
   private static final @Nullable MethodHandle GET_PLUGIN_META;
   private static final CommandMap COMMAND_MAP;

   private PluginCommands() {
      Preconditions.cannotInstantiate(PluginCommands.class);
   }

   @CheckReturnValue
   public static @NotNull PluginCommand create(String name, @NotNull JavaPlugin plugin) {
      try {
         return create(plugin.getName(), name, plugin);
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   @CheckReturnValue
   public static @NotNull PluginCommand create(String fallbackPrefix, String name, @NotNull JavaPlugin plugin) {
      try {
         PluginCommand command = getCommand(plugin, name);
         if (command != null) {
            return command;
         } else {
            command = (PluginCommand)COMMAND_CONSTRUCTOR.newInstance(name, plugin);
            COMMAND_MAP.register(fallbackPrefix, command);
            return command;
         }
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   public static void unregister(@NotNull PluginCommand command, @NotNull JavaPlugin owningPlugin) {
      command.unregister(COMMAND_MAP);
      Map<String, Command> knownCommands = getKnownCommands();
      if (knownCommands != null) {
         Command rawAlias = (Command)knownCommands.get(command.getName());
         if (rawAlias instanceof PluginCommand && ((PluginCommand)rawAlias).getPlugin() == owningPlugin) {
            knownCommands.remove(command.getName());
         }
      }

   }

   private static @Nullable Map<String, Command> getKnownCommands() {
      try {
         return KNOWN_COMMANDS != null ? (Map)KNOWN_COMMANDS.get(COMMAND_MAP) : null;
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   @Nullable
   public static PluginCommand getCommand(@NotNull JavaPlugin plugin, @NotNull String name) {
      Object meta = getPluginMetaOrNull(plugin);
      return meta != null && !(meta instanceof PluginDescriptionFile) ? null : plugin.getCommand(name);
   }

   private static @Nullable Object getPluginMetaOrNull(@NotNull JavaPlugin plugin) {
      try {
         return GET_PLUGIN_META == null ? null : GET_PLUGIN_META.invoke(plugin);
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   static {
      Field knownCommands = null;
      MethodHandle getPluginMeta = null;

      try {
         getPluginMeta = MethodHandles.lookup().unreflect(JavaPlugin.class.getDeclaredMethod("getPluginMeta"));
      } catch (Exception var5) {
      }

      Constructor<PluginCommand> ctr;
      CommandMap commandMap;
      try {
         ctr = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
         ctr.setAccessible(true);
         Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
         commandMapField.setAccessible(true);
         commandMap = (CommandMap)commandMapField.get(Bukkit.getServer());
         if (commandMap instanceof SimpleCommandMap) {
            knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommands.setAccessible(true);
         }
      } catch (NoSuchMethodException e) {
         e.printStackTrace();
         throw new IllegalStateException("Unable to access PluginCommand(String, Plugin) construtor!");
      } catch (IllegalAccessException | NoSuchFieldException e) {
         ((ReflectiveOperationException)e).printStackTrace();
         throw new IllegalStateException("Unable to access Bukkit.getServer()#commandMap!");
      }

      COMMAND_CONSTRUCTOR = ctr;
      COMMAND_MAP = commandMap;
      KNOWN_COMMANDS = knownCommands;
      GET_PLUGIN_META = getPluginMeta;
   }
}
