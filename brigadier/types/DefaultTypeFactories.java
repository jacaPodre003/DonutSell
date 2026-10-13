package revxrsal.commands.brigadier.types;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import revxrsal.commands.annotation.Range;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.util.Classes;

final class DefaultTypeFactories {
   public static final ArgumentTypeFactory<CommandActor> STRING = (parameter) -> {
      if (parameter.type() == String.class) {
         return parameter.isGreedy() ? StringArgumentType.greedyString() : StringArgumentType.string();
      } else {
         return null;
      }
   };
   public static final ArgumentTypeFactory<CommandActor> INTEGER = (parameter) -> {
      if (Classes.wrap(parameter.type()) == Integer.class) {
         Range range = (Range)parameter.annotations().get(Range.class);
         return range == null ? IntegerArgumentType.integer() : IntegerArgumentType.integer((int)range.min(), (int)range.max());
      } else {
         return null;
      }
   };
   public static final ArgumentTypeFactory<CommandActor> SHORT = (parameter) -> {
      if (Classes.wrap(parameter.type()) == Short.class) {
         Range range = (Range)parameter.annotations().get(Range.class);
         return range == null ? IntegerArgumentType.integer(-32768, 32767) : IntegerArgumentType.integer((short)((int)range.min()), (short)((int)range.max()));
      } else {
         return null;
      }
   };
   public static final ArgumentTypeFactory<CommandActor> BYTE = (parameter) -> {
      if (Classes.wrap(parameter.type()) == Byte.class) {
         Range range = (Range)parameter.annotations().get(Range.class);
         return range == null ? IntegerArgumentType.integer(-128, 127) : IntegerArgumentType.integer((byte)((int)range.min()), (byte)((int)range.max()));
      } else {
         return null;
      }
   };
   public static final ArgumentTypeFactory<CommandActor> LONG = (parameter) -> {
      if (Classes.wrap(parameter.type()) == Long.class) {
         Range range = (Range)parameter.annotations().get(Range.class);
         return range == null ? LongArgumentType.longArg() : LongArgumentType.longArg((long)range.min(), (long)range.max());
      } else {
         return null;
      }
   };
   public static final ArgumentTypeFactory<CommandActor> DOUBLE = (parameter) -> {
      if (Classes.wrap(parameter.type()) == Double.class) {
         Range range = (Range)parameter.annotations().get(Range.class);
         return range == null ? DoubleArgumentType.doubleArg() : DoubleArgumentType.doubleArg(range.min(), range.max());
      } else {
         return null;
      }
   };
   public static final ArgumentTypeFactory<CommandActor> FLOAT = (parameter) -> {
      if (Classes.wrap(parameter.type()) == Float.class) {
         Range range = (Range)parameter.annotations().get(Range.class);
         return range == null ? FloatArgumentType.floatArg() : FloatArgumentType.floatArg((float)range.min(), (float)range.max());
      } else {
         return null;
      }
   };
   public static final ArgumentTypeFactory<CommandActor> BOOLEAN;
   public static final ArgumentTypeFactory<CommandActor> CHAR;

   private DefaultTypeFactories() {
   }

   static {
      BOOLEAN = ArgumentTypeFactory.<CommandActor>forType(Boolean.TYPE, BoolArgumentType.bool());
      CHAR = ArgumentTypeFactory.<CommandActor>forType(Character.TYPE, StringArgumentType.string());
   }
}
