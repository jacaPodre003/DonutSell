package revxrsal.commands.node;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public interface HasDescription {
   @Contract(
      pure = true
   )
   @Nullable String description();
}
