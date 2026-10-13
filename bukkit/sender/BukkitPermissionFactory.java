package revxrsal.commands.bukkit.sender;

import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.command.CommandPermission;

public enum BukkitPermissionFactory implements CommandPermission.Factory<BukkitCommandActor> {
   INSTANCE;

   public @Nullable CommandPermission<BukkitCommandActor> create(@NotNull AnnotationList annotations, @NotNull Lamp<BukkitCommandActor> lamp) {
      revxrsal.commands.bukkit.annotation.CommandPermission permissionAnn = (revxrsal.commands.bukkit.annotation.CommandPermission)annotations.get(revxrsal.commands.bukkit.annotation.CommandPermission.class);
      return permissionAnn == null ? null : new BukkitCommandPermission(new Permission(permissionAnn.value(), permissionAnn.defaultAccess()));
   }
}
