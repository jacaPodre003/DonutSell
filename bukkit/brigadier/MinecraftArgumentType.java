package revxrsal.commands.bukkit.brigadier;

import com.mojang.brigadier.arguments.ArgumentType;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.bukkit.util.BukkitVersion;

public enum MinecraftArgumentType {
   ENTITY(new String[]{"ArgumentEntity", "EntityArgument"}, new Class[]{Boolean.TYPE, Boolean.TYPE}),
   GAME_PROFILE(new String[]{"ArgumentProfile", "GameProfileArgument"}),
   COLOR(new String[]{"ArgumentChatFormat", "ColorArgument"}),
   COMPONENT(new String[]{"ArgumentChatComponent", "ComponentArgument"}),
   MESSAGE(new String[]{"ArgumentChat", "MessageArgument"}),
   NBT(new String[]{"ArgumentNBTTag", "CompoundTagArgument"}),
   NBT_TAG(new String[]{"ArgumentNBTBase", "NbtTagArgument"}),
   NBT_PATH(new String[]{"ArgumentNBTKey", "NbtPathArgument"}),
   SCOREBOARD_OBJECTIVE(new String[]{"ArgumentScoreboardObjective", "ObjectiveArgument"}),
   OBJECTIVE_CRITERIA(new String[]{"ArgumentScoreboardCriteria", "ObjectiveCriteriaArgument"}),
   SCOREBOARD_SLOT(new String[]{"ArgumentScoreboardSlot", "SlotArgument"}),
   SCORE_HOLDER(new String[]{"ArgumentScoreholder", "ScoreHolderArgument"}),
   TEAM(new String[]{"ArgumentScoreboardTeam", "TeamArgument"}),
   OPERATION(new String[]{"ArgumentMathOperation", "OperationArgument"}),
   PARTICLE(new String[]{"ArgumentParticle", "ParticleArgument"}),
   ANGLE(new String[]{"ArgumentAngle", "AngleArgument"}),
   ITEM_SLOT(new String[]{"ArgumentInventorySlot", "SlotArgument"}),
   RESOURCE_LOCATION(new String[]{"ArgumentMinecraftKeyRegistered", "ResourceLocationArgument"}),
   POTION_EFFECT(new String[]{"ArgumentMobEffect"}),
   ENCHANTMENT(new String[]{"ArgumentEnchantment"}),
   ENTITY_SUMMON(new String[]{"ArgumentEntitySummon"}),
   DIMENSION(new String[]{"ArgumentDimension", "DimensionArgument"}),
   TIME(new String[]{"ArgumentTime", "TimeArgument"}),
   UUID(new String[]{"ArgumentUUID", "UuidArgument"}),
   BLOCK_POS(new String[]{"coordinates.ArgumentPosition", "coordinates.BlockPosArgument"}),
   COLUMN_POS(new String[]{"coordinates.ArgumentVec2I", "coordinates.ColumnPosArgument"}),
   VECTOR_3(new String[]{"coordinates.ArgumentVec3", "coordinates.Vec3Argument"}),
   VECTOR_2(new String[]{"coordinates.ArgumentVec2", "coordinates.Vec2Argument"}),
   ROTATION(new String[]{"coordinates.ArgumentRotation", "coordinates.RotationArgument"}),
   SWIZZLE(new String[]{"coordinates.ArgumentRotationAxis", "coordinates.SwizzleArgument"}),
   BLOCK_STATE(new String[]{"blocks.ArgumentTile", "blocks.BlockStateArgument"}),
   BLOCK_PREDICATE(new String[]{"blocks.ArgumentBlockPredicate", "blocks.BlockPredicateArgument"}),
   ITEM_STACK(new String[]{"item.ArgumentItemStack", "item.ItemArgument"}),
   ITEM_PREDICATE(new String[]{"item.ArgumentItemPredicate", "item.ItemPredicateArgument"}),
   FUNCTION(new String[]{"item.ArgumentTag", "item.FunctionArgument"}),
   ENTITY_ANCHOR(new String[]{"ArgumentAnchor", "EntityAnchorArgument"}),
   INT_RANGE(new String[]{"ArgumentCriterionValue$b", "RangeArgument$Ints"}),
   FLOAT_RANGE(new String[]{"ArgumentCriterionValue$a", "RangeArgument$Floats"}),
   TEMPLATE_MIRROR(new String[]{"TemplateMirrorArgument"}),
   TEMPLATE_ROTATION(new String[]{"TemplateRotationArgument"});

   private final Class<?>[] parameters;
   private @Nullable ArgumentType<?> argumentType;
   private @Nullable Constructor<? extends ArgumentType> argumentConstructor;

   private MinecraftArgumentType(String... names) {
      this(names);
   }

   private MinecraftArgumentType(String[] names, Class<?>... parameters) {
      Class<?> argumentClass = null;

      for(String name : names) {
         argumentClass = resolveArgumentClass(name);
         if (argumentClass != null) {
            break;
         }
      }

      this.parameters = parameters;
      if (argumentClass == null) {
         this.argumentType = null;
         this.argumentConstructor = null;
      } else {
         try {
            this.argumentConstructor = argumentClass.asSubclass(ArgumentType.class).getDeclaredConstructor(parameters);
            if (!this.argumentConstructor.isAccessible()) {
               this.argumentConstructor.setAccessible(true);
            }

            if (parameters.length == 0) {
               this.argumentType = (ArgumentType)this.argumentConstructor.newInstance();
            } else {
               this.argumentType = null;
            }
         } catch (Throwable var10) {
            this.argumentType = null;
            this.argumentConstructor = null;
         }

      }
   }

   private static @Nullable Class<?> resolveArgumentClass(String name) {
      String strippedName;
      if (name.lastIndexOf(46) != -1) {
         strippedName = name.substring(name.lastIndexOf(46) + 1);
      } else {
         strippedName = name;
      }

      for(String s : MinecraftArgumentType.Data.POSSIBLE_CLASS_NAMES) {
         String className = s.replace("{version}", BukkitVersion.version()).replace("{name}", name).replace("{stripped_name}", strippedName);

         try {
            return Class.forName(className);
         } catch (ClassNotFoundException var6) {
         }
      }

      return null;
   }

   public boolean isSupported() {
      return this.argumentConstructor != null;
   }

   public boolean requiresParameters() {
      return this.parameters.length != 0;
   }

   public <T> @NotNull ArgumentType<T> get() {
      if (this.argumentConstructor == null) {
         throw new IllegalArgumentException("Argument type '" + this.name().toLowerCase() + "' is not available on this version.");
      } else if (this.argumentType != null) {
         return this.argumentType;
      } else {
         throw new IllegalArgumentException("This argument type requires " + this.parameters.length + " parameter(s) of type(s) " + (String)Arrays.stream(this.parameters).map(Class::getName).collect(Collectors.joining(", ")) + ". Use #create() instead.");
      }
   }

   public <T> @NotNull ArgumentType<T> create(Object... arguments) {
      try {
         if (this.argumentConstructor == null) {
            throw new IllegalArgumentException("Argument type '" + this.name().toLowerCase() + "' is not available on this version.");
         } else {
            return this.argumentType != null && arguments.length == 0 ? this.argumentType : (ArgumentType)this.argumentConstructor.newInstance(arguments);
         }
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   public <T> @NotNull Optional<ArgumentType<T>> getIfPresent() {
      if (this.argumentConstructor == null) {
         return Optional.empty();
      } else if (this.argumentType != null) {
         return Optional.of(this.argumentType);
      } else {
         throw new IllegalArgumentException("This argument type requires " + this.parameters.length + " parameter(s) of type(s) " + (String)Arrays.stream(this.parameters).map(Class::getName).collect(Collectors.joining(", ")) + ". Use #create() instead.");
      }
   }

   public <T> @NotNull Optional<ArgumentType<T>> createIfPresent(Object... arguments) {
      try {
         if (this.argumentConstructor == null) {
            return Optional.empty();
         } else {
            return this.argumentType != null && arguments.length == 0 ? Optional.of(this.argumentType) : Optional.of(this.argumentConstructor.newInstance(arguments));
         }
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   static class Data {
      private static final List<String> POSSIBLE_CLASS_NAMES = Arrays.asList("net.minecraft.server.{name}", "net.minecraft.server.{version}.{name}", "net.minecraft.commands.arguments.{name}", "net.minecraft.server.{version}.{stripped_name}");
   }
}
