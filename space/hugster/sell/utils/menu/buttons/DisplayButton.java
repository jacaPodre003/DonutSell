package space.hugster.sell.utils.menu.buttons;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.hugster.sell.utils.menu.Button;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0013"},
   d2 = {"Lspace/hugster/sell/utils/menu/buttons/DisplayButton;", "Lspace/hugster/sell/utils/menu/Button;", "itemStack", "Lorg/bukkit/inventory/ItemStack;", "cancel", "", "<init>", "(Lorg/bukkit/inventory/ItemStack;Z)V", "getItemStack", "()Lorg/bukkit/inventory/ItemStack;", "setItemStack", "(Lorg/bukkit/inventory/ItemStack;)V", "getCancel", "()Z", "setCancel", "(Z)V", "getButtonItem", "player", "Lorg/bukkit/entity/Player;", "DonutSell"}
)
public final class DisplayButton extends Button {
   @Nullable
   private ItemStack itemStack;
   private boolean cancel;

   public DisplayButton(@Nullable ItemStack itemStack, boolean cancel) {
      this.itemStack = itemStack;
      this.cancel = cancel;
   }

   // $FF: synthetic method
   public DisplayButton(ItemStack var1, boolean var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 1) != 0) {
         var1 = null;
      }

      if ((var3 & 2) != 0) {
         var2 = true;
      }

      this(var1, var2);
   }

   @Nullable
   public final ItemStack getItemStack() {
      return this.itemStack;
   }

   public final void setItemStack(@Nullable ItemStack <set-?>) {
      this.itemStack = <set-?>;
   }

   public final boolean getCancel() {
      return this.cancel;
   }

   public final void setCancel(boolean <set-?>) {
      this.cancel = <set-?>;
   }

   @NotNull
   public ItemStack getButtonItem(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      ItemStack var10000 = this.itemStack;
      if (var10000 == null) {
         var10000 = new ItemStack(Material.AIR);
      }

      return var10000;
   }

   public DisplayButton() {
      this((ItemStack)null, false, 3, (DefaultConstructorMarker)null);
   }
}
