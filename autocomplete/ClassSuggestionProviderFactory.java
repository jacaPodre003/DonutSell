package revxrsal.commands.autocomplete;

import java.lang.reflect.Type;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.util.Classes;

final class ClassSuggestionProviderFactory<A extends CommandActor> implements SuggestionProvider.Factory<A> {
   private final Class<?> type;
   private final SuggestionProvider<A> provider;
   private final boolean allowSubclasses;

   ClassSuggestionProviderFactory(Class<?> type, SuggestionProvider<A> provider, boolean allowSubclasses) {
      this.type = Classes.wrap(type);
      this.provider = provider;
      this.allowSubclasses = allowSubclasses;
   }

   public @Nullable SuggestionProvider<A> create(@NotNull Type parameterType, @NotNull AnnotationList annotations, @NotNull Lamp<A> lamp) {
      Class<?> pType = Classes.wrap(Classes.getRawType(parameterType));
      if (this.allowSubclasses && this.type.isAssignableFrom(pType)) {
         return this.provider;
      } else {
         return this.type == pType ? this.provider : null;
      }
   }

   public Class<?> type() {
      return this.type;
   }

   public SuggestionProvider<A> provider() {
      return this.provider;
   }

   public boolean allowSubclasses() {
      return this.allowSubclasses;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         ClassSuggestionProviderFactory that = (ClassSuggestionProviderFactory)obj;
         return Objects.equals(this.type, that.type) && Objects.equals(this.provider, that.provider) && this.allowSubclasses == that.allowSubclasses;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.type, this.provider, this.allowSubclasses});
   }

   public String toString() {
      return "ClassSuggestionProviderFactory[type=" + this.type + ", provider=" + this.provider + ", allowSubclasses=" + this.allowSubclasses + ']';
   }
}
