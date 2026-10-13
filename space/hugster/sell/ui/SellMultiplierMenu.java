package space.hugster.sell.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.data.LevelSystem;
import space.hugster.sell.data.Profile;
import space.hugster.sell.data.StatData;
import space.hugster.sell.utils.CC;
import space.hugster.sell.utils.ItemBuilder;
import space.hugster.sell.utils.Utilities;
import space.hugster.sell.utils.menu.Button;
import space.hugster.sell.utils.menu.Menu;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u000fB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u001c\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b2\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\u000e\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
   d2 = {"Lspace/hugster/sell/ui/SellMultiplierMenu;", "Lspace/hugster/sell/utils/menu/Menu;", "profile", "Lspace/hugster/sell/data/Profile;", "<init>", "(Lspace/hugster/sell/data/Profile;)V", "getTitle", "", "player", "Lorg/bukkit/entity/Player;", "getButtons", "", "", "Lspace/hugster/sell/utils/menu/Button;", "getSize", "MultiplierButton", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nSellMultiplierMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SellMultiplierMenu.kt\nspace/hugster/sell/ui/SellMultiplierMenu\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,128:1\n216#2,2:129\n*S KotlinDebug\n*F\n+ 1 SellMultiplierMenu.kt\nspace/hugster/sell/ui/SellMultiplierMenu\n*L\n44#1:129,2\n*E\n"})
public final class SellMultiplierMenu extends Menu {
   @NotNull
   private final Profile profile;

   public SellMultiplierMenu(@NotNull Profile profile) {
      Intrinsics.checkNotNullParameter(profile, "profile");
      super();
      this.profile = profile;
   }

   @NotNull
   public String getTitle(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      CC var10000 = CC.INSTANCE;
      String var10001 = this.getPlugin().getConfig().getString("SELL-MENU.MULTIPLIER-TITLE");
      if (var10001 == null) {
         var10001 = "&8ѕᴇʟʟ ᴍᴜʟᴛɪᴘʟɪᴇʀѕ";
      }

      return var10000.translate(var10001);
   }

   @NotNull
   public Map<Integer, Button> getButtons(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      Map buttons = (Map)(new LinkedHashMap());
      ConfigurationSection config = this.getPlugin().getConfig().getConfigurationSection("SELL-MENU");
      Pair[] var5 = new Pair[]{TuplesKt.to("CROPS-BUTTON", "CROPS"), TuplesKt.to("ORES-BUTTON", "ORES"), TuplesKt.to("MOBS-BUTTON", "MOBS"), TuplesKt.to("NATURAL-BUTTON", "NATURAL"), TuplesKt.to("ARMOR-AND-TOOLS-BUTTON", "ARMOR_AND_TOOLS"), TuplesKt.to("FISH-BUTTON", "FISH"), TuplesKt.to("BOOK-BUTTON", "BOOK"), TuplesKt.to("POTIONS-BUTTON", "POTIONS"), TuplesKt.to("BLOCKS-BUTTON", "BLOCKS")};
      Map buttonMapping = MapsKt.mapOf(var5);
      int slotIndex = 0;
      int $i$f$forEach = 0;

      for(Map.Entry element$iv : buttonMapping.entrySet()) {
         int var11 = 0;
         String buttonKey = (String)element$iv.getKey();
         String statType = (String)element$iv.getValue();
         ConfigurationSection buttonSection = config != null ? config.getConfigurationSection(buttonKey) : null;
         if (buttonSection != null) {
            int slot = 9 + slotIndex;
            Integer var16 = slot;
            buttons.put(var16, new MultiplierButton(statType, this.profile, buttonSection));
            ++slotIndex;
         }
      }

      return buttons;
   }

   public int getSize() {
      return 27;
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J(\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0011H\u0016J\u0010\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
      d2 = {"Lspace/hugster/sell/ui/SellMultiplierMenu$MultiplierButton;", "Lspace/hugster/sell/utils/menu/Button;", "type", "", "profile", "Lspace/hugster/sell/data/Profile;", "buttonConfig", "Lorg/bukkit/configuration/ConfigurationSection;", "<init>", "(Ljava/lang/String;Lspace/hugster/sell/data/Profile;Lorg/bukkit/configuration/ConfigurationSection;)V", "getButtonItem", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "clicked", "", "slot", "", "clickType", "Lorg/bukkit/event/inventory/ClickType;", "hotbarButton", "buildProgressBar", "percentage", "", "DonutSell"}
   )
   @SourceDebugExtension({"SMAP\nSellMultiplierMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SellMultiplierMenu.kt\nspace/hugster/sell/ui/SellMultiplierMenu$MultiplierButton\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,128:1\n1557#2:129\n1628#2,3:130\n1557#2:133\n1628#2,3:134\n*S KotlinDebug\n*F\n+ 1 SellMultiplierMenu.kt\nspace/hugster/sell/ui/SellMultiplierMenu$MultiplierButton\n*L\n92#1:129\n92#1:130,3\n105#1:133\n105#1:134,3\n*E\n"})
   public static final class MultiplierButton extends Button {
      @NotNull
      private final String type;
      @NotNull
      private final Profile profile;
      @NotNull
      private final ConfigurationSection buttonConfig;

      public MultiplierButton(@NotNull String type, @NotNull Profile profile, @NotNull ConfigurationSection buttonConfig) {
         Intrinsics.checkNotNullParameter(type, "type");
         Intrinsics.checkNotNullParameter(profile, "profile");
         Intrinsics.checkNotNullParameter(buttonConfig, "buttonConfig");
         super();
         this.type = type;
         this.profile = profile;
         this.buttonConfig = buttonConfig;
      }

      @NotNull
      public ItemStack getButtonItem(@NotNull Player player) {
         Intrinsics.checkNotNullParameter(player, "player");
         StatData var10000 = (StatData)this.profile.getStats().get(this.type);
         if (var10000 == null) {
            var10000 = new StatData(0, (double)0.0F, 3, (DefaultConstructorMarker)null);
         }

         StatData statData = var10000;
         int currentLevel = statData.getCurrentLevel();
         double currentEarned = statData.getEarned();
         double nextLevelGoal = LevelSystem.INSTANCE.getNextLevelGoal(currentLevel);
         double progress = nextLevelGoal > (double)0.0F ? RangesKt.coerceAtMost(currentEarned / nextLevelGoal * (double)100, (double)100.0F) : (double)100.0F;
         String progressBar = this.buildProgressBar(progress);
         double multiplier = LevelSystem.INSTANCE.getMultiplierForLevel(currentLevel);
         double nextMultiplier = currentLevel >= 20 ? (double)3.0F : (double)1.0F + (double)(currentLevel + 1) * 0.1;
         String formattedPercentage = ProgressTypeMenu.ProgressButton.Companion.getPERCENTAGE_FORMAT().format(progress);
         String var47 = this.buttonConfig.getString("MATERIAL");
         if (var47 == null) {
            var47 = "STONE";
         }

         String materialName = var47;

         Material var18;
         try {
            var18 = Material.valueOf(materialName);
         } catch (IllegalArgumentException var34) {
            var18 = Material.STONE;
         }

         Material material = var18;
         var47 = this.buttonConfig.getString("TITLE");
         if (var47 == null) {
            var47 = "&8Unknown";
         }

         String title = var47;
         List var49 = this.buttonConfig.getStringList("LORE");
         Intrinsics.checkNotNullExpressionValue(var49, "getStringList(...)");
         List loreList = var49;
         Iterable $this$map$iv = (Iterable)loreList;
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            String line = (String)item$iv$iv;
            int var29 = 0;
            Intrinsics.checkNotNull(line);
            String var30 = "%.1f";
            Object[] var31 = new Object[]{multiplier};
            String var10002 = String.format(var30, Arrays.copyOf(var31, var31.length));
            Intrinsics.checkNotNullExpressionValue(var10002, "format(...)");
            String var50 = StringsKt.replace$default(line, "{multiplier}", "x" + var10002, false, 4, (Object)null);
            var30 = "%.1f";
            var31 = new Object[]{nextMultiplier};
            var10002 = String.format(var30, Arrays.copyOf(var31, var31.length));
            Intrinsics.checkNotNullExpressionValue(var10002, "format(...)");
            var50 = StringsKt.replace$default(StringsKt.replace$default(var50, "{next_multiplier}", "x" + var10002, false, 4, (Object)null), "{porcentage_level}", progressBar, false, 4, (Object)null);
            Intrinsics.checkNotNull(formattedPercentage);
            destination$iv$iv.add(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(var50, "{porcentage}", formattedPercentage, false, 4, (Object)null), "{current_level}", String.valueOf(currentLevel), false, 4, (Object)null), "{next_level}", String.valueOf(currentLevel + 1), false, 4, (Object)null), "{current_earned}", Utilities.INSTANCE.getNicestInteger(currentEarned), false, 4, (Object)null), "{next_goal}", Utilities.INSTANCE.getNicestInteger(nextLevelGoal), false, 4, (Object)null));
         }

         List processedLore = (List)destination$iv$iv;
         ItemBuilder var52 = (new ItemBuilder(material)).name(CC.INSTANCE.translate(title));
         $this$map$iv = (Iterable)processedLore;
         ItemBuilder var32 = var52;
         $i$f$map = 0;
         destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            String it = (String)item$iv$iv;
            int var44 = 0;
            destination$iv$iv.add(CC.INSTANCE.translate(it));
         }

         var52 = var32.lore((List)destination$iv$iv);
         ItemFlag[] var37 = new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS};
         return var52.flags(var37).build();
      }

      public void clicked(@NotNull Player player, int slot, @NotNull ClickType clickType, int hotbarButton) {
         Intrinsics.checkNotNullParameter(player, "player");
         Intrinsics.checkNotNullParameter(clickType, "clickType");
         (new ProgressTypeMenu(this.profile, this.type)).openMenu(player);
         player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
      }

      private final String buildProgressBar(double percentage) {
         double progress = RangesKt.coerceIn(percentage, (double)0.0F, (double)100.0F);
         int filled = (int)(progress / (double)10);
         int empty = 10 - filled;
         String filledChar = LevelSystem.INSTANCE.getProgressBarChar();
         String emptyChar = "&7" + LevelSystem.INSTANCE.getProgressBarChar();
         String var10000 = StringsKt.repeat((CharSequence)filledChar, filled);
         return "&a" + var10000 + StringsKt.repeat((CharSequence)emptyChar, empty);
      }
   }
}
