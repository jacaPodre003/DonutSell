package revxrsal.commands.autocomplete;

import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.SuggestWith;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.util.InstanceCreator;

enum SuggestWithProviderFactory implements SuggestionProvider.Factory<CommandActor> {
   INSTANCE;

   public @Nullable SuggestionProvider<CommandActor> create(@NotNull Type parameterType, @NotNull AnnotationList annotations, @NotNull Lamp<CommandActor> lamp) {
      SuggestWith suggestWith = (SuggestWith)annotations.get(SuggestWith.class);
      if (suggestWith == null) {
         return null;
      } else {
         BaseSuggestionProvider type = (BaseSuggestionProvider)InstanceCreator.create(suggestWith.value());
         if (type instanceof SuggestionProvider) {
            SuggestionProvider<?> pType = (SuggestionProvider)type;
            return pType;
         } else if (type instanceof SuggestionProvider.Factory) {
            SuggestionProvider.Factory<?> factory = (SuggestionProvider.Factory)type;
            return factory.create(parameterType, annotations, lamp);
         } else if (type instanceof AsyncSuggestionProvider) {
            AsyncSuggestionProvider<?> async = (AsyncSuggestionProvider)type;
            return SuggestionProvider.<CommandActor>fromAsync(async);
         } else {
            throw new IllegalArgumentException("Don't know how to create a SuggestionProvider from " + type);
         }
      }
   }
}
