package space.hugster.sell.utils;

import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0001¨\u0006\u0007"},
   d2 = {"Lspace/hugster/sell/utils/VersionUtils;", "", "<init>", "()V", "getTopInventory", "Lorg/bukkit/inventory/Inventory;", "view", "DonutSell"}
)
public final class VersionUtils {
   @NotNull
   public static final VersionUtils INSTANCE = new VersionUtils();

   private VersionUtils() {
   }

   @NotNull
   public final Inventory getTopInventory(@NotNull Object view) {
      Intrinsics.checkNotNullParameter(view, "view");

      try {
         Method method = view.getClass().getMethod("getTopInventory");
         method.setAccessible(true);
         Object var10000 = method.invoke(view);
         Intrinsics.checkNotNull(var10000, "null cannot be cast to non-null type org.bukkit.inventory.Inventory");
         Inventory var5 = (Inventory)var10000;
         return var5;
      } catch (Exception e) {
         throw new RuntimeException("Error: InventoryView.", (Throwable)e);
      }
   }
}
