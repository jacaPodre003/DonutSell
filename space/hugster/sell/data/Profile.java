package space.hugster.sell.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.hugster.sell.DonutSell;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\tJ\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020$HÖ\u0001J\t\u0010%\u001a\u00020\tHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR&\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00100\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006&"},
   d2 = {"Lspace/hugster/sell/data/Profile;", "", "uuid", "Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;)V", "getUuid", "()Ljava/util/UUID;", "username", "", "getUsername", "()Ljava/lang/String;", "setUsername", "(Ljava/lang/String;)V", "stats", "", "Lspace/hugster/sell/data/StatData;", "getStats", "()Ljava/util/Map;", "setStats", "(Ljava/util/Map;)V", "sellHistory", "Lspace/hugster/sell/data/ProfileHistory;", "getSellHistory", "()Lspace/hugster/sell/data/ProfileHistory;", "setSellHistory", "(Lspace/hugster/sell/data/ProfileHistory;)V", "getProgressPerType", "", "type", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "DonutSell"}
)
public final class Profile {
   @NotNull
   private final UUID uuid;
   @NotNull
   private String username;
   @NotNull
   private Map<String, StatData> stats;
   @NotNull
   private ProfileHistory sellHistory;

   public Profile(@NotNull UUID uuid) {
      Intrinsics.checkNotNullParameter(uuid, "uuid");
      super();
      this.uuid = uuid;
      this.username = "";
      this.stats = (Map)(new LinkedHashMap());
      this.sellHistory = new ProfileHistory(DonutSell.Companion.getINSTANCE().getDatabase());
   }

   @NotNull
   public final UUID getUuid() {
      return this.uuid;
   }

   @NotNull
   public final String getUsername() {
      return this.username;
   }

   public final void setUsername(@NotNull String <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.username = <set-?>;
   }

   @NotNull
   public final Map<String, StatData> getStats() {
      return this.stats;
   }

   public final void setStats(@NotNull Map<String, StatData> <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.stats = <set-?>;
   }

   @NotNull
   public final ProfileHistory getSellHistory() {
      return this.sellHistory;
   }

   public final void setSellHistory(@NotNull ProfileHistory <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.sellHistory = <set-?>;
   }

   public final double getProgressPerType(@NotNull String type) {
      Intrinsics.checkNotNullParameter(type, "type");
      StatData statData = (StatData)this.stats.get(type);
      return statData != null ? statData.getEarned() : (double)0.0F;
   }

   @NotNull
   public final UUID component1() {
      return this.uuid;
   }

   @NotNull
   public final Profile copy(@NotNull UUID uuid) {
      Intrinsics.checkNotNullParameter(uuid, "uuid");
      return new Profile(uuid);
   }

   // $FF: synthetic method
   public static Profile copy$default(Profile var0, UUID var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = var0.uuid;
      }

      return var0.copy(var1);
   }

   @NotNull
   public String toString() {
      return "Profile(uuid=" + this.uuid + ")";
   }

   public int hashCode() {
      return this.uuid.hashCode();
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Profile)) {
         return false;
      } else {
         Profile var2 = (Profile)other;
         return Intrinsics.areEqual(this.uuid, var2.uuid);
      }
   }
}
