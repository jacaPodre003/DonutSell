package space.hugster.sell.vault;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.DonutSell;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u000eJ\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\fJ\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\fJk\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\f2K\u0010\u0014\u001aG\u0012\u0013\u0012\u00110\u0016¢\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u00110\u001a¢\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u001b\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u001c0\u0015H\u0002R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u001d"},
   d2 = {"Lspace/hugster/sell/vault/VaultUtils;", "", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "getPlugin", "()Lspace/hugster/sell/DonutSell;", "plugin$delegate", "Lkotlin/Lazy;", "getBalance", "Ljava/util/concurrent/CompletableFuture;", "", "player", "Ljava/util/UUID;", "giveBalance", "", "amount", "takeBalance", "performEconomyOperation", "operation", "Lkotlin/Function3;", "Lnet/milkbowl/vault/economy/Economy;", "Lkotlin/ParameterName;", "name", "economy", "Lorg/bukkit/OfflinePlayer;", "offlinePlayer", "Lnet/milkbowl/vault/economy/EconomyResponse;", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nVaultUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VaultUtils.kt\nspace/hugster/sell/vault/VaultUtils\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,54:1\n1#2:55\n*E\n"})
public final class VaultUtils {
   @NotNull
   public static final VaultUtils INSTANCE = new VaultUtils();
   @NotNull
   private static final Lazy plugin$delegate = LazyKt.lazy(VaultUtils::plugin_delegate$lambda$0);

   private VaultUtils() {
   }

   private final DonutSell getPlugin() {
      Lazy var1 = plugin$delegate;
      return (DonutSell)var1.getValue();
   }

   @NotNull
   public final CompletableFuture<Double> getBalance(@NotNull UUID player) {
      Intrinsics.checkNotNullParameter(player, "player");
      CompletableFuture var10000 = CompletableFuture.supplyAsync(VaultUtils::getBalance$lambda$4);
      Intrinsics.checkNotNullExpressionValue(var10000, "supplyAsync(...)");
      return var10000;
   }

   @NotNull
   public final CompletableFuture<Boolean> giveBalance(@NotNull UUID player, double amount) {
      Intrinsics.checkNotNullParameter(player, "player");
      return this.performEconomyOperation(player, amount, VaultUtils::giveBalance$lambda$5);
   }

   @NotNull
   public final CompletableFuture<Boolean> takeBalance(@NotNull UUID player, double amount) {
      Intrinsics.checkNotNullParameter(player, "player");
      return this.performEconomyOperation(player, amount, VaultUtils::takeBalance$lambda$6);
   }

   private final CompletableFuture<Boolean> performEconomyOperation(UUID player, double amount, Function3<? super Economy, ? super OfflinePlayer, ? super Double, ? extends EconomyResponse> operation) {
      CompletableFuture var10000 = CompletableFuture.supplyAsync(VaultUtils::performEconomyOperation$lambda$10);
      Intrinsics.checkNotNullExpressionValue(var10000, "supplyAsync(...)");
      return var10000;
   }

   private static final DonutSell plugin_delegate$lambda$0() {
      return DonutSell.Companion.getINSTANCE();
   }

   private static final Double getBalance$lambda$4(UUID $player) {
      VaultUtils $this$getBalance_u24lambda_u244_u24lambda_u242 = INSTANCE;

      Object $this$getBalance_u24lambda_u244_u24lambda_u242;
      try {
         Result.Companion var9 = Result.Companion;
         int var3 = 0;
         Economy var10 = $this$getBalance_u24lambda_u244_u24lambda_u242.getPlugin().getEconomy();
         double var12;
         if (var10 != null) {
            Economy economy = var10;
            int var5 = 0;
            OfflinePlayer var11 = $this$getBalance_u24lambda_u244_u24lambda_u242.getPlugin().getServer().getOfflinePlayer($player);
            Intrinsics.checkNotNullExpressionValue(var11, "getOfflinePlayer(...)");
            OfflinePlayer offlinePlayer = var11;
            var12 = economy.getBalance(offlinePlayer);
         } else {
            var12 = (double)0.0F;
         }

         $this$getBalance_u24lambda_u244_u24lambda_u242 = Result.constructor-impl(var12);
      } catch (Throwable var7) {
         Result.Companion var10000 = Result.Companion;
         $this$getBalance_u24lambda_u244_u24lambda_u242 = Result.constructor-impl(ResultKt.createFailure(var7));
      }

      Object var13;
      if (Result.exceptionOrNull-impl($this$getBalance_u24lambda_u244_u24lambda_u242) == null) {
         var13 = $this$getBalance_u24lambda_u244_u24lambda_u242;
      } else {
         int var8 = 0;
         var13 = (double)0.0F;
      }

      return (Double)var13;
   }

   private static final EconomyResponse giveBalance$lambda$5(double $amount, Economy economy, OfflinePlayer offlinePlayer, double <unused var>) {
      Intrinsics.checkNotNullParameter(economy, "economy");
      Intrinsics.checkNotNullParameter(offlinePlayer, "offlinePlayer");
      EconomyResponse var10000 = economy.depositPlayer(offlinePlayer, $amount);
      Intrinsics.checkNotNullExpressionValue(var10000, "depositPlayer(...)");
      return var10000;
   }

   private static final EconomyResponse takeBalance$lambda$6(double $amount, Economy economy, OfflinePlayer offlinePlayer, double <unused var>) {
      Intrinsics.checkNotNullParameter(economy, "economy");
      Intrinsics.checkNotNullParameter(offlinePlayer, "offlinePlayer");
      EconomyResponse var10000 = economy.withdrawPlayer(offlinePlayer, $amount);
      Intrinsics.checkNotNullExpressionValue(var10000, "withdrawPlayer(...)");
      return var10000;
   }

   private static final Boolean performEconomyOperation$lambda$10(UUID $player, Function3 $operation, double $amount) {
      VaultUtils $this$performEconomyOperation_u24lambda_u2410_u24lambda_u248 = INSTANCE;

      Object $this$performEconomyOperation_u24lambda_u2410_u24lambda_u248;
      try {
         Result.Companion var12 = Result.Companion;
         int var6 = 0;
         Economy var13 = $this$performEconomyOperation_u24lambda_u2410_u24lambda_u248.getPlugin().getEconomy();
         boolean var15;
         if (var13 != null) {
            Economy economy = var13;
            int var8 = 0;
            OfflinePlayer var14 = $this$performEconomyOperation_u24lambda_u2410_u24lambda_u248.getPlugin().getServer().getOfflinePlayer($player);
            Intrinsics.checkNotNullExpressionValue(var14, "getOfflinePlayer(...)");
            OfflinePlayer offlinePlayer = var14;
            var15 = ((EconomyResponse)$operation.invoke(economy, offlinePlayer, $amount)).transactionSuccess();
         } else {
            var15 = false;
         }

         $this$performEconomyOperation_u24lambda_u2410_u24lambda_u248 = Result.constructor-impl(var15);
      } catch (Throwable var10) {
         Result.Companion var10000 = Result.Companion;
         $this$performEconomyOperation_u24lambda_u2410_u24lambda_u248 = Result.constructor-impl(ResultKt.createFailure(var10));
      }

      Object var16;
      if (Result.exceptionOrNull-impl($this$performEconomyOperation_u24lambda_u2410_u24lambda_u248) == null) {
         var16 = $this$performEconomyOperation_u24lambda_u2410_u24lambda_u248;
      } else {
         int var11 = 0;
         var16 = false;
      }

      return (Boolean)var16;
   }
}
