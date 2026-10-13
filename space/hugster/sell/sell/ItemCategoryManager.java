package space.hugster.sell.sell;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.DonutSell;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lspace/hugster/sell/sell/ItemCategoryManager;", "", "plugin", "Lspace/hugster/sell/DonutSell;", "<init>", "(Lspace/hugster/sell/DonutSell;)V", "itemCategories", "Ljava/util/concurrent/ConcurrentHashMap;", "", "loadItemCategories", "", "getItemCategory", "material", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nItemCategoryManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemCategoryManager.kt\nspace/hugster/sell/sell/ItemCategoryManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,51:1\n1863#2:52\n1863#2,2:53\n1864#2:55\n*S KotlinDebug\n*F\n+ 1 ItemCategoryManager.kt\nspace/hugster/sell/sell/ItemCategoryManager\n*L\n22#1:52\n28#1:53,2\n22#1:55\n*E\n"})
public final class ItemCategoryManager {
   @NotNull
   private final DonutSell plugin;
   @NotNull
   private final ConcurrentHashMap<String, String> itemCategories;

   public ItemCategoryManager(@NotNull DonutSell plugin) {
      Intrinsics.checkNotNullParameter(plugin, "plugin");
      super();
      this.plugin = plugin;
      this.itemCategories = new ConcurrentHashMap();
   }

   public final void loadItemCategories() {
      try {
         this.itemCategories.clear();
         FileConfiguration worthConfig = this.plugin.getWorthConfig().getConfig();
         ConfigurationSection var10000 = worthConfig.getConfigurationSection("TYPE");
         if (var10000 == null) {
            ItemCategoryManager $this$loadItemCategories_u24lambda_u240 = this;
            int element$iv = 0;
            $this$loadItemCategories_u24lambda_u240.plugin.getLogger().warning("No 'TYPE' section found in worthConfig!");
            return;
         }

         ConfigurationSection typeSection = var10000;
         Set var25 = typeSection.getKeys(false);
         Intrinsics.checkNotNullExpressionValue(var25, "getKeys(...)");
         Iterable $this$forEach$iv = (Iterable)var25;
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            String category = (String)element$iv;
            int var8 = 0;
            ConfigurationSection var26 = typeSection.getConfigurationSection(category);
            if (var26 == null) {
               ItemCategoryManager $this$loadItemCategories_u24lambda_u243_u24lambda_u241 = this;
               int element$iv = 0;
               $this$loadItemCategories_u24lambda_u243_u24lambda_u241.plugin.getLogger().warning("Invalid category section: " + category);
            } else {
               ConfigurationSection categorySection = var26;
               Set var27 = categorySection.getKeys(false);
               Intrinsics.checkNotNullExpressionValue(var27, "getKeys(...)");
               Iterable $this$forEach$iv = (Iterable)var27;
               int $i$f$forEach = 0;

               for(Object element$iv : $this$forEach$iv) {
                  String itemKey = (String)element$iv;
                  int var15 = 0;

                  try {
                     Intrinsics.checkNotNull(itemKey);
                     Locale var28 = Locale.ENGLISH;
                     Intrinsics.checkNotNullExpressionValue(var28, "ENGLISH");
                     String var29 = itemKey.toUpperCase(var28);
                     Intrinsics.checkNotNullExpressionValue(var29, "toUpperCase(...)");
                     String normalizedKey = var29;
                     if (this.itemCategories.containsKey(normalizedKey)) {
                        this.plugin.getLogger().warning("Duplicate item key: " + normalizedKey + " in category " + category);
                     }

                     Map var16 = (Map)this.itemCategories;
                     Intrinsics.checkNotNull(category);
                     Locale var30 = Locale.ENGLISH;
                     Intrinsics.checkNotNullExpressionValue(var30, "ENGLISH");
                     String var31 = category.toUpperCase(var30);
                     Intrinsics.checkNotNullExpressionValue(var31, "toUpperCase(...)");
                     String var18 = var31;
                     var16.put(normalizedKey, var18);
                  } catch (Exception e) {
                     this.plugin.getLogger().warning("Error processing item " + itemKey + " in category " + category + ": " + e.getMessage());
                  }
               }
            }
         }

         Logger var32 = this.plugin.getLogger();
         int var10001 = this.itemCategories.size();
         var32.info("Loaded " + var10001 + " items into " + typeSection.getKeys(false).size() + " categories");
      } catch (Exception e) {
         this.plugin.getLogger().severe("Failed to load item categories: " + e.getMessage());
      }

   }

   @NotNull
   public final String getItemCategory(@NotNull String material) {
      Intrinsics.checkNotNullParameter(material, "material");
      ConcurrentHashMap var10000 = this.itemCategories;
      String var10001 = material.toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var10001, "toUpperCase(...)");
      String var2 = (String)var10000.get(var10001);
      if (var2 == null) {
         var2 = "";
      }

      return var2;
   }
}
