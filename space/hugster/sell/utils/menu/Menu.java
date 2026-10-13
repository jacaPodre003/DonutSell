package space.hugster.sell.utils.menu;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.DonutSell;
import space.hugster.sell.utils.CC;
import space.hugster.sell.utils.menu.event.MenuOpenEvent;
import space.hugster.sell.utils.menu.event.MenuUpdateEvent;
import space.hugster.sell.utils.menu.task.MenuUpdateTask;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\b&\u0018\u0000 82\u00020\u0001:\u00018B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\rH\u0002J\u000e\u0010+\u001a\u00020,2\u0006\u0010(\u001a\u00020)J\u001a\u0010-\u001a\u00020\f2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0.J\b\u0010/\u001a\u00020\fH\u0016J\u0016\u00100\u001a\u00020\f2\u0006\u00101\u001a\u00020\f2\u0006\u00102\u001a\u00020\fJ\u0010\u00103\u001a\u0002042\u0006\u0010(\u001a\u00020)H&J\u0006\u00105\u001a\u000204J\u001c\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0.2\u0006\u0010(\u001a\u00020)H&J\u0010\u00106\u001a\u00020,2\u0006\u0010(\u001a\u00020)H\u0016J\u0010\u00107\u001a\u00020,2\u0006\u0010(\u001a\u00020)H\u0016R\u001b\u0010\u0004\u001a\u00020\u00058DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R&\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0015\"\u0004\b\u001a\u0010\u0017R\u001a\u0010\u001b\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0015\"\u0004\b\u001d\u0010\u0017R\u001a\u0010\u001e\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0015\"\u0004\b \u0010\u0017R\u001a\u0010!\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%¨\u00069"},
   d2 = {"Lspace/hugster/sell/utils/menu/Menu;", "", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "getPlugin", "()Lspace/hugster/sell/DonutSell;", "plugin$delegate", "Lkotlin/Lazy;", "buttons", "", "", "Lspace/hugster/sell/utils/menu/Button;", "getButtons", "()Ljava/util/Map;", "setButtons", "(Ljava/util/Map;)V", "autoUpdate", "", "getAutoUpdate", "()Z", "setAutoUpdate", "(Z)V", "closedByMenu", "getClosedByMenu", "setClosedByMenu", "placeholder", "getPlaceholder", "setPlaceholder", "canInteract", "getCanInteract", "setCanInteract", "placeholderButton", "getPlaceholderButton", "()Lspace/hugster/sell/utils/menu/Button;", "setPlaceholderButton", "(Lspace/hugster/sell/utils/menu/Button;)V", "createItemStack", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "button", "openMenu", "", "size", "", "getSize", "getSlot", "x", "y", "getTitle", "", "getID", "onOpen", "onClose", "Companion", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Menu.kt\nspace/hugster/sell/utils/menu/Menu\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,142:1\n216#2,2:143\n*S KotlinDebug\n*F\n+ 1 Menu.kt\nspace/hugster/sell/utils/menu/Menu\n*L\n83#1:143,2\n*E\n"})
public abstract class Menu {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final Lazy plugin$delegate = LazyKt.lazy(Menu::plugin_delegate$lambda$0);
   @NotNull
   private Map<Integer, Button> buttons = (Map)(new ConcurrentHashMap());
   private boolean autoUpdate;
   private boolean closedByMenu;
   private boolean placeholder;
   private boolean canInteract;
   @NotNull
   private Button placeholderButton;
   @NotNull
   private static final Map<UUID, Menu> currentlyOpenedMenus = (Map)(new ConcurrentHashMap());

   public Menu() {
      Button.Companion var10001 = Button.Companion;
      Material var10002 = Material.GRAY_STAINED_GLASS_PANE;
      String[] var1 = new String[]{" "};
      this.placeholderButton = var10001.placeholder(var10002, (byte)0, var1);
   }

   @NotNull
   protected final DonutSell getPlugin() {
      Lazy var1 = this.plugin$delegate;
      return (DonutSell)var1.getValue();
   }

   @NotNull
   public final Map<Integer, Button> getButtons() {
      return this.buttons;
   }

   public final void setButtons(@NotNull Map<Integer, Button> <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.buttons = <set-?>;
   }

   public final boolean getAutoUpdate() {
      return this.autoUpdate;
   }

   public final void setAutoUpdate(boolean <set-?>) {
      this.autoUpdate = <set-?>;
   }

   public final boolean getClosedByMenu() {
      return this.closedByMenu;
   }

   public final void setClosedByMenu(boolean <set-?>) {
      this.closedByMenu = <set-?>;
   }

   public final boolean getPlaceholder() {
      return this.placeholder;
   }

   public final void setPlaceholder(boolean <set-?>) {
      this.placeholder = <set-?>;
   }

   public final boolean getCanInteract() {
      return this.canInteract;
   }

   public final void setCanInteract(boolean <set-?>) {
      this.canInteract = <set-?>;
   }

   @NotNull
   public final Button getPlaceholderButton() {
      return this.placeholderButton;
   }

   public final void setPlaceholderButton(@NotNull Button <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.placeholderButton = <set-?>;
   }

   private final ItemStack createItemStack(Player player, Button button) {
      ItemStack item = button.getButtonItem(player);
      if (item.getType() != Material.LEGACY_SKULL_ITEM) {
         ItemMeta meta = item.getItemMeta();
         if (meta != null) {
            int var6 = 0;
            if (meta.hasDisplayName()) {
               meta.setDisplayName(meta.getDisplayName());
            }

            item.setItemMeta(meta);
         }
      }

      return item;
   }

   public final void openMenu(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");

      try {
         Menu previousMenu = (Menu)currentlyOpenedMenus.get(player.getUniqueId());
         if (previousMenu != null) {
            previousMenu.onClose(player);
            previousMenu.closedByMenu = true;
            currentlyOpenedMenus.remove(player.getUniqueId());
            MenuUpdateEvent event = new MenuUpdateEvent(this);
            event.call();
         } else {
            MenuOpenEvent event = new MenuOpenEvent(this);
            event.call();
         }

         this.buttons = MapsKt.toMutableMap(this.getButtons(player));
         CC var10000 = CC.INSTANCE;
         String var10001 = this.getTitle(player);
         String title = var10000.translate("&7" + var10001);
         if (title.length() > 32) {
            String var19 = title.substring(0, 32);
            Intrinsics.checkNotNullExpressionValue(var19, "substring(...)");
            title = var19;
         }

         int size = this.getSize() == -1 ? this.size(this.buttons) : this.getSize();
         Inventory var20 = Bukkit.createInventory((InventoryHolder)player, size, title);
         Intrinsics.checkNotNullExpressionValue(var20, "createInventory(...)");
         Inventory inventory = var20;
         Map $this$forEach$iv = this.buttons;
         int $i$f$forEach = 0;

         for(Map.Entry element$iv : $this$forEach$iv.entrySet()) {
            int var11 = 0;
            int key = ((Number)element$iv.getKey()).intValue();
            Button button = (Button)element$iv.getValue();
            inventory.setItem(key, this.createItemStack(player, button));
         }

         if (this.placeholder) {
            for(int index = 0; index < size; ++index) {
               if (this.buttons.get(index) == null) {
                  Integer var18 = index;
                  this.buttons.put(var18, this.placeholderButton);
                  inventory.setItem(index, this.placeholderButton.getButtonItem(player));
               }
            }
         }

         player.openInventory(inventory);
         currentlyOpenedMenus.put(player.getUniqueId(), this);
         this.onOpen(player);
         this.closedByMenu = false;
      } catch (Exception e) {
         e.printStackTrace();
         player.closeInventory();
      }

   }

   public final int size(@NotNull Map<Integer, ? extends Button> buttons) {
      Intrinsics.checkNotNullParameter(buttons, "buttons");
      int highest = 0;
      Iterator var3 = buttons.keySet().iterator();

      while(var3.hasNext()) {
         int buttonValue = ((Number)var3.next()).intValue();
         if (buttonValue > highest) {
            highest = buttonValue;
         }
      }

      return (int)(Math.ceil((double)(highest + 1) / (double)9.0F) * (double)9);
   }

   public int getSize() {
      return -1;
   }

   public final int getSlot(int x, int y) {
      return 9 * y + x;
   }

   @NotNull
   public abstract String getTitle(@NotNull Player player);

   @NotNull
   public final String getID() {
      String var10000 = Reflection.getOrCreateKotlinClass(this.getClass()).getSimpleName();
      if (var10000 == null) {
         var10000 = "";
      }

      return var10000;
   }

   @NotNull
   public abstract Map<Integer, Button> getButtons(@NotNull Player player);

   public void onOpen(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
   }

   public void onClose(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
   }

   private static final DonutSell plugin_delegate$lambda$0() {
      return DonutSell.Companion.getINSTANCE();
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\n\u001a\u00020\u000bR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\f"},
      d2 = {"Lspace/hugster/sell/utils/menu/Menu$Companion;", "", "<init>", "()V", "currentlyOpenedMenus", "", "Ljava/util/UUID;", "Lspace/hugster/sell/utils/menu/Menu;", "getCurrentlyOpenedMenus", "()Ljava/util/Map;", "init", "", "DonutSell"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final Map<UUID, Menu> getCurrentlyOpenedMenus() {
         return Menu.currentlyOpenedMenus;
      }

      public final void init() {
         new MenuUpdateTask();
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
