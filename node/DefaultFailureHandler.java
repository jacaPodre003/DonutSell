package revxrsal.commands.node;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.Potential;
import revxrsal.commands.exception.NoPermissionException;
import revxrsal.commands.stream.StringStream;

final class DefaultFailureHandler<A extends CommandActor> implements FailureHandler<A> {
   private static final int MAX_NUMBER_OF_SUGGESTIONS = 6;
   private static final DefaultFailureHandler<CommandActor> INSTANCE = new DefaultFailureHandler<CommandActor>();

   public static <A extends CommandActor> FailureHandler<A> defaultFailureHandler() {
      return INSTANCE;
   }

   public void handleFailedAttempts(@NotNull A actor, @NotNull @Unmodifiable List<Potential<A>> failedAttempts, @NotNull StringStream input) {
      if (!failedAttempts.isEmpty()) {
         if (failedAttempts.size() == 1) {
            ((Potential)failedAttempts.get(0)).handleException();
         } else if (((Potential)failedAttempts.get(0)).error() instanceof NoPermissionException) {
            ((Potential)failedAttempts.get(0)).handleException();
         } else {
            actor.error("Failed to find a suitable command for your input (\"" + input.source() + "\"). Did you mean:");

            for(int i = 0; i < failedAttempts.size() && i < 6; ++i) {
               Potential<A> failedAttempt = (Potential)failedAttempts.get(i);
               actor.reply("- " + failedAttempt.context().command().path());
            }

         }
      }
   }
}
