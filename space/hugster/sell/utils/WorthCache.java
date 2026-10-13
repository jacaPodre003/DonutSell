package space.hugster.sell.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.hugster.sell.DonutSell;
import space.hugster.sell.ui.ItemButton;
import space.hugster.sell.utils.menu.Button;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0016B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\fJ\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"},
   d2 = {"Lspace/hugster/sell/utils/WorthCache;", "", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "getPlugin", "()Lspace/hugster/sell/DonutSell;", "plugin$delegate", "Lkotlin/Lazy;", "cache", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lspace/hugster/sell/utils/WorthCache$ItemData;", "loadCache", "", "getPrice", "", "material", "getAllButtons", "", "Lspace/hugster/sell/utils/menu/Button;", "ItemData", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nWorthCache.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WorthCache.kt\nspace/hugster/sell/utils/WorthCache\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,90:1\n1863#2:91\n1863#2:92\n1864#2:94\n1864#2:95\n1557#2:96\n1628#2,3:97\n1#3:93\n*S KotlinDebug\n*F\n+ 1 WorthCache.kt\nspace/hugster/sell/utils/WorthCache\n*L\n28#1:91\n31#1:92\n31#1:94\n28#1:95\n89#1:96\n89#1:97,3\n*E\n"})
public final class WorthCache {
   @NotNull
   public static final WorthCache INSTANCE = new WorthCache();
   @NotNull
   private static final Lazy plugin$delegate = LazyKt.lazy(WorthCache::plugin_delegate$lambda$0);
   @NotNull
   private static final ConcurrentHashMap<String, ItemData> cache = new ConcurrentHashMap();

   private WorthCache() {
   }

   private final DonutSell getPlugin() {
      Lazy var1 = plugin$delegate;
      return (DonutSell)var1.getValue();
   }

   public final void loadCache() {
      cache.clear();
      FileConfiguration worthConfig = this.getPlugin().getWorthConfig().getConfig();
      ConfigurationSection var10000 = worthConfig.getConfigurationSection("TYPE");
      if (var10000 != null) {
         Set var45 = var10000.getKeys(false);
         if (var45 != null) {
            Iterable $this$forEach$iv = (Iterable)var45;
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               String category = (String)element$iv;
               int var7 = 0;
               ConfigurationSection var46 = worthConfig.getConfigurationSection("TYPE." + category);
               if (var46 != null) {
                  ConfigurationSection categorySection = var46;
                  Set var47 = categorySection.getKeys(false);
                  Intrinsics.checkNotNullExpressionValue(var47, "getKeys(...)");
                  Iterable $this$forEach$iv = (Iterable)var47;
                  int $i$f$forEach = 0;

                  for(Object element$iv : $this$forEach$iv) {
                     String configKey = (String)element$iv;
                     int var14 = 0;
                     Intrinsics.checkNotNull(configKey);
                     CharSequence var48 = (CharSequence)configKey;
                     String baseKey = new String[]{":"};
                     List parts = StringsKt.split$default(var48, baseKey, false, 0, 6, (Object)null);
                     String var49 = ((String)parts.get(0)).toUpperCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var49, "toUpperCase(...)");
                     baseKey = var49;

                     try {
                        Material material = Material.valueOf(baseKey);
                        Double var18 = categorySection.getDouble(configKey);
                        double it = ((Number)var18).doubleValue();
                        int var21 = 0;
                        Double var50 = it > (double)0.0F ? var18 : null;
                        if ((it > (double)0.0F ? var18 : null) == null) {
                           throw new IllegalArgumentException("Invalid price");
                        }

                        double price = var50;
                        String displayName = null;
                        Object var35 = null;
                        Button var36;
                        if (material == Material.ENCHANTED_BOOK && parts.size() == 3) {
                           String var55 = ((String)parts.get(1)).toUpperCase(Locale.ROOT);
                           Intrinsics.checkNotNullExpressionValue(var55, "toUpperCase(...)");
                           String enchantName = var55;
                           Enchantment var56 = Enchantment.getByName(enchantName);
                           if (var56 == null) {
                              throw new IllegalArgumentException("Invalid enchantment " + enchantName);
                           }

                           Enchantment enchant = var56;
                           Integer var57 = StringsKt.toIntOrNull((String)parts.get(2));
                           if (var57 == null) {
                              throw new IllegalArgumentException("Invalid enchantment level " + parts.get(2));
                           }

                           var21 = var57;
                           displayName = "&eEnchanted Book";
                           Map var43 = MapsKt.mapOf(TuplesKt.to(enchant, var21));
                           var36 = new ItemButton(material, price, displayName, var43, (PotionType)null, 16, (DefaultConstructorMarker)null);
                        } else if ((material == Material.POTION || material == Material.SPLASH_POTION || material == Material.LINGERING_POTION) && parts.size() == 2) {
                           String var51 = ((String)parts.get(1)).toUpperCase(Locale.ROOT);
                           Intrinsics.checkNotNullExpressionValue(var51, "toUpperCase(...)");
                           String potionTypeName = var51;

                           try {
                              level = PotionType.valueOf(potionTypeName);
                           } catch (IllegalArgumentException var32) {
                              throw new IllegalArgumentException("Invalid potion type " + potionTypeName);
                           }

                           PotionType potionType = level;
                           var51 = Utilities.INSTANCE.formatItemName(baseKey);
                           String var10001 = StringsKt.replace$default(level.name(), "_", " ", false, 4, (Object)null).toLowerCase(Locale.ROOT);
                           Intrinsics.checkNotNullExpressionValue(var10001, "toLowerCase(...)");
                           String var38 = var10001;
                           if (((CharSequence)var38).length() > 0) {
                              StringBuilder var59 = new StringBuilder();
                              char enchants = var38.charAt(0);
                              StringBuilder var28 = var59;
                              String var29 = var51;
                              int var30 = 0;
                              var51 = String.valueOf(enchants);
                              Intrinsics.checkNotNull(var51, "null cannot be cast to non-null type java.lang.String");
                              var51 = var51.toUpperCase(Locale.ROOT);
                              Intrinsics.checkNotNullExpressionValue(var51, "toUpperCase(...)");
                              CharSequence var31 = (CharSequence)var51;
                              var51 = var29;
                              var59 = var28.append(var31);
                              var30 = 1;
                              String var10002 = var38.substring(var30);
                              Intrinsics.checkNotNullExpressionValue(var10002, "substring(...)");
                              var10001 = var59.append(var10002).toString();
                           } else {
                              var10001 = var38;
                           }

                           displayName = var51 + " of " + var10001;
                           var36 = new ItemButton(material, price, displayName, (Map)null, potionType, 8, (DefaultConstructorMarker)null);
                        } else {
                           displayName = Utilities.INSTANCE.formatItemName(baseKey);
                           var36 = new ItemButton(material, price, displayName, (Map)null, (PotionType)null, 24, (DefaultConstructorMarker)null);
                        }

                        Map var58 = (Map)cache;
                        String var62 = configKey.toUpperCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var62, "toUpperCase(...)");
                        var58.put(var62, new ItemData(price, displayName, var36));
                     } catch (IllegalArgumentException e) {
                        INSTANCE.getPlugin().getLogger().warning("Error loading item '" + configKey + "' in category '" + category + "': " + e.getMessage());
                     }
                  }
               }
            }
         }
      }

   }

   public final double getPrice(@NotNull String material) {
      Intrinsics.checkNotNullParameter(material, "material");
      ConcurrentHashMap var10000 = cache;
      String var10001 = material.toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var10001, "toUpperCase(...)");
      ItemData var2 = (ItemData)var10000.get(var10001);
      return var2 != null ? var2.getPrice() : (double)1.0F;
   }

   @NotNull
   public final Collection<Button> getAllButtons() {
      Collection var10000 = cache.values();
      Intrinsics.checkNotNullExpressionValue(var10000, "<get-values>(...)");
      Iterable $this$map$iv = (Iterable)var10000;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         ItemData it = (ItemData)item$iv$iv;
         int var9 = 0;
         destination$iv$iv.add(it.getButton());
      }

      return destination$iv$iv;
   }

   private static final DonutSell plugin_delegate$lambda$0() {
      return DonutSell.Companion.getINSTANCE();
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0007HÆ\u0003J'\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a"},
      d2 = {"Lspace/hugster/sell/utils/WorthCache$ItemData;", "", "price", "", "displayName", "", "button", "Lspace/hugster/sell/utils/menu/Button;", "<init>", "(DLjava/lang/String;Lspace/hugster/sell/utils/menu/Button;)V", "getPrice", "()D", "getDisplayName", "()Ljava/lang/String;", "getButton", "()Lspace/hugster/sell/utils/menu/Button;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "DonutSell"}
   )
   private static final class ItemData {
      private final double price;
      @NotNull
      private final String displayName;
      @NotNull
      private final Button button;

      public ItemData(double price, @NotNull String displayName, @NotNull Button button) {
         Intrinsics.checkNotNullParameter(displayName, "displayName");
         Intrinsics.checkNotNullParameter(button, "button");
         super();
         this.price = price;
         this.displayName = displayName;
         this.button = button;
      }

      public final double getPrice() {
         return this.price;
      }

      @NotNull
      public final String getDisplayName() {
         return this.displayName;
      }

      @NotNull
      public final Button getButton() {
         return this.button;
      }

      public final double component1() {
         return this.price;
      }

      @NotNull
      public final String component2() {
         return this.displayName;
      }

      @NotNull
      public final Button component3() {
         return this.button;
      }

      @NotNull
      public final ItemData copy(double price, @NotNull String displayName, @NotNull Button button) {
         Intrinsics.checkNotNullParameter(displayName, "displayName");
         Intrinsics.checkNotNullParameter(button, "button");
         return new ItemData(price, displayName, button);
      }

      // $FF: synthetic method
      public static ItemData copy$default(ItemData var0, double var1, String var3, Button var4, int var5, Object var6) {
         if ((var5 & 1) != 0) {
            var1 = var0.price;
         }

         if ((var5 & 2) != 0) {
            var3 = var0.displayName;
         }

         if ((var5 & 4) != 0) {
            var4 = var0.button;
         }

         return var0.copy(var1, var3, var4);
      }

      @NotNull
      public String toString() {
         return "ItemData(price=" + this.price + ", displayName=" + this.displayName + ", button=" + this.button + ")";
      }

      public int hashCode() {
         int result = Double.hashCode(this.price);
         result = result * 31 + this.displayName.hashCode();
         result = result * 31 + this.button.hashCode();
         return result;
      }

      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof ItemData)) {
            return false;
         } else {
            ItemData var2 = (ItemData)other;
            if (Double.compare(this.price, var2.price) != 0) {
               return false;
            } else if (!Intrinsics.areEqual(this.displayName, var2.displayName)) {
               return false;
            } else {
               return Intrinsics.areEqual(this.button, var2.button);
            }
         }
      }
   }
}
