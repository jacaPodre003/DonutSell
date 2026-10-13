package space.hugster.sell.ui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.utils.WorthCache;
import space.hugster.sell.utils.menu.Button;
import space.hugster.sell.utils.menu.pagination.PaginatedMenu;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001c\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001c\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\u000e\u001a\u00020\nH\u0016¨\u0006\u0010"},
   d2 = {"Lspace/hugster/sell/ui/WorthMenu;", "Lspace/hugster/sell/utils/menu/pagination/PaginatedMenu;", "<init>", "()V", "getPrePaginatedTitle", "", "player", "Lorg/bukkit/entity/Player;", "getAllPagesButtons", "", "", "Lspace/hugster/sell/utils/menu/Button;", "getGlobalButtons", "getMaxItemsPerPage", "getSize", "Companion", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nWorthMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WorthMenu.kt\nspace/hugster/sell/ui/WorthMenu\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,165:1\n1062#2:166\n1053#2:167\n1053#2:168\n1187#2,2:169\n1261#2,4:171\n*S KotlinDebug\n*F\n+ 1 WorthMenu.kt\nspace/hugster/sell/ui/WorthMenu\n*L\n39#1:166\n42#1:167\n45#1:168\n50#1:169,2\n50#1:171,4\n*E\n"})
public final class WorthMenu extends PaginatedMenu {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private static WorthSortType currentSortType;

   @NotNull
   public String getPrePaginatedTitle(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      String var10000 = this.getPlugin().getMainConfig().getConfig().getString("WORTH-MENU.TITLE");
      if (var10000 == null) {
         var10000 = "&8ɪᴛᴇᴍ ᴘʀɪᴄᴇѕ";
      }

      return var10000;
   }

   @NotNull
   public Map<Integer, Button> getAllPagesButtons(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      List buttons = CollectionsKt.toList((Iterable)WorthCache.INSTANCE.getAllButtons());
      List var10000;
      switch (WorthMenu.WhenMappings.$EnumSwitchMapping$0[currentSortType.ordinal()]) {
         case 1:
            Iterable $this$sortedByDescending$iv = (Iterable)buttons;
            int $i$f$sortedByDescending = 0;
            var10000 = CollectionsKt.sortedWith($this$sortedByDescending$iv, new WorthMenu$getAllPagesButtons$$inlined$sortedByDescending$1());
            break;
         case 2:
            Iterable $this$sortedBy$iv = (Iterable)buttons;
            int $i$f$sortedBy = 0;
            var10000 = CollectionsKt.sortedWith($this$sortedBy$iv, new WorthMenu$getAllPagesButtons$$inlined$sortedBy$1());
            break;
         case 3:
            Iterable $this$sortedBy$iv = (Iterable)buttons;
            int $i$f$sortedBy = 0;
            var10000 = CollectionsKt.sortedWith($this$sortedBy$iv, new WorthMenu$getAllPagesButtons$$inlined$sortedBy$2());
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      List sortedButtons = var10000;
      Iterable $this$associate$iv = CollectionsKt.withIndex((Iterable)sortedButtons);
      int $i$f$associate = 0;
      int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associate$iv, 10)), 16);
      Map destination$iv$iv = (Map)(new LinkedHashMap(capacity$iv));
      int $i$f$associateTo = 0;

      for(Object element$iv$iv : $this$associate$iv) {
         IndexedValue var13 = (IndexedValue)element$iv$iv;
         int var14 = 0;
         int index = var13.component1();
         Button button = (Button)var13.component2();
         Pair var22 = TuplesKt.to(index, button);
         destination$iv$iv.put(var22.getFirst(), var22.getSecond());
      }

      return destination$iv$iv;
   }

   @NotNull
   public Map<Integer, Button> getGlobalButtons(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      return MapsKt.mapOf(TuplesKt.to(49, new SortWorthButton()));
   }

   public int getMaxItemsPerPage(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      return 45;
   }

   public int getSize() {
      return 54;
   }

   static {
      currentSortType = WorthSortType.HIGHEST_PRICE;
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"},
      d2 = {"Lspace/hugster/sell/ui/WorthMenu$Companion;", "", "<init>", "()V", "currentSortType", "Lspace/hugster/sell/ui/WorthSortType;", "getCurrentSortType", "()Lspace/hugster/sell/ui/WorthSortType;", "setCurrentSortType", "(Lspace/hugster/sell/ui/WorthSortType;)V", "DonutSell"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final WorthSortType getCurrentSortType() {
         return WorthMenu.currentSortType;
      }

      public final void setCurrentSortType(@NotNull WorthSortType <set-?>) {
         Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
         WorthMenu.currentSortType = <set-?>;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
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
         int[] var0 = new int[WorthSortType.values().length];

         try {
            var0[WorthSortType.HIGHEST_PRICE.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[WorthSortType.LOWEST_PRICE.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[WorthSortType.ALPHABETICALLY.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }
}
