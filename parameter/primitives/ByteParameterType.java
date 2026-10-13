package revxrsal.commands.parameter.primitives;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.parameter.PrioritySpec;
import revxrsal.commands.stream.MutableStringStream;

public final class ByteParameterType implements ParameterType<CommandActor, Byte> {
   private static final PrioritySpec PRIORITY = PrioritySpec.builder().higherThan(DoubleParameterType.class).higherThan(FloatParameterType.class).higherThan(LongParameterType.class).higherThan(IntParameterType.class).higherThan(ShortParameterType.class).build();

   public Byte parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<CommandActor> context) {
      return input.readByte();
   }

   public @NotNull PrioritySpec parsePriority() {
      return PRIORITY;
   }
}
