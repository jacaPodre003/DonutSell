package revxrsal.commands.node.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.CommandAction;
import revxrsal.commands.node.CommandNode;

abstract class MutableCommandNode<A extends CommandActor> {
   private final @NotNull String name;
   private @Nullable CommandAction<A> action;
   private boolean last;

   public abstract CommandNode<A> toNode();

   public @NotNull String getName() {
      return this.name;
   }

   public @Nullable CommandAction<A> getAction() {
      return this.action;
   }

   public boolean isLast() {
      return this.last;
   }

   public void setAction(@Nullable CommandAction<A> action) {
      this.action = action;
   }

   public void setLast(boolean last) {
      this.last = last;
   }

   public String toString() {
      return "MutableCommandNode(name=" + this.getName() + ", action=" + this.getAction() + ", last=" + this.isLast() + ")";
   }

   public MutableCommandNode(@NotNull String name) {
      this.name = name;
   }
}
