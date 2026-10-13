package revxrsal.commands.brigadier.types;

import com.mojang.brigadier.arguments.ArgumentType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.util.Classes;

@FunctionalInterface
public interface ArgumentTypeFactory<A extends CommandActor> {
   static <A extends CommandActor> @NotNull ArgumentTypeFactory<A> forType(Class<?> type, ArgumentType<?> argumentType) {
      Class<?> wrapped = Classes.wrap(type);
      return (parameter) -> Classes.wrap(parameter.type()) == wrapped ? argumentType : null;
   }

   static <A extends CommandActor> @NotNull ArgumentTypeFactory<A> forTypeAndSubclasses(Class<?> type, ArgumentType<?> argumentType) {
      Class<?> wrapped = Classes.wrap(type);
      return (parameter) -> wrapped.isAssignableFrom(Classes.wrap(parameter.type())) ? argumentType : null;
   }

   @Nullable ArgumentType<?> getArgumentType(@NotNull ParameterNode<A, ?> var1);
}
