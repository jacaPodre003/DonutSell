package space.hugster.sell.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.hugster.sell.DonutSell;
import space.hugster.sell.data.LevelSystem;
import space.hugster.sell.data.Profile;
import space.hugster.sell.managers.ProfileManager;
import space.hugster.sell.utils.ItemBuilder;
import space.hugster.sell.utils.Utilities;
import space.hugster.sell.utils.menu.Button;
import space.hugster.sell.utils.menu.Menu;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00102\u00020\u0001:\u0005\u0010\u0011\u0012\u0013\u0014B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000f\u001a\u00020\rH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
   d2 = {"Lspace/hugster/sell/ui/ProgressTypeMenu;", "Lspace/hugster/sell/utils/menu/Menu;", "profile", "Lspace/hugster/sell/data/Profile;", "type", "", "<init>", "(Lspace/hugster/sell/data/Profile;Ljava/lang/String;)V", "getTitle", "player", "Lorg/bukkit/entity/Player;", "getButtons", "", "", "Lspace/hugster/sell/utils/menu/Button;", "getSize", "Companion", "ProgressButton", "ProgressTypeButton", "BackToSell", "ButtonConfig", "DonutSell"}
)
public final class ProgressTypeMenu extends Menu {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final Profile profile;
   @NotNull
   private final String type;
   @NotNull
   private static final DonutSell plugin;
   @NotNull
   private static final FileConfiguration config;
   @NotNull
   private static final int[] BUTTON_POSITIONS;
   @NotNull
   private static final Map<String, String> menuTitles;
   @NotNull
   private static final ButtonConfig workingButtonConfig;
   @NotNull
   private static final ButtonConfig completedButtonConfig;

   public ProgressTypeMenu(@NotNull Profile profile, @NotNull String type) {
      Intrinsics.checkNotNullParameter(profile, "profile");
      Intrinsics.checkNotNullParameter(type, "type");
      super();
      this.profile = profile;
      this.type = type;
      this.setPlaceholder(true);
   }

   @NotNull
   public String getTitle(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      String var10000 = (String)menuTitles.get(this.type);
      if (var10000 == null) {
         var10000 = this.type + " progress";
      }

      return var10000;
   }

   @NotNull
   public Map<Integer, Button> getButtons(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      Map buttons = (Map)(new LinkedHashMap());
      buttons.put(1, new ProgressTypeButton(this.type));
      buttons.put(45, new BackToSell());
      int i = 0;

      for(int var4 = LevelSystem.INSTANCE.getMaxLevel(); i < var4; ++i) {
         buttons.put(BUTTON_POSITIONS[i], new ProgressButton(this.profile, i + 1, this.type, LevelSystem.INSTANCE.getNextLevelGoal(i)));
      }

      return buttons;
   }

   public int getSize() {
      return 54;
   }

   static {
      plugin = DonutSell.Companion.getINSTANCE();
      config = plugin.getMainConfig().getConfig();
      int[] var0 = new int[]{10, 19, 28, 37, 38, 39, 30, 21, 12, 13, 14, 23, 32, 41, 42, 43, 34, 25, 16, 7};
      BUTTON_POSITIONS = var0;
      Pair[] var1 = new Pair[9];
      String var10003 = config.getString("PROGRESS-MENU.TITLE.CROPS");
      if (var10003 == null) {
         var10003 = "&8ᴄʀᴏᴘ ᴘʀᴏɢʀᴇѕѕ";
      }

      var1[0] = TuplesKt.to("CROPS", var10003);
      var10003 = config.getString("PROGRESS-MENU.TITLE.ORES");
      if (var10003 == null) {
         var10003 = "&8ᴏʀᴇ ᴘʀᴏɢʀᴇѕѕ";
      }

      var1[1] = TuplesKt.to("ORES", var10003);
      var10003 = config.getString("PROGRESS-MENU.TITLE.MOBS");
      if (var10003 == null) {
         var10003 = "&8ᴍᴏʙ ᴅʀᴏᴘѕ ᴘʀᴏɢʀᴇѕѕ";
      }

      var1[2] = TuplesKt.to("MOBS", var10003);
      var10003 = config.getString("PROGRESS-MENU.TITLE.NATURAL");
      if (var10003 == null) {
         var10003 = "&8ɴᴀᴛᴜʀᴀʟ ɪᴛᴇᴍѕ ᴘʀᴏɢʀᴇѕѕ";
      }

      var1[3] = TuplesKt.to("NATURAL", var10003);
      var10003 = config.getString("PROGRESS-MENU.TITLE.ARMOR-AND-TOOLS");
      if (var10003 == null) {
         var10003 = "&8ᴀʀᴍᴏʀ ᴀɴᴅ ᴛᴏᴏʟѕ ᴘʀᴏɢʀᴇѕѕ";
      }

      var1[4] = TuplesKt.to("ARMOR_AND_TOOLS", var10003);
      var10003 = config.getString("PROGRESS-MENU.TITLE.FISH");
      if (var10003 == null) {
         var10003 = "&8ꜰɪѕʜ ᴘʀᴏɢʀᴇѕѕ";
      }

      var1[5] = TuplesKt.to("FISH", var10003);
      var10003 = config.getString("PROGRESS-MENU.TITLE.BOOK");
      if (var10003 == null) {
         var10003 = "&8ᴇɴᴄʜᴀɴᴛᴇᴅ ʙᴏᴏᴋ ᴘʀᴏɢʀᴇѕѕ";
      }

      var1[6] = TuplesKt.to("BOOK", var10003);
      var10003 = config.getString("PROGRESS-MENU.TITLE.POTIONS");
      if (var10003 == null) {
         var10003 = "&8ᴘᴏᴛɪᴏɴѕ ᴘʀᴏɢʀᴇѕѕ";
      }

      var1[7] = TuplesKt.to("POTIONS", var10003);
      var10003 = config.getString("PROGRESS-MENU.TITLE.BLOCKS");
      if (var10003 == null) {
         var10003 = "&8ʙʟᴏᴄᴋѕ ᴘʀᴏɢʀᴇѕѕ";
      }

      var1[8] = TuplesKt.to("BLOCKS", var10003);
      menuTitles = MapsKt.mapOf(var1);
      ButtonConfig var10000 = new ButtonConfig;
      String var10002 = config.getString("PROGRESS-MENU.WORKING-BUTTON.TITLE");
      if (var10002 == null) {
         var10002 = "&eᴡᴏʀᴋɪɴɢ";
      }

      var10003 = config.getString("PROGRESS-MENU.WORKING-BUTTON.MATERIAL");
      if (var10003 == null) {
         var10003 = "YELLOW_STAINED_GLASS_PANE";
      }

      Material var13 = Material.matchMaterial(var10003);
      if (var13 == null) {
         var13 = Material.YELLOW_STAINED_GLASS_PANE;
      }

      List var10004 = config.getStringList("PROGRESS-MENU.WORKING-BUTTON.LORE");
      Intrinsics.checkNotNullExpressionValue(var10004, "getStringList(...)");
      var10000.<init>(var10002, var13, var10004);
      workingButtonConfig = var10000;
      var10000 = new ButtonConfig;
      var10002 = config.getString("PROGRESS-MENU.COMPLETED-BUTTON.TITLE");
      if (var10002 == null) {
         var10002 = "&aᴄᴏᴍᴘʟᴇᴛᴇᴅ";
      }

      String var14 = config.getString("PROGRESS-MENU.COMPLETED-BUTTON.MATERIAL");
      if (var14 == null) {
         var14 = "LIME_STAINED_GLASS_PANE";
      }

      Material var15 = Material.matchMaterial(var14);
      if (var15 == null) {
         var15 = Material.LIME_STAINED_GLASS_PANE;
      }

      var10004 = config.getStringList("PROGRESS-MENU.COMPLETED-BUTTON.LORE");
      Intrinsics.checkNotNullExpressionValue(var10004, "getStringList(...)");
      var10000.<init>(var10002, var15, var10004);
      completedButtonConfig = var10000;
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001f\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\t\u0012\u00070\f¢\u0006\u0002\b\r0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
      d2 = {"Lspace/hugster/sell/ui/ProgressTypeMenu$Companion;", "", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "config", "Lorg/bukkit/configuration/file/FileConfiguration;", "BUTTON_POSITIONS", "", "menuTitles", "", "", "Lorg/jetbrains/annotations/Nullable;", "workingButtonConfig", "Lspace/hugster/sell/ui/ProgressTypeMenu$ButtonConfig;", "completedButtonConfig", "DonutSell"}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\tH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"},
      d2 = {"Lspace/hugster/sell/ui/ProgressTypeMenu$ProgressButton;", "Lspace/hugster/sell/utils/menu/Button;", "profile", "Lspace/hugster/sell/data/Profile;", "level", "", "type", "", "goalAmount", "", "<init>", "(Lspace/hugster/sell/data/Profile;ILjava/lang/String;D)V", "getButtonItem", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "buildProgressBar", "percentage", "Companion", "DonutSell"}
   )
   @SourceDebugExtension({"SMAP\nProgressTypeMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProgressTypeMenu.kt\nspace/hugster/sell/ui/ProgressTypeMenu$ProgressButton\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,191:1\n1557#2:192\n1628#2,3:193\n*S KotlinDebug\n*F\n+ 1 ProgressTypeMenu.kt\nspace/hugster/sell/ui/ProgressTypeMenu$ProgressButton\n*L\n102#1:192\n102#1:193,3\n*E\n"})
   public static final class ProgressButton extends Button {
      @NotNull
      public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
      @NotNull
      private final Profile profile;
      private final int level;
      @NotNull
      private final String type;
      private final double goalAmount;
      @NotNull
      private static final DecimalFormat PERCENTAGE_FORMAT = new DecimalFormat("0.0");

      public ProgressButton(@NotNull Profile profile, int level, @NotNull String type, double goalAmount) {
         Intrinsics.checkNotNullParameter(profile, "profile");
         Intrinsics.checkNotNullParameter(type, "type");
         super();
         this.profile = profile;
         this.level = level;
         this.type = type;
         this.goalAmount = goalAmount;
      }

      @NotNull
      public ItemStack getButtonItem(@NotNull Player player) {
         Intrinsics.checkNotNullParameter(player, "player");
         double currentAmount = this.profile.getProgressPerType(this.type);
         double percentageCompleted = Utilities.INSTANCE.calculatePercentage(currentAmount, this.goalAmount);
         String formattedPercentage = PERCENTAGE_FORMAT.format(percentageCompleted);
         boolean isCompleted = currentAmount >= this.goalAmount;
         ButtonConfig config = isCompleted ? ProgressTypeMenu.completedButtonConfig : ProgressTypeMenu.workingButtonConfig;
         Iterable $this$map$iv = (Iterable)config.getLore();
         int $i$f$map = 0;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
         int $i$f$mapTo = 0;

         for(Object item$iv$iv : $this$map$iv) {
            String line = (String)item$iv$iv;
            int var18 = 0;
            String var10000 = StringsKt.replace$default(line, "{porcentage_level}", this.buildProgressBar(percentageCompleted), false, 4, (Object)null);
            String var19 = "%.1f";
            Object[] var20 = new Object[]{LevelSystem.INSTANCE.getMultiplierForLevel(this.level)};
            String var10002 = String.format(var19, Arrays.copyOf(var20, var20.length));
            Intrinsics.checkNotNullExpressionValue(var10002, "format(...)");
            var10000 = StringsKt.replace$default(var10000, "{next_multiplier}", "x" + var10002, false, 4, (Object)null);
            Intrinsics.checkNotNull(formattedPercentage);
            destination$iv$iv.add(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(var10000, "{porcentage}", formattedPercentage, false, 4, (Object)null), "{current_earned}", Utilities.INSTANCE.getNicestInteger(currentAmount), false, 4, (Object)null), "{next_goal}", Utilities.INSTANCE.getNicestInteger(this.goalAmount), false, 4, (Object)null));
         }

         List processedLore = (List)destination$iv$iv;
         return (new ItemBuilder(config.getMaterial())).name(config.getTitle()).lore(processedLore).build();
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

      @Metadata(
         mv = {2, 1, 0},
         k = 1,
         xi = 48,
         d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"},
         d2 = {"Lspace/hugster/sell/ui/ProgressTypeMenu$ProgressButton$Companion;", "", "<init>", "()V", "PERCENTAGE_FORMAT", "Ljava/text/DecimalFormat;", "getPERCENTAGE_FORMAT", "()Ljava/text/DecimalFormat;", "DonutSell"}
      )
      public static final class Companion {
         private Companion() {
         }

         @NotNull
         public final DecimalFormat getPERCENTAGE_FORMAT() {
            return ProgressTypeMenu.ProgressButton.PERCENTAGE_FORMAT;
         }

         // $FF: synthetic method
         public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
         }
      }
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
      d2 = {"Lspace/hugster/sell/ui/ProgressTypeMenu$ProgressTypeButton;", "Lspace/hugster/sell/utils/menu/Button;", "type", "", "<init>", "(Ljava/lang/String;)V", "getButtonItem", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "Companion", "DonutSell"}
   )
   public static final class ProgressTypeButton extends Button {
      @NotNull
      public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
      @NotNull
      private final String type;
      @NotNull
      private static final DonutSell plugin;
      @NotNull
      private static final FileConfiguration config;

      public ProgressTypeButton(@NotNull String type) {
         Intrinsics.checkNotNullParameter(type, "type");
         super();
         this.type = type;
      }

      @NotNull
      public ItemStack getButtonItem(@NotNull Player player) {
         Intrinsics.checkNotNullParameter(player, "player");
         String title = Companion.getTypeConfig("TITLE", this.type, "&8" + this.type);
         Material material = Companion.getTypeMaterial(this.type, Material.STONE);
         List baseLore = Companion.getTypeLore(this.type);
         return (new ItemBuilder(material)).name(title).lore(baseLore).build();
      }

      static {
         plugin = DonutSell.Companion.getINSTANCE();
         config = plugin.getMainConfig().getConfig();
      }

      @Metadata(
         mv = {2, 1, 0},
         k = 1,
         xi = 48,
         d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\tH\u0002J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000eH\u0002J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u00102\u0006\u0010\u000b\u001a\u00020\tH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
         d2 = {"Lspace/hugster/sell/ui/ProgressTypeMenu$ProgressTypeButton$Companion;", "", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "config", "Lorg/bukkit/configuration/file/FileConfiguration;", "getTypeConfig", "", "key", "type", "default", "getTypeMaterial", "Lorg/bukkit/Material;", "getTypeLore", "", "DonutSell"}
      )
      public static final class Companion {
         private Companion() {
         }

         private final String getTypeConfig(String key, String type, String default) {
            String var10000 = ProgressTypeMenu.ProgressTypeButton.config.getString("PROGRESS-MENU.TYPE-BUTTON." + key + "." + type);
            if (var10000 == null) {
               var10000 = default;
            }

            return var10000;
         }

         private final Material getTypeMaterial(String type, Material default) {
            String materialName = ProgressTypeMenu.ProgressTypeButton.config.getString("PROGRESS-MENU.TYPE-BUTTON.MATERIAL." + type);
            String var10000 = materialName;
            if (materialName == null) {
               var10000 = "";
            }

            Material var4 = Material.matchMaterial(var10000);
            if (var4 == null) {
               var4 = default;
            }

            return var4;
         }

         private final List<String> getTypeLore(String type) {
            List var10000 = ProgressTypeMenu.ProgressTypeButton.config.getStringList("PROGRESS-MENU.TYPE-BUTTON.LORE." + type);
            Intrinsics.checkNotNullExpressionValue(var10000, "getStringList(...)");
            return var10000;
         }

         // $FF: synthetic method
         public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
         }
      }
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"},
      d2 = {"Lspace/hugster/sell/ui/ProgressTypeMenu$BackToSell;", "Lspace/hugster/sell/utils/menu/Button;", "<init>", "()V", "getButtonItem", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "clicked", "", "clickType", "Lorg/bukkit/event/inventory/ClickType;", "DonutSell"}
   )
   public static final class BackToSell extends Button {
      @NotNull
      public ItemStack getButtonItem(@NotNull Player player) {
         Intrinsics.checkNotNullParameter(player, "player");
         return (new ItemBuilder(Material.RED_STAINED_GLASS_PANE)).name("&cʙᴀᴄᴋ").build();
      }

      public void clicked(@NotNull Player player, @NotNull ClickType clickType) {
         Intrinsics.checkNotNullParameter(player, "player");
         Intrinsics.checkNotNullParameter(clickType, "clickType");
         ProfileManager var10000 = this.getPlugin().getProfileManager();
         UUID var10001 = player.getUniqueId();
         Intrinsics.checkNotNullExpressionValue(var10001, "getUniqueId(...)");
         Profile var4 = var10000.getProfile(var10001);
         if (var4 != null) {
            Profile profile = var4;
            (new SellMultiplierMenu(profile)).openMenu(player);
         }
      }
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\u0004\b\b\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007HÆ\u0003J-\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a"},
      d2 = {"Lspace/hugster/sell/ui/ProgressTypeMenu$ButtonConfig;", "", "title", "", "material", "Lorg/bukkit/Material;", "lore", "", "<init>", "(Ljava/lang/String;Lorg/bukkit/Material;Ljava/util/List;)V", "getTitle", "()Ljava/lang/String;", "getMaterial", "()Lorg/bukkit/Material;", "getLore", "()Ljava/util/List;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "DonutSell"}
   )
   private static final class ButtonConfig {
      @NotNull
      private final String title;
      @NotNull
      private final Material material;
      @NotNull
      private final List<String> lore;

      public ButtonConfig(@NotNull String title, @NotNull Material material, @NotNull List<String> lore) {
         Intrinsics.checkNotNullParameter(title, "title");
         Intrinsics.checkNotNullParameter(material, "material");
         Intrinsics.checkNotNullParameter(lore, "lore");
         super();
         this.title = title;
         this.material = material;
         this.lore = lore;
      }

      @NotNull
      public final String getTitle() {
         return this.title;
      }

      @NotNull
      public final Material getMaterial() {
         return this.material;
      }

      @NotNull
      public final List<String> getLore() {
         return this.lore;
      }

      @NotNull
      public final String component1() {
         return this.title;
      }

      @NotNull
      public final Material component2() {
         return this.material;
      }

      @NotNull
      public final List<String> component3() {
         return this.lore;
      }

      @NotNull
      public final ButtonConfig copy(@NotNull String title, @NotNull Material material, @NotNull List<String> lore) {
         Intrinsics.checkNotNullParameter(title, "title");
         Intrinsics.checkNotNullParameter(material, "material");
         Intrinsics.checkNotNullParameter(lore, "lore");
         return new ButtonConfig(title, material, lore);
      }

      // $FF: synthetic method
      public static ButtonConfig copy$default(ButtonConfig var0, String var1, Material var2, List var3, int var4, Object var5) {
         if ((var4 & 1) != 0) {
            var1 = var0.title;
         }

         if ((var4 & 2) != 0) {
            var2 = var0.material;
         }

         if ((var4 & 4) != 0) {
            var3 = var0.lore;
         }

         return var0.copy(var1, var2, var3);
      }

      @NotNull
      public String toString() {
         return "ButtonConfig(title=" + this.title + ", material=" + this.material + ", lore=" + this.lore + ")";
      }

      public int hashCode() {
         int result = this.title.hashCode();
         result = result * 31 + this.material.hashCode();
         result = result * 31 + this.lore.hashCode();
         return result;
      }

      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof ButtonConfig)) {
            return false;
         } else {
            ButtonConfig var2 = (ButtonConfig)other;
            if (!Intrinsics.areEqual(this.title, var2.title)) {
               return false;
            } else if (this.material != var2.material) {
               return false;
            } else {
               return Intrinsics.areEqual(this.lore, var2.lore);
            }
         }
      }
   }
}
