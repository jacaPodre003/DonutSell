package revxrsal.commands.reflect;

import org.jetbrains.annotations.Nullable;

public interface MethodCaller {
   Object call(@Nullable Object var1, Object... var2);

   default BoundMethodCaller bindTo(@Nullable Object instance) {
      return (arguments) -> this.call(instance, arguments);
   }

   public interface BoundMethodCaller {
      Object call(Object... var1);
   }
}
