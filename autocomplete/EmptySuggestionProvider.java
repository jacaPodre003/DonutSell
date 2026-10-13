package revxrsal.commands.autocomplete;

import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;

final class EmptySuggestionProvider implements SuggestionProvider<CommandActor> {
   public static final EmptySuggestionProvider INSTANCE = new EmptySuggestionProvider();

   private EmptySuggestionProvider() {
   }

   public @NotNull List<String> getSuggestions(@NotNull ExecutionContext<CommandActor> context) {
      return Collections.emptyList();
   }

   public String toString() {
      return "SuggestionProvider.empty()";
   }

   public int hashCode() {
      return EmptySuggestionProvider.class.hashCode();
   }

   public boolean equals(Object obj) {
      return obj instanceof EmptySuggestionProvider;
   }
}
