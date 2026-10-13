package revxrsal.commands.parameter.primitives;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.parameter.PrioritySpec;
import revxrsal.commands.stream.MutableStringStream;

public final class BooleanParameterType implements ParameterType<CommandActor, Boolean> {
   private static final SuggestionProvider<CommandActor> SUGGESTIONS = SuggestionProvider.of("true", "false");

   public Boolean parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<CommandActor> context) {
      return input.readBoolean();
   }

   public @NotNull SuggestionProvider<CommandActor> defaultSuggestions() {
      return SUGGESTIONS;
   }

   public @NotNull PrioritySpec parsePriority() {
      return PrioritySpec.highest();
   }
}
