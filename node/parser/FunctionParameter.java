package revxrsal.commands.node.parser;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Length;
import revxrsal.commands.annotation.Sized;
import revxrsal.commands.annotation.Switch;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandParameter;

final class FunctionParameter implements CommandParameter {
   private final @NotNull Parameter parameter;
   private final @NotNull String name;
   private final @NotNull AnnotationList annotations;
   private final int methodIndex;

   FunctionParameter(@NotNull Parameter parameter, @NotNull String name, @NotNull AnnotationList annotations, int methodIndex) {
      this.parameter = parameter;
      this.name = name;
      this.annotations = annotations;
      this.methodIndex = methodIndex;
   }

   public boolean isLastInMethod() {
      return this.method().getParameterCount() == this.methodIndex + 1;
   }

   public @NotNull Method method() {
      return (Method)this.parameter.getDeclaringExecutable();
   }

   public @NotNull Class<?> type() {
      return this.parameter.getType();
   }

   public @NotNull Type fullType() {
      return this.parameter.getParameterizedType();
   }

   public @NotNull List<Type> generics() {
      Type type = this.parameter.getParameterizedType();
      return type instanceof ParameterizedType ? Arrays.asList(((ParameterizedType)type).getActualTypeArguments()) : Collections.emptyList();
   }

   public boolean isOptional() {
      if (this.type() == Optional.class) {
         return true;
      } else if (!this.annotations.contains(revxrsal.commands.annotation.Optional.class) && !this.annotations.contains(Default.class) && !this.annotations.contains(Switch.class)) {
         Sized sized = (Sized)this.annotations.get(Sized.class);
         if (sized != null) {
            return sized.min() == 0;
         } else {
            Length length = (Length)this.annotations.get(Length.class);
            if (length != null) {
               return length.min() == 0;
            } else {
               return false;
            }
         }
      } else {
         return true;
      }
   }

   public @NotNull Parameter parameter() {
      return this.parameter;
   }

   public @NotNull String name() {
      return this.name;
   }

   public @NotNull AnnotationList annotations() {
      return this.annotations;
   }

   public int methodIndex() {
      return this.methodIndex;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         FunctionParameter that = (FunctionParameter)obj;
         return Objects.equals(this.parameter, that.parameter) && Objects.equals(this.name, that.name) && Objects.equals(this.annotations, that.annotations) && this.methodIndex == that.methodIndex;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.parameter, this.name, this.annotations, this.methodIndex});
   }

   public String toString() {
      return "FunctionParameter[parameter=" + this.parameter + ", name=" + this.name + ", annotations=" + this.annotations + ", methodIndex=" + this.methodIndex + ']';
   }
}
