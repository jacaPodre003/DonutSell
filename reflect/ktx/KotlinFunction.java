package revxrsal.commands.reflect.ktx;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.util.Preconditions;

public interface KotlinFunction {
   static @NotNull KotlinFunction wrap(@NotNull Method method) {
      Preconditions.notNull(method, "method");
      return new KotlinFunctionImpl(method);
   }

   boolean isSuspend();

   <T> T call(@Nullable Object var1, @NotNull List<Object> var2, @NotNull Function<Parameter, Boolean> var3);

   <T> T callByIndices(@Nullable Object var1, @NotNull Map<Integer, Object> var2, @NotNull Function<Parameter, Boolean> var3);

   <T> T callByParameters(@Nullable Object var1, @NotNull Map<Parameter, Object> var2, @NotNull Function<Parameter, Boolean> var3);

   <T> T callByNames(@Nullable Object var1, @NotNull Map<String, Object> var2, @NotNull Function<Parameter, Boolean> var3);

   @NotNull CallableMethod getMethod();

   @Nullable CallableMethod getDefaultSyntheticMethod();

   @Unmodifiable @NotNull List<Parameter> getParameters();

   @Unmodifiable @NotNull Map<String, Parameter> getParametersByName();

   @NotNull Parameter getParameter(@NotNull String var1);

   @NotNull Parameter getParameter(int var1) throws IndexOutOfBoundsException;
}
