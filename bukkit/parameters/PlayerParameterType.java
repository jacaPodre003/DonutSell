package revxrsal.commands.bukkit.parameters;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.exception.EmptyEntitySelectorException;
import revxrsal.commands.bukkit.exception.InvalidPlayerException;
import revxrsal.commands.bukkit.exception.MalformedEntitySelectorException;
import revxrsal.commands.bukkit.exception.MoreThanOneEntityException;
import revxrsal.commands.bukkit.exception.NonPlayerEntitiesException;
import revxrsal.commands.bukkit.util.BukkitVersion;
import revxrsal.commands.exception.CommandErrorException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.util.Collections;

public final class PlayerParameterType implements ParameterType<BukkitCommandActor, Player> {
   private final boolean brigadierEnabled;

   public PlayerParameterType(boolean brigadierEnabled) {
      this.brigadierEnabled = brigadierEnabled;
   }

   private static @NotNull Player fromSelector(@NotNull CommandSender sender, @NotNull String selector) {
      try {
         List<Entity> entityList = Bukkit.selectEntities(sender, selector);
         if (entityList.isEmpty()) {
            throw new EmptyEntitySelectorException(selector);
         } else if (entityList.size() != 1) {
            throw new MoreThanOneEntityException(selector);
         } else {
            Entity entity = (Entity)entityList.get(0);
            if (!(entity instanceof Player)) {
               throw new NonPlayerEntitiesException(selector);
            } else {
               Player player = (Player)entity;
               return player;
            }
         }
      } catch (IllegalArgumentException e) {
         throw new MalformedEntitySelectorException(selector, e.getCause().getMessage());
      } catch (NoSuchMethodError var6) {
         throw new CommandErrorException("Entity selectors on legacy versions are not supported yet!", new Object[0]);
      }
   }

   public Player parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<BukkitCommandActor> context) {
      String value = input.readString();
      if (BukkitVersion.isBrigadierSupported()) {
         return fromSelector(((BukkitCommandActor)context.actor()).sender(), value);
      } else if (!value.equals("self") && !value.equals("me") && !value.equals("@s")) {
         Player player = Bukkit.getPlayerExact(value);
         if (player != null) {
            return player;
         } else {
            throw new InvalidPlayerException(value);
         }
      } else {
         return ((BukkitCommandActor)context.actor()).requirePlayer();
      }
   }

   public @NotNull SuggestionProvider<BukkitCommandActor> defaultSuggestions() {
      return BukkitVersion.isBrigadierSupported() && this.brigadierEnabled ? SuggestionProvider.empty() : (context) -> Collections.map(Bukkit.getOnlinePlayers(), OfflinePlayer::getName);
   }
}
