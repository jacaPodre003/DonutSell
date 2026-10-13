package revxrsal.commands.command;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.help.Help;
import revxrsal.commands.node.CommandAction;
import revxrsal.commands.node.CommandNode;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.node.HasDescription;
import revxrsal.commands.node.LiteralNode;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.node.RequiresPermission;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.util.Preconditions;

public interface ExecutableCommand<A extends CommandActor> extends Comparable<ExecutableCommand<A>>, Iterable<CommandNode<A>>, RequiresPermission<A>, HasDescription {
   @NotNull Lamp<A> lamp();

   @Range(
   from = 1L,
   to = 2147483647L
) int size();

   int optionalParameters();

   int requiredInput();

   @NotNull String path();

   @NotNull String usage();

   @Nullable String description();

   @NotNull CommandFunction function();

   @NotNull CommandNode<A> lastNode();

   @NotNull LiteralNode<A> firstNode();

   @CheckReturnValue
   @NotNull Potential<A> test(@NotNull A var1, @NotNull MutableStringStream var2);

   @NotNull @Unmodifiable List<CommandNode<A>> nodes();

   @NotNull OptionalInt commandPriority();

   boolean isSecret();

   void unregister();

   default void execute(@NotNull A actor, @NotNull MutableStringStream input) {
      this.lamp().registry().execute(actor, this, input);
   }

   void execute(@NotNull ExecutionContext<A> var1);

   default @NotNull CommandAction<A> action() {
      return (CommandAction)Objects.requireNonNull(this.lastNode().action(), "lastNode().action() is null");
   }

   default @NotNull AnnotationList annotations() {
      return this.function().annotations();
   }

   Help.@NotNull RelatedCommands<A> relatedCommands(@Nullable A var1);

   default Help.@NotNull RelatedCommands<A> relatedCommands() {
      return this.relatedCommands((CommandActor)null);
   }

   Help.@NotNull ChildrenCommands<A> childrenCommands(@Nullable A var1);

   default Help.@NotNull ChildrenCommands<A> childrenCommands() {
      return this.childrenCommands((CommandActor)null);
   }

   Help.@NotNull SiblingCommands<A> siblingCommands(@Nullable A var1);

   default Help.@NotNull SiblingCommands<A> siblingCommands() {
      return this.siblingCommands((CommandActor)null);
   }

   @Contract(
      pure = true
   )
   @NotNull @Unmodifiable Map<String, ParameterNode<A, Object>> parameters();

   default <T> @Nullable ParameterNode<A, T> parameterOrNull(@NotNull String name) {
      Preconditions.notNull(name, "parameter name");
      return (ParameterNode)this.parameters().get(name);
   }

   default <T> @NotNull ParameterNode<A, T> parameter(@NotNull String name) {
      ParameterNode<A, T> parameter = this.parameterOrNull(name);
      return parameter;
   }

   boolean isSiblingOf(@NotNull ExecutableCommand<A> var1);

   boolean isChildOf(@NotNull ExecutableCommand<A> var1);

   default boolean isParentOf(@NotNull ExecutableCommand<A> command) {
      return command.isChildOf(this);
   }

   default boolean isRelatedTo(@NotNull ExecutableCommand<A> command) {
      return this.isParentOf(command) || this.isSiblingOf(command);
   }

   default boolean isVisibleTo(@NotNull A actor) {
      return !this.isSecret() && this.permission().isExecutableBy(actor);
   }

   boolean containsFlags();

   @Range(
   from = 0L,
   to = 2147483647L
) int flagCount();
}
