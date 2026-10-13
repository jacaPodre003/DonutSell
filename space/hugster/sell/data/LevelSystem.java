package space.hugster.sell.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.DonutSell;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0011\u001a\u00020\nJ\u0006\u0010\u0012\u001a\u00020\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"},
   d2 = {"Lspace/hugster/sell/data/LevelSystem;", "", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "levelGoals", "", "", "progressBarChar", "", "initialize", "", "getMultiplierForLevel", "currentLevel", "", "getNextLevelGoal", "getProgressBarChar", "getMaxLevel", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nLevelSystem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LevelSystem.kt\nspace/hugster/sell/data/LevelSystem\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,36:1\n1557#2:37\n1628#2,3:38\n*S KotlinDebug\n*F\n+ 1 LevelSystem.kt\nspace/hugster/sell/data/LevelSystem\n*L\n14#1:37\n14#1:38,3\n*E\n"})
public final class LevelSystem {
   @NotNull
   public static final LevelSystem INSTANCE = new LevelSystem();
   @NotNull
   private static final DonutSell plugin;
   private static List<Double> levelGoals;
   @NotNull
   private static String progressBarChar;

   private LevelSystem() {
   }

   public final void initialize() {
      FileConfiguration config = plugin.getMainConfig().getConfig();
      List var10000 = config.getDoubleList("SETTINGS.LEVEL");
      Intrinsics.checkNotNullExpressionValue(var10000, "getDoubleList(...)");
      Iterable $this$map$iv = (Iterable)var10000;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         Double it = (Double)item$iv$iv;
         int var10 = 0;
         destination$iv$iv.add(it);
      }

      levelGoals = (List)destination$iv$iv;
      String var12 = config.getString("SETTINGS.PROGRESS-BAR");
      if (var12 == null) {
         var12 = "-";
      }

      progressBarChar = var12;
   }

   public final double getMultiplierForLevel(int currentLevel) {
      double var10000;
      if (currentLevel <= 0) {
         var10000 = (double)1.0F;
      } else {
         List var10001 = levelGoals;
         if (var10001 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("levelGoals");
            var10001 = null;
         }

         var10000 = currentLevel >= var10001.size() ? (double)3.0F : (double)1.0F + (double)currentLevel * 0.1;
      }

      return var10000;
   }

   public final double getNextLevelGoal(int currentLevel) {
      List var10001 = levelGoals;
      if (var10001 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("levelGoals");
         var10001 = null;
      }

      double var2;
      if (currentLevel >= var10001.size()) {
         List var10000 = levelGoals;
         if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("levelGoals");
            var10000 = null;
         }

         var2 = ((Number)CollectionsKt.last(var10000)).doubleValue();
      } else {
         List var3 = levelGoals;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("levelGoals");
            var3 = null;
         }

         var2 = ((Number)var3.get(currentLevel)).doubleValue();
      }

      return var2;
   }

   @NotNull
   public final String getProgressBarChar() {
      return progressBarChar;
   }

   public final int getMaxLevel() {
      List var10000 = levelGoals;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("levelGoals");
         var10000 = null;
      }

      return var10000.size();
   }

   static {
      plugin = DonutSell.Companion.getINSTANCE();
      progressBarChar = "-";
   }
}
