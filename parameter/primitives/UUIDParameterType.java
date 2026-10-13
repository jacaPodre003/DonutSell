package revxrsal.commands.parameter.primitives;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.InvalidUUIDException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;

public final class UUIDParameterType implements ParameterType<CommandActor, UUID> {
   public UUID parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<CommandActor> context) {
      String value = input.readUnquotedString();

      try {
         return UUID.fromString(value);
      } catch (IllegalArgumentException var5) {
         throw new InvalidUUIDException(value);
      }
   }
}
