package revxrsal.commands.bukkit.brigadier;

import com.mojang.brigadier.arguments.ArgumentType;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Single;
import revxrsal.commands.brigadier.types.ArgumentTypes;
import revxrsal.commands.bukkit.parameters.EntitySelector;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.util.Classes;

public final class BukkitArgumentTypes {
   private static final ArgumentType<?> SINGLE_PLAYER;
   private static final ArgumentType<?> PLAYERS;
   private static final ArgumentType<?> SINGLE_ENTITY;
   private static final ArgumentType<?> ENTITIES;

   @Contract(
      value = "-> new",
      pure = true
   )
   public static <A extends CommandActor> ArgumentTypes.@NotNull Builder<A> builder() {
      ArgumentTypes.Builder<A> builder = ArgumentTypes.<A>builder();
      MinecraftArgumentType.UUID.getIfPresent().ifPresent((uuid) -> builder.addTypeLast(UUID.class, uuid));
      return builder.addTypeLast(OfflinePlayer.class, SINGLE_PLAYER).addTypeLast(Player.class, SINGLE_PLAYER).addTypeLast(Entity.class, SINGLE_ENTITY).addTypeLast(Location.class, MinecraftArgumentType.BLOCK_POS.get()).addTypeFactoryLast((parameter) -> {
         if (parameter.type() != EntitySelector.class) {
            return null;
         } else {
            Class<? extends Entity> entityType = Classes.getRawType(Classes.getFirstGeneric(parameter.fullType(), Entity.class)).asSubclass(Entity.class);
            boolean single = parameter.annotations().contains(Single.class);
            if (entityType == Player.class) {
               return single ? SINGLE_PLAYER : PLAYERS;
            } else {
               return single ? SINGLE_ENTITY : ENTITIES;
            }
         }
      });
   }

   static {
      SINGLE_PLAYER = MinecraftArgumentType.ENTITY.create(true, true);
      PLAYERS = MinecraftArgumentType.ENTITY.create(false, true);
      SINGLE_ENTITY = MinecraftArgumentType.ENTITY.create(true, false);
      ENTITIES = MinecraftArgumentType.ENTITY.create(false, false);
   }
}
