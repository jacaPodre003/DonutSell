package space.hugster.sell.ui;

import com.cjcrafter.foliascheduler.AsyncSchedulerImplementation;
import com.cjcrafter.foliascheduler.TaskImplementation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.data.Profile;
import space.hugster.sell.managers.ProfileManager;
import space.hugster.sell.sell.SellInventoryManager;
import space.hugster.sell.sell.SellServiceManager;
import space.hugster.sell.sell.data.SellResult;
import space.hugster.sell.utils.CC;
import space.hugster.sell.utils.Utilities;
import space.hugster.sell.utils.VersionUtils;
import space.hugster.sell.utils.menu.Button;
import space.hugster.sell.utils.menu.Menu;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u001c\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\n\u0010\u0016\u001a\u00060\u0017j\u0002`\u0018H\u0002J\u001e\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0002J\u0018\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u001c\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010$\u001a\u00020\"H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006%"},
   d2 = {"Lspace/hugster/sell/ui/SellMenu;", "Lspace/hugster/sell/utils/menu/Menu;", "<init>", "()V", "serviceManager", "Lspace/hugster/sell/sell/SellServiceManager;", "inventoryManager", "Lspace/hugster/sell/sell/SellInventoryManager;", "getInventoryManager", "()Lspace/hugster/sell/sell/SellInventoryManager;", "inventoryManager$delegate", "Lkotlin/Lazy;", "getTitle", "", "player", "Lorg/bukkit/entity/Player;", "onClose", "", "handleSellCompletion", "sellResult", "Lspace/hugster/sell/sell/data/SellResult;", "handleSellError", "exception", "Ljava/lang/Exception;", "Lkotlin/Exception;", "returnBlockedItems", "itemsToReturn", "", "Lorg/bukkit/inventory/ItemStack;", "processSellReward", "totalWorth", "", "getButtons", "", "", "Lspace/hugster/sell/utils/menu/Button;", "getSize", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nSellMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SellMenu.kt\nspace/hugster/sell/ui/SellMenu\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,122:1\n1863#2:123\n1863#2,2:124\n1864#2:126\n*S KotlinDebug\n*F\n+ 1 SellMenu.kt\nspace/hugster/sell/ui/SellMenu\n*L\n86#1:123\n87#1:124,2\n86#1:126\n*E\n"})
public final class SellMenu extends Menu {
   @NotNull
   private final SellServiceManager serviceManager;
   @NotNull
   private final Lazy inventoryManager$delegate;

   public SellMenu() {
      this.serviceManager = SellServiceManager.Companion.getInstance();
      this.inventoryManager$delegate = LazyKt.lazy(SellMenu::inventoryManager_delegate$lambda$0);
      this.setCanInteract(true);
   }

   private final SellInventoryManager getInventoryManager() {
      Lazy var1 = this.inventoryManager$delegate;
      return (SellInventoryManager)var1.getValue();
   }

   @NotNull
   public String getTitle(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      String var10000 = this.getPlugin().getConfig().getString("SELL-MENU.TITLE");
      if (var10000 == null) {
         var10000 = "&8ᴘʟᴀᴄᴇ ɪᴛᴇᴍѕ ɪɴ ʜᴇʀᴇ ᴛᴏ ѕᴇʟʟ";
      }

      return var10000;
   }

   public void onClose(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      if (!this.getInventoryManager().isProcessing(player)) {
         this.getInventoryManager().setProcessing(player, true);
         VersionUtils var10000 = VersionUtils.INSTANCE;
         InventoryView var10001 = player.getOpenInventory();
         Intrinsics.checkNotNullExpressionValue(var10001, "getOpenInventory(...)");
         Inventory inventory = var10000.getTopInventory(var10001);
         ProfileManager var10 = this.getPlugin().getProfileManager();
         UUID var12 = player.getUniqueId();
         Intrinsics.checkNotNullExpressionValue(var12, "getUniqueId(...)");
         Profile var11 = var10.getProfile(var12);
         if (var11 == null) {
            SellMenu $this$onClose_u24lambda_u241 = this;
            int sellResult = 0;
            $this$onClose_u24lambda_u241.getInventoryManager().setProcessing(player, false);
         } else {
            Profile profile = var11;
            AsyncSchedulerImplementation $this$onClose_u24lambda_u242 = this.getPlugin().getFoliaScheduler().async();
            int $this$onClose_u24lambda_u241 = 0;

            try {
               SellResult sellResult = this.serviceManager.getSellProcessingService().processInventoryContents(inventory, profile);
               this.serviceManager.getProgressUpdateService().updatePlayerStats(profile, sellResult.getStatCategoryEarnings());
               this.handleSellCompletion(player, sellResult);
            } catch (Exception e) {
               this.handleSellError(player, e);
            }

         }
      }
   }

   private final void handleSellCompletion(Player player, SellResult sellResult) {
      this.getPlugin().getFoliaScheduler().entity((Entity)player).run(SellMenu::handleSellCompletion$lambda$3);
   }

   private final void handleSellError(Player player, Exception exception) {
      this.getPlugin().getFoliaScheduler().entity((Entity)player).run(SellMenu::handleSellError$lambda$4);
   }

   private final void returnBlockedItems(Player player, List<? extends ItemStack> itemsToReturn) {
      Iterable $this$forEach$iv = (Iterable)itemsToReturn;
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         ItemStack item = (ItemStack)element$iv;
         int var8 = 0;
         PlayerInventory var10000 = player.getInventory();
         ItemStack[] var9 = new ItemStack[]{item};
         Collection var16 = var10000.addItem(var9).values();
         Intrinsics.checkNotNullExpressionValue(var16, "<get-values>(...)");
         Iterable $this$forEach$iv = (Iterable)var16;
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            ItemStack remaining = (ItemStack)element$iv;
            int var14 = 0;
            player.getWorld().dropItem(player.getLocation(), remaining);
         }
      }

   }

   private final void processSellReward(final Player player, final double totalWorth) {
      final String formattedWorth = Utilities.INSTANCE.getNicestInteger(totalWorth);
      BuildersKt.launch$default(this.getPlugin().getCoroutineScope(), (CoroutineContext)null, (CoroutineStart)null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>((Continuation)null) {
         int label;

         public final Object invokeSuspend(Object $result) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
               case 0:
                  ResultKt.throwOnFailure($result);

                  try {
                     SellMenu.this.getPlugin().getFoliaScheduler().entity((Entity)player).run(<undefinedtype>::invokeSuspend$lambda$1);
                  } catch (Exception e) {
                     Logger var10000 = SellMenu.this.getPlugin().getLogger();
                     String var10001 = player.getName();
                     var10000.severe("Error processing sell reward for " + var10001 + ": " + e.getMessage());
                     e.printStackTrace();
                  }

                  return Unit.INSTANCE;
               default:
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
         }

         public final Continuation<Unit> create(Object value, Continuation<?> $completion) {
            return (Continuation)(new <anonymous constructor>($completion));
         }

         public final Object invoke(CoroutineScope p1, Continuation<? super Unit> p2) {
            return ((<undefinedtype>)this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
         }

         private static final void invokeSuspend$lambda$1(SellMenu this$0, Player $player, String $formattedWorth, double $totalWorth, TaskImplementation it) {
            ProfileManager var10000 = this$0.getPlugin().getProfileManager();
            UUID var10001 = $player.getUniqueId();
            Intrinsics.checkNotNullExpressionValue(var10001, "getUniqueId(...)");
            Profile profile = var10000.getProfile(var10001);
            if (profile != null) {
               int var9 = 0;
               Economy var11 = this$0.getPlugin().getEconomy();
               if (var11 != null) {
                  var11.depositPlayer((OfflinePlayer)$player, $totalWorth);
               }
            }

            label20: {
               String itx = this$0.getPlugin().getMainConfig().getConfig().getString("SETTINGS.SELL-MESSAGE");
               if (itx != null) {
                  String var10 = StringsKt.replace$default(itx, "%price%", $formattedWorth, false, 4, (Object)null);
                  if (var10 != null) {
                     var12 = var10;
                     break label20;
                  }
               }

               var12 = "&a+" + $formattedWorth;
            }

            String sellMsg = var12;
            $player.sendMessage(CC.INSTANCE.translate(sellMsg));
            $player.sendActionBar(CC.INSTANCE.translate(sellMsg));
            $player.playSound($player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 2.0F);
            $player.updateInventory();
         }
      }, 3, (Object)null);
   }

   @NotNull
   public Map<Integer, Button> getButtons(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      return MapsKt.emptyMap();
   }

   public int getSize() {
      return 36;
   }

   private static final SellInventoryManager inventoryManager_delegate$lambda$0() {
      return new SellInventoryManager();
   }

   private static final void handleSellCompletion$lambda$3(SellMenu this$0, Player $player, SellResult $sellResult, TaskImplementation it) {
      try {
         this$0.returnBlockedItems($player, $sellResult.getItemsToReturn());
         if ($sellResult.getTotalWorth() > (double)0.0F) {
            this$0.processSellReward($player, $sellResult.getTotalWorth());
         }
      } finally {
         this$0.getInventoryManager().setProcessing($player, false);
      }

   }

   private static final void handleSellError$lambda$4(SellMenu this$0, Player $player, Exception $exception, TaskImplementation it) {
      this$0.getInventoryManager().setProcessing($player, false);
      $exception.printStackTrace();
   }
}
