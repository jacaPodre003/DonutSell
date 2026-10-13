package revxrsal.commands.node.parser;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandFunction;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.exception.context.ErrorContext;
import revxrsal.commands.node.CommandAction;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ContextParameter;

public final class ReflectionAction<A extends CommandActor> implements CommandAction<A> {
   private final CommandFunction function;
   private final Map<Integer, ParameterSupplier<A>> parameters = new HashMap();

   public ReflectionAction(CommandFunction function) {
      this.function = function;
   }

   public void execute(ExecutionContext<A> context) {
      try {
         Object[] arguments = new Object[this.function.method().getParameterCount()];
         this.parameters.forEach((index, parameter) -> arguments[index] = parameter.get(context));
         context.resolvedArguments().forEach((parameterName, value) -> {
            context.lamp().validate(context.actor(), value, context.command().parameter(parameterName));
            int index = this.function.parameter(parameterName).methodIndex();
            arguments[index] = value;
         });
         Object result = this.function.call(arguments);
         if (result != null) {
            this.function.responseHandler().handleResponse(result, context);
         }

         context.lamp().hooks().onPostCommandExecuted(context.command(), context);
      } catch (Throwable t) {
         context.lamp().handleException(t, ErrorContext.executingFunction(context));
      }

   }

   void addContextParameter(CommandParameter parameter, ContextParameter<A, ?> contextParameter) {
      this.parameters.put(parameter.methodIndex(), (ParameterSupplier)(context) -> contextParameter.resolve(parameter, context));
   }

   private interface ParameterSupplier<A extends CommandActor> {
      Object get(@NotNull ExecutionContext<A> var1);
   }
}
