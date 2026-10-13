package revxrsal.commands.parameter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.Range;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.NumberNotInRangeException;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.process.ParameterValidator;

@Internal
public enum RangeChecker implements ParameterValidator<CommandActor, Number> {
   INSTANCE;

   public void validate(@NotNull CommandActor actor, Number value, @NotNull ParameterNode<CommandActor, Number> parameter, @NotNull Lamp<CommandActor> lamp) {
      Range range = (Range)parameter.annotations().get(Range.class);
      if (range != null) {
         if (value != null) {
            if (value.doubleValue() > range.max() || value.doubleValue() < range.min()) {
               throw new NumberNotInRangeException(value, range.min(), range.max());
            }
         }
      }
   }
}
