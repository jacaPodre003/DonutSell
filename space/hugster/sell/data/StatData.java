package space.hugster.sell.data;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0019"},
   d2 = {"Lspace/hugster/sell/data/StatData;", "", "currentLevel", "", "earned", "", "<init>", "(ID)V", "getCurrentLevel", "()I", "setCurrentLevel", "(I)V", "getEarned", "()D", "setEarned", "(D)V", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "DonutSell"}
)
public final class StatData {
   private int currentLevel;
   private double earned;

   public StatData(int currentLevel, double earned) {
      this.currentLevel = currentLevel;
      this.earned = earned;
   }

   // $FF: synthetic method
   public StatData(int var1, double var2, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = (double)0.0F;
      }

      this(var1, var2);
   }

   public final int getCurrentLevel() {
      return this.currentLevel;
   }

   public final void setCurrentLevel(int <set-?>) {
      this.currentLevel = <set-?>;
   }

   public final double getEarned() {
      return this.earned;
   }

   public final void setEarned(double <set-?>) {
      this.earned = <set-?>;
   }

   public final int component1() {
      return this.currentLevel;
   }

   public final double component2() {
      return this.earned;
   }

   @NotNull
   public final StatData copy(int currentLevel, double earned) {
      return new StatData(currentLevel, earned);
   }

   // $FF: synthetic method
   public static StatData copy$default(StatData var0, int var1, double var2, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = var0.currentLevel;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.earned;
      }

      return var0.copy(var1, var2);
   }

   @NotNull
   public String toString() {
      return "StatData(currentLevel=" + this.currentLevel + ", earned=" + this.earned + ")";
   }

   public int hashCode() {
      int result = Integer.hashCode(this.currentLevel);
      result = result * 31 + Double.hashCode(this.earned);
      return result;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof StatData)) {
         return false;
      } else {
         StatData var2 = (StatData)other;
         if (this.currentLevel != var2.currentLevel) {
            return false;
         } else {
            return Double.compare(this.earned, var2.earned) == 0;
         }
      }
   }

   public StatData() {
      this(0, (double)0.0F, 3, (DefaultConstructorMarker)null);
   }
}
