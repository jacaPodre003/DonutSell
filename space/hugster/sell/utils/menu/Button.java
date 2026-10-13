package space.hugster.sell.utils.menu;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.DonutSell;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b&\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J(\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0017\u001a\u00020\u00162\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u001b\u0010\u0004\u001a\u00020\u00058DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0019"},
   d2 = {"Lspace/hugster/sell/utils/menu/Button;", "", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "getPlugin", "()Lspace/hugster/sell/DonutSell;", "plugin$delegate", "Lkotlin/Lazy;", "getButtonItem", "Lorg/bukkit/inventory/ItemStack;", "player", "Lorg/bukkit/entity/Player;", "clicked", "", "clickType", "Lorg/bukkit/event/inventory/ClickType;", "slot", "", "hotbarSlot", "shouldCancel", "", "shouldUpdate", "Companion", "DonutSell"}
)
public abstract class Button {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final Lazy plugin$delegate = LazyKt.lazy(Button::plugin_delegate$lambda$0);

   @NotNull
   protected final DonutSell getPlugin() {
      Lazy var1 = this.plugin$delegate;
      return (DonutSell)var1.getValue();
   }

   @NotNull
   public abstract ItemStack getButtonItem(@NotNull Player player);

   public void clicked(@NotNull Player player, @NotNull ClickType clickType) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(clickType, "clickType");
   }

   public void clicked(@NotNull Player player, int slot, @NotNull ClickType clickType, int hotbarSlot) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(clickType, "clickType");
   }

   public boolean shouldCancel(@NotNull Player player, @NotNull ClickType clickType) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(clickType, "clickType");
      return true;
   }

   public boolean shouldUpdate(@NotNull Player player, @NotNull ClickType clickType) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(clickType, "clickType");
      return false;
   }

   private static final DonutSell plugin_delegate$lambda$0() {
      return DonutSell.Companion.getINSTANCE();
   }

   @JvmStatic
   @NotNull
   public static final Button placeholder(@NotNull Material material, byte data, @NotNull String... title) {
      return Companion.placeholder(material, data, title);
   }

   @JvmStatic
   public static final void playFail(@NotNull Player player) {
      Companion.playFail(player);
   }

   @JvmStatic
   public static final void playSuccess(@NotNull Player player) {
      Companion.playSuccess(player);
   }

   @JvmStatic
   public static final void playNeutral(@NotNull Player player) {
      Companion.playNeutral(player);
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J1\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u000b\"\u00020\fH\u0007¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007¨\u0006\u0014"},
      d2 = {"Lspace/hugster/sell/utils/menu/Button$Companion;", "", "<init>", "()V", "placeholder", "Lspace/hugster/sell/utils/menu/Button;", "material", "Lorg/bukkit/Material;", "data", "", "title", "", "", "(Lorg/bukkit/Material;B[Ljava/lang/String;)Lspace/hugster/sell/utils/menu/Button;", "playFail", "", "player", "Lorg/bukkit/entity/Player;", "playSuccess", "playNeutral", "DonutSell"}
   )
   public static final class Companion {
      private Companion() {
      }

      @JvmStatic
      @NotNull
      public final Button placeholder(@NotNull final Material material, final byte data, @NotNull final String... title) {
         Intrinsics.checkNotNullParameter(material, "material");
         Intrinsics.checkNotNullParameter(title, "title");
         return new Button() {
            public ItemStack getButtonItem(Player player) {
               Intrinsics.checkNotNullParameter(player, "player");
               ItemStack item = new ItemStack(material, 1, (short)data);
               ItemMeta meta = item.getItemMeta();
               if (meta != null) {
                  String[] var5 = title;
                  int var7 = 0;
                  meta.setDisplayName(ArraysKt.joinToString$default(var5, (CharSequence)"", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null));
                  item.setItemMeta(meta);
               }

               return item;
            }
         };
      }

      @JvmStatic
      public final void playFail(@NotNull Player player) {
         Intrinsics.checkNotNullParameter(player, "player");
         player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 20.0F, 0.1F);
      }

      @JvmStatic
      public final void playSuccess(@NotNull Player player) {
         Intrinsics.checkNotNullParameter(player, "player");
         player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 20.0F, 15.0F);
      }

      @JvmStatic
      public final void playNeutral(@NotNull Player player) {
         Intrinsics.checkNotNullParameter(player, "player");
         player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 20.0F, 1.0F);
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
