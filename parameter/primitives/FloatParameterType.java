package revxrsal.commands.parameter.primitives;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;

public final class FloatParameterType implements ParameterType<CommandActor, Float> {
   public Float parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<CommandActor> context) {
      return input.readFloat();
   }
}
