package revxrsal.commands.reflect.ktx;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.reflect.MethodCaller;
import revxrsal.commands.util.Lazy;

final class KotlinFunctionImpl implements KotlinFunction {
   private static final String CONTEXT_PARAMETER_PREFIX = "$this$";
   private final CallableMethod mainMethod;
   private final Supplier<@Nullable CallableMethod> defaultMethod;
   private final @Unmodifiable List<Parameter> parameters;
   private final Supplier<@Unmodifiable Map<String, Parameter>> byName = Lazy.<Map<String, Parameter>>of(() -> {
      Map<String, Parameter> byName = new HashMap();

      for(Parameter parameter : this.getParameters()) {
         byName.put(parameter.getName(), parameter);
      }

      return Collections.unmodifiableMap(byName);
   });

   public KotlinFunctionImpl(Method mainMethod) {
      MethodCaller mainCaller = KotlinSingletons.getCallerForNonDefault(mainMethod);
      this.parameters = Arrays.asList(mainMethod.getParameters());
      this.mainMethod = new CallableMethod(mainMethod, mainCaller);
      this.defaultMethod = Lazy.<CallableMethod>of(() -> DefaultFunctionFinder.findDefaultFunction(mainMethod));
   }

   private static void checkCallableStatic(@Nullable Object instance, @NotNull Method method) {
      if (instance == null && !Modifier.isStatic(method.getModifiers())) {
         throw new IllegalArgumentException("The given method is not static, and no instance was provided. Either mark the function as static with @JvmStatic, or pass the object/companion object value for the instance.");
      }
   }

   public @NotNull CallableMethod getMethod() {
      return this.mainMethod;
   }

   public @Nullable CallableMethod getDefaultSyntheticMethod() {
      return (CallableMethod)this.defaultMethod.get();
   }

   public boolean isSuspend() {
      Parameter lastParameter = (Parameter)revxrsal.commands.util.Collections.getOrNull(this.parameters, this.parameters.size() - 1);
      return lastParameter != null && lastParameter.getType() == KotlinConstants.continuation();
   }

   public <T> T call(@Nullable Object instance, @NotNull List<Object> arguments, @NotNull Function<Parameter, Boolean> isOptional) {
      Map<Parameter, Object> callArgs = this.mapArgsToParams((i) -> revxrsal.commands.util.Collections.getOrNull(arguments, i));
      return (T)this.callByParameters(instance, callArgs, isOptional);
   }

   public <T> T callByIndices(@Nullable Object instance, @NotNull Map<Integer, Object> arguments, @NotNull Function<Parameter, Boolean> isOptional) {
      arguments.getClass();
      Map<Parameter, Object> callArgs = this.mapArgsToParams(arguments::get);
      return (T)this.callByParameters(instance, callArgs, isOptional);
   }

   public <T> T callByNames(@Nullable Object instance, @NotNull Map<String, Object> arguments, @NotNull Function<Parameter, Boolean> isOptional) {
      return (T)this.callByParameters(instance, revxrsal.commands.util.Collections.mapKeys(arguments, this::getParameter), isOptional);
   }

   public <T> T callByParameters(@Nullable Object instance, @NotNull Map<Parameter, Object> arguments, @NotNull Function<Parameter, Boolean> isOptional) {
      checkCallableStatic(instance, this.mainMethod.method());
      List<Object> args = new ArrayList();
      int mask = 0;
      List<Integer> masks = new ArrayList(1);
      int index = 0;
      boolean anyOptional = false;
      boolean hasContextReceiver = false;

      for(Parameter parameter : this.parameters) {
         if (index == 0 && parameter.getName().startsWith("$this$")) {
            hasContextReceiver = true;
         }

         if (index != 0 && index % 32 == 0) {
            masks.add(mask);
            mask = 0;
         }

         Object providedArg = arguments.get(parameter);
         if (providedArg != null) {
            args.add(providedArg);
         } else if ((Boolean)isOptional.apply(parameter)) {
            mask |= 1 << index % 32;
            args.add(KotlinConstants.defaultPrimitiveValue(parameter.getType()));
            anyOptional = true;
         } else {
            if (!parameter.isVarArgs()) {
               throw new IllegalArgumentException("No argument provided for a required parameter: " + parameter + ".");
            }

            args.add(Array.newInstance(parameter.getType(), 0));
         }

         ++index;
      }

      if (!anyOptional) {
         return (T)this.mainMethod.caller().call(instance, args.toArray());
      } else {
         CallableMethod defaultMethod = (CallableMethod)this.defaultMethod.get();
         if (defaultMethod == null) {
            if (this.mainMethod.method().getParameterCount() == args.size()) {
               return (T)this.mainMethod.caller().call(instance, args.toArray());
            } else {
               throw new IllegalArgumentException("Unable to invoke function with default parameters. This may happen because you have an @Optional non-null primitive type (e.g. Int) with no default value using @Default or a Kotlin-default value. It may also occur if you have @Switch with no default value. (@Switch param: Boolean = ...). Either mark it as nullable, add a default value (@Optional param: Type = ...), or use @Default");
            }
         } else {
            masks.add(mask);
            if (hasContextReceiver) {
               masks.set(0, (Integer)masks.get(0) / 2);
            }

            args.addAll(masks);
            args.add((Object)null);
            return (T)defaultMethod.caller().call(instance, args.toArray());
         }
      }
   }

   private @NotNull Map<Parameter, Object> mapArgsToParams(@NotNull Function<Integer, Object> map) {
      Map<Parameter, Object> callArgs = new HashMap();

      for(int i = 0; i < this.parameters.size(); ++i) {
         Parameter parameter = (Parameter)this.parameters.get(i);
         callArgs.put(parameter, map.apply(i));
      }

      return callArgs;
   }

   public @Unmodifiable @NotNull List<Parameter> getParameters() {
      return this.parameters;
   }

   public @Unmodifiable @NotNull Map<String, Parameter> getParametersByName() {
      return (Map)this.byName.get();
   }

   public @NotNull Parameter getParameter(@NotNull String name) {
      Parameter parameter = (Parameter)this.getParametersByName().get(name);
      return parameter;
   }

   public @NotNull Parameter getParameter(int index) {
      return (Parameter)this.parameters.get(index);
   }
}
