package revxrsal.commands.autocomplete;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;

final class WrapperAsyncSuggestionProvider<A extends CommandActor> implements SuggestionProvider<A>, AsyncSuggestionProvider<A> {
   private final @NotNull AsyncSuggestionProvider<A> provider;

   public WrapperAsyncSuggestionProvider(@NotNull AsyncSuggestionProvider<A> provider) {
      this.provider = provider;
   }

   public @NotNull Collection<String> getSuggestions(@NotNull ExecutionContext<A> context) {
      try {
         return (Collection)this.provider.getSuggestionsAsync(context).get();
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   public @NotNull CompletableFuture<Collection<String>> getSuggestionsAsync(@NotNull ExecutionContext<A> context) {
      return this.provider.getSuggestionsAsync(context);
   }
}
