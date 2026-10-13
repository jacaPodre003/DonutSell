package revxrsal.commands.util;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.list.AnnotationList;

public final class Strings {
   public static final Pattern SPACE = Pattern.compile(" ", 16);
   public static final Pattern SNOWFLAKE = Pattern.compile("<(@!|@|@&|#)(?<snowflake>\\d{18})>");

   private Strings() {
      Preconditions.cannotInstantiate(Strings.class);
   }

   public static @Nullable String getSnowflake(String mention) {
      Matcher matcher = SNOWFLAKE.matcher(mention);
      return matcher.find() ? matcher.group(2) : null;
   }

   public static Optional<String> getOverriddenName(@NotNull AnnotationList parameter) {
      Named named = (Named)parameter.get(Named.class);
      return named != null ? Optional.of(named.value()) : Optional.empty();
   }

   public static @NotNull String stripNamespace(String namespace, @NotNull String command) {
      int colon = command.indexOf(namespace + ':');
      return colon == -1 ? command : command.substring(namespace.length() + 1);
   }

   public static @NotNull String stripNamespace(@NotNull String command) {
      int colon = command.indexOf(58);
      return colon == -1 ? command : command.substring(colon + 1);
   }

   public static @NotNull String removeRanges(@NotNull String input, @NotNull List<StringRange> ranges) {
      ranges.sort(Comparator.comparingInt(StringRange::start));
      StringBuilder builder = new StringBuilder();
      int currentIndex = 0;

      for(StringRange range : ranges) {
         if (currentIndex < range.start()) {
            builder.append(input, currentIndex, range.start());
         }

         currentIndex = range.end();
      }

      if (currentIndex < input.length()) {
         builder.append(input, currentIndex, input.length());
      }

      return builder.toString();
   }

   public static final class StringRange {
      private final int start;
      private final int end;

      public StringRange(int start, int end) {
         this.start = start;
         this.end = end;
      }

      public int start() {
         return this.start;
      }

      public int end() {
         return this.end;
      }

      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else if (obj != null && obj.getClass() == this.getClass()) {
            StringRange that = (StringRange)obj;
            return this.start == that.start && this.end == that.end;
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.start, this.end});
      }

      public String toString() {
         return "StringRange[start=" + this.start + ", end=" + this.end + ']';
      }
   }
}
