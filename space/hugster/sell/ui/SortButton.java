package space.hugster.sell.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.utils.ItemBuilder;
import space.hugster.sell.utils.menu.Button;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
   d2 = {"Lspace/hugster/sell/ui/SortButton;", "Lspace/hugster/sell/utils/menu/Button;", "menu", "Lspace/hugster/sell/ui/SellHistoryMenu;", "<init>", "(Lspace/hugster/sell/ui/SellHistoryMenu;)V", "getButtonItem", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "clicked", "", "clickType", "Lorg/bukkit/event/inventory/ClickType;", "shouldUpdate", "", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nSellHistoryMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SellHistoryMenu.kt\nspace/hugster/sell/ui/SortButton\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,107:1\n1557#2:108\n1628#2,3:109\n*S KotlinDebug\n*F\n+ 1 SellHistoryMenu.kt\nspace/hugster/sell/ui/SortButton\n*L\n87#1:108\n87#1:109,3\n*E\n"})
public final class SortButton extends Button {
   @NotNull
   private final SellHistoryMenu menu;

   public SortButton(@NotNull SellHistoryMenu menu) {
      Intrinsics.checkNotNullParameter(menu, "menu");
      super();
      this.menu = menu;
   }

   @NotNull
   public ItemStack getButtonItem(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      ConfigurationSection var10000 = this.getPlugin().getConfig().getConfigurationSection("SELL-HISTORY-MENU.SORT-BUTTON");
      Intrinsics.checkNotNull(var10000);
      ConfigurationSection buttonConfig = var10000;
      String var16 = buttonConfig.getString("MATERIAL");
      Intrinsics.checkNotNull(var16);
      Material var17 = Material.matchMaterial(var16);
      if (var17 == null) {
         var17 = Material.ANVIL;
      }

      Material material = var17;
      String var18 = buttonConfig.getString("TITLE");
      Intrinsics.checkNotNull(var18);
      String var10002 = this.menu.getSortState().name().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var10002, "toLowerCase(...)");
      String title = StringsKt.replace$default(var18, "{sort_state}", StringsKt.replace$default(var10002, "_", " ", false, 4, (Object)null), false, 4, (Object)null);
      List var19 = buttonConfig.getStringList("LORE");
      Intrinsics.checkNotNullExpressionValue(var19, "getStringList(...)");
      Iterable $this$map$iv = (Iterable)var19;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         String it = (String)item$iv$iv;
         int var14 = 0;
         Intrinsics.checkNotNull(it);
         var10002 = this.menu.getSortState().name().toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var10002, "toLowerCase(...)");
         destination$iv$iv.add(StringsKt.replace$default(it, "{sort_state}", StringsKt.replace$default(var10002, "_", " ", false, 4, (Object)null), false, 4, (Object)null));
      }

      List lore = (List)destination$iv$iv;
      return (new ItemBuilder(material)).name(title).addLore(lore).build();
   }

   public void clicked(@NotNull Player player, @NotNull ClickType clickType) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(clickType, "clickType");
      SellHistoryMenu var10000 = this.menu;
      SellHistoryMenu.SortState var10001;
      switch (SortButton.WhenMappings.$EnumSwitchMapping$0[this.menu.getSortState().ordinal()]) {
         case 1 -> var10001 = SellHistoryMenu.SortState.BY_NAME;
         case 2 -> var10001 = SellHistoryMenu.SortState.BY_AMOUNT;
         default -> throw new NoWhenBranchMatchedException();
      }

      var10000.setSortState(var10001);
   }

   public boolean shouldUpdate(@NotNull Player player, @NotNull ClickType clickType) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(clickType, "clickType");
      return true;
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
