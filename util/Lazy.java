package revxrsal.commands.util;

import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Lazy {
   private Lazy() {
      Preconditions.cannotInstantiate(Lazy.class);
   }

   public static <T> @NotNull Supplier<T> of(@NotNull Supplier<T> fetch) {
      Preconditions.notNull(fetch, "fetch supplier");
      return new LazySupplier<T>(fetch);
   }

   static final class LazySupplier<T extends @Nullable Object> implements Supplier<T> {
      final Supplier<T> delegate;
      transient volatile boolean initialized;
      transient @Nullable T value;

      LazySupplier(Supplier<T> delegate) {
         this.delegate = delegate;
      }

      public T get() {
         if (!this.initialized) {
            synchronized(this) {
               if (!this.initialized) {
                  T t = (T)this.delegate.get();
                  this.value = t;
                  this.initialized = true;
                  return t;
               }
            }
         }

         return this.value;
      }

      public String toString() {
         return "Suppliers.lazy(" + (this.initialized ? "<supplier that returned " + this.value + ">" : this.delegate) + ")";
      }
   }
}
