package space.hugster.sell.utils.menu.pagination;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.utils.menu.Button;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J(\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lspace/hugster/sell/utils/menu/pagination/JumpToPageButton;", "Lspace/hugster/sell/utils/menu/Button;", "page", "", "menu", "Lspace/hugster/sell/utils/menu/pagination/PaginatedMenu;", "current", "", "<init>", "(ILspace/hugster/sell/utils/menu/pagination/PaginatedMenu;Z)V", "getButtonItem", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "clicked", "", "i", "clickType", "Lorg/bukkit/event/inventory/ClickType;", "hb", "DonutSell"}
)
public final class JumpToPageButton extends Button {
   private final int page;
   @NotNull
   private final PaginatedMenu menu;
   private final boolean current;

   public JumpToPageButton(int page, @NotNull PaginatedMenu menu, boolean current) {
      Intrinsics.checkNotNullParameter(menu, "menu");
      super();
      this.page = page;
      this.menu = menu;
      this.current = current;
   }

   @NotNull
   public ItemStack getButtonItem(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      Material material = this.current ? Material.ENCHANTED_BOOK : Material.BOOK;
      ItemStack itemStack = new ItemStack(material, this.page);
      ItemMeta itemMeta = itemStack.getItemMeta();
      if (itemMeta != null) {
         int var6 = 0;
         itemMeta.setDisplayName(ChatColor.GREEN + "Page " + this.page);
         if (this.current) {
            String[] var7 = new String[]{"", ChatColor.GREEN + "Current page"};
            itemMeta.setLore(CollectionsKt.listOf(var7));
         }

         itemStack.setItemMeta(itemMeta);
      }

      return itemStack;
   }

   public void clicked(@NotNull Player player, int i, @NotNull ClickType clickType, int hb) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(clickType, "clickType");
      this.menu.modPage(player, this.page - this.menu.getPage());
      Button.Companion.playNeutral(player);
   }
}
