package space.hugster.sell.utils.menu.buttons;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.utils.menu.Button;
import space.hugster.sell.utils.menu.Menu;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J(\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lspace/hugster/sell/utils/menu/buttons/BackButton;", "Lspace/hugster/sell/utils/menu/Button;", "back", "Lspace/hugster/sell/utils/menu/Menu;", "<init>", "(Lspace/hugster/sell/utils/menu/Menu;)V", "getButtonItem", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "clicked", "", "i", "", "clickType", "Lorg/bukkit/event/inventory/ClickType;", "hb", "DonutSell"}
)
public final class BackButton extends Button {
   @NotNull
   private final Menu back;

   public BackButton(@NotNull Menu back) {
      Intrinsics.checkNotNullParameter(back, "back");
      super();
      this.back = back;
   }

   @NotNull
   public ItemStack getButtonItem(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      ItemStack itemStack = new ItemStack(Material.RED_BED);
      ItemMeta itemMeta = itemStack.getItemMeta();
      if (itemMeta != null) {
         itemMeta.setDisplayName(ChatColor.RED + "Go back");
      }

      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public void clicked(@NotNull Player player, int i, @NotNull ClickType clickType, int hb) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(clickType, "clickType");
      Button.Companion.playNeutral(player);
      this.back.openMenu(player);
   }
}
