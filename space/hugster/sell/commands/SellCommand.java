package space.hugster.sell.commands;

import java.util.UUID;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;
import space.hugster.sell.DonutSell;
import space.hugster.sell.data.Profile;
import space.hugster.sell.managers.ProfileManager;
import space.hugster.sell.ui.SellHistoryMenu;
import space.hugster.sell.ui.SellMenu;
import space.hugster.sell.ui.SellMultiplierMenu;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"},
   d2 = {"Lspace/hugster/sell/commands/SellCommand;", "", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "getPlugin", "()Lspace/hugster/sell/DonutSell;", "plugin$delegate", "Lkotlin/Lazy;", "onDefault", "", "player", "Lorg/bukkit/entity/Player;", "onSellHistory", "onSellMultiplier", "DonutSell"}
)
public final class SellCommand {
   @NotNull
   private final Lazy plugin$delegate = LazyKt.lazy(SellCommand::plugin_delegate$lambda$0);

   private final DonutSell getPlugin() {
      Lazy var1 = this.plugin$delegate;
      return (DonutSell)var1.getValue();
   }

   @Command({"sell"})
   public final void onDefault(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      (new SellMenu()).openMenu(player);
   }

   @Command({"sellhistory"})
   public final void onSellHistory(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      (new SellHistoryMenu()).openMenu(player);
   }

   @Command({"sellmulti", "sellmultiplier"})
   public final void onSellMultiplier(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      ProfileManager var10000 = this.getPlugin().getProfileManager();
      UUID var10001 = player.getUniqueId();
      Intrinsics.checkNotNullExpressionValue(var10001, "getUniqueId(...)");
      Profile var3 = var10000.getProfile(var10001);
      if (var3 != null) {
         Profile profile = var3;
         (new SellMultiplierMenu(profile)).openMenu(player);
      }
   }

   private static final DonutSell plugin_delegate$lambda$0() {
      return DonutSell.Companion.getINSTANCE();
   }
}
