package revxrsal.commands.node;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.stream.StringStream;

final class BasicMutableExecutionContext<A extends CommandActor> extends BasicExecutionContext<A> implements MutableExecutionContext<A> {
   public BasicMutableExecutionContext(ExecutableCommand<A> command, StringStream input, A actor) {
      super(command, input, actor);
   }

   public void addResolvedArgument(@NotNull String name, Object result) {
      Object old = this.resolvedArguments.put(name, result);
      if (old != null) {
         throw new IllegalArgumentException("A parameter with name '" + name + "' already exists!");
      }
   }

   public void clearResolvedArguments() {
      this.resolvedArguments.clear();
   }
}
