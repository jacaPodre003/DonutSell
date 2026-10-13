package revxrsal.commands.parameter.primitives;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.parameter.PrioritySpec;
import revxrsal.commands.parameter.StringParameterType;
import revxrsal.commands.stream.MutableStringStream;

public final class CharParameterType implements ParameterType<CommandActor, Character> {
   private static final PrioritySpec PRIORITY = PrioritySpec.lowest().toBuilder().higherThan(StringParameterType.class).build();

   public Character parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<CommandActor> context) {
      return input.read();
   }

   public @NotNull PrioritySpec parsePriority() {
      return PRIORITY;
   }
}
