package space.hugster.sell.utils.menu;

import com.cjcrafter.foliascheduler.TaskImplementation;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.DonutSell;
import space.hugster.sell.utils.VersionUtils;
import space.hugster.sell.utils.menu.pagination.PaginatedMenu;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\rH\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"Lspace/hugster/sell/utils/menu/ButtonListener;", "Lorg/bukkit/event/Listener;", "plugin", "Lspace/hugster/sell/DonutSell;", "<init>", "(Lspace/hugster/sell/DonutSell;)V", "onButtonPress", "", "event", "Lorg/bukkit/event/inventory/InventoryClickEvent;", "onInventoryClose", "Lorg/bukkit/event/inventory/InventoryCloseEvent;", "onPlayerMove", "Lorg/bukkit/event/player/PlayerMoveEvent;", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nButtonListener.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ButtonListener.kt\nspace/hugster/sell/utils/menu/ButtonListener\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,116:1\n1#2:117\n13402#3,2:118\n*S KotlinDebug\n*F\n+ 1 ButtonListener.kt\nspace/hugster/sell/utils/menu/ButtonListener\n*L\n107#1:118,2\n*E\n"})
public final class ButtonListener implements Listener {
   @NotNull
   private final DonutSell plugin;

   public ButtonListener(@NotNull DonutSell plugin) {
      Intrinsics.checkNotNullParameter(plugin, "plugin");
      super();
      this.plugin = plugin;
   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public final void onButtonPress(@NotNull InventoryClickEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      HumanEntity var4 = event.getWhoClicked();
      Player var10000 = var4 instanceof Player ? (Player)var4 : null;
      if ((var4 instanceof Player ? (Player)var4 : null) != null) {
         Player player = var10000;
         Menu var14 = (Menu)Menu.Companion.getCurrentlyOpenedMenus().get(player.getUniqueId());
         if (var14 != null) {
            Menu openMenu = var14;
            VersionUtils var15 = VersionUtils.INSTANCE;
            InventoryView var10001 = event.getView();
            Intrinsics.checkNotNullExpressionValue(var10001, "getView(...)");
            Inventory topInventory = var15.getTopInventory(var10001);
            PlayerInventory var16 = player.getInventory();
            Intrinsics.checkNotNullExpressionValue(var16, "getInventory(...)");
            PlayerInventory bottomInventory = var16;
            if (!openMenu.getCanInteract()) {
               if (Intrinsics.areEqual(event.getClickedInventory(), topInventory)) {
                  event.setCancelled(true);
               }

               if ((event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) && Intrinsics.areEqual(event.getClickedInventory(), bottomInventory)) {
                  event.setCancelled(true);
               } else if (event.getClick() == ClickType.NUMBER_KEY) {
                  event.setCancelled(true);
               } else if (Intrinsics.areEqual(event.getClickedInventory(), topInventory)) {
                  Button var17 = (Button)openMenu.getButtons().get(event.getSlot());
                  if (var17 != null) {
                     Button button = var17;
                     int var7 = 0;
                     ClickType var10002 = event.getClick();
                     Intrinsics.checkNotNullExpressionValue(var10002, "getClick(...)");
                     boolean cancel = button.shouldCancel(player, var10002);
                     if (!cancel && (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
                        event.setCancelled(true);
                        ItemStack var18 = event.getCurrentItem();
                        if (var18 != null) {
                           ItemStack it = var18;
                           int var10 = 0;
                           PlayerInventory var19 = player.getInventory();
                           ItemStack[] var11 = new ItemStack[]{it};
                           var19.addItem(var11);
                        }
                     } else {
                        event.setCancelled(cancel);
                     }

                     label53: {
                        var10002 = event.getClick();
                        Intrinsics.checkNotNullExpressionValue(var10002, "getClick(...)");
                        button.clicked(player, var10002);
                        int var21 = event.getSlot();
                        ClickType var10003 = event.getClick();
                        Intrinsics.checkNotNullExpressionValue(var10003, "getClick(...)");
                        button.clicked(player, var21, var10003, event.getHotbarButton());
                        Menu newMenu = (Menu)Menu.Companion.getCurrentlyOpenedMenus().get(player.getUniqueId());
                        if (Intrinsics.areEqual(newMenu, openMenu)) {
                           ClickType var22 = event.getClick();
                           Intrinsics.checkNotNullExpressionValue(var22, "getClick(...)");
                           if (button.shouldUpdate(player, var22)) {
                              openMenu.setClosedByMenu(true);
                              newMenu.openMenu(player);
                              break label53;
                           }
                        }

                        ClickType var23 = event.getClick();
                        Intrinsics.checkNotNullExpressionValue(var23, "getClick(...)");
                        if (button.shouldUpdate(player, var23)) {
                           openMenu.setClosedByMenu(true);
                           openMenu.openMenu(player);
                        }
                     }

                     if (event.isCancelled()) {
                        this.plugin.getFoliaScheduler().global().run(ButtonListener::onButtonPress$lambda$2$lambda$1);
                     }
                  }

               }
            }
         }
      }
   }

   @EventHandler(
      priority = EventPriority.HIGH
   )
   public final void onInventoryClose(@NotNull InventoryCloseEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      HumanEntity var10000 = event.getPlayer();
      Intrinsics.checkNotNull(var10000, "null cannot be cast to non-null type org.bukkit.entity.Player");
      Player player = (Player)var10000;
      Menu openMenu = (Menu)Menu.Companion.getCurrentlyOpenedMenus().get(player.getUniqueId());
      if (openMenu != null) {
         openMenu.onClose(player);
         Menu.Companion.getCurrentlyOpenedMenus().remove(player.getUniqueId());
         if (openMenu instanceof PaginatedMenu) {
            return;
         }
      }

      player.setMetadata("scanglitch", (MetadataValue)(new FixedMetadataValue((Plugin)this.plugin, true)));
   }

   @EventHandler
   public final void onPlayerMove(@NotNull PlayerMoveEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Player var10000 = event.getPlayer();
      Intrinsics.checkNotNullExpressionValue(var10000, "getPlayer(...)");
      Player player = var10000;
      if (player.hasMetadata("scanglitch")) {
         player.removeMetadata("scanglitch", (Plugin)this.plugin);
         ItemStack[] var12 = player.getInventory().getContents();
         Intrinsics.checkNotNullExpressionValue(var12, "getContents(...)");
         Object[] $this$forEach$iv = var12;
         int $i$f$forEach = 0;
         int var5 = 0;

         for(int var6 = $this$forEach$iv.length; var5 < var6; ++var5) {
            Object element$iv = $this$forEach$iv[var5];
            ItemStack it = (ItemStack)element$iv;
            int var9 = 0;
            if (it != null) {
               ItemMeta var13 = it.getItemMeta();
               if (var13 != null) {
                  ItemMeta meta = var13;
                  int var11 = 0;
                  if (meta.hasDisplayName()) {
                     String var14 = meta.getDisplayName();
                     Intrinsics.checkNotNullExpressionValue(var14, "getDisplayName(...)");
                     if (StringsKt.contains$default((CharSequence)var14, (CharSequence)"§b§c§d§e", false, 2, (Object)null)) {
                        player.getInventory().remove(it);
                     }
                  }
               }
            }
         }
      }

   }

   private static final void onButtonPress$lambda$2$lambda$1(Player $player, TaskImplementation <unused var>) {
      $player.updateInventory();
   }
}
