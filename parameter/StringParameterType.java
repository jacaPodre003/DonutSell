package revxrsal.commands.parameter;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.stream.MutableStringStream;

public final class StringParameterType implements ParameterType<CommandActor, String> {
   private static final StringParameterType GREEDY = new StringParameterType(true);
   private static final StringParameterType SINGLE = new StringParameterType(false);
   private final boolean greedy;

   private StringParameterType(boolean greedy) {
      this.greedy = greedy;
   }

   public static <A extends CommandActor> @NotNull ParameterType<A, String> greedy() {
      return GREEDY;
   }

   public static <A extends CommandActor> @NotNull ParameterType<A, String> single() {
      return SINGLE;
   }

   public String parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<CommandActor> context) {
      return input.peek() != '"' && this.greedy ? input.consumeRemaining() : input.readString();
   }

   public boolean isGreedy() {
      return this.greedy;
   }

   public @NotNull PrioritySpec parsePriority() {
      return PrioritySpec.lowest();
   }
}
