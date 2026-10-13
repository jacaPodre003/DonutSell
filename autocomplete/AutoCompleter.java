package revxrsal.commands.autocomplete;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.stream.StringStream;

public interface AutoCompleter<A extends CommandActor> {
   @Internal
   static <A extends CommandActor> @NotNull AutoCompleter<A> create(@NotNull Lamp<A> lamp) {
      return new StandardAutoCompleter<A>(lamp);
   }

   @NotNull List<String> complete(@NotNull A var1, @NotNull String var2);

   @NotNull List<String> complete(@NotNull A var1, @NotNull StringStream var2);
}
