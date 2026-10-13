package space.hugster.sell.utils.menu.pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.hugster.sell.utils.menu.Button;
import space.hugster.sell.utils.menu.Menu;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0005J\u000e\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fJ\u001c\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u000b\u001a\u00020\fH\u0017J\u0010\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001e\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00122\u0006\u0010\u000b\u001a\u00020\fH\u0016J,\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00130\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0004J\u0010\u0010\u001d\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH&J\u001c\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u000b\u001a\u00020\fH&R\u001e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u001f"},
   d2 = {"Lspace/hugster/sell/utils/menu/pagination/PaginatedMenu;", "Lspace/hugster/sell/utils/menu/Menu;", "<init>", "()V", "value", "", "page", "getPage", "()I", "getTitle", "", "player", "Lorg/bukkit/entity/Player;", "modPage", "", "mod", "getPages", "getButtons", "", "Lspace/hugster/sell/utils/menu/Button;", "getMaxItemsPerPage", "getGlobalButtons", "bottomTopButtons", "full", "", "buttons", "", "itemStack", "Lorg/bukkit/inventory/ItemStack;", "getPrePaginatedTitle", "getAllPagesButtons", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nPaginatedMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PaginatedMenu.kt\nspace/hugster/sell/utils/menu/pagination/PaginatedMenu\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,114:1\n216#2,2:115\n774#3:117\n865#3,2:118\n1863#3,2:120\n*S KotlinDebug\n*F\n+ 1 PaginatedMenu.kt\nspace/hugster/sell/utils/menu/pagination/PaginatedMenu\n*L\n57#1:115,2\n87#1:117\n87#1:118,2\n88#1:120,2\n*E\n"})
public abstract class PaginatedMenu extends Menu {
   private int page = 1;

   public final int getPage() {
      return this.page;
   }

   @NotNull
   public String getTitle(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      String var10000 = this.getPrePaginatedTitle(player);
      return var10000 + " - " + this.page + "/" + this.getPages(player);
   }

   public final void modPage(@NotNull Player player, int mod) {
      Intrinsics.checkNotNullParameter(player, "player");
      this.page += mod;
      this.getButtons().clear();
      this.openMenu(player);
   }

   public final int getPages(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      int buttonAmount = this.getAllPagesButtons(player).size();
      return buttonAmount == 0 ? 1 : (int)Math.ceil((double)buttonAmount / (double)this.getMaxItemsPerPage(player));
   }

   /** @deprecated */
   @Deprecated(
      message = "Use `getButtons` method directly"
   )
   @NotNull
   public Map<Integer, Button> getButtons(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      int minIndex = (this.page - 1) * this.getMaxItemsPerPage(player);
      int maxIndex = this.page * this.getMaxItemsPerPage(player);
      Map buttons = (Map)(new LinkedHashMap());
      buttons.put(48, new PageButton(-1, this));
      buttons.put(50, new PageButton(1, this));
      Map $this$forEach$iv = this.getAllPagesButtons(player);
      int $i$f$forEach = 0;

      for(Map.Entry element$iv : $this$forEach$iv.entrySet()) {
         int var10 = 0;
         int ind = ((Number)element$iv.getKey()).intValue();
         Button button = (Button)element$iv.getValue();
         if (minIndex <= ind ? ind < maxIndex : false) {
            int adjustedIndex = ind - this.getMaxItemsPerPage(player) * (this.page - 1);
            Integer var14 = adjustedIndex;
            buttons.put(var14, button);
         }
      }

      Map var10000 = this.getGlobalButtons(player);
      if (var10000 != null) {
         Map global = var10000;
         int var16 = 0;
         buttons.putAll(global);
      }

      return buttons;
   }

   public int getMaxItemsPerPage(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      return 18;
   }

   @Nullable
   public Map<Integer, Button> getGlobalButtons(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      return null;
   }

   protected final void bottomTopButtons(boolean full, @NotNull Map<Integer, Button> buttons, @NotNull final ItemStack itemStack) {
      Intrinsics.checkNotNullParameter(buttons, "buttons");
      Intrinsics.checkNotNullParameter(itemStack, "itemStack");
      Iterable $this$filter$iv = (Iterable)RangesKt.until(0, this.getSize());
      int $i$f$filter = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterTo = 0;

      for(Object element$iv$iv : $this$filter$iv) {
         int slot = ((Number)element$iv$iv).intValue();
         int var12 = 0;
         if (buttons.get(slot) == null) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      $this$filter$iv = (Iterable)((List)destination$iv$iv);
      $i$f$filter = 0;

      for(Object element$iv : $this$filter$iv) {
         $i$f$filterTo = ((Number)element$iv).intValue();
         int var17 = 0;
         if ($i$f$filterTo < 9 || $i$f$filterTo > this.getSize() - 10 || full && ($i$f$filterTo % 9 == 0 || ($i$f$filterTo + 1) % 9 == 0)) {
            Integer var18 = $i$f$filterTo;
            buttons.put(var18, new Button() {
               public ItemStack getButtonItem(Player player) {
                  Intrinsics.checkNotNullParameter(player, "player");
                  return itemStack;
               }
            });
         }
      }

   }

   @NotNull
   public abstract String getPrePaginatedTitle(@NotNull Player player);

   @NotNull
   public abstract Map<Integer, Button> getAllPagesButtons(@NotNull Player player);
}
