package revxrsal.commands.node;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.stream.StringStream;
import revxrsal.commands.util.Preconditions;

public interface ExecutionContext<A extends CommandActor> {
   @Contract("_,_,_ -> new")
   static <A extends CommandActor> @NotNull MutableExecutionContext<A> createMutable(@NotNull ExecutableCommand<A> command, @NotNull A actor, @NotNull StringStream input) {
      Preconditions.notNull(command, "command");
      Preconditions.notNull(actor, "actor");
      Preconditions.notNull(input, "input");
      return new BasicMutableExecutionContext<A>(command, input, actor);
   }

   @Contract("_,_,_ -> new")
   static <A extends CommandActor> @NotNull ExecutionContext<A> create(@NotNull ExecutableCommand<A> command, @NotNull A actor, @NotNull StringStream input) {
      Preconditions.notNull(command, "command");
      Preconditions.notNull(actor, "actor");
      Preconditions.notNull(input, "input");
      return new BasicExecutionContext<A>(command, input, actor);
   }

   @NotNull A actor();

   @NotNull Lamp<A> lamp();

   @NotNull ExecutableCommand<A> command();

   @NotNull @UnmodifiableView Map<String, Object> resolvedArguments();

   @NotNull StringStream input();

   <T> @Nullable T getResolvedArgumentOrNull(@NotNull String var1);

   <T> @Nullable T getResolvedArgumentOrNull(@NotNull Class<T> var1);

   default <T> @NotNull T getResolvedArgument(@NotNull String argumentName) {
      T argument = (T)this.getResolvedArgumentOrNull(argumentName);
      return argument;
   }

   default <T> @NotNull T getResolvedArgument(@NotNull Class<T> argumentType) {
      T argument = (T)this.getResolvedArgumentOrNull(argumentType);
      if (argument == null) {
         List<Class<?>> types = (List)this.resolvedArguments().values().stream().filter(Objects::nonNull).map(Object::getClass).collect(Collectors.toList());
         throw new IllegalArgumentException("Couldn't find an argument that matches the type " + argumentType + ". Available types: " + types);
      } else {
         return argument;
      }
   }
}
