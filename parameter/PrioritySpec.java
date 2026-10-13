package revxrsal.commands.parameter;

import java.util.Comparator;
import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class PrioritySpec {
   private static final PrioritySpec DEFAULT = new PrioritySpec((o1, o2) -> 0);
   private static final PrioritySpec LOWEST = new PrioritySpec((o1, o2) -> o1.getClass() == o2.getClass() ? 0 : 1);
   private static final PrioritySpec HIGHEST = new PrioritySpec((o1, o2) -> o1.getClass() == o2.getClass() ? 0 : -1);
   private final Comparator<ParameterType<?, ?>> comparator;

   public PrioritySpec(Comparator<ParameterType<?, ?>> comparator) {
      this.comparator = comparator;
   }

   @Contract("-> new")
   public static @NotNull Builder builder() {
      return new Builder();
   }

   public static @NotNull PrioritySpec lowest() {
      return LOWEST;
   }

   public static @NotNull PrioritySpec highest() {
      return HIGHEST;
   }

   public static @NotNull PrioritySpec defaultPriority() {
      return DEFAULT;
   }

   @Contract(
      pure = true,
      value = "-> new"
   )
   public @NotNull Builder toBuilder() {
      return new Builder(this.comparator);
   }

   public Comparator<ParameterType<?, ?>> comparator() {
      return this.comparator;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         PrioritySpec that = (PrioritySpec)obj;
         return Objects.equals(this.comparator, that.comparator);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.comparator});
   }

   public String toString() {
      return "PrioritySpec[comparator=" + this.comparator + ']';
   }

   public static class Builder {
      private Comparator<ParameterType<?, ?>> comparator;

      public Builder() {
         this((o1, o2) -> 0);
      }

      public Builder(Comparator<ParameterType<?, ?>> comparator) {
         this.comparator = comparator;
      }

      public @NotNull Builder addComparator(@NotNull Comparator<ParameterType<?, ?>> newComparator) {
         this.comparator = newComparator.thenComparing(this.comparator);
         return this;
      }

      public @NotNull Builder higherThan(Class<? extends ParameterType<?, ?>> parameterType) {
         Comparator<ParameterType<?, ?>> c = (o1, o2) -> {
            if (parameterType.isAssignableFrom(o2.getClass())) {
               return -1;
            } else {
               return parameterType.isAssignableFrom(o1.getClass()) ? 1 : 0;
            }
         };
         return this.addComparator(c);
      }

      public @NotNull Builder lowerThan(Class<? extends ParameterType<?, ?>> parameterType) {
         Comparator<ParameterType<?, ?>> c = (o1, o2) -> {
            if (parameterType.isAssignableFrom(o2.getClass())) {
               return 1;
            } else {
               return parameterType.isAssignableFrom(o1.getClass()) ? -1 : 0;
            }
         };
         return this.addComparator(c);
      }

      @Contract(
         value = "-> new",
         pure = true
      )
      public @NotNull PrioritySpec build() {
         return new PrioritySpec(this.comparator);
      }
   }
}
