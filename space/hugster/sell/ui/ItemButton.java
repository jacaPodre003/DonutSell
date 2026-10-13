package space.hugster.sell.ui;

import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.hugster.sell.utils.ItemBuilder;
import space.hugster.sell.utils.Utilities;
import space.hugster.sell.utils.menu.Button;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001cH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001d"},
   d2 = {"Lspace/hugster/sell/ui/ItemButton;", "Lspace/hugster/sell/utils/menu/Button;", "material", "Lorg/bukkit/Material;", "price", "", "cachedName", "", "enchants", "", "Lorg/bukkit/enchantments/Enchantment;", "", "potionType", "Lorg/bukkit/potion/PotionType;", "<init>", "(Lorg/bukkit/Material;DLjava/lang/String;Ljava/util/Map;Lorg/bukkit/potion/PotionType;)V", "getPrice", "()D", "getCachedName", "()Ljava/lang/String;", "cachedItem", "Lorg/bukkit/inventory/ItemStack;", "getCachedItem", "()Lorg/bukkit/inventory/ItemStack;", "cachedItem$delegate", "Lkotlin/Lazy;", "getButtonItem", "player", "Lorg/bukkit/entity/Player;", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nWorthMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WorthMenu.kt\nspace/hugster/sell/ui/ItemButton\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,165:1\n216#2,2:166\n*S KotlinDebug\n*F\n+ 1 WorthMenu.kt\nspace/hugster/sell/ui/ItemButton\n*L\n90#1:166,2\n*E\n"})
public final class ItemButton extends Button {
   @NotNull
   private final Material material;
   private final double price;
   @NotNull
   private final String cachedName;
   @NotNull
   private final Map<Enchantment, Integer> enchants;
   @Nullable
   private final PotionType potionType;
   @NotNull
   private final Lazy cachedItem$delegate;

   public ItemButton(@NotNull Material material, double price, @NotNull String cachedName, @NotNull Map<Enchantment, Integer> enchants, @Nullable PotionType potionType) {
      Intrinsics.checkNotNullParameter(material, "material");
      Intrinsics.checkNotNullParameter(cachedName, "cachedName");
      Intrinsics.checkNotNullParameter(enchants, "enchants");
      super();
      this.material = material;
      this.price = price;
      this.cachedName = cachedName;
      this.enchants = enchants;
      this.potionType = potionType;
      this.cachedItem$delegate = LazyKt.lazy(ItemButton::cachedItem_delegate$lambda$1);
   }

   // $FF: synthetic method
   public ItemButton(Material var1, double var2, String var4, Map var5, PotionType var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 8) != 0) {
         var5 = MapsKt.emptyMap();
      }

      if ((var7 & 16) != 0) {
         var6 = null;
      }

      this(var1, var2, var4, var5, var6);
   }

   public final double getPrice() {
      return this.price;
   }

   @NotNull
   public final String getCachedName() {
      return this.cachedName;
   }

   private final ItemStack getCachedItem() {
      Lazy var1 = this.cachedItem$delegate;
      return (ItemStack)var1.getValue();
   }

   @NotNull
   public ItemStack getButtonItem(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      return this.getCachedItem();
   }

   private static final ItemStack cachedItem_delegate$lambda$1(ItemButton this$0) {
      String var10000 = this$0.getPlugin().getConfig().getString("WORTH-MENU.FORMAT");
      if (var10000 == null) {
         var10000 = "&fPrice: &a$" + this$0.price;
      }

      String format = var10000;
      String loreLine = StringsKt.replace$default(StringsKt.replace$default(format, "{item}", this$0.cachedName, false, 4, (Object)null), "{price}", Utilities.INSTANCE.getNicestInteger(this$0.price), false, 4, (Object)null);
      ItemBuilder var19 = (new ItemBuilder(this$0.material)).name("&f" + this$0.cachedName);
      String[] var4 = new String[]{loreLine};
      ItemBuilder builder = var19.lore(var4);
      ItemStack var20;
      switch (ItemButton.WhenMappings.$EnumSwitchMapping$0[this$0.material.ordinal()]) {
         case 1:
            ItemStack item = builder.build();
            ItemMeta var17 = item.getItemMeta();
            EnchantmentStorageMeta meta = var17 instanceof EnchantmentStorageMeta ? (EnchantmentStorageMeta)var17 : null;
            Map $this$forEach$iv = this$0.enchants;
            int $i$f$forEach = 0;

            for(Map.Entry element$iv : $this$forEach$iv.entrySet()) {
               int var12 = 0;
               Enchantment enchant = (Enchantment)element$iv.getKey();
               int level = ((Number)element$iv.getValue()).intValue();
               if (meta != null) {
                  meta.addStoredEnchant(enchant, level, true);
               }
            }

            item.setItemMeta((ItemMeta)meta);
            var20 = item;
            break;
         case 2:
         case 3:
         case 4:
            ItemStack item = builder.build();
            ItemMeta $this$forEach$iv = item.getItemMeta();
            PotionMeta meta = $this$forEach$iv instanceof PotionMeta ? (PotionMeta)$this$forEach$iv : null;
            if (this$0.potionType != null) {
               if (meta != null) {
                  meta.setBasePotionData(new PotionData(this$0.potionType));
               }
            }

            item.setItemMeta((ItemMeta)meta);
            var20 = item;
            break;
         default:
            var20 = builder.build();
      }

      return var20;
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
         int[] var0 = new int[Material.values().length];

         try {
            var0[Material.ENCHANTED_BOOK.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[Material.POTION.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[Material.SPLASH_POTION.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[Material.LINGERING_POTION.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }
}
