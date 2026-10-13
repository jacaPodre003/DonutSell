package revxrsal.commands.node.parser;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandFunction;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.reflect.MethodCaller;
import revxrsal.commands.response.ResponseHandler;
import revxrsal.commands.util.Strings;

final class CommandFunctionImpl implements CommandFunction {
   private final @NotNull Lamp<?> lamp;
   private final @NotNull Method method;
   private final @Unmodifiable Map<String, CommandParameter> parameters;
   private final @NotNull AnnotationList annotations;
   private final MethodCaller.@NotNull BoundMethodCaller caller;
   private final @NotNull ResponseHandler<?, ?> responseHandler;

   public static @NotNull CommandFunction create(@NotNull Method method, @NotNull AnnotationList annotations, @NotNull Lamp<?> lamp, MethodCaller.@NotNull BoundMethodCaller caller) {
      Parameter[] pArray = method.getParameters();
      Map<String, CommandParameter> parameters = new LinkedHashMap(pArray.length);

      for(int methodIndex = 0; methodIndex < pArray.length; ++methodIndex) {
         Parameter parameter = pArray[methodIndex];
         AnnotationList parameterAnnotations = AnnotationList.create(parameter).replaceAnnotations(parameter, lamp.annotationReplacers());
         String name = (String)Strings.getOverriddenName(parameterAnnotations).orElseGet(() -> lamp.parameterNamingStrategy().getName(parameter));
         FunctionParameter fnParameter = new FunctionParameter(parameter, name, parameterAnnotations, methodIndex);
         parameters.put(fnParameter.name(), fnParameter);
      }

      Map<String, CommandParameter> var11 = Collections.unmodifiableMap(parameters);
      ResponseHandler<?, Object> handler = lamp.responseHandler(method.getGenericReturnType(), annotations);
      return new CommandFunctionImpl(lamp, method, var11, annotations, caller, handler);
   }

   public <A extends CommandActor> Lamp<A> lamp() {
      return this.lamp;
   }

   public @NotNull String name() {
      return this.method.getName();
   }

   public @NotNull AnnotationList annotations() {
      return this.annotations;
   }

   public @NotNull Method method() {
      return this.method;
   }

   public @NotNull @Unmodifiable Map<String, CommandParameter> parametersByName() {
      return this.parameters;
   }

   public @NotNull CommandParameter parameter(String name) {
      CommandParameter parameter = (CommandParameter)this.parameters.get(name);
      return parameter;
   }

   public MethodCaller.@NotNull BoundMethodCaller caller() {
      return this.caller;
   }

   public <T> T call(Object... arguments) {
      return (T)this.caller.call(arguments);
   }

   public <T> @NotNull ResponseHandler<?, T> responseHandler() {
      return this.responseHandler;
   }

   public String toString() {
      return "CommandFunction(" + this.method.toGenericString() + ")";
   }

   public CommandFunctionImpl(@NotNull Lamp<?> lamp, @NotNull Method method, Map<String, CommandParameter> parameters, @NotNull AnnotationList annotations, MethodCaller.@NotNull BoundMethodCaller caller, @NotNull ResponseHandler<?, ?> responseHandler) {
      this.lamp = lamp;
      this.method = method;
      this.parameters = parameters;
      this.annotations = annotations;
      this.caller = caller;
      this.responseHandler = responseHandler;
   }
}
