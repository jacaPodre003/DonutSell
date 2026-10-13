package revxrsal.commands.autocomplete;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;

@FunctionalInterface
public interface AsyncSuggestionProvider<A extends CommandActor> extends BaseSuggestionProvider {
   static <A extends CommandActor> @NotNull AsyncSuggestionProvider<A> from(@NotNull SuggestionProvider<A> provider) {
      return (context) -> CompletableFuture.supplyAsync(() -> provider.getSuggestions(context));
   }

   @NotNull CompletableFuture<Collection<String>> getSuggestionsAsync(@NotNull ExecutionContext<A> var1);
}
