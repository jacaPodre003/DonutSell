package revxrsal.commands.node.parser;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.CommandNode;
import revxrsal.commands.node.LiteralNode;

class MutableLiteralNode<A extends CommandActor> extends MutableCommandNode<A> {
   public MutableLiteralNode(@NotNull String name) {
      super(name);
   }

   public @NotNull LiteralNode<A> createNode() {
      return new LiteralNodeImpl<A>(this.getName(), this.getAction(), this.isLast());
   }

   public CommandNode<A> toNode() {
      return this.createNode();
   }

   public String toString() {
      return "MutableLiteralNode()";
   }
}
