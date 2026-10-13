package revxrsal.commands.orphan;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import revxrsal.commands.util.Preconditions;

public final class Orphans {
   private final List<String> paths;

   public Orphans(List<String> paths) {
      this.paths = paths;
   }

   public static Orphans path(String... paths) {
      Preconditions.notNull(paths, "paths");
      return new Orphans(Arrays.asList(paths));
   }

   public OrphanRegistry handler(OrphanCommand handler) {
      Preconditions.notNull(handler, "orphan command");
      return new OrphanRegistry(this.paths, handler);
   }

   public List<String> paths() {
      return this.paths;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         Orphans that = (Orphans)obj;
         return Objects.equals(this.paths, that.paths);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.paths});
   }

   public String toString() {
      return "Orphans[paths=" + this.paths + ']';
   }
}
