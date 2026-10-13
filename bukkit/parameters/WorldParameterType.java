package revxrsal.commands.bukkit.parameters;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.exception.InvalidWorldException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.util.Collections;

public final class WorldParameterType implements ParameterType<BukkitCommandActor, World> {
   public World parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<BukkitCommandActor> context) {
      String name = input.readString();
      if (!name.equals("self") && !name.equals("me") && !name.equals("mine")) {
         World world = Bukkit.getWorld(name);
         if (world != null) {
            return world;
         } else {
            throw new InvalidWorldException(name);
         }
      } else {
         return ((BukkitCommandActor)context.actor()).requirePlayer().getWorld();
      }
   }

   public @NotNull SuggestionProvider<BukkitCommandActor> defaultSuggestions() {
      return (context) -> Collections.map(Bukkit.getWorlds(), World::getName);
   }
}
