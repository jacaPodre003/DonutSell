package revxrsal.commands.parameter.builtins;

import java.lang.reflect.Type;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.util.Classes;

@Internal
public final class ClassParameterTypeFactory<A extends CommandActor, T> implements ParameterType.Factory<A> {
   private final Class<?> type;
   private final ParameterType<A, T> parameterType;
   private final boolean allowSubclasses;

   public ClassParameterTypeFactory(Class<?> type, ParameterType<A, T> parameterType, boolean allowSubclasses) {
      this.type = Classes.wrap(type);
      this.parameterType = parameterType;
      this.allowSubclasses = allowSubclasses;
   }

   public <L> ParameterType<A, L> create(@NotNull Type parameterType, @NotNull AnnotationList annotations, @NotNull Lamp<A> lamp) {
      Class<?> pType = Classes.wrap(Classes.getRawType(parameterType));
      if (this.allowSubclasses && this.type.isAssignableFrom(pType)) {
         return this.parameterType;
      } else {
         return this.type == pType ? this.parameterType : null;
      }
   }

   public Class<?> type() {
      return this.type;
   }

   public ParameterType<A, T> parameterType() {
      return this.parameterType;
   }

   public boolean allowSubclasses() {
      return this.allowSubclasses;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         ClassParameterTypeFactory that = (ClassParameterTypeFactory)obj;
         return Objects.equals(this.type, that.type) && Objects.equals(this.parameterType, that.parameterType) && this.allowSubclasses == that.allowSubclasses;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.type, this.parameterType, this.allowSubclasses});
   }

   public String toString() {
      return "ClassParameterTypeFactory[type=" + this.type + ", parameterType=" + this.parameterType + ", allowSubclasses=" + this.allowSubclasses + ']';
   }
}
