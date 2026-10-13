package revxrsal.commands.stream;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface StringStream {
   static @NotNull StringStream create(@NotNull String source, int position) {
      return new BaseStringStream(source, position);
   }

   static @NotNull StringStream create(@NotNull String source) {
      return new BaseStringStream(source);
   }

   static @NotNull MutableStringStream createMutable(@NotNull String source, int position) {
      return new MutableStringStreamImpl(source, position);
   }

   static @NotNull MutableStringStream createMutable(@NotNull String source) {
      return new MutableStringStreamImpl(source);
   }

   @NotNull String source();

   int totalSize();

   int remaining();

   char peek();

   String peek(int var1);

   char peekOffset(int var1);

   boolean hasRemaining();

   boolean hasFinished();

   boolean canRead(int var1);

   int position();

   @NotNull String peekUnquotedString();

   @NotNull String peekString();

   @NotNull String peekRemaining();

   @NotNull @Unmodifiable StringStream toImmutableCopy();

   @NotNull MutableStringStream toMutableCopy();

   boolean isMutable();

   boolean isEmpty();
}
