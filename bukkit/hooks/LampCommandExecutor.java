package revxrsal.commands.bukkit.hooks;

import java.util.List;
import java.util.StringJoiner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.actor.ActorFactory;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.util.BukkitUtils;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.stream.StringStream;
import revxrsal.commands.util.Collections;
import revxrsal.commands.util.Strings;

public final class LampCommandExecutor<A extends BukkitCommandActor> implements TabExecutor {
   private final @NotNull Lamp<A> lamp;
   private final @NotNull ActorFactory<A> actorFactory;

   public LampCommandExecutor(@NotNull Lamp<A> lamp, @NotNull ActorFactory<A> actorFactory) {
      this.lamp = lamp;
      this.actorFactory = actorFactory;
   }

   private static String ignoreAfterSpace(String v) {
      int spaceIndex = v.indexOf(32);
      return spaceIndex == -1 ? v : v.substring(0, spaceIndex);
   }

   @NotNull
   private static MutableStringStream createInput(String commandName, String[] args) {
      StringJoiner userInput = new StringJoiner(" ");
      userInput.add(Strings.stripNamespace(commandName));

      for(String arg : args) {
         userInput.add(arg);
      }

      return StringStream.createMutable(userInput.toString());
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      A actor = this.actorFactory.create(sender, this.lamp);
      MutableStringStream input = createInput(command.getName(), args);
      this.lamp.dispatch(actor, (StringStream)input);
      return true;
   }

   public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
      A actor = this.actorFactory.create(sender, this.lamp);
      MutableStringStream input = createInput(command.getName(), args);
      List<String> completions = this.lamp.autoCompleter().complete(actor, (StringStream)input);
      return BukkitUtils.isBrigadierAvailable() ? completions : Collections.map(completions, LampCommandExecutor::ignoreAfterSpace);
   }
}
