package revxrsal.commands.node.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.CommandAction;
import revxrsal.commands.node.CommandNode;
import revxrsal.commands.node.LiteralNode;

final class LiteralNodeImpl<A extends CommandActor> extends BaseCommandNode<A> implements LiteralNode<A> {
   public LiteralNodeImpl(@NotNull String name, @Nullable CommandAction<A> action, boolean isLast) {
      super(name, action, isLast);
   }

   public String toString() {
      return "LiteralNode(name='" + this.name() + "')";
   }

   public int compareTo(@NotNull CommandNode<A> o) {
      return o instanceof ParameterNodeImpl ? -1 : 0;
   }
}
