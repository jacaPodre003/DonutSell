package space.hugster.sell.listeners;

import java.util.UUID;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.DonutSell;
import space.hugster.sell.data.Profile;
import space.hugster.sell.managers.ProfileManager;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000fH\u0007R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"},
   d2 = {"Lspace/hugster/sell/listeners/ProfileListener;", "Lorg/bukkit/event/Listener;", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "getPlugin", "()Lspace/hugster/sell/DonutSell;", "plugin$delegate", "Lkotlin/Lazy;", "onProfileJoin", "", "event", "Lorg/bukkit/event/player/PlayerJoinEvent;", "onProfileQuit", "Lorg/bukkit/event/player/PlayerQuitEvent;", "DonutSell"}
)
public final class ProfileListener implements Listener {
   @NotNull
   private final Lazy plugin$delegate = LazyKt.lazy(ProfileListener::plugin_delegate$lambda$0);

   private final DonutSell getPlugin() {
      Lazy var1 = this.plugin$delegate;
      return (DonutSell)var1.getValue();
   }

   @EventHandler
   public final void onProfileJoin(@NotNull PlayerJoinEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Player var10000 = event.getPlayer();
      Intrinsics.checkNotNullExpressionValue(var10000, "getPlayer(...)");
      Player player = var10000;
      ProfileManager var6 = this.getPlugin().getProfileManager();
      UUID var10001 = player.getUniqueId();
      Intrinsics.checkNotNullExpressionValue(var10001, "getUniqueId(...)");
      Profile var7 = var6.getProfile(var10001);
      if (var7 != null) {
         Profile profile = var7;
         int var5 = 0;
         if (!Intrinsics.areEqual(profile.getUsername(), player.getName())) {
            profile.setUsername(player.getName());
         }

      }
   }

   @EventHandler
   public final void onProfileQuit(@NotNull PlayerQuitEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Player var10000 = event.getPlayer();
      Intrinsics.checkNotNullExpressionValue(var10000, "getPlayer(...)");
      Player player = var10000;
      ProfileManager var6 = this.getPlugin().getProfileManager();
      UUID var10001 = player.getUniqueId();
      Intrinsics.checkNotNullExpressionValue(var10001, "getUniqueId(...)");
      Profile var7 = var6.getProfile(var10001);
      if (var7 != null) {
         Profile profile = var7;
         int var5 = 0;
         this.getPlugin().getProfileManager().removeProfile(profile);
      }
   }

   private static final DonutSell plugin_delegate$lambda$0() {
      return DonutSell.Companion.getINSTANCE();
   }
}
