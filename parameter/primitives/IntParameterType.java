package revxrsal.commands.parameter.primitives;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.parameter.PrioritySpec;
import revxrsal.commands.stream.MutableStringStream;

public final class IntParameterType implements ParameterType<CommandActor, Integer> {
   private static final PrioritySpec PRIORITY = PrioritySpec.builder().higherThan(DoubleParameterType.class).higherThan(FloatParameterType.class).higherThan(LongParameterType.class).build();

   public Integer parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<CommandActor> context) {
      return input.readInt();
   }

   public @NotNull PrioritySpec parsePriority() {
      return PRIORITY;
   }
}
