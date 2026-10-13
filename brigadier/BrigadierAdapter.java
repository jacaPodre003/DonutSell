package revxrsal.commands.brigadier;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType.StringType;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.IntegerSuggestion;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.autocomplete.AsyncSuggestionProvider;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.Potential;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.stream.StringStream;
import revxrsal.commands.util.Strings;

public final class BrigadierAdapter {
   public static <S, A extends CommandActor> com.mojang.brigadier.suggestion.@Nullable SuggestionProvider<S> createSuggestionProvider(ParameterNode<A, ?> parameter, BrigadierConverter<A, S> converter) {
      SuggestionProvider<A> suggestions = parameter.suggestions();
      if (suggestions.equals(SuggestionProvider.empty())) {
         if (parameter.parameterType() instanceof BrigadierParameterType) {
            BrigadierParameterType<?, ?> brigadierParameterType = (BrigadierParameterType)parameter.parameterType();
            ArgumentType var10000 = brigadierParameterType.argumentType;
            return var10000::listSuggestions;
         } else {
            return null;
         }
      } else {
         String tooltipMessage = parameter.description() == null ? parameter.name() : parameter.description();
         return (context, builder) -> {
            A actor = converter.createActor(context.getSource(), parameter.lamp());
            Message tooltip = new LiteralMessage(tooltipMessage);
            String input = context.getInput();
            MutableStringStream stream = StringStream.createMutable(input.startsWith("/") ? input.substring(1) : input);
            if (stream.peekUnquotedString().indexOf(58) != -1) {
               stream = StringStream.createMutable(Strings.stripNamespace(input));
            }

            Potential<A> test = parameter.command().test(actor, stream.toMutableCopy());
            if (suggestions instanceof AsyncSuggestionProvider) {
               return provideAsyncCompletions((AsyncSuggestionProvider)suggestions, builder, test.context(), tooltip);
            } else {
               List<Suggestion> values = (List)suggestions.getSuggestions(test.context()).stream().sorted(String.CASE_INSENSITIVE_ORDER).distinct().map((s) -> toSuggestion(s, builder, tooltip)).collect(Collectors.toList());
               return CompletableFuture.completedFuture(Suggestions.create(builder.getInput(), values));
            }
         };
      }
   }

   public static <A extends CommandActor> @NotNull CompletableFuture<Suggestions> provideAsyncCompletions(@NotNull AsyncSuggestionProvider<A> suggestions, @NotNull SuggestionsBuilder builder, @NotNull ExecutionContext<A> context, @Nullable Message tooltip) {
      CompletableFuture<Collection<String>> completions = suggestions.getSuggestionsAsync(context);
      return completions.thenApply((strings) -> Suggestions.create(builder.getInput(), (Collection)strings.stream().sorted(String.CASE_INSENSITIVE_ORDER).distinct().map((v) -> toSuggestion(v, builder, tooltip)).collect(Collectors.toList())));
   }

   private static @NotNull Suggestion toSuggestion(@NotNull String value, @NotNull SuggestionsBuilder builder, @Nullable Message tooltip) {
      try {
         int intValue = Integer.parseInt(value);
         return new IntegerSuggestion(StringRange.between(builder.getStart(), builder.getInput().length()), intValue, tooltip);
      } catch (NumberFormatException var4) {
         return new Suggestion(StringRange.between(builder.getStart(), builder.getInput().length()), value, tooltip);
      }
   }

   public static <A extends CommandActor, T> @NotNull ParameterType<A, T> toParameterType(@NotNull ArgumentType<T> argumentType) {
      return new BrigadierParameterType<A, T>(argumentType);
   }

   private static final class BrigadierParameterType<A extends CommandActor, T> implements ParameterType<A, T> {
      private final ArgumentType<T> argumentType;

      private BrigadierParameterType(ArgumentType<T> argumentType) {
         this.argumentType = argumentType;
      }

      public boolean isGreedy() {
         if (this.argumentType instanceof StringArgumentType) {
            StringArgumentType sat = (StringArgumentType)this.argumentType;
            return sat.getType() == StringType.GREEDY_PHRASE;
         } else {
            return false;
         }
      }

      public T parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<A> context) {
         try {
            StringReader reader = new StringReader(input.source());
            reader.setCursor(input.position());
            T result = (T)this.argumentType.parse(reader);
            input.setPosition(reader.getCursor());
            return result;
         } catch (Throwable $ex) {
            throw $ex;
         }
      }

      public ArgumentType<T> argumentType() {
         return this.argumentType;
      }

      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else if (obj != null && obj.getClass() == this.getClass()) {
            BrigadierParameterType<A, ?> that = (BrigadierParameterType)obj;
            return Objects.equals(this.argumentType, that.argumentType);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.argumentType});
      }

      public String toString() {
         return "BrigadierParameterType[argumentType=" + this.argumentType + ']';
      }
   }
}
