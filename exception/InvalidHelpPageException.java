package revxrsal.commands.exception;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;

@ThrowableFromCommand
public class InvalidHelpPageException extends RuntimeException {
   private final List<ExecutableCommand<CommandActor>> commands;
   private final int page;
   private final int elementsPerPage;
   private final int numberOfPages;

   public <A extends CommandActor> InvalidHelpPageException(List<ExecutableCommand<A>> commands, int page, int elementsPerPage, int numberOfPages) {
      this.commands = commands;
      this.page = page;
      this.elementsPerPage = elementsPerPage;
      this.numberOfPages = numberOfPages;
   }

   public int elementsPerPage() {
      return this.elementsPerPage;
   }

   public int page() {
      return this.page;
   }

   public @Range(
   from = 1L,
   to = 2147483647L
) int numberOfPages() {
      return this.numberOfPages;
   }

   public <A extends CommandActor> @NotNull @Unmodifiable List<ExecutableCommand<A>> commands() {
      return this.commands;
   }
}
