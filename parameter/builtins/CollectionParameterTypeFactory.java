package revxrsal.commands.parameter.builtins;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.Delimiter;
import revxrsal.commands.annotation.Sized;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.InputParseException;
import revxrsal.commands.exception.InvalidListSizeException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.parameter.PrioritySpec;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.util.Classes;
import revxrsal.commands.util.Collections;

@Internal
abstract class CollectionParameterTypeFactory implements ParameterType.Factory<CommandActor> {
   protected abstract boolean matchType(@NotNull Type var1, @NotNull Class<?> var2);

   protected abstract Type getElementType(@NotNull Type var1);

   protected abstract Object convert(List<Object> var1, Type var2);

   protected boolean preventsDuplicates() {
      return false;
   }

   @Nullable
   public <T> ParameterType<CommandActor, T> create(@NotNull Type parameterType, @NotNull AnnotationList annotations, @NotNull Lamp<CommandActor> lamp) {
      Class<?> rawType = Classes.getRawType(parameterType);
      if (!this.matchType(parameterType, rawType)) {
         return null;
      } else {
         Type elementType = this.getElementType(parameterType);
         if (elementType == null) {
            return null;
         } else {
            ParameterType<CommandActor, Object> componentType = lamp.resolver(elementType).requireParameterType(elementType);
            Sized sized = (Sized)annotations.get(Sized.class);
            int min = 0;
            int max = Integer.MAX_VALUE;
            if (sized != null) {
               min = sized.min();
               max = sized.max();
               if (min < 0 || max < 0 || max < min) {
                  throw new IllegalArgumentException("Illegal range input in @Sized");
               }
            }

            char delimiter = (Character)annotations.mapOr(Delimiter.class, Delimiter::value, ' ');
            return new CollectionParameterType(delimiter, min, max, componentType, elementType);
         }
      }
   }

   private final class CollectionParameterType implements ParameterType<CommandActor, Object> {
      private final char delimiter;
      private final int minSize;
      private final int maxSize;
      private final ParameterType<CommandActor, Object> componentType;
      private final PrioritySpec priority;
      private final Type elementType;

      public CollectionParameterType(char delimiter, int minSize, int maxSize, ParameterType<CommandActor, Object> componentType, Type elementType) {
         this.delimiter = delimiter;
         this.minSize = minSize;
         this.maxSize = maxSize;
         this.componentType = componentType;
         this.elementType = elementType;
         this.priority = componentType.parsePriority().toBuilder().lowerThan(componentType.getClass()).build();
      }

      private List<Object> parseList(@NotNull MutableStringStream input, @NotNull ExecutionContext<CommandActor> context) {
         List<Object> elements = new ArrayList();

         while(input.hasRemaining()) {
            Object el = this.componentType.parse(input, context);
            elements.add(el);
            if (input.hasRemaining()) {
               if (input.peek() != this.delimiter) {
                  throw new InputParseException(InputParseException.Cause.EXPECTED_WHITESPACE);
               }

               input.skipWhitespace();
            }
         }

         if (elements.size() <= this.maxSize && elements.size() >= this.minSize) {
            return elements;
         } else {
            throw new InvalidListSizeException(this.minSize, this.maxSize, elements.size(), elements);
         }
      }

      public Object parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<CommandActor> context) {
         List<Object> objects = this.parseList(input, context);
         return CollectionParameterTypeFactory.this.convert(objects, this.elementType);
      }

      public @NotNull SuggestionProvider<CommandActor> defaultSuggestions() {
         SuggestionProvider<CommandActor> paramSuggestions = this.componentType.defaultSuggestions();
         return paramSuggestions.equals(SuggestionProvider.empty()) ? SuggestionProvider.empty() : (context) -> {
            List<String> inputted = Arrays.asList(context.input().peekRemaining().split(Character.toString(this.delimiter)));
            return (Collection)(CollectionParameterTypeFactory.this.preventsDuplicates() ? Collections.filter(paramSuggestions.getSuggestions(context), (c) -> !inputted.contains(c)) : paramSuggestions.getSuggestions(context));
         };
      }

      public @NotNull PrioritySpec parsePriority() {
         return this.priority;
      }

      public boolean isGreedy() {
         return this.maxSize == Integer.MAX_VALUE;
      }
   }
}
