package revxrsal.commands.bukkit.parameters;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.exception.EmptyEntitySelectorException;
import revxrsal.commands.bukkit.exception.MalformedEntitySelectorException;
import revxrsal.commands.bukkit.exception.MoreThanOneEntityException;
import revxrsal.commands.bukkit.util.BukkitVersion;
import revxrsal.commands.exception.CommandErrorException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.util.Collections;

public final class EntityParameterType implements ParameterType<BukkitCommandActor, Entity> {
   private static @NotNull Entity fromSelector(@NotNull CommandSender sender, @NotNull String selector) {
      try {
         List<Entity> entityList = Bukkit.selectEntities(sender, selector);
         if (entityList.isEmpty()) {
            throw new EmptyEntitySelectorException(selector);
         } else if (entityList.size() != 1) {
            throw new MoreThanOneEntityException(selector);
         } else {
            return (Entity)entityList.get(0);
         }
      } catch (IllegalArgumentException e) {
         throw new MalformedEntitySelectorException(selector, e.getCause().getMessage());
      } catch (NoSuchMethodError var4) {
         throw new CommandErrorException("Entity selectors on legacy versions are not supported yet!", new Object[0]);
      }
   }

   public Entity parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<BukkitCommandActor> context) {
      String value = input.readString();
      return fromSelector(((BukkitCommandActor)context.actor()).sender(), value);
   }

   public @NotNull SuggestionProvider<BukkitCommandActor> defaultSuggestions() {
      return BukkitVersion.isBrigadierSupported() ? SuggestionProvider.empty() : (context) -> Collections.map(Bukkit.getOnlinePlayers(), OfflinePlayer::getName);
   }
}
