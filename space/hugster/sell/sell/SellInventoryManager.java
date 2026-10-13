package space.hugster.sell.sell;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\bRP\u0010\u0004\u001aB\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\b0\b \u0007* \u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\b0\b\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0010"},
   d2 = {"Lspace/hugster/sell/sell/SellInventoryManager;", "", "<init>", "()V", "processingPlayers", "Ljava/util/concurrent/ConcurrentHashMap$KeySetView;", "Ljava/util/UUID;", "kotlin.jvm.PlatformType", "", "Ljava/util/concurrent/ConcurrentHashMap$KeySetView;", "isProcessing", "player", "Lorg/bukkit/entity/Player;", "setProcessing", "", "processing", "DonutSell"}
)
public final class SellInventoryManager {
   private final ConcurrentHashMap.KeySetView<UUID, Boolean> processingPlayers = ConcurrentHashMap.newKeySet();

   public final boolean isProcessing(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      return this.processingPlayers.contains(player.getUniqueId());
   }

   public final void setProcessing(@NotNull Player player, boolean processing) {
      Intrinsics.checkNotNullParameter(player, "player");
      if (processing) {
         this.processingPlayers.add(player.getUniqueId());
      } else {
         this.processingPlayers.remove(player.getUniqueId());
      }

   }
}
