package space.hugster.sell.commands;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import space.hugster.sell.DonutSell;
import space.hugster.sell.utils.CC;
import space.hugster.sell.utils.WorthCache;

@Command({"donutsell"})
@CommandPermission("donutsell.admin")
@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"},
   d2 = {"Lspace/hugster/sell/commands/DonutSellCommand;", "", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "getPlugin", "()Lspace/hugster/sell/DonutSell;", "plugin$delegate", "Lkotlin/Lazy;", "onReload", "", "sender", "Lorg/bukkit/command/CommandSender;", "DonutSell"}
)
public final class DonutSellCommand {
   @NotNull
   private final Lazy plugin$delegate = LazyKt.lazy(DonutSellCommand::plugin_delegate$lambda$0);

   private final DonutSell getPlugin() {
      Lazy var1 = this.plugin$delegate;
      return (DonutSell)var1.getValue();
   }

   @Subcommand({"reload"})
   public final void onReload(@NotNull CommandSender sender) {
      Intrinsics.checkNotNullParameter(sender, "sender");
      long init = System.currentTimeMillis();
      this.getPlugin().getMainConfig().reload();
      this.getPlugin().getWorthConfig().reload();
      WorthCache.INSTANCE.loadCache();
      sender.sendMessage(CC.INSTANCE.translate("&aAll configs has been reloaded &7(" + (System.currentTimeMillis() - init) + "ms)"));
   }

   private static final DonutSell plugin_delegate$lambda$0() {
      return DonutSell.Companion.getINSTANCE();
   }
}
