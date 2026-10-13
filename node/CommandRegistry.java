package revxrsal.commands.node;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.stream.StringStream;

public interface CommandRegistry<A extends CommandActor> extends Iterable<ExecutableCommand<A>> {
   @Contract(
      pure = true
   )
   @NotNull Lamp<A> lamp();

   default void execute(@NotNull A actor, @NotNull String input) {
      this.execute(actor, StringStream.create(input));
   }

   void execute(@NotNull A var1, @NotNull StringStream var2);

   void execute(@NotNull A var1, @NotNull ExecutableCommand<A> var2, @NotNull MutableStringStream var3);

   @NotNull @UnmodifiableView List<ExecutableCommand<A>> commands();

   void unregister(@NotNull ExecutableCommand<A> var1);

   void unregisterIf(@NotNull Predicate<@NotNull ExecutableCommand<A>> var1);

   boolean any(@NotNull Predicate<@NotNull ExecutableCommand<A>> var1);

   @CheckReturnValue
   @Contract("_ -> new")
   @NotNull List<ExecutableCommand<A>> filter(@NotNull Predicate<@NotNull ExecutableCommand<A>> var1);

   @NotNull @UnmodifiableView Iterator<ExecutableCommand<A>> iterator();
}
