package revxrsal.commands.stream;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.exception.InputParseException;

class BaseStringStream implements StringStream {
   protected static final char ESCAPE = '\\';
   protected static final char DOUBLE_QUOTE = '"';
   protected String source;
   protected int pos;

   BaseStringStream(@NotNull String source) {
      this(source, 0);
   }

   BaseStringStream(@NotNull String source, int position) {
      this.source = source;
      this.pos = position;
   }

   public @NotNull String source() {
      return this.source;
   }

   public int totalSize() {
      return this.source.length();
   }

   public int remaining() {
      return this.source.length() - this.pos;
   }

   @Contract(
      pure = true
   )
   public char peek() {
      return this.source.charAt(this.pos);
   }

   public String peek(int characters) {
      return !this.canRead(characters) ? this.peekRemaining() : this.source.substring(this.pos, this.pos + characters);
   }

   @Contract(
      pure = true
   )
   public char peekOffset(int offset) {
      return this.source.charAt(this.pos + offset);
   }

   public boolean hasRemaining() {
      return this.canRead(1);
   }

   public boolean hasFinished() {
      return !this.hasRemaining();
   }

   public boolean canRead(int characters) {
      return this.pos + characters <= this.source.length();
   }

   public int position() {
      return this.pos;
   }

   public @NotNull String readUnquotedString() {
      int start;
      for(start = this.pos; this.hasRemaining() && !Character.isWhitespace(this.peek()); ++this.pos) {
      }

      return this.source.substring(start, this.pos);
   }

   public @NotNull String readString() {
      if (!this.hasRemaining()) {
         return "";
      } else {
         char next = this.peek();
         if (next == '"') {
            ++this.pos;
            return this.readUntil('"');
         } else {
            return this.readUnquotedString();
         }
      }
   }

   public char read() {
      return this.source.charAt(this.pos++);
   }

   public @NotNull String readUntil(char delimiter) {
      return this.readUntil(delimiter, false);
   }

   public @NotNull String readUntil(char delimiter, boolean allowUnclosed) {
      StringBuilder result = new StringBuilder();
      boolean escaped = false;

      while(this.hasRemaining()) {
         char c = this.read();
         if (escaped) {
            if (c != delimiter && c != '\\') {
               --this.pos;
               throw new InputParseException(InputParseException.Cause.INVALID_ESCAPE_CHARACTER);
            }

            result.append(c);
            escaped = false;
         } else if (c == '\\') {
            escaped = true;
         } else {
            if (c == delimiter) {
               return result.toString();
            }

            result.append(c);
         }
      }

      if (allowUnclosed) {
         return result.toString();
      } else {
         throw new InputParseException(InputParseException.Cause.UNCLOSED_QUOTE);
      }
   }

   public @NotNull String peekString() {
      if (!this.hasRemaining()) {
         return "";
      } else {
         int cursor = this.pos;
         char next = this.peek();
         if (next == '"') {
            ++this.pos;
            String result = this.readUntil('"', true);
            this.pos = cursor;
            return result;
         } else {
            return this.peekUnquotedString();
         }
      }
   }

   public @NotNull String peekUnquotedString() {
      int cursor = this.pos;
      String value = this.readUnquotedString();
      this.pos = cursor;
      return value;
   }

   public @NotNull String peekRemaining() {
      return this.hasFinished() ? "" : this.source.substring(this.pos);
   }

   public @NotNull @Unmodifiable StringStream toImmutableCopy() {
      return new BaseStringStream(this.source, this.pos);
   }

   @Contract(
      value = "-> new",
      pure = true
   )
   public @NotNull MutableStringStream toMutableCopy() {
      return new MutableStringStreamImpl(this.source, this.pos);
   }

   public boolean isMutable() {
      return false;
   }

   public boolean isEmpty() {
      return this.source.isEmpty();
   }
}
