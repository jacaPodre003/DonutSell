package space.hugster.sell.data;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H¦@¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H¦@¢\u0006\u0002\u0010\u0006J\u001c\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH&J \u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\tH&¨\u0006\u0012"},
   d2 = {"Lspace/hugster/sell/data/ProfileDataProvider;", "", "save", "", "profile", "Lspace/hugster/sell/data/Profile;", "(Lspace/hugster/sell/data/Profile;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "load", "createSoldItemDocument", "Lorg/json/JSONObject;", "amount", "", "price", "", "updateSoldItemDocument", "document", "documentToJson", "", "DonutSell"}
)
public interface ProfileDataProvider {
   @Nullable
   Object save(@NotNull Profile profile, @NotNull Continuation<? super Unit> $completion);

   @Nullable
   Object load(@NotNull Profile profile, @NotNull Continuation<? super Unit> $completion);

   @NotNull
   JSONObject createSoldItemDocument(int amount, double price);

   void updateSoldItemDocument(@NotNull JSONObject document, int amount, double price);

   @NotNull
   String documentToJson(@NotNull JSONObject document);

   @Metadata(
      mv = {2, 1, 0},
      k = 3,
      xi = 48
   )
   public static final class DefaultImpls {
      // $FF: synthetic method
      public static JSONObject createSoldItemDocument$default(ProfileDataProvider var0, int var1, double var2, int var4, Object var5) {
         if (var5 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createSoldItemDocument");
         } else {
            if ((var4 & 1) != 0) {
               var1 = 0;
            }

            if ((var4 & 2) != 0) {
               var2 = (double)0.0F;
            }

            return var0.createSoldItemDocument(var1, var2);
         }
      }
   }
}
