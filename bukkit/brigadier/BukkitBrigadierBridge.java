package revxrsal.commands.bukkit.brigadier;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.command.ExecutableCommand;

public interface BukkitBrigadierBridge<A extends BukkitCommandActor> {
   static @NotNull List<String> getAliases(Command command) {
      Objects.requireNonNull(command, "command");
      Stream<String> aliasesStream = Stream.concat(Stream.of(command.getLabel()), command.getAliases().stream());
      if (command instanceof PluginCommand) {
         String fallbackPrefix = ((PluginCommand)command).getPlugin().getName().toLowerCase().trim();
         aliasesStream = aliasesStream.flatMap((alias) -> Stream.of(alias, fallbackPrefix + ":" + alias));
      }

      return (List)aliasesStream.distinct().collect(Collectors.toList());
   }

   void register(ExecutableCommand<A> var1);
}
