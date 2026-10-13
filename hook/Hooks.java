package revxrsal.commands.hook;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.util.Collections;
import revxrsal.commands.util.Preconditions;

public final class Hooks<A extends CommandActor> {
   private final @Unmodifiable List<Hook> hooks;

   private Hooks(Builder<A> builder) {
      this.hooks = Collections.<Hook>copyList(builder.hooks);
   }

   @Contract(
      value = "-> new",
      pure = true
   )
   public static <A extends CommandActor> @NotNull Builder<A> builder() {
      return new Builder<A>();
   }

   private static @NotNull CancelHandle newCancelHandle() {
      return new BasicCancelHandle();
   }

   @Internal
   public boolean onCommandRegistered(@NotNull ExecutableCommand<A> command) {
      CancelHandle cancelHandle = newCancelHandle();

      for(Hook hook : this.hooks) {
         if (hook instanceof CommandRegisteredHook) {
            CommandRegisteredHook registeredHook = (CommandRegisteredHook)hook;
            registeredHook.onRegistered(command, cancelHandle);
         }
      }

      return !cancelHandle.wasCancelled();
   }

   @Internal
   public boolean onCommandUnregistered(@NotNull ExecutableCommand<A> command) {
      CancelHandle cancelHandle = newCancelHandle();

      for(Hook hook : this.hooks) {
         if (hook instanceof CommandUnregisteredHook) {
            CommandUnregisteredHook unregisteredHook = (CommandUnregisteredHook)hook;
            unregisteredHook.onUnregistered(command, cancelHandle);
         }
      }

      return !cancelHandle.wasCancelled();
   }

   @Internal
   public boolean onCommandExecuted(@NotNull ExecutableCommand<A> command, @NotNull ExecutionContext<A> context) {
      CancelHandle cancelHandle = newCancelHandle();

      for(Hook hook : this.hooks) {
         if (hook instanceof CommandExecutedHook) {
            CommandExecutedHook executedHook = (CommandExecutedHook)hook;
            executedHook.onExecuted(command, context, cancelHandle);
         }
      }

      return !cancelHandle.wasCancelled();
   }

   @Internal
   public void onPostCommandExecuted(@NotNull ExecutableCommand<A> command, @NotNull ExecutionContext<A> context) {
      for(Hook hook : this.hooks) {
         if (hook instanceof PostCommandExecutedHook) {
            PostCommandExecutedHook<A> executedHook = (PostCommandExecutedHook)hook;
            executedHook.onPostExecuted(command, context);
         }
      }

   }

   public static class Builder<A extends CommandActor> {
      private final List<Hook> hooks = new ArrayList();

      public @NotNull Builder<A> onCommandExecuted(@NotNull CommandExecutedHook<? super A> hook) {
         return this.hook(hook);
      }

      public @NotNull Builder<A> onPostCommandExecuted(@NotNull PostCommandExecutedHook<? super A> hook) {
         return this.hook(hook);
      }

      public @NotNull Builder<A> onCommandRegistered(@NotNull CommandRegisteredHook<? super A> hook) {
         return this.hook(hook);
      }

      public @NotNull Builder<A> onCommandUnregistered(@NotNull CommandUnregisteredHook<? super A> hook) {
         return this.hook(hook);
      }

      private @NotNull Builder<A> hook(@NotNull Hook hook) {
         Preconditions.notNull(hook, "hook");
         this.hooks.add(hook);
         return this;
      }

      @Contract(
         value = "-> new",
         pure = true
      )
      public @NotNull Hooks<A> build() {
         return new Hooks<A>(this);
      }
   }

   private static final class BasicCancelHandle implements CancelHandle {
      private boolean cancelled;

      private BasicCancelHandle() {
         this.cancelled = false;
      }

      public boolean wasCancelled() {
         return this.cancelled;
      }

      public void cancel() {
         this.cancelled = true;
      }
   }
}
