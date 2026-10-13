package revxrsal.commands.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandFunction;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.exception.context.ErrorContext;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.node.LiteralNode;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.util.Reflections;

public class RuntimeExceptionAdapter<A extends CommandActor> implements CommandExceptionHandler<A> {
   private final List<CommandExceptionHandler<A>> handlers = new ArrayList();

   public RuntimeExceptionAdapter() {
      for(Method method : Reflections.getAllMethods(this.getClass())) {
         if (isHandler(method)) {
            CommandExceptionHandler<A> handler = this.createHandler(method);
            this.handlers.add(handler);
         }
      }

   }

   @Contract("_ -> fail")
   private static void sneakyThrow(Throwable t) {
      try {
         throw t;
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   private static boolean isHandler(Method method) {
      return method.isAnnotationPresent(HandleException.class);
   }

   protected static @NotNull String fmt(@NotNull Number number) {
      return NumberFormat.getInstance().format(number);
   }

   private @NotNull CommandExceptionHandler<A> createHandler(Method method) {
      HandlerParameterSupplier<A>[] suppliers = new HandlerParameterSupplier[method.getParameterCount()];
      List<HandlerPredicate<A>> conditions = new ArrayList();
      Parameter[] parameters = method.getParameters();

      for(int i = 0; i < parameters.length; ++i) {
         Parameter parameter = parameters[i];
         Class<?> type = parameter.getType();
         if (Throwable.class.isAssignableFrom(type)) {
            conditions.add((HandlerPredicate)(throwable, errorContext) -> type.isAssignableFrom(throwable.getClass()));
            suppliers[i] = (throwable, errorContext) -> throwable;
         } else if (ExecutionContext.class.isAssignableFrom(type)) {
            conditions.add((HandlerPredicate)(throwable, errorContext) -> errorContext.hasExecutionContext());
            suppliers[i] = (throwable, errorContext) -> errorContext.context();
         } else if (ErrorContext.class.isAssignableFrom(type)) {
            conditions.add((HandlerPredicate)(throwable, errorContext) -> type.isAssignableFrom(errorContext.getClass()));
            suppliers[i] = (throwable, errorContext) -> errorContext;
         } else if (CommandActor.class.isAssignableFrom(type)) {
            suppliers[i] = (throwable, errorContext) -> errorContext.actor();
         } else if (Lamp.class.isAssignableFrom(type)) {
            suppliers[i] = (throwable, errorContext) -> errorContext.lamp();
         } else if (ExecutableCommand.class.isAssignableFrom(type)) {
            conditions.add((HandlerPredicate)(throwable, errorContext) -> errorContext.hasExecutionContext());
            suppliers[i] = (throwable, errorContext) -> errorContext.context().command();
         } else if (CommandFunction.class.isAssignableFrom(type)) {
            conditions.add((HandlerPredicate)(throwable, errorContext) -> errorContext.hasExecutionContext());
            suppliers[i] = (throwable, errorContext) -> errorContext.context().command().function();
         } else if (CommandParameter.class.isAssignableFrom(type)) {
            conditions.add((HandlerPredicate)(throwable, errorContext) -> errorContext instanceof ErrorContext.ParsingParameter);
            suppliers[i] = (throwable, errorContext) -> ((ErrorContext.ParsingParameter)errorContext).parameter().parameter();
         } else if (ParameterNode.class.isAssignableFrom(type)) {
            conditions.add((HandlerPredicate)(throwable, errorContext) -> errorContext instanceof ErrorContext.ParsingParameter);
            suppliers[i] = (throwable, errorContext) -> ((ErrorContext.ParsingParameter)errorContext).parameter();
         } else {
            if (!LiteralNode.class.isAssignableFrom(type)) {
               throw new IllegalArgumentException("Don't know how to handle parameter of type " + type + " for a @HandleException function (" + method + ")");
            }

            conditions.add((HandlerPredicate)(throwable, errorContext) -> errorContext instanceof ErrorContext.ParsingLiteral);
            suppliers[i] = (throwable, errorContext) -> ((ErrorContext.ParsingLiteral)errorContext).literal();
         }
      }

      return (throwable, errorContext) -> {
         for(HandlerPredicate<A> condition : conditions) {
            if (!condition.test(throwable, errorContext)) {
               return;
            }
         }

         Object[] arguments = new Object[parameters.length];

         for(int i = 0; i < suppliers.length; ++i) {
            HandlerParameterSupplier<A> supplier = suppliers[i];
            arguments[i] = supplier.supply(throwable, errorContext);
         }

         try {
            method.invoke(this, arguments);
         } catch (IllegalAccessException e) {
            sneakyThrow(e);
         } catch (InvocationTargetException e) {
            sneakyThrow(e.getCause());
         }

      };
   }

   public final void handleException(@NotNull Throwable throwable, @NotNull ErrorContext<A> errorContext) {
      for(CommandExceptionHandler<A> handler : this.handlers) {
         handler.handleException(throwable, errorContext);
      }

   }

   @Target({ElementType.METHOD})
   @Retention(RetentionPolicy.RUNTIME)
   public @interface HandleException {
   }

   private interface HandlerParameterSupplier<A extends CommandActor> {
      Object supply(@NotNull Throwable var1, @NotNull ErrorContext<A> var2);
   }

   private interface HandlerPredicate<A extends CommandActor> {
      boolean test(@NotNull Throwable var1, @NotNull ErrorContext<A> var2);
   }
}
