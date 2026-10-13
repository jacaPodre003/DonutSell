package space.hugster.sell.sell.data;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0004\b\n\u0010\u000bJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u0015\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J9\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001c"},
   d2 = {"Lspace/hugster/sell/sell/data/SellResult;", "", "totalWorth", "", "statCategoryEarnings", "", "", "itemsToReturn", "", "Lorg/bukkit/inventory/ItemStack;", "<init>", "(DLjava/util/Map;Ljava/util/List;)V", "getTotalWorth", "()D", "getStatCategoryEarnings", "()Ljava/util/Map;", "getItemsToReturn", "()Ljava/util/List;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "DonutSell"}
)
public final class SellResult {
   private final double totalWorth;
   @NotNull
   private final Map<String, Double> statCategoryEarnings;
   @NotNull
   private final List<ItemStack> itemsToReturn;

   public SellResult(double totalWorth, @NotNull Map<String, Double> statCategoryEarnings, @NotNull List<? extends ItemStack> itemsToReturn) {
      Intrinsics.checkNotNullParameter(statCategoryEarnings, "statCategoryEarnings");
      Intrinsics.checkNotNullParameter(itemsToReturn, "itemsToReturn");
      super();
      this.totalWorth = totalWorth;
      this.statCategoryEarnings = statCategoryEarnings;
      this.itemsToReturn = itemsToReturn;
   }

   public final double getTotalWorth() {
      return this.totalWorth;
   }

   @NotNull
   public final Map<String, Double> getStatCategoryEarnings() {
      return this.statCategoryEarnings;
   }

   @NotNull
   public final List<ItemStack> getItemsToReturn() {
      return this.itemsToReturn;
   }

   public final double component1() {
      return this.totalWorth;
   }

   @NotNull
   public final Map<String, Double> component2() {
      return this.statCategoryEarnings;
   }

   @NotNull
   public final List<ItemStack> component3() {
      return this.itemsToReturn;
   }

   @NotNull
   public final SellResult copy(double totalWorth, @NotNull Map<String, Double> statCategoryEarnings, @NotNull List<? extends ItemStack> itemsToReturn) {
      Intrinsics.checkNotNullParameter(statCategoryEarnings, "statCategoryEarnings");
      Intrinsics.checkNotNullParameter(itemsToReturn, "itemsToReturn");
      return new SellResult(totalWorth, statCategoryEarnings, itemsToReturn);
   }

   // $FF: synthetic method
   public static SellResult copy$default(SellResult var0, double var1, Map var3, List var4, int var5, Object var6) {
      if ((var5 & 1) != 0) {
         var1 = var0.totalWorth;
      }

      if ((var5 & 2) != 0) {
         var3 = var0.statCategoryEarnings;
      }

      if ((var5 & 4) != 0) {
         var4 = var0.itemsToReturn;
      }

      return var0.copy(var1, var3, var4);
   }

   @NotNull
   public String toString() {
      return "SellResult(totalWorth=" + this.totalWorth + ", statCategoryEarnings=" + this.statCategoryEarnings + ", itemsToReturn=" + this.itemsToReturn + ")";
   }

   public int hashCode() {
      int result = Double.hashCode(this.totalWorth);
      result = result * 31 + this.statCategoryEarnings.hashCode();
      result = result * 31 + this.itemsToReturn.hashCode();
      return result;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof SellResult)) {
         return false;
      } else {
         SellResult var2 = (SellResult)other;
         if (Double.compare(this.totalWorth, var2.totalWorth) != 0) {
            return false;
         } else if (!Intrinsics.areEqual(this.statCategoryEarnings, var2.statCategoryEarnings)) {
            return false;
         } else {
            return Intrinsics.areEqual(this.itemsToReturn, var2.itemsToReturn);
         }
      }
   }
}
