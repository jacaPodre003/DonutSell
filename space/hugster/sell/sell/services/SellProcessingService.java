package space.hugster.sell.sell.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.DonutSell;
import space.hugster.sell.data.LevelSystem;
import space.hugster.sell.data.Profile;
import space.hugster.sell.data.StatData;
import space.hugster.sell.sell.ItemCategoryManager;
import space.hugster.sell.sell.data.SellResult;
import space.hugster.sell.utils.WorthCache;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J,\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00160\u001aH\u0002J,\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00160\u001aH\u0002J\u0018\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010 \u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R<\u0010\b\u001a.\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n \u000b*\u0015\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n0\r¢\u0006\u0002\b\f0\t¢\u0006\u0002\b\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000e¨\u0006!"},
   d2 = {"Lspace/hugster/sell/sell/services/SellProcessingService;", "", "plugin", "Lspace/hugster/sell/DonutSell;", "categoryManager", "Lspace/hugster/sell/sell/ItemCategoryManager;", "<init>", "(Lspace/hugster/sell/DonutSell;Lspace/hugster/sell/sell/ItemCategoryManager;)V", "blockedItems", "", "", "kotlin.jvm.PlatformType", "Lorg/jetbrains/annotations/NotNull;", "", "Ljava/util/List;", "processInventoryContents", "Lspace/hugster/sell/sell/data/SellResult;", "inventory", "Lorg/bukkit/inventory/Inventory;", "profile", "Lspace/hugster/sell/data/Profile;", "processRegularItem", "", "item", "Lorg/bukkit/inventory/ItemStack;", "statCategoryEarnings", "", "processShulkerContents", "getMultiplierForCategory", "category", "isShulkerBox", "", "getWorthKey", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nSellProcessingService.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SellProcessingService.kt\nspace/hugster/sell/sell/services/SellProcessingService\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,150:1\n1863#2,2:151\n1863#2,2:153\n*S KotlinDebug\n*F\n+ 1 SellProcessingService.kt\nspace/hugster/sell/sell/services/SellProcessingService\n*L\n34#1:151,2\n49#1:153,2\n*E\n"})
public final class SellProcessingService {
   @NotNull
   private final ItemCategoryManager categoryManager;
   @NotNull
   private final List<String> blockedItems;

   public SellProcessingService(@NotNull DonutSell plugin, @NotNull ItemCategoryManager categoryManager) {
      Intrinsics.checkNotNullParameter(plugin, "plugin");
      Intrinsics.checkNotNullParameter(categoryManager, "categoryManager");
      super();
      this.categoryManager = categoryManager;
      List var10001 = plugin.getWorthConfig().getConfig().getStringList("BLOCK-ITEMS");
      Intrinsics.checkNotNullExpressionValue(var10001, "getStringList(...)");
      this.blockedItems = var10001;
   }

   @NotNull
   public final SellResult processInventoryContents(@NotNull Inventory inventory, @NotNull Profile profile) {
      Intrinsics.checkNotNullParameter(inventory, "inventory");
      Intrinsics.checkNotNullParameter(profile, "profile");
      ItemStack[] var10000 = inventory.getContents();
      Intrinsics.checkNotNullExpressionValue(var10000, "getContents(...)");
      List contents = ArraysKt.filterNotNull(var10000);
      if (contents.isEmpty()) {
         return new SellResult((double)0.0F, MapsKt.emptyMap(), CollectionsKt.emptyList());
      } else {
         List itemsToReturn = (List)(new ArrayList());
         List validItems = (List)(new ArrayList());
         Iterable $this$forEach$iv = (Iterable)contents;
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            ItemStack item = (ItemStack)element$iv;
            int var11 = 0;
            if (this.blockedItems.contains(item.getType().name())) {
               itemsToReturn.add(item);
            } else {
               validItems.add(item);
            }
         }

         if (validItems.isEmpty()) {
            return new SellResult((double)0.0F, MapsKt.emptyMap(), itemsToReturn);
         } else {
            double totalWorth = (double)0.0F;
            Map statCategoryEarnings = (Map)(new LinkedHashMap());
            Iterable $this$forEach$iv = (Iterable)validItems;
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               ItemStack item = (ItemStack)element$iv;
               int var13 = 0;
               double itemResult = this.isShulkerBox(item) ? this.processShulkerContents(item, profile, statCategoryEarnings) : this.processRegularItem(item, profile, statCategoryEarnings);
               totalWorth += itemResult;
            }

            return new SellResult(totalWorth, statCategoryEarnings, itemsToReturn);
         }
      }
   }

   private final double processRegularItem(ItemStack item, Profile profile, Map<String, Double> statCategoryEarnings) {
      String worthKey = this.getWorthKey(item);
      double worth = WorthCache.INSTANCE.getPrice(worthKey);
      if (worth <= (double)0.0F) {
         return (double)0.0F;
      } else {
         String category = this.categoryManager.getItemCategory(item.getType().name());
         double multiplier = this.getMultiplierForCategory(category, profile);
         int amount = Math.max(item.getAmount(), 0);
         double itemWorth = worth * (double)amount * multiplier;
         if (((CharSequence)category).length() > 0) {
            statCategoryEarnings.put(category, ((Number)statCategoryEarnings.getOrDefault(category, (double)0.0F)).doubleValue() + itemWorth);
         }

         profile.getSellHistory().addOrUpdateSoldItem(item.getType().name(), item.getAmount(), itemWorth);
         return itemWorth;
      }
   }

   private final double processShulkerContents(ItemStack item, Profile profile, Map<String, Double> statCategoryEarnings) {
      if (!this.isShulkerBox(item)) {
         return (double)0.0F;
      } else {
         ItemMeta var6 = item.getItemMeta();
         BlockStateMeta var10000 = var6 instanceof BlockStateMeta ? (BlockStateMeta)var6 : null;
         if ((var6 instanceof BlockStateMeta ? (BlockStateMeta)var6 : null) == null) {
            return (double)0.0F;
         } else {
            BlockStateMeta meta = var10000;
            BlockState var7 = meta.getBlockState();
            ShulkerBox var14 = var7 instanceof ShulkerBox ? (ShulkerBox)var7 : null;
            if ((var7 instanceof ShulkerBox ? (ShulkerBox)var7 : null) == null) {
               return (double)0.0F;
            } else {
               ShulkerBox shulkerBox = var14;
               double totalShulkerContentsWorth = (double)0.0F;
               ItemStack[] var15 = shulkerBox.getInventory().getContents();
               Intrinsics.checkNotNullExpressionValue(var15, "getContents(...)");

               for(ItemStack shulkerItem : ArraysKt.filterNotNull(var15)) {
                  if (!this.blockedItems.contains(shulkerItem.getType().name())) {
                     double itemResult = this.isShulkerBox(shulkerItem) ? this.processShulkerContents(shulkerItem, profile, statCategoryEarnings) : this.processRegularItem(shulkerItem, profile, statCategoryEarnings);
                     totalShulkerContentsWorth += itemResult;
                  }
               }

               double shulkerWorth = this.processRegularItem(item, profile, statCategoryEarnings);
               return totalShulkerContentsWorth + shulkerWorth;
            }
         }
      }
   }

   private final double getMultiplierForCategory(String category, Profile profile) {
      double var10000;
      if (((CharSequence)category).length() > 0) {
         StatData statData = (StatData)profile.getStats().get(category);
         var10000 = LevelSystem.INSTANCE.getMultiplierForLevel(statData != null ? statData.getCurrentLevel() : 0);
      } else {
         var10000 = (double)1.0F;
      }

      return var10000;
   }

   private final boolean isShulkerBox(ItemStack item) {
      return StringsKt.endsWith$default(item.getType().name(), "SHULKER_BOX", false, 2, (Object)null);
   }

   private final String getWorthKey(ItemStack item) {
      String materialName = item.getType().name();
      if (Intrinsics.areEqual(materialName, "ENCHANTED_BOOK")) {
         ItemMeta var9 = item.getItemMeta();
         EnchantmentStorageMeta var12 = var9 instanceof EnchantmentStorageMeta ? (EnchantmentStorageMeta)var9 : null;
         if ((var9 instanceof EnchantmentStorageMeta ? (EnchantmentStorageMeta)var9 : null) == null) {
            return materialName;
         } else {
            EnchantmentStorageMeta meta = var12;
            if (meta.getStoredEnchants().isEmpty()) {
               return materialName;
            } else {
               Map.Entry var8 = (Map.Entry)CollectionsKt.first((Iterable)meta.getStoredEnchants().entrySet());
               Enchantment enchant = (Enchantment)var8.getKey();
               Integer level = (Integer)var8.getValue();
               String var13 = enchant.getKey().getKey();
               Intrinsics.checkNotNullExpressionValue(var13, "getKey(...)");
               var13 = var13.toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var13, "toUpperCase(...)");
               return materialName + ":" + var13 + ":" + level;
            }
         }
      } else {
         switch (materialName.hashCode()) {
            case -1929101933:
               if (!materialName.equals("POTION")) {
                  return materialName;
               }
               break;
            case 647953427:
               if (!materialName.equals("LINGERING_POTION")) {
                  return materialName;
               }
               break;
            case 1854040683:
               if (!materialName.equals("SPLASH_POTION")) {
                  return materialName;
               }
               break;
            default:
               return materialName;
         }

         ItemMeta enchant = item.getItemMeta();
         PotionMeta var10000 = enchant instanceof PotionMeta ? (PotionMeta)enchant : null;
         if ((enchant instanceof PotionMeta ? (PotionMeta)enchant : null) == null) {
            return materialName;
         } else {
            PotionMeta meta = var10000;
            PotionType var11 = meta.getBasePotionData().getType();
            Intrinsics.checkNotNullExpressionValue(var11, "getType(...)");
            PotionType type = var11;
            String var10001 = type.name().toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var10001, "toUpperCase(...)");
            return materialName + ":" + var10001;
         }
      }
   }
}
