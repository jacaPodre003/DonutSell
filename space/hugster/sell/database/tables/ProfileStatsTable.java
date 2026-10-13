package space.hugster.sell.database.tables;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.exposed.sql.Column;
import org.jetbrains.exposed.sql.Table;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\bR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bR\u0018\u0010\u0011\u001a\u00060\u0012R\u00020\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"},
   d2 = {"Lspace/hugster/sell/database/tables/ProfileStatsTable;", "Lorg/jetbrains/exposed/sql/Table;", "<init>", "()V", "profileUuid", "Lorg/jetbrains/exposed/sql/Column;", "", "getProfileUuid", "()Lorg/jetbrains/exposed/sql/Column;", "type", "getType", "currentLevel", "", "getCurrentLevel", "earned", "", "getEarned", "primaryKey", "Lorg/jetbrains/exposed/sql/Table$PrimaryKey;", "getPrimaryKey", "()Lorg/jetbrains/exposed/sql/Table$PrimaryKey;", "DonutSell"}
)
public final class ProfileStatsTable extends Table {
   @NotNull
   public static final ProfileStatsTable INSTANCE = new ProfileStatsTable();
   @NotNull
   private static final Column<String> profileUuid;
   @NotNull
   private static final Column<String> type;
   @NotNull
   private static final Column<Integer> currentLevel;
   @NotNull
   private static final Column<Double> earned;
   @NotNull
   private static final Table.PrimaryKey primaryKey;

   private ProfileStatsTable() {
      super("profile_stats");
   }

   @NotNull
   public final Column<String> getProfileUuid() {
      return profileUuid;
   }

   @NotNull
   public final Column<String> getType() {
      return type;
   }

   @NotNull
   public final Column<Integer> getCurrentLevel() {
      return currentLevel;
   }

   @NotNull
   public final Column<Double> getEarned() {
      return earned;
   }

   @NotNull
   public Table.PrimaryKey getPrimaryKey() {
      return primaryKey;
   }

   static {
      profileUuid = INSTANCE.references(Table.varchar$default(INSTANCE, "profile_uuid", 36, (String)null, 4, (Object)null), ProfileTable.INSTANCE.getUuid());
      type = Table.varchar$default(INSTANCE, "type", 50, (String)null, 4, (Object)null);
      currentLevel = Table.integer$default(INSTANCE, "current_level", (String)null, 2, (Object)null);
      earned = INSTANCE.double("earned");
      Table var10002 = INSTANCE;
      ProfileStatsTable var10003 = INSTANCE;
      Column var1 = profileUuid;
      Column[] var0 = new Column[1];
      ProfileStatsTable var10006 = INSTANCE;
      var0[0] = type;
      primaryKey = new Table.PrimaryKey(var10002, var1, var0, "PK_ProfileStats");
   }
}
