package revxrsal.commands.autocomplete;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.util.Classes;

@FunctionalInterface
public interface SuggestionProvider<A extends CommandActor> extends BaseSuggestionProvider {
   static <A extends CommandActor> @NotNull SuggestionProvider<A> empty() {
      return EmptySuggestionProvider.INSTANCE;
   }

   @Contract("_ -> new")
   static <A extends CommandActor> @NotNull SuggestionProvider<A> fromAsync(@NotNull AsyncSuggestionProvider<A> provider) {
      return new WrapperAsyncSuggestionProvider<A>(provider);
   }

   static <A extends CommandActor> @NotNull SuggestionProvider<A> of(String... suggestions) {
      if (suggestions != null && suggestions.length != 0) {
         List<String> list = Arrays.asList(suggestions);
         return (context) -> list;
      } else {
         return empty();
      }
   }

   static <A extends CommandActor> @NotNull SuggestionProvider<A> of(@NotNull List<String> suggestions) {
      return suggestions.isEmpty() ? empty() : (context) -> suggestions;
   }

   @NotNull Collection<String> getSuggestions(@NotNull ExecutionContext<A> var1);

   public interface Factory<A extends CommandActor> extends BaseSuggestionProvider {
      static <A extends CommandActor> Factory<? super A> forType(@NotNull Class<?> type, @NotNull SuggestionProvider<A> provider) {
         return new ClassSuggestionProviderFactory<A>(type, provider, false);
      }

      static <A extends CommandActor> Factory<? super A> forTypeAndSubclasses(@NotNull Class<?> type, @NotNull SuggestionProvider<A> provider) {
         return new ClassSuggestionProviderFactory<A>(type, provider, true);
      }

      static <A extends CommandActor, L extends Annotation> @NotNull @NotNull Factory<? super A> forAnnotation(@NotNull Class<L> annotationType, @NotNull Function<L, SuggestionProvider<A>> provider) {
         Classes.checkRetention(annotationType);
         return (type, annotations, lamp) -> {
            L annotation = (L)annotations.get(annotationType);
            return annotation != null ? (SuggestionProvider)provider.apply(annotation) : null;
         };
      }

      @Nullable SuggestionProvider<A> create(@NotNull Type var1, @NotNull AnnotationList var2, @NotNull Lamp<A> var3);
   }
}
