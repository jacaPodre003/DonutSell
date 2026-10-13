package space.hugster.sell.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
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
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0012"},
   d2 = {"Lspace/hugster/sell/ui/SortWorthButton;", "Lspace/hugster/sell/utils/menu/Button;", "<init>", "()V", "getButtonItem", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "getSortLoreString", "", "type", "Lspace/hugster/sell/ui/WorthSortType;", "clicked", "", "clickType", "Lorg/bukkit/event/inventory/ClickType;", "shouldUpdate", "", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nWorthMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WorthMenu.kt\nspace/hugster/sell/ui/SortWorthButton\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,165:1\n1557#2:166\n1628#2,3:167\n*S KotlinDebug\n*F\n+ 1 WorthMenu.kt\nspace/hugster/sell/ui/SortWorthButton\n*L\n129#1:166\n129#1:167,3\n*E\n"})
public final class SortWorthButton extends Button {
   @NotNull
   public ItemStack getButtonItem(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      FileConfiguration config = this.getPlugin().getMainConfig().getConfig();
      String var10000 = config.getString("WORTH-MENU.SORT-BUTTON.NAME");
      if (var10000 == null) {
         var10000 = "&aSort";
      }

      String name = var10000;
      List var16 = config.getStringList("WORTH-MENU.SORT-BUTTON.LORE");
      Intrinsics.checkNotNullExpressionValue(var16, "getStringList(...)");
      List loreTemplate = var16;
      Iterable $this$map$iv = (Iterable)loreTemplate;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         String line = (String)item$iv$iv;
         int var14 = 0;
         Intrinsics.checkNotNull(line);
         destination$iv$iv.add(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(line, "{status_HIGHEST_PRICE}", this.getSortLoreString(WorthSortType.HIGHEST_PRICE), false, 4, (Object)null), "{status_LOWEST_PRICE}", this.getSortLoreString(WorthSortType.LOWEST_PRICE), false, 4, (Object)null), "{status_ALPHABETICALLY}", this.getSortLoreString(WorthSortType.ALPHABETICALLY), false, 4, (Object)null));
      }

      List lore = (List)destination$iv$iv;
      return (new ItemBuilder(Material.CAULDRON)).name(name).addLore(lore).build();
   }

   private final String getSortLoreString(WorthSortType type) {
      FileConfiguration config = this.getPlugin().getMainConfig().getConfig();
      String var10000 = config.getString("WORTH-MENU.SORT-STATUS." + type.name());
      if (var10000 == null) {
         var10000 = "▪ " + type.getDisplayName();
      }

      String statusText = var10000;
      String prefix = WorthMenu.Companion.getCurrentSortType() == type ? "&a" : "&f";
      return prefix + statusText;
   }

   public void clicked(@NotNull Player player, @NotNull ClickType clickType) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(clickType, "clickType");
      WorthMenu.Companion var10000 = WorthMenu.Companion;
      WorthSortType var10001;
      switch (SortWorthButton.WhenMappings.$EnumSwitchMapping$0[WorthMenu.Companion.getCurrentSortType().ordinal()]) {
         case 1 -> var10001 = WorthSortType.LOWEST_PRICE;
         case 2 -> var10001 = WorthSortType.ALPHABETICALLY;
         case 3 -> var10001 = WorthSortType.HIGHEST_PRICE;
         default -> throw new NoWhenBranchMatchedException();
      }

      var10000.setCurrentSortType(var10001);
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
