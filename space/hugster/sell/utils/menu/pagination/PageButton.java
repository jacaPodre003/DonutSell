package space.hugster.sell.utils.menu.pagination;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.utils.CC;
import space.hugster.sell.utils.menu.Button;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J(\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0003H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lspace/hugster/sell/utils/menu/pagination/PageButton;", "Lspace/hugster/sell/utils/menu/Button;", "mod", "", "menu", "Lspace/hugster/sell/utils/menu/pagination/PaginatedMenu;", "<init>", "(ILspace/hugster/sell/utils/menu/pagination/PaginatedMenu;)V", "getButtonItem", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "clicked", "", "i", "clickType", "Lorg/bukkit/event/inventory/ClickType;", "hb", "hasNext", "", "DonutSell"}
)
public final class PageButton extends Button {
   private final int mod;
   @NotNull
   private final PaginatedMenu menu;

   public PageButton(int mod, @NotNull PaginatedMenu menu) {
      Intrinsics.checkNotNullParameter(menu, "menu");
      super();
      this.mod = mod;
      this.menu = menu;
   }

   @NotNull
   public ItemStack getButtonItem(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      FileConfiguration config = this.getPlugin().getMainConfig().getConfig();
      ItemStack itemStack = new ItemStack(Material.ARROW);
      ItemMeta itemMeta = itemStack.getItemMeta();
      if (itemMeta != null) {
         int var6 = 0;
         String var8;
         if (this.hasNext(player)) {
            if (this.mod > 0) {
               CC var10000 = CC.INSTANCE;
               String var10001 = config.getString("SETTINGS.BUTTONS.NEXT-PAGE");
               if (var10001 == null) {
                  var10001 = "&aɴᴇxᴛ";
               }

               var8 = var10000.translate(var10001);
            } else {
               CC var9 = CC.INSTANCE;
               String var12 = config.getString("SETTINGS.BUTTONS.BACK-PAGE");
               if (var12 == null) {
                  var12 = "&aʙᴀᴄᴋ";
               }

               var8 = var9.translate(var12);
            }
         } else if (this.mod > 0) {
            CC var10 = CC.INSTANCE;
            String var13 = config.getString("SETTINGS.BUTTONS.LAST-PAGE");
            if (var13 == null) {
               var13 = "&aʟᴀѕᴛ ᴘᴀɢᴇ";
            }

            var8 = var10.translate(var13);
         } else {
            CC var11 = CC.INSTANCE;
            String var14 = config.getString("SETTINGS.BUTTONS.FIRST-PAGE");
            if (var14 == null) {
               var14 = "&aꜰɪʀѕᴛ ᴘᴀɢᴇ";
            }

            var8 = var11.translate(var14);
         }

         String displayName = var8;
         itemMeta.setDisplayName(displayName);
         itemStack.setItemMeta(itemMeta);
      }

      itemStack.setDurability((short)7);
      return itemStack;
   }

   public void clicked(@NotNull Player player, int i, @NotNull ClickType clickType, int hb) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(clickType, "clickType");
      if (clickType == ClickType.RIGHT) {
         (new ViewAllPagesMenu(this.menu)).openMenu(player);
         Button.Companion.playNeutral(player);
      } else if (this.hasNext(player)) {
         this.menu.modPage(player, this.mod);
         Button.Companion.playNeutral(player);
      } else {
         Button.Companion.playFail(player);
      }

   }

   private final boolean hasNext(Player player) {
      int pg = this.menu.getPage() + this.mod;
      return pg > 0 && this.menu.getPages(player) >= pg;
   }
}
