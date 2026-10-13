package revxrsal.commands.node.parser;

import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.help.Help;

final class HelpImpl {
   abstract static class CommandListImpl<A extends CommandActor> implements Help.CommandList<A> {
      private final @Unmodifiable List<ExecutableCommand<A>> commands;

      public CommandListImpl(@Unmodifiable List<ExecutableCommand<A>> commands) {
         this.commands = commands;
      }

      public @Range(
   from = 1L,
   to = 2147483647L
) int numberOfPages(int elementsPerPage) {
         return Help.numberOfPages(this.commands.size(), elementsPerPage);
      }

      public @Unmodifiable List<ExecutableCommand<A>> all() {
         return this.commands;
      }

      public @Unmodifiable List<ExecutableCommand<A>> paginate(int pageNumber, int elementsPerPage) {
         return Help.paginate(this.commands, pageNumber, elementsPerPage);
      }

      public @NotNull Iterator<ExecutableCommand<A>> iterator() {
         return this.commands.iterator();
      }

      public String toString() {
         return this.getClass().getSimpleName() + "(commands=" + this.commands + ')';
      }
   }

   static final class RelatedCommandsImpl<A extends CommandActor> extends CommandListImpl<A> implements Help.RelatedCommands<A> {
      public RelatedCommandsImpl(@Unmodifiable List<ExecutableCommand<A>> commands) {
         super(commands);
      }
   }

   static final class ChildrenCommandsImpl<A extends CommandActor> extends CommandListImpl<A> implements Help.ChildrenCommands<A> {
      public ChildrenCommandsImpl(@Unmodifiable List<ExecutableCommand<A>> commands) {
         super(commands);
      }
   }

   static final class SiblingCommandsImpl<A extends CommandActor> extends CommandListImpl<A> implements Help.SiblingCommands<A> {
      public SiblingCommandsImpl(@Unmodifiable List<ExecutableCommand<A>> commands) {
         super(commands);
      }
   }
}
