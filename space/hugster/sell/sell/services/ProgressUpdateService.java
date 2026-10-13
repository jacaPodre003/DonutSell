package space.hugster.sell.sell.services;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.data.LevelSystem;
import space.hugster.sell.data.Profile;
import space.hugster.sell.data.StatData;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0006\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\"\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¨\u0006\f"},
   d2 = {"Lspace/hugster/sell/sell/services/ProgressUpdateService;", "", "<init>", "()V", "updatePlayerStats", "", "profile", "Lspace/hugster/sell/data/Profile;", "statCategoryEarnings", "", "", "", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nProgressUpdateService.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProgressUpdateService.kt\nspace/hugster/sell/sell/services/ProgressUpdateService\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,21:1\n381#2,7:22\n*S KotlinDebug\n*F\n+ 1 ProgressUpdateService.kt\nspace/hugster/sell/sell/services/ProgressUpdateService\n*L\n12#1:22,7\n*E\n"})
public final class ProgressUpdateService {
   public final void updatePlayerStats(@NotNull Profile profile, @NotNull Map<String, Double> statCategoryEarnings) {
      Intrinsics.checkNotNullParameter(profile, "profile");
      Intrinsics.checkNotNullParameter(statCategoryEarnings, "statCategoryEarnings");

      for(Map.Entry var4 : statCategoryEarnings.entrySet()) {
         String category = (String)var4.getKey();
         double earnings = ((Number)var4.getValue()).doubleValue();
         Map $this$getOrPut$iv = profile.getStats();
         int $i$f$getOrPut = 0;
         Object value$iv = $this$getOrPut$iv.get(category);
         Object var10000;
         if (value$iv == null) {
            int var12 = 0;
            Object answer$iv = new StatData(0, (double)0.0F, 3, (DefaultConstructorMarker)null);
            $this$getOrPut$iv.put(category, answer$iv);
            var10000 = answer$iv;
         } else {
            var10000 = value$iv;
         }

         StatData statData = (StatData)var10000;
         statData.setEarned(statData.getEarned() + earnings);

         while(statData.getCurrentLevel() < LevelSystem.INSTANCE.getMaxLevel() && statData.getEarned() >= LevelSystem.INSTANCE.getNextLevelGoal(statData.getCurrentLevel())) {
            int var13 = statData.getCurrentLevel();
            statData.setCurrentLevel(var13 + 1);
         }
      }

   }
}
