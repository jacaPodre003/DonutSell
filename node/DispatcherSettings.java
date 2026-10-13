package revxrsal.commands.node;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.util.Preconditions;
import revxrsal.commands.util.StackTraceSanitizer;

public final class DispatcherSettings<A extends CommandActor> {
   public static final String LONG_FORMAT_PREFIX = "--";
   public static final String SHORT_FORMAT_PREFIX = "-";
   public static final int DEFAULT_MAXIMUM_FAILED_ATTEMPTS = 5;
   private final int maximumFailedAttempts;
   private final @NotNull FailureHandler<A> failureHandler;
   private final @NotNull StackTraceSanitizer stackTraceSanitizer;

   private DispatcherSettings(Builder<A> builder) {
      this.maximumFailedAttempts = builder.maximumFailedAttempts;
      this.failureHandler = builder.failureHandler;
      this.stackTraceSanitizer = builder.stackTraceSanitizer;
   }

   public static <A extends CommandActor> @NotNull Builder<A> builder() {
      return new Builder<A>();
   }

   public int maximumFailedAttempts() {
      return this.maximumFailedAttempts;
   }

   public @NotNull FailureHandler<A> failureHandler() {
      return this.failureHandler;
   }

   public @NotNull StackTraceSanitizer stackTraceSanitizer() {
      return this.stackTraceSanitizer;
   }

   @Contract(
      value = "-> new",
      pure = true
   )
   public @NotNull Builder<A> toBuilder() {
      return (new Builder<A>()).maximumFailedAttempts(this.maximumFailedAttempts).failureHandler(this.failureHandler);
   }

   public static final class Builder<A extends CommandActor> {
      private int maximumFailedAttempts = 5;
      private FailureHandler<A> failureHandler = DefaultFailureHandler.<A>defaultFailureHandler();
      private @NotNull StackTraceSanitizer stackTraceSanitizer = StackTraceSanitizer.defaultSanitizer();

      public @NotNull Builder<A> maximumFailedAttempts(@Range(
   from = 1L,
   to = 2147483647L
) int maximumFailedAttempts) {
         if (maximumFailedAttempts < 0) {
            throw new IllegalArgumentException("Maximum failed attempts cannot be a negative number!");
         } else {
            this.maximumFailedAttempts = maximumFailedAttempts;
            return this;
         }
      }

      @Contract("null -> fail")
      public @NotNull Builder<A> failureHandler(FailureHandler<? super A> failureHandler) {
         Preconditions.notNull(failureHandler, "failure handler");
         this.failureHandler = failureHandler;
         return this;
      }

      public Builder<A> stackTraceSanitizer(@NotNull StackTraceSanitizer stackTraceSanitizer) {
         this.stackTraceSanitizer = (StackTraceSanitizer)Preconditions.notNull(stackTraceSanitizer, "stack trace sanitizer");
         return this;
      }

      @Contract(
         value = "-> new",
         pure = true
      )
      public @NotNull DispatcherSettings<A> build() {
         return new DispatcherSettings<A>(this);
      }
   }
}
