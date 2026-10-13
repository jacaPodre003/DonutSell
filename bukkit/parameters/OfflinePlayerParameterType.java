package revxrsal.commands.bukkit.parameters;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.exception.InvalidPlayerException;
import revxrsal.commands.bukkit.util.BukkitVersion;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.util.Collections;

public final class OfflinePlayerParameterType implements ParameterType<BukkitCommandActor, OfflinePlayer> {
   private final boolean brigadierEnabled;

   public OfflinePlayerParameterType(boolean brigadierEnabled) {
      this.brigadierEnabled = brigadierEnabled;
   }

   private static boolean exists(OfflinePlayer player) {
      return player.hasPlayedBefore() || player.isOnline() || player.getFirstPlayed() != 0L;
   }

   public OfflinePlayer parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<BukkitCommandActor> context) {
      String name = input.readString();
      if (!name.equals("self") && !name.equals("me") && !name.equals("@s")) {
         OfflinePlayer player = Bukkit.getOfflinePlayer(name);
         if (exists(player)) {
            return player;
         } else {
            throw new InvalidPlayerException(name);
         }
      } else {
         return ((BukkitCommandActor)context.actor()).requirePlayer();
      }
   }

   public @NotNull SuggestionProvider<BukkitCommandActor> defaultSuggestions() {
      return BukkitVersion.isBrigadierSupported() && this.brigadierEnabled ? SuggestionProvider.empty() : (context) -> Collections.map(Bukkit.getOnlinePlayers(), OfflinePlayer::getName);
   }
}
