package revxrsal.commands.node;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandPermission;

public interface RequiresPermission<A extends CommandActor> {
   @NotNull CommandPermission<A> permission();
}
