package space.hugster.sell.data;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\"\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\n2\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0015"},
   d2 = {"Lspace/hugster/sell/data/ProfileHistory;", "", "dataProvider", "Lspace/hugster/sell/data/ProfileDataProvider;", "<init>", "(Lspace/hugster/sell/data/ProfileDataProvider;)V", "getDataProvider", "()Lspace/hugster/sell/data/ProfileDataProvider;", "soldItems", "", "", "Lorg/json/JSONObject;", "getSoldItems", "()Ljava/util/Map;", "addOrUpdateSoldItem", "", "materialName", "amount", "", "price", "", "DonutSell"}
)
public final class ProfileHistory {
   @NotNull
   private final ProfileDataProvider dataProvider;
   @NotNull
   private final Map<String, JSONObject> soldItems;

   public ProfileHistory(@NotNull ProfileDataProvider dataProvider) {
      Intrinsics.checkNotNullParameter(dataProvider, "dataProvider");
      super();
      this.dataProvider = dataProvider;
      this.soldItems = (Map)(new LinkedHashMap());
   }

   @NotNull
   public final ProfileDataProvider getDataProvider() {
      return this.dataProvider;
   }

   @NotNull
   public final Map<String, JSONObject> getSoldItems() {
      return this.soldItems;
   }

   public final void addOrUpdateSoldItem(@NotNull String materialName, int amount, double price) {
      Intrinsics.checkNotNullParameter(materialName, "materialName");
      JSONObject var10000 = (JSONObject)this.soldItems.get(materialName);
      if (var10000 == null) {
         var10000 = this.dataProvider.createSoldItemDocument(0, (double)0.0F);
      }

      JSONObject currentData = var10000;
      this.dataProvider.updateSoldItemDocument(currentData, amount, price);
      this.soldItems.put(materialName, currentData);
   }

   // $FF: synthetic method
   public static void addOrUpdateSoldItem$default(ProfileHistory var0, String var1, int var2, double var3, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = (double)0.0F;
      }

      var0.addOrUpdateSoldItem(var1, var2, var3);
   }
}
