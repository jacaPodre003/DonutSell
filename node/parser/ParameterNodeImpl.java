package revxrsal.commands.node.parser;

import java.util.Collection;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Flag;
import revxrsal.commands.annotation.Length;
import revxrsal.commands.annotation.Sized;
import revxrsal.commands.annotation.Switch;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.command.CommandPermission;
import revxrsal.commands.exception.MissingArgumentException;
import revxrsal.commands.exception.NoPermissionException;
import revxrsal.commands.node.CommandAction;
import revxrsal.commands.node.CommandNode;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.reflect.ktx.KotlinConstants;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.stream.MutableStringStreamImpl;
import revxrsal.commands.util.Classes;

final class ParameterNodeImpl<A extends CommandActor, T> extends BaseCommandNode<A> implements ParameterNode<A, T> {
   private final @NotNull ParameterType<A, T> type;
   private final @NotNull SuggestionProvider<A> suggestions;
   private final @NotNull CommandParameter parameter;
   private final @NotNull CommandPermission<A> permission;
   private final boolean isOptional;
   private final @Nullable Switch switchAnn;
   private final @Nullable Flag flagAnn;

   public ParameterNodeImpl(@NotNull String name, @Nullable CommandAction<A> action, boolean isLast, @NotNull ParameterType<A, T> type, @NotNull SuggestionProvider<A> suggestions, @NotNull CommandParameter parameter, @NotNull CommandPermission<A> permission, boolean isOptional) {
      super(name, action, isLast);
      this.type = type;
      this.suggestions = suggestions;
      this.parameter = parameter;
      this.permission = permission;
      this.isOptional = isOptional;
      this.switchAnn = (Switch)parameter.getAnnotation(Switch.class);
      this.flagAnn = (Flag)parameter.getAnnotation(Flag.class);
      if (this.isSwitch() && Classes.wrap(this.type()) != Boolean.class) {
         throw new IllegalArgumentException("@Switch can only be used on boolean types!");
      } else if (this.isSwitch() && this.isFlag()) {
         throw new IllegalArgumentException("A parameter cannot have @Switch and @Flag at the same time!");
      }
   }

   private static @Nullable String getDefaultValue(AnnotationList annotations) {
      String defaultValue = (String)annotations.map(Default.class, Default::value);
      if (defaultValue == null) {
         Sized sized = (Sized)annotations.get(Sized.class);
         if (sized != null) {
            return sized.min() == 0 ? "" : null;
         }

         Length length = (Length)annotations.get(Length.class);
         if (length != null) {
            return length.min() == 0 ? "" : null;
         }
      }

      return defaultValue;
   }

   public T parse(MutableStringStream input, ExecutionContext<A> context) {
      this.checkForPermission(context);
      if (input.hasFinished()) {
         if (!this.isOptional()) {
            throw new MissingArgumentException(this, context.command());
         }

         String defaultValue = getDefaultValue(this.parameter.annotations());
         if (defaultValue == null) {
            if (KotlinConstants.isKotlinClass(context.command().function().method().getDeclaringClass())) {
               return null;
            }

            return (T)KotlinConstants.defaultPrimitiveValue(this.parameter.type());
         }

         ((MutableStringStreamImpl)input).extend(defaultValue);
      }

      return this.type.parse(input, context);
   }

   private void checkForPermission(ExecutionContext<A> context) {
      if (!this.permission.isExecutableBy(context.actor())) {
         throw new NoPermissionException(this);
      }
   }

   public int compareTo(@NotNull CommandNode<A> o) {
      if (o instanceof LiteralNodeImpl) {
         return 1;
      } else {
         ParameterNodeImpl<?, A> n = (ParameterNodeImpl)o;
         if (this.isOptional && !n.isOptional) {
            return 1;
         } else if (n.isOptional && !this.isOptional) {
            return -1;
         } else {
            int compare = this.type.parsePriority().comparator().compare(this.parameterType(), n.parameterType());
            if (compare != 0) {
               return compare;
            } else {
               compare = n.parameterType().parsePriority().comparator().compare(n.parameterType(), this.parameterType());
               return compare == 0 ? 0 : -compare;
            }
         }
      }
   }

   public @NotNull AnnotationList annotations() {
      return this.parameter.annotations();
   }

   public boolean isOptional() {
      return this.isOptional;
   }

   public @NotNull ParameterType<A, T> parameterType() {
      return this.type;
   }

   public @NotNull SuggestionProvider<A> suggestions() {
      return this.suggestions;
   }

   public boolean isGreedy() {
      return this.parameterType().isGreedy();
   }

   public @NotNull CommandParameter parameter() {
      return this.parameter;
   }

   public @NotNull CommandPermission<A> permission() {
      return this.permission;
   }

   public @NotNull Collection<String> complete(@NotNull ExecutionContext<A> context) {
      return this.suggestions.getSuggestions(context);
   }

   public <L> @NotNull ParameterNode<A, L> requireParameterNode() {
      return this;
   }

   public @Nullable String description() {
      return (String)this.annotations().map(Description.class, Description::value);
   }

   public @Nullable String switchName() {
      if (this.switchAnn != null) {
         return this.switchAnn.value().isEmpty() ? this.name() : this.switchAnn.value();
      } else {
         return null;
      }
   }

   public @Nullable String flagName() {
      if (this.flagAnn != null) {
         return this.flagAnn.value().isEmpty() ? this.name() : this.flagAnn.value();
      } else {
         return null;
      }
   }

   public Character shorthand() {
      char shorthand;
      if (this.isFlag()) {
         shorthand = this.flagAnn.shorthand();
         if (shorthand == 0) {
            shorthand = this.flagName().charAt(0);
         }
      } else {
         if (!this.isSwitch()) {
            return null;
         }

         shorthand = this.switchAnn.shorthand();
         if (shorthand == 0) {
            shorthand = this.switchName().charAt(0);
         }
      }

      return shorthand;
   }

   public @NotNull String representation() {
      if (this.isFlag() || this.isSwitch()) {
         char shorthand = (Character)Objects.requireNonNull(this.shorthand(), "shorthand() is null for a flag or switch. This is not supposed to happen!");
         if (this.isSwitch()) {
            return "[--" + this.switchName() + " | -" + shorthand + "]";
         }

         if (this.isFlag()) {
            if (this.isOptional()) {
               return "[--" + this.flagName() + " <" + this.name() + "> | -" + shorthand + " <" + this.name() + ">]";
            }

            return "<--" + this.flagName() + " <" + this.name() + "> | -" + shorthand + " <" + this.name() + ">]";
         }
      }

      return this.isRequired() ? "<" + this.name() + ">" : "[" + this.name() + "]";
   }

   public boolean isSwitch() {
      return this.switchAnn != null;
   }

   public boolean isFlag() {
      return this.flagAnn != null;
   }

   public String toString() {
      return "ParameterNode(name=" + this.name() + ')';
   }
}
