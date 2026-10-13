package space.hugster.sell.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import space.hugster.sell.DonutSell;
import space.hugster.sell.commands.suggests.annotations.WorthItem;
import space.hugster.sell.ui.ItemButton;
import space.hugster.sell.ui.WorthMenu;
import space.hugster.sell.utils.CC;
import space.hugster.sell.utils.Utilities;
import space.hugster.sell.utils.WorthCache;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001c\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\n\b\u0003\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0007R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"},
   d2 = {"Lspace/hugster/sell/commands/WorthCommand;", "", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "getPlugin", "()Lspace/hugster/sell/DonutSell;", "plugin$delegate", "Lkotlin/Lazy;", "onWorth", "", "player", "Lorg/bukkit/entity/Player;", "itemName", "", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nWorthCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WorthCommand.kt\nspace/hugster/sell/commands/WorthCommand\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,66:1\n808#2,11:67\n295#2,2:78\n*S KotlinDebug\n*F\n+ 1 WorthCommand.kt\nspace/hugster/sell/commands/WorthCommand\n*L\n34#1:67,11\n35#1:78,2\n*E\n"})
public final class WorthCommand {
   @NotNull
   private final Lazy plugin$delegate = LazyKt.lazy(WorthCommand::plugin_delegate$lambda$0);

   private final DonutSell getPlugin() {
      Lazy var1 = this.plugin$delegate;
      return (DonutSell)var1.getValue();
   }

   @Command({"worth"})
   public final void onWorth(@NotNull Player player, @Optional @WorthItem @Nullable String itemName) {
      Intrinsics.checkNotNullParameter(player, "player");
      if (itemName == null) {
         (new WorthMenu()).openMenu(player);
      } else {
         ItemStack var10000 = player.getInventory().getItemInMainHand();
         Intrinsics.checkNotNullExpressionValue(var10000, "getItemInMainHand(...)");
         ItemStack itemInHand = var10000;
         Iterable $this$filterIsInstance$iv = (Iterable)WorthCache.INSTANCE.getAllButtons();
         int $i$f$filterIsInstance = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         int $i$f$filterIsInstanceTo = 0;

         for(Object element$iv$iv : $this$filterIsInstance$iv) {
            if (element$iv$iv instanceof ItemButton) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         $this$filterIsInstance$iv = (Iterable)((List)destination$iv$iv);
         $i$f$filterIsInstance = 0;
         Iterator $this$filterIsInstanceTo$iv$iv = $this$filterIsInstance$iv.iterator();

         while(true) {
            if (!$this$filterIsInstanceTo$iv$iv.hasNext()) {
               var22 = null;
               break;
            }

            Object element$iv = $this$filterIsInstanceTo$iv$iv.next();
            ItemButton it = (ItemButton)element$iv;
            int handItemMessage = 0;
            if (StringsKt.equals(it.getCachedName(), itemName, true)) {
               var22 = element$iv;
               break;
            }
         }

         ItemButton cachedItem = (ItemButton)var22;
         if (cachedItem != null) {
            String var23 = this.getPlugin().getConfig().getString("SETTINGS.WORTH-DEFAULT");
            if (var23 == null) {
               var23 = "&b1 " + cachedItem.getCachedName() + " &7is now &a$" + cachedItem.getPrice();
            }

            String itemMessage = var23;
            player.sendMessage(CC.INSTANCE.translate(StringsKt.replace$default(StringsKt.replace$default(itemMessage, "{item}", cachedItem.getCachedName(), false, 4, (Object)null), "{price}", String.valueOf(cachedItem.getPrice()), false, 4, (Object)null)));
         } else if (itemInHand.getType() != Material.AIR) {
            double handItemPrice = WorthCache.INSTANCE.getPrice(itemInHand.getType().name());
            int totalAmount = itemInHand.getAmount();
            double totalWorth = handItemPrice * (double)totalAmount;
            String var24 = this.getPlugin().getConfig().getString("SETTINGS.WORTH-HAND-ITEM");
            if (var24 == null) {
               var24 = "&b" + totalAmount + " " + Utilities.INSTANCE.formatItemName(itemInHand.getType().name()) + " &7is now &a$" + totalWorth;
            }

            String handItemMessage = var24;
            player.sendMessage(CC.INSTANCE.translate(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(handItemMessage, "{amount}", String.valueOf(totalAmount), false, 4, (Object)null), "{item}", Utilities.INSTANCE.formatItemName(itemInHand.getType().name()), false, 4, (Object)null), "{total}", String.valueOf(totalWorth), false, 4, (Object)null)));
         } else {
            CC var10001 = CC.INSTANCE;
            String var10002 = this.getPlugin().getConfig().getString("SETTINGS.NO-SELLABLE");
            if (var10002 == null) {
               var10002 = "&cThis item is not sellable.";
            }

            player.sendMessage(var10001.translate(var10002));
         }
      }

   }

   // $FF: synthetic method
   public static void onWorth$default(WorthCommand var0, Player var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.onWorth(var1, var2);
   }

   private static final DonutSell plugin_delegate$lambda$0() {
      return DonutSell.Companion.getINSTANCE();
   }
}
