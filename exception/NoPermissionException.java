package revxrsal.commands.exception;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.RequiresPermission;

@ThrowableFromCommand
public class NoPermissionException extends RuntimeException {
   private final @NotNull RequiresPermission<?> target;

   public NoPermissionException(@NotNull RequiresPermission<?> target) {
      this.target = target;
   }

   public <A extends CommandActor> @NotNull RequiresPermission<A> target() {
      return this.target;
   }
}
