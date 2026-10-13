package revxrsal.commands.parameter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.Length;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.InvalidStringSizeException;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.process.ParameterValidator;

@Internal
public enum LengthChecker implements ParameterValidator<CommandActor, String> {
   INSTANCE;

   public void validate(@NotNull CommandActor actor, String value, @NotNull ParameterNode<CommandActor, String> parameter, @NotNull Lamp<CommandActor> lamp) {
      Length range = (Length)parameter.annotations().get(Length.class);
      if (range != null) {
         if (value.length() > range.max() || value.length() < range.min()) {
            throw new InvalidStringSizeException(range.min(), range.max(), value);
         }
      }
   }
}
