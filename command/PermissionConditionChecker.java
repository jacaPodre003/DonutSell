package revxrsal.commands.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.exception.NoPermissionException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.process.CommandCondition;

@Internal
public enum PermissionConditionChecker implements CommandCondition<CommandActor> {
   INSTANCE;

   public void test(@NotNull ExecutionContext<CommandActor> context) {
      if (!context.command().permission().isExecutableBy(context.actor())) {
         throw new NoPermissionException(context.command());
      }
   }
}
