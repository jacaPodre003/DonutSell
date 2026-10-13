package revxrsal.commands.autocomplete;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.util.Preconditions;

public final class SuggestionProviders<A extends CommandActor> {
   private static final List<SuggestionProvider.Factory<?>> DEFAULT_FACTORIES;
   private final List<SuggestionProvider.Factory<? super A>> factories;
   private final int lastIndex;

   private SuggestionProviders(Builder<A> builder) {
      List<SuggestionProvider.Factory<? super A>> factories = new ArrayList(builder.factories.size() + DEFAULT_FACTORIES.size());
      factories.addAll(builder.factories);
      factories.addAll(DEFAULT_FACTORIES);
      this.factories = factories;
      this.lastIndex = builder.lastIndex;
   }

   @Contract(
      value = "-> new",
      pure = true
   )
   public static <A extends CommandActor> @NotNull Builder<A> builder() {
      return new Builder<A>();
   }

   public @NotNull SuggestionProvider<A> provider(@NotNull CommandParameter parameter, @NotNull Lamp<A> lamp) {
      Preconditions.notNull(parameter, "parameter");
      Preconditions.notNull(lamp, "Lamp");
      return this.provider(parameter.fullType(), parameter.annotations(), lamp);
   }

   public @NotNull SuggestionProvider<A> provider(@NotNull Type type, @NotNull AnnotationList annotations, @NotNull Lamp<A> lamp) {
      Preconditions.notNull(type, "type");
      Preconditions.notNull(annotations, "annotations");
      Preconditions.notNull(lamp, "Lamp");

      for(SuggestionProvider.Factory<? super A> factory : this.factories) {
         SuggestionProvider<A> provider = factory.create(type, annotations, lamp);
         if (provider != null) {
            return provider;
         }
      }

      return SuggestionProvider.<A>empty();
   }

   public @NotNull SuggestionProvider<A> findNextProvider(@NotNull Type type, @NotNull AnnotationList annotations, SuggestionProvider.@NotNull Factory<? super A> skipPast, @NotNull Lamp<A> lamp) {
      int skipPastIndex = this.factories.indexOf(skipPast);
      if (skipPastIndex == -1) {
         throw new IllegalArgumentException("Don't know how to skip past unknown provider factory: " + skipPastIndex + " (it isn't registered?)");
      } else {
         int i = skipPastIndex + 1;

         for(int size = this.factories.size(); i < size; ++i) {
            SuggestionProvider.Factory<? super A> factory = (SuggestionProvider.Factory)this.factories.get(i);
            SuggestionProvider<A> parameterType = factory.create(type, annotations, lamp);
            if (parameterType != null) {
               return parameterType;
            }
         }

         return SuggestionProvider.<A>empty();
      }
   }

   @Contract(
      value = "-> new",
      pure = true
   )
   public @NotNull Builder<A> toBuilder() {
      Builder<A> result = new Builder<A>();

      for(int i = 0; i < this.lastIndex; ++i) {
         result.addProviderFactory((SuggestionProvider.Factory)this.factories.get(i));
      }

      int i = this.lastIndex;

      for(int limit = this.factories.size() - DEFAULT_FACTORIES.size(); i < limit; ++i) {
         result.addProviderFactoryLast((SuggestionProvider.Factory)this.factories.get(i));
      }

      return result;
   }

   static {
      DEFAULT_FACTORIES = Arrays.asList(SuggestAnnotationProviderFactory.INSTANCE, SuggestWithProviderFactory.INSTANCE);
   }

   public static class Builder<A extends CommandActor> {
      private final List<SuggestionProvider.Factory<? super A>> factories = new ArrayList();
      private int lastIndex = 0;

      public @NotNull Builder<A> addProvider(@NotNull Class<?> type, @NotNull SuggestionProvider<A> provider) {
         this.addProviderFactory(SuggestionProvider.Factory.forType(type, provider));
         return this;
      }

      public @NotNull Builder<A> addProviderLast(@NotNull Class<?> type, @NotNull SuggestionProvider<A> provider) {
         Preconditions.notNull(type, "type");
         this.addProviderFactoryLast(SuggestionProvider.Factory.forType(type, provider));
         return this;
      }

      public <L extends Annotation> @NotNull Builder<A> addProviderForAnnotation(@NotNull Class<L> type, @NotNull Function<L, SuggestionProvider<A>> provider) {
         this.addProviderFactory(SuggestionProvider.Factory.forAnnotation(type, provider));
         return this;
      }

      public <L extends Annotation> @NotNull Builder<A> addProviderForAnnotationLast(@NotNull Class<L> type, @NotNull Function<L, SuggestionProvider<A>> provider) {
         this.addProviderFactoryLast(SuggestionProvider.Factory.forAnnotation(type, provider));
         return this;
      }

      public Builder<A> addProviderFactory(SuggestionProvider.@NotNull Factory<? super A> factory) {
         Preconditions.notNull(factory, "factory");
         this.factories.add(this.lastIndex++, factory);
         return this;
      }

      public Builder<A> addProviderFactoryLast(SuggestionProvider.@NotNull Factory<? super A> factory) {
         Preconditions.notNull(factory, "factory");
         this.factories.add(factory);
         return this;
      }

      @Contract(
         pure = true,
         value = "-> new"
      )
      public @NotNull SuggestionProviders<A> build() {
         return new SuggestionProviders<A>(this);
      }
   }
}
