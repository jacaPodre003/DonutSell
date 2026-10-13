package revxrsal.commands.brigadier;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import java.util.function.Predicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.util.Preconditions;

final class BNode<S> {
   private final CommandNode<S> node;

   private BNode(CommandNode<S> node) {
      this.node = node;
   }

   public static <S> @NotNull BNode<S> of(@NotNull CommandNode<S> node) {
      Preconditions.notNull(node, "node");
      return new BNode<S>(node);
   }

   public static <S> @NotNull BNode<S> literal(@NotNull String name) {
      return of(LiteralArgumentBuilder.literal(name).build());
   }

   public @NotNull BNode<S> executes(Command<S> command) {
      Nodes.setCommand(this.node, command);
      return this;
   }

   public @NotNull BNode<S> requires(Predicate<S> requirement) {
      Nodes.setRequirement(this.node, requirement);
      return this;
   }

   public @NotNull BNode<S> suggests(SuggestionProvider<S> suggestionProvider) {
      if (this.node instanceof ArgumentCommandNode) {
         ArgumentCommandNode<S, ?> argument = (ArgumentCommandNode)this.node;
         Nodes.setSuggestionProvider(argument, suggestionProvider);
      }

      return this;
   }

   public @NotNull BNode<S> then(@NotNull BNode<S> node) {
      BrigadierParser.addChild(this.node, node.node);
      return this;
   }

   public @NotNull BNode<S> then(@NotNull CommandNode<S> node) {
      BrigadierParser.addChild(this.node, node);
      return this;
   }

   @Contract(
      pure = true
   )
   public @NotNull CommandNode<S> asBrigadierNode() {
      return this.node;
   }

   @NotNull BNode<S> nextChild() {
      return of((CommandNode)this.node.getChildren().iterator().next());
   }

   public String toString() {
      return "BNode(" + this.node + ")";
   }
}
