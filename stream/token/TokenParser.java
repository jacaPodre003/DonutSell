package revxrsal.commands.stream.token;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.stream.MutableStringStream;

@Internal
public final class TokenParser {
   @Contract(
      mutates = "param1"
   )
   public static @NotNull Token parseNextToken(@NotNull MutableStringStream stream) {
      if (stream.peek() == '<') {
         stream.moveForward();
         String name = stream.readUntil('>');
         if (name.isEmpty()) {
            throw new ParseException("Cannot have <> for an argument name!");
         } else {
            return new ParameterToken(name);
         }
      } else {
         String name = stream.readUnquotedString();
         return new LiteralToken(name);
      }
   }

   public static class ParseException extends RuntimeException {
      public ParseException(String message) {
         super(message);
      }
   }
}
