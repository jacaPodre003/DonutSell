package revxrsal.commands.node;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.stream.StringStream;
import revxrsal.commands.util.Classes;
import revxrsal.commands.util.Preconditions;

class BasicExecutionContext<A extends CommandActor> implements ExecutionContext<A> {
   protected final ExecutableCommand<A> command;
   protected final StringStream input;
   protected final A actor;
   protected final Map<String, Object> resolvedArguments = new LinkedHashMap();

   public BasicExecutionContext(ExecutableCommand<A> command, StringStream input, A actor) {
      this.command = command;
      this.input = input;
      this.actor = actor;
   }

   public @NotNull A actor() {
      return this.actor;
   }

   public @NotNull Lamp<A> lamp() {
      return this.command.lamp();
   }

   public @NotNull ExecutableCommand<A> command() {
      return this.command;
   }

   public @NotNull @UnmodifiableView Map<String, Object> resolvedArguments() {
      return this.resolvedArguments;
   }

   public @NotNull StringStream input() {
      return this.input;
   }

   public <T> @Nullable T getResolvedArgumentOrNull(@NotNull Class<T> argumentType) {
      Preconditions.notNull(argumentType, "argument type");
      argumentType = Classes.<T>wrap(argumentType);

      for(Object value : this.resolvedArguments.values()) {
         if (value != null && argumentType.isAssignableFrom(value.getClass())) {
            return (T)value;
         }
      }

      return null;
   }

   public <T> @Nullable T getResolvedArgumentOrNull(@NotNull String argumentName) {
      Preconditions.notNull(argumentName, "argument name");
      return (T)this.resolvedArguments.get(argumentName);
   }
}
