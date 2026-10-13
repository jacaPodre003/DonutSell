package revxrsal.commands.node;

import java.lang.reflect.Type;
import java.util.Collection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;

public interface ParameterNode<A extends CommandActor, T> extends CommandNode<A>, RequiresPermission<A>, HasDescription {
   @Nullable T parse(MutableStringStream var1, ExecutionContext<A> var2);

   @NotNull AnnotationList annotations();

   boolean isOptional();

   default boolean isRequired() {
      return !this.isOptional();
   }

   @NotNull ParameterType<A, T> parameterType();

   @NotNull SuggestionProvider<A> suggestions();

   @NotNull CommandParameter parameter();

   boolean isGreedy();

   @Contract(
      pure = true
   )
   @NotNull Collection<String> complete(@NotNull ExecutionContext<A> var1);

   default @NotNull Class<?> type() {
      return this.parameter().type();
   }

   default @NotNull Type fullType() {
      return this.parameter().fullType();
   }

   default boolean isLiteral() {
      return false;
   }

   default boolean isParameter() {
      return true;
   }

   @Contract("-> fail")
   default @NotNull LiteralNode<A> requireLiteralNode() {
      throw new IllegalStateException("Expected a LiteralNode, found a ParameterNode");
   }

   boolean isFlag();

   boolean isSwitch();

   @Contract(
      pure = true
   )
   @Nullable Character shorthand();

   @Contract(
      pure = true
   )
   @Nullable String switchName();

   @Contract(
      pure = true
   )
   @Nullable String flagName();
}
