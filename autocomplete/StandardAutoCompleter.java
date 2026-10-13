package revxrsal.commands.autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.stream.StringStream;

final class StandardAutoCompleter<A extends CommandActor> implements AutoCompleter<A> {
   private final Lamp<A> lamp;

   public StandardAutoCompleter(Lamp<A> lamp) {
      this.lamp = lamp;
   }

   public @NotNull List<String> complete(@NotNull A actor, @NotNull String input) {
      return this.complete(actor, StringStream.create(input));
   }

   public @NotNull List<String> complete(@NotNull A actor, @NotNull StringStream input) {
      Set<String> suggestions = new LinkedHashSet();
      if (input.isEmpty()) {
         return Collections.emptyList();
      } else {
         String firstWord = input.peekUnquotedString();

         for(ExecutableCommand<A> possible : this.lamp.registry().commands()) {
            if (!possible.isSecret() && possible.firstNode().name().startsWith(firstWord) && possible.permission().isExecutableBy(actor)) {
               suggestions.addAll(this.complete(possible, input.toMutableCopy(), actor));
            }
         }

         return new ArrayList(suggestions);
      }
   }

   private List<String> complete(ExecutableCommand<A> possible, MutableStringStream input, A actor) {
      SingleCommandCompleter<A> commandCompleter = new SingleCommandCompleter<A>(actor, possible, input);
      commandCompleter.complete();
      return commandCompleter.suggestions();
   }
}
