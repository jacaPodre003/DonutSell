package space.hugster.sell.ui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import space.hugster.sell.data.Profile;
import space.hugster.sell.managers.ProfileManager;
import space.hugster.sell.utils.menu.Button;
import space.hugster.sell.utils.menu.pagination.PaginatedMenu;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u0015B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u001c\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f2\u0006\u0010\f\u001a\u00020\rH\u0016J\u001c\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u0014\u001a\u00020\u0010H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0016"},
   d2 = {"Lspace/hugster/sell/ui/SellHistoryMenu;", "Lspace/hugster/sell/utils/menu/pagination/PaginatedMenu;", "<init>", "()V", "sortState", "Lspace/hugster/sell/ui/SellHistoryMenu$SortState;", "getSortState", "()Lspace/hugster/sell/ui/SellHistoryMenu$SortState;", "setSortState", "(Lspace/hugster/sell/ui/SellHistoryMenu$SortState;)V", "getPrePaginatedTitle", "", "player", "Lorg/bukkit/entity/Player;", "getAllPagesButtons", "", "", "Lspace/hugster/sell/utils/menu/Button;", "getGlobalButtons", "getMaxItemsPerPage", "getSize", "SortState", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nSellHistoryMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SellHistoryMenu.kt\nspace/hugster/sell/ui/SellHistoryMenu\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,107:1\n1062#2:108\n1053#2:109\n1863#2,2:110\n*S KotlinDebug\n*F\n+ 1 SellHistoryMenu.kt\nspace/hugster/sell/ui/SellHistoryMenu\n*L\n30#1:108\n33#1:109\n36#1:110,2\n*E\n"})
public final class SellHistoryMenu extends PaginatedMenu {
   @NotNull
   private SortState sortState;

   public SellHistoryMenu() {
      this.sortState = SellHistoryMenu.SortState.BY_AMOUNT;
   }

   @NotNull
   public final SortState getSortState() {
      return this.sortState;
   }

   public final void setSortState(@NotNull SortState <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.sortState = <set-?>;
   }

   @NotNull
   public String getPrePaginatedTitle(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      String var10000 = this.getPlugin().getConfig().getString("SELL-HISTORY-MENU.TITLE");
      if (var10000 == null) {
         var10000 = "&8ѕᴇʟʟ ʜɪѕᴛᴏʀʏ";
      }

      return var10000;
   }

   @NotNull
   public Map<Integer, Button> getAllPagesButtons(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      Map buttons = (Map)(new LinkedHashMap());
      ProfileManager var10000 = this.getPlugin().getProfileManager();
      UUID var10001 = player.getUniqueId();
      Intrinsics.checkNotNullExpressionValue(var10001, "getUniqueId(...)");
      Profile var22 = var10000.getProfile(var10001);
      if (var22 == null) {
         return buttons;
      } else {
         Profile profile = var22;
         Map soldItems = profile.getSellHistory().getSoldItems();
         List var23;
         switch (SellHistoryMenu.WhenMappings.$EnumSwitchMapping$0[this.sortState.ordinal()]) {
            case 1:
               Iterable $this$sortedByDescending$iv = (Iterable)soldItems.entrySet();
               int $i$f$sortedByDescending = 0;
               var23 = CollectionsKt.sortedWith($this$sortedByDescending$iv, new SellHistoryMenu$getAllPagesButtons$$inlined$sortedByDescending$1());
               break;
            case 2:
               Iterable $this$sortedBy$iv = (Iterable)soldItems.entrySet();
               int $i$f$sortedBy = 0;
               var23 = CollectionsKt.sortedWith($this$sortedBy$iv, new SellHistoryMenu$getAllPagesButtons$$inlined$sortedBy$1());
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }

         List sortedItems = var23;
         Iterable $this$forEach$iv = (Iterable)sortedItems;
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            Map.Entry var10 = (Map.Entry)element$iv;
            int var11 = 0;
            String materialName = (String)var10.getKey();
            JSONObject data = (JSONObject)var10.getValue();
            Material var24 = Material.matchMaterial(materialName);
            if (var24 != null) {
               Material material = var24;
               int amount = data.optInt("amount", 0);
               double price = data.optDouble("price", (double)0.0F);
               buttons.put(buttons.size(), new MaterialButton(material, amount, price));
            }
         }

         return buttons;
      }
   }

   @NotNull
   public Map<Integer, Button> getGlobalButtons(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      return MapsKt.mapOf(TuplesKt.to(49, new SortButton(this)));
   }

   public int getMaxItemsPerPage(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      return 45;
   }

   public int getSize() {
      return 54;
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
      d2 = {"Lspace/hugster/sell/ui/SellHistoryMenu$SortState;", "", "<init>", "(Ljava/lang/String;I)V", "BY_AMOUNT", "BY_NAME", "DonutSell"}
   )
   public static enum SortState {
      BY_AMOUNT,
      BY_NAME;

      // $FF: synthetic field
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

      @NotNull
      public static EnumEntries<SortState> getEntries() {
         return $ENTRIES;
      }

      // $FF: synthetic method
      private static final SortState[] $values() {
         SortState[] var0 = new SortState[]{BY_AMOUNT, BY_NAME};
         return var0;
      }
   }

   // $FF: synthetic class
   @Metadata(
      mv = {2, 1, 0},
      k = 3,
      xi = 48
   )
   public class WhenMappings {
      // $FF: synthetic field
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[SellHistoryMenu.SortState.values().length];

         try {
            var0[SellHistoryMenu.SortState.BY_AMOUNT.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[SellHistoryMenu.SortState.BY_NAME.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }
}
