package revxrsal.commands.brigadier.types;

import com.mojang.brigadier.arguments.ArgumentType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.brigadier.annotations.BType;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.util.InstanceCreator;

enum BTypeFactory implements ArgumentTypeFactory<CommandActor> {
   INSTANCE;

   public @Nullable ArgumentType<?> getArgumentType(@NotNull ParameterNode<CommandActor, ?> parameter) {
      BType b = (BType)parameter.annotations().get(BType.class);
      if (b == null) {
         return null;
      } else {
         Object v = InstanceCreator.create(b.value());
         if (v instanceof ArgumentTypeFactory) {
            ArgumentTypeFactory<?> factory = (ArgumentTypeFactory)v;
            return factory.getArgumentType(parameter);
         } else if (v instanceof ArgumentType) {
            return (ArgumentType)v;
         } else {
            throw new IllegalArgumentException("Don't know how to create an ArgumentType from @BType(" + b.value().getName() + ".class)");
         }
      }
   }
}
