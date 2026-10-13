package space.hugster.sell.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.utils.ItemBuilder;
import space.hugster.sell.utils.Utilities;
import space.hugster.sell.utils.menu.Button;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"Lspace/hugster/sell/ui/MaterialButton;", "Lspace/hugster/sell/utils/menu/Button;", "material", "Lorg/bukkit/Material;", "amount", "", "price", "", "<init>", "(Lorg/bukkit/Material;ID)V", "getButtonItem", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nSellHistoryMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SellHistoryMenu.kt\nspace/hugster/sell/ui/MaterialButton\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,107:1\n1557#2:108\n1628#2,3:109\n*S KotlinDebug\n*F\n+ 1 SellHistoryMenu.kt\nspace/hugster/sell/ui/MaterialButton\n*L\n70#1:108\n70#1:109,3\n*E\n"})
public final class MaterialButton extends Button {
   @NotNull
   private final Material material;
   private final int amount;
   private final double price;

   public MaterialButton(@NotNull Material material, int amount, double price) {
      Intrinsics.checkNotNullParameter(material, "material");
      super();
      this.material = material;
      this.amount = amount;
      this.price = price;
   }

   @NotNull
   public ItemStack getButtonItem(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      List var10000 = this.getPlugin().getMainConfig().getConfig().getStringList("SELL-HISTORY-MENU.SOLD-ITEM-LORE");
      Intrinsics.checkNotNullExpressionValue(var10000, "getStringList(...)");
      List loreTemplate = var10000;
      Iterable $this$map$iv = (Iterable)loreTemplate;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         String it = (String)item$iv$iv;
         int var12 = 0;
         Intrinsics.checkNotNull(it);
         destination$iv$iv.add(StringsKt.replace$default(StringsKt.replace$default(it, "{price}", Utilities.INSTANCE.getNicestInteger(this.price), false, 4, (Object)null), "{amount}", String.valueOf(this.amount), false, 4, (Object)null));
      }

      List lore = (List)destination$iv$iv;
      return (new ItemBuilder(this.material)).addLore(lore).build();
   }
}
