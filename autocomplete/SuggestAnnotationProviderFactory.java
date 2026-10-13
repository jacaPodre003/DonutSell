package revxrsal.commands.autocomplete;

import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;

enum SuggestAnnotationProviderFactory implements SuggestionProvider.Factory<CommandActor> {
   INSTANCE;

   public @Nullable SuggestionProvider<CommandActor> create(@NotNull Type type, @NotNull AnnotationList annotations, @NotNull Lamp<CommandActor> lamp) {
      Suggest suggest = (Suggest)annotations.get(Suggest.class);
      return suggest == null ? null : SuggestionProvider.of(suggest.value());
   }
}
