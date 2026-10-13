package revxrsal.commands.stream;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.exception.InputParseException;
import revxrsal.commands.exception.InvalidBooleanException;
import revxrsal.commands.exception.InvalidDecimalException;
import revxrsal.commands.exception.InvalidIntegerException;

public final class MutableStringStreamImpl extends BaseStringStream implements MutableStringStream {
   private StringStreamView immutableView = null;

   MutableStringStreamImpl(String source) {
      super(source);
   }

   MutableStringStreamImpl(String source, int position) {
      super(source, position);
   }

   public String read(int characters) {
      return !this.canRead(characters) ? this.consumeRemaining() : this.source.substring(this.pos, this.pos += characters);
   }

   public void moveForward() {
      this.moveForward(1);
   }

   public void skipWhitespace() {
      while(this.hasRemaining() && this.peek() == ' ') {
         this.moveForward();
      }

   }

   public void moveBackward() {
      this.moveBackward(1);
   }

   public void moveForward(int by) {
      this.pos += by;
   }

   public void moveBackward(int by) {
      this.pos = Math.max(0, this.pos - by);
   }

   public @NotNull String consumeRemaining() {
      if (this.hasFinished()) {
         return "";
      } else {
         String v = this.source.substring(this.pos);
         this.skipToEnd();
         return v;
      }
   }

   public void skipToEnd() {
      this.pos = this.source.length();
   }

   @Internal
   public void extend(@NotNull String str) {
      this.source = this.source + str;
   }

   public @NotNull String readUntil(char delimiter) {
      StringBuilder result = new StringBuilder();
      boolean escaped = false;

      while(this.hasRemaining()) {
         char c = this.read();
         if (escaped) {
            if (c != delimiter && c != '\\') {
               this.moveBackward(1);
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

      throw new InputParseException(InputParseException.Cause.UNCLOSED_QUOTE);
   }

   public @NotNull String readWhile(CharPredicate predicate) {
      int start = this.pos;

      while(this.hasRemaining() && predicate.test(this.peek())) {
         this.moveForward();
      }

      return this.source.substring(start, this.pos);
   }

   public float readFloat() {
      String value = this.readUnquotedString();

      try {
         float v = Float.parseFloat(value);
         if (Float.isFinite(v)) {
            return v;
         } else {
            throw new InvalidDecimalException(value);
         }
      } catch (NumberFormatException var3) {
         throw new InvalidDecimalException(value);
      }
   }

   public double readDouble() {
      String value = this.readUnquotedString();

      try {
         double v = Double.parseDouble(value);
         if (Double.isFinite(v)) {
            return v;
         } else {
            throw new InvalidDecimalException(value);
         }
      } catch (NumberFormatException var4) {
         throw new InvalidDecimalException(value);
      }
   }

   public int readInt() {
      String value = this.readUnquotedString();

      try {
         return Integer.parseInt(value);
      } catch (NumberFormatException var3) {
         throw new InvalidIntegerException(value);
      }
   }

   public long readLong() {
      String value = this.readUnquotedString();

      try {
         return Long.parseLong(value);
      } catch (NumberFormatException var3) {
         throw new InvalidIntegerException(value);
      }
   }

   public short readShort() {
      String value = this.readUnquotedString();

      try {
         return Short.parseShort(value);
      } catch (NumberFormatException var3) {
         throw new InvalidIntegerException(value);
      }
   }

   public byte readByte() {
      String value = this.readUnquotedString();

      try {
         return Byte.parseByte(value);
      } catch (NumberFormatException var3) {
         throw new InvalidIntegerException(value);
      }
   }

   public boolean readBoolean() {
      String value = this.readString();
      switch (value.toLowerCase(Locale.ENGLISH)) {
         case "true":
         case "yes":
            return true;
         case "false":
         case "no":
         case "nope":
            return false;
         default:
            throw new InvalidBooleanException(value);
      }
   }

   public void setPosition(int pos) {
      this.pos = pos;
   }

   public boolean isMutable() {
      return true;
   }

   public @NotNull @Unmodifiable StringStream toImmutableView() {
      if (this.immutableView == null) {
         this.immutableView = new StringStreamView();
      }

      return this.immutableView;
   }

   private class StringStreamView implements StringStream {
      private StringStreamView() {
      }

      public @NotNull String source() {
         return MutableStringStreamImpl.this.source();
      }

      public int totalSize() {
         return MutableStringStreamImpl.this.totalSize();
      }

      public int remaining() {
         return MutableStringStreamImpl.this.remaining();
      }

      public char peek() {
         return MutableStringStreamImpl.this.peek();
      }

      public String peek(int characters) {
         return MutableStringStreamImpl.this.peek(characters);
      }

      public char peekOffset(int offset) {
         return MutableStringStreamImpl.this.peekOffset(offset);
      }

      public boolean hasRemaining() {
         return MutableStringStreamImpl.this.hasRemaining();
      }

      public boolean hasFinished() {
         return MutableStringStreamImpl.this.hasFinished();
      }

      public boolean canRead(int characters) {
         return MutableStringStreamImpl.this.canRead(characters);
      }

      public int position() {
         return MutableStringStreamImpl.this.position();
      }

      public @NotNull String peekUnquotedString() {
         return MutableStringStreamImpl.this.peekUnquotedString();
      }

      public @NotNull String peekString() {
         return MutableStringStreamImpl.this.peekString();
      }

      public @NotNull String peekRemaining() {
         return MutableStringStreamImpl.this.peekRemaining();
      }

      public @NotNull @Unmodifiable StringStream toImmutableCopy() {
         return MutableStringStreamImpl.this.toImmutableCopy();
      }

      public @NotNull MutableStringStream toMutableCopy() {
         return MutableStringStreamImpl.this.toMutableCopy();
      }

      public boolean isMutable() {
         return false;
      }

      public boolean isEmpty() {
         return MutableStringStreamImpl.this.isEmpty();
      }
   }
}
