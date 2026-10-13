package revxrsal.commands.orphan;

import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public final class OrphanRegistry {
   private final @NotNull @Unmodifiable List<String> paths;
   private final @NotNull OrphanCommand handler;

   public OrphanRegistry(@NotNull @Unmodifiable List<String> paths, @NotNull OrphanCommand handler) {
      this.paths = paths;
      this.handler = handler;
   }

   public @NotNull @Unmodifiable List<String> paths() {
      return this.paths;
   }

   public @NotNull OrphanCommand handler() {
      return this.handler;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         OrphanRegistry that = (OrphanRegistry)obj;
         return Objects.equals(this.paths, that.paths) && Objects.equals(this.handler, that.handler);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.paths, this.handler});
   }

   public String toString() {
      return "OrphanRegistry[paths=" + this.paths + ", handler=" + this.handler + ']';
   }
}
