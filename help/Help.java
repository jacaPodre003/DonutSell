package revxrsal.commands.help;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.exception.InvalidHelpPageException;

public interface Help {
   static <A extends CommandActor> @NotNull @Unmodifiable List<ExecutableCommand<A>> paginate(@NotNull List<ExecutableCommand<A>> commands, @Range(
   from = 1L,
   to = 2147483647L
) int page, @Range(
   from = 1L,
   to = 2147483647L
) int elementsPerPage) throws InvalidHelpPageException {
      if (commands.isEmpty()) {
         return Collections.emptyList();
      } else {
         int size = numberOfPages(commands.size(), elementsPerPage);
         if (page <= 0) {
            throw new InvalidHelpPageException(commands, page, elementsPerPage, size);
         } else {
            List<ExecutableCommand<A>> list = new ArrayList();
            if (page > size) {
               throw new InvalidHelpPageException(commands, page, elementsPerPage, size);
            } else {
               int listIndex = page - 1;
               int l = Math.min(page * elementsPerPage, commands.size());

               for(int i = listIndex * elementsPerPage; i < l; ++i) {
                  list.add(commands.get(i));
               }

               return Collections.unmodifiableList(list);
            }
         }
      }
   }

   static @Range(
   from = 1L,
   to = Long.MAX_VALUE
) int numberOfPages(@Range(
   from = 0L,
   to = 2147483647L
) int numberOfEntries, @Range(
   from = 1L,
   to = 2147483647L
) int elementsPerPage) {
      if (elementsPerPage < 1) {
         throw new IllegalArgumentException("Elements per page cannot be less than 1! (Found " + elementsPerPage + ")");
      } else {
         return numberOfEntries / elementsPerPage + (numberOfEntries % elementsPerPage == 0 ? 0 : 1);
      }
   }

   public interface ChildrenCommands<A extends CommandActor> extends CommandList<A> {
   }

   public interface CommandList<A extends CommandActor> extends Iterable<ExecutableCommand<A>> {
      @Unmodifiable List<ExecutableCommand<A>> all();

      @Range(
   from = 1L,
   to = 2147483647L
) int numberOfPages(@Range(
   from = 1L,
   to = 2147483647L
) int var1);

      @Unmodifiable List<ExecutableCommand<A>> paginate(@Range(
   from = 1L,
   to = 2147483647L
) int var1, @Range(
   from = 1L,
   to = 2147483647L
) int var2) throws InvalidHelpPageException;
   }

   public interface RelatedCommands<A extends CommandActor> extends CommandList<A> {
   }

   public interface SiblingCommands<A extends CommandActor> extends CommandList<A> {
   }
}
