package revxrsal.commands.bukkit.sender;

import java.util.Objects;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.command.CommandPermission;

public final class BukkitCommandPermission implements CommandPermission<BukkitCommandActor> {
   private final @NotNull Permission permission;

   public BukkitCommandPermission(@NotNull Permission permission) {
      this.permission = permission;
   }

   public boolean isExecutableBy(@NotNull BukkitCommandActor actor) {
      return actor.sender().hasPermission(this.permission);
   }

   public @NotNull Permission permission() {
      return this.permission;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         BukkitCommandPermission that = (BukkitCommandPermission)obj;
         return Objects.equals(this.permission, that.permission);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.permission});
   }

   public String toString() {
      return "BukkitCommandPermission[permission=" + this.permission.getName() + "]";
   }
}
