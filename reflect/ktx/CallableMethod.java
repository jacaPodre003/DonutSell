package revxrsal.commands.reflect.ktx;

import java.lang.reflect.Method;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.reflect.MethodCaller;

public final class CallableMethod {
   private final @NotNull Method method;
   private final @NotNull MethodCaller caller;

   public CallableMethod(@NotNull Method method, @NotNull MethodCaller caller) {
      this.method = method;
      this.caller = caller;
   }

   public @NotNull Method method() {
      return this.method;
   }

   public @NotNull MethodCaller caller() {
      return this.caller;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         CallableMethod that = (CallableMethod)obj;
         return Objects.equals(this.method, that.method) && Objects.equals(this.caller, that.caller);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.method, this.caller});
   }

   public String toString() {
      return "CallableMethod[method=" + this.method + ", caller=" + this.caller + ']';
   }
}
