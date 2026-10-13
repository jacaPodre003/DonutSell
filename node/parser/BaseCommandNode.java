package revxrsal.commands.node.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.node.CommandAction;
import revxrsal.commands.node.CommandNode;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.stream.MutableStringStream;

abstract class BaseCommandNode<A extends CommandActor> implements CommandNode<A> {
   private final @NotNull String name;
   private final @Nullable CommandAction<A> action;
   private final boolean isLast;
   private Lamp<A> lamp;
   private ExecutableCommand<A> command;

   public void execute(@NotNull ExecutionContext<A> context, @NotNull MutableStringStream input) {
      if (this.action != null) {
         this.action.execute(context);
      }

   }

   public @NotNull String name() {
      return this.name;
   }

   public @Nullable CommandAction<A> action() {
      return this.action;
   }

   public boolean isLast() {
      return this.isLast;
   }

   public @NotNull Lamp<A> lamp() {
      return this.lamp;
   }

   public @NotNull ExecutableCommand<A> command() {
      return this.command;
   }

   public BaseCommandNode(@NotNull String name, @Nullable CommandAction<A> action, boolean isLast) {
      this.name = name;
      this.action = action;
      this.isLast = isLast;
   }

   public void setLamp(Lamp<A> lamp) {
      this.lamp = lamp;
   }

   public void setCommand(ExecutableCommand<A> command) {
      this.command = command;
   }
}
