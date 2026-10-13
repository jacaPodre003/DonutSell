package revxrsal.commands.stream;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface MutableStringStream extends StringStream {
   char read();

   String read(int var1);

   void moveForward();

   void skipWhitespace();

   void moveBackward();

   void moveForward(int var1);

   void moveBackward(int var1);

   @NotNull String consumeRemaining();

   void skipToEnd();

   @NotNull String readUnquotedString();

   @NotNull String readString();

   @NotNull String readUntil(char var1);

   @NotNull String readWhile(CharPredicate var1);

   float readFloat();

   double readDouble();

   int readInt();

   long readLong();

   short readShort();

   byte readByte();

   boolean readBoolean();

   void setPosition(int var1);

   @Contract(
      pure = true,
      value = "-> new"
   )
   @NotNull @Unmodifiable StringStream toImmutableCopy();

   @Contract(
      pure = true,
      value = "-> new"
   )
   @NotNull MutableStringStream toMutableCopy();

   @Contract(
      pure = true
   )
   @NotNull @Unmodifiable StringStream toImmutableView();
}
