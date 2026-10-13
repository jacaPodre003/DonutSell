package revxrsal.commands.bukkit.util;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.util.Preconditions;

public final class BukkitUtils {
   private static final boolean BRIGADIER = brigadierAvailable();

   private BukkitUtils() {
      Preconditions.cannotInstantiate(BukkitUtils.class);
   }

   private static boolean brigadierAvailable() {
      try {
         Class.forName("com.mojang.brigadier.arguments.StringArgumentType");
         return true;
      } catch (ClassNotFoundException var1) {
         return false;
      }
   }

   public static boolean isBrigadierAvailable() {
      return BRIGADIER;
   }

   public static @NotNull String legacyColorize(@NotNull String text) {
      return ChatColor.translateAlternateColorCodes('&', text);
   }
}
