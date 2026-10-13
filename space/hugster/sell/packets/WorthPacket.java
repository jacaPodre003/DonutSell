package space.hugster.sell.packets;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Server;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetSlot;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.bukkit.GameMode;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.DonutSell;
import space.hugster.sell.utils.ItemBuilder;
import space.hugster.sell.utils.Utilities;
import space.hugster.sell.utils.WorthCache;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0007H\u0002J \u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0007H\u0002J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0007H\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u000e\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0019\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u001cH\u0007R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lspace/hugster/sell/packets/WorthPacket;", "Lcom/github/retrooper/packetevents/event/PacketListenerAbstract;", "Lorg/bukkit/event/Listener;", "<init>", "()V", "amethystItemNames", "", "", "onPacketSend", "", "event", "Lcom/github/retrooper/packetevents/event/PacketSendEvent;", "handleWindowItems", "player", "Lorg/bukkit/entity/Player;", "worthLine", "handleSetSlot", "processPacketItem", "Lcom/github/retrooper/packetevents/protocol/item/ItemStack;", "packetItem", "calculatePrice", "", "item", "Lorg/bukkit/inventory/ItemStack;", "getWorthKey", "onWorthDrop", "Lorg/bukkit/event/player/PlayerDropItemEvent;", "onWorthCloseInventory", "Lorg/bukkit/event/inventory/InventoryCloseEvent;", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nWorthPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WorthPacket.kt\nspace/hugster/sell/packets/WorthPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,212:1\n1755#2,3:213\n827#2:216\n855#2,2:217\n1863#2,2:219\n*S KotlinDebug\n*F\n+ 1 WorthPacket.kt\nspace/hugster/sell/packets/WorthPacket\n*L\n116#1:213,3\n136#1:216\n136#1:217,2\n166#1:219,2\n*E\n"})
public final class WorthPacket extends PacketListenerAbstract implements Listener {
   @NotNull
   private final Set<String> amethystItemNames;

   public WorthPacket() {
      DonutSell.Companion.getINSTANCE().getServer().getPluginManager().registerEvents(this, (Plugin)DonutSell.Companion.getINSTANCE());
      PacketEvents.getAPI().getEventManager().registerListener((PacketListenerCommon)this);
      String[] var1 = new String[]{"ᴀᴍᴇᴛʜʏѕᴛ ᴅʀɪʟʟ", "ᴀᴍᴇᴛʜʏѕᴛ ᴛʀᴇᴇ ᴄʜᴏᴘᴘᴇʀ", "ᴀᴍᴇᴛʜʏѕᴛ ѕᴇʟʟ ᴀxᴇ", "ᴀᴍᴇᴛʜʏѕᴛ ѕʜᴏᴠᴇʟ"};
      this.amethystItemNames = SetsKt.setOf(var1);
   }

   public void onPacketSend(@NotNull PacketSendEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Player var10000 = (Player)event.getPlayer();
      if (var10000 != null) {
         Player player = var10000;
         if (player.getGameMode() != GameMode.CREATIVE) {
            String var5 = DonutSell.Companion.getINSTANCE().getMainConfig().getConfig().getString("SETTINGS.WORTH-FORMAT");
            if (var5 == null) {
               var5 = "&7Worth: &a$%price%";
            }

            String worthLine = var5;
            PacketTypeCommon var4 = event.getPacketType();
            if (var4 == Server.WINDOW_ITEMS) {
               this.handleWindowItems(event, player, worthLine);
            } else if (var4 == Server.SET_SLOT) {
               this.handleSetSlot(event, player, worthLine);
            }

         }
      }
   }

   private final void handleWindowItems(PacketSendEvent event, Player player, String worthLine) {
      WrapperPlayServerWindowItems wrapper = new WrapperPlayServerWindowItems(event);
      int windowId = wrapper.getWindowId();
      boolean isPlayerInventory = windowId == 0;
      if (isPlayerInventory) {
         List items = wrapper.getItems();
         List newItems = (List)(new ArrayList());

         for(ItemStack packetItem : items) {
            Intrinsics.checkNotNull(packetItem);
            ItemStack processedItem = this.processPacketItem(packetItem, worthLine);
            newItems.add(processedItem);
         }

         wrapper.setItems(newItems);
      }
   }

   private final void handleSetSlot(PacketSendEvent event, Player player, String worthLine) {
      WrapperPlayServerSetSlot wrapper = new WrapperPlayServerSetSlot(event);
      int windowId = wrapper.getWindowId();
      int slot = wrapper.getSlot();
      boolean isPlayerInventory = windowId == 0;
      if (isPlayerInventory && slot >= 0) {
         ItemStack packetItem = wrapper.getItem();
         Intrinsics.checkNotNull(packetItem);
         ItemStack processedItem = this.processPacketItem(packetItem, worthLine);
         wrapper.setItem(processedItem);
      }
   }

   private final ItemStack processPacketItem(ItemStack packetItem, String worthLine) {
      org.bukkit.inventory.ItemStack bukkitItem = SpigotConversionUtil.toBukkitItemStack(packetItem);
      if (bukkitItem != null && !bukkitItem.getType().isAir()) {
         org.bukkit.inventory.ItemStack var10000 = bukkitItem.clone();
         Intrinsics.checkNotNullExpressionValue(var10000, "clone(...)");
         org.bukkit.inventory.ItemStack clonedItem = var10000;
         ItemMeta var37 = clonedItem.getItemMeta();
         String displayName = var37 != null ? var37.getDisplayName() : null;
         if (displayName != null) {
            Iterable $this$any$iv = (Iterable)this.amethystItemNames;
            int $i$f$any = 0;
            boolean var38;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
               var38 = false;
            } else {
               Iterator var8 = $this$any$iv.iterator();

               while(true) {
                  if (!var8.hasNext()) {
                     var38 = false;
                     break;
                  }

                  Object element$iv = var8.next();
                  String it = (String)element$iv;
                  int var11 = 0;
                  if (StringsKt.contains$default((CharSequence)displayName, (CharSequence)it, false, 2, (Object)null)) {
                     var38 = true;
                     break;
                  }
               }
            }

            if (var38) {
               return packetItem;
            }
         }

         double price = this.calculatePrice(clonedItem);
         if (price <= (double)0.0F) {
            return packetItem;
         } else {
            ItemBuilder builder = new ItemBuilder(clonedItem);
            ItemMeta meta = clonedItem.getItemMeta();
            if (meta.hasLore()) {
               List var39 = meta.getLore();
               if (var39 == null) {
                  var39 = (List)(new ArrayList());
               }

               List currentLore = var39;
               CharSequence var12 = (CharSequence)StringsKt.substringBefore$default(worthLine, "%price%", (String)null, 2, (Object)null);
               Regex var13 = new Regex("&[0-9a-fk-or]");
               String var14 = "";
               String cleanPrefixPattern = StringsKt.trim((CharSequence)var13.replace(var12, var14)).toString();
               Iterable $this$filterNot$iv = (Iterable)currentLore;
               int $i$f$filterNot = 0;
               Collection destination$iv$iv = (Collection)(new ArrayList());
               int $i$f$filterNotTo = 0;

               for(Object element$iv$iv : $this$filterNot$iv) {
                  String loreLine = (String)element$iv$iv;
                  int var21 = 0;
                  Intrinsics.checkNotNull(loreLine);
                  CharSequence var22 = (CharSequence)loreLine;
                  Regex var23 = new Regex("§[0-9a-fk-or]");
                  String var24 = "";
                  String cleanLoreLine = StringsKt.trim((CharSequence)var23.replace(var22, var24)).toString();
                  if (!StringsKt.startsWith$default(cleanLoreLine, cleanPrefixPattern, false, 2, (Object)null) && !StringsKt.contains$default((CharSequence)loreLine, (CharSequence)"Worth:", false, 2, (Object)null) && !StringsKt.contains$default((CharSequence)loreLine, (CharSequence)"$", false, 2, (Object)null)) {
                     destination$iv$iv.add(element$iv$iv);
                  }
               }

               List filteredLore = (List)destination$iv$iv;
               builder.lore(filteredLore);
            }

            String formattedLore = StringsKt.replace$default(worthLine, "%price%", Utilities.INSTANCE.getNicestInteger(price), false, 4, (Object)null);
            String[] processedBukkitItem = new String[]{formattedLore};
            builder.addLore(processedBukkitItem);
            org.bukkit.inventory.ItemStack processedBukkitItem = builder.build();
            ItemStack var40 = SpigotConversionUtil.fromBukkitItemStack(processedBukkitItem);
            Intrinsics.checkNotNullExpressionValue(var40, "fromBukkitItemStack(...)");
            return var40;
         }
      } else {
         return packetItem;
      }
   }

   private final double calculatePrice(org.bukkit.inventory.ItemStack item) {
      double total = (double)0.0F;
      String worthKey = this.getWorthKey(item);
      double basePrice = WorthCache.INSTANCE.getPrice(worthKey);
      if (basePrice > (double)0.0F) {
         total += basePrice * (double)item.getAmount();
      }

      ItemMeta meta = item.getItemMeta();
      if (meta instanceof BlockStateMeta) {
         BlockState var10000 = ((BlockStateMeta)meta).getBlockState();
         Intrinsics.checkNotNullExpressionValue(var10000, "getBlockState(...)");
         BlockState state = var10000;
         if (state instanceof ShulkerBox) {
            org.bukkit.inventory.ItemStack[] var16 = ((ShulkerBox)state).getInventory().getContents();
            Intrinsics.checkNotNullExpressionValue(var16, "getContents(...)");
            Iterable $this$forEach$iv = (Iterable)ArraysKt.filterNotNull(var16);
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               org.bukkit.inventory.ItemStack it = (org.bukkit.inventory.ItemStack)element$iv;
               int var12 = 0;
               String key = this.getWorthKey(it);
               total += WorthCache.INSTANCE.getPrice(key) * (double)it.getAmount();
            }
         }
      }

      return total;
   }

   @NotNull
   public final String getWorthKey(@NotNull org.bukkit.inventory.ItemStack item) {
      Intrinsics.checkNotNullParameter(item, "item");
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

   @EventHandler
   public final void onWorthDrop(@NotNull PlayerDropItemEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
         event.getPlayer().updateInventory();
      }
   }

   @EventHandler
   public final void onWorthCloseInventory(@NotNull InventoryCloseEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
         HumanEntity var10000 = event.getPlayer();
         Intrinsics.checkNotNull(var10000, "null cannot be cast to non-null type org.bukkit.entity.Player");
         Player player = (Player)var10000;
         player.updateInventory();
      }
   }
}
