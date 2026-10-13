package revxrsal.commands.node.parser;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.command.CommandPermission;
import revxrsal.commands.node.CommandNode;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.parameter.ParameterType;

final class MutableParameterNode<A extends CommandActor, T> extends MutableCommandNode<A> implements Comparable<MutableParameterNode<A, Object>> {
   private @NotNull ParameterType<A, T> type;
   private @NotNull SuggestionProvider<A> suggestions = SuggestionProvider.<A>empty();
   private @NotNull CommandPermission<A> permission = CommandPermission.<A>alwaysTrue();
   private @NotNull CommandParameter parameter;
   private boolean isOptional;

   public MutableParameterNode(@NotNull String name) {
      super(name);
   }

   public int compareTo(@NotNull MutableParameterNode<A, Object> o) {
      return this.type.parsePriority().comparator().compare(this.type(), o.type());
   }

   public @NotNull ParameterNode<A, T> createNode() {
      return new ParameterNodeImpl<A, T>(this.getName(), this.getAction(), this.isLast(), this.type, this.suggestions, this.parameter, this.permission, this.isOptional);
   }

   public void setType(@NotNull ParameterType<A, T> type) {
      this.type = type;
      if (this.suggestions == SuggestionProvider.empty()) {
         this.suggestions = type.defaultSuggestions();
      }

   }

   public CommandNode<A> toNode() {
      return this.createNode();
   }

   public @NotNull ParameterType<A, T> type() {
      return this.type;
   }

   public @NotNull SuggestionProvider<A> suggestions() {
      return this.suggestions;
   }

   public @NotNull CommandParameter parameter() {
      return this.parameter;
   }

   public boolean isOptional() {
      return this.isOptional;
   }

   public void setSuggestions(@NotNull SuggestionProvider<A> suggestions) {
      this.suggestions = suggestions;
   }

   public void setPermission(@NotNull CommandPermission<A> permission) {
      this.permission = permission;
   }

   public void setParameter(@NotNull CommandParameter parameter) {
      this.parameter = parameter;
   }

   public void setOptional(boolean isOptional) {
      this.isOptional = isOptional;
   }
}
