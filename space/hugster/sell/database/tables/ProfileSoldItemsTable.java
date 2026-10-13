package space.hugster.sell.database.tables;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.exposed.sql.Column;
import org.jetbrains.exposed.sql.Table;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0018\u0010\r\u001a\u00060\u000eR\u00020\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"},
   d2 = {"Lspace/hugster/sell/database/tables/ProfileSoldItemsTable;", "Lorg/jetbrains/exposed/sql/Table;", "<init>", "()V", "profileUuid", "Lorg/jetbrains/exposed/sql/Column;", "", "getProfileUuid", "()Lorg/jetbrains/exposed/sql/Column;", "material", "getMaterial", "data", "getData", "primaryKey", "Lorg/jetbrains/exposed/sql/Table$PrimaryKey;", "getPrimaryKey", "()Lorg/jetbrains/exposed/sql/Table$PrimaryKey;", "DonutSell"}
)
public final class ProfileSoldItemsTable extends Table {
   @NotNull
   public static final ProfileSoldItemsTable INSTANCE = new ProfileSoldItemsTable();
   @NotNull
   private static final Column<String> profileUuid;
   @NotNull
   private static final Column<String> material;
   @NotNull
   private static final Column<String> data;
   @NotNull
   private static final Table.PrimaryKey primaryKey;

   private ProfileSoldItemsTable() {
      super("profile_sold_items");
   }

   @NotNull
   public final Column<String> getProfileUuid() {
      return profileUuid;
   }

   @NotNull
   public final Column<String> getMaterial() {
      return material;
   }

   @NotNull
   public final Column<String> getData() {
      return data;
   }

   @NotNull
   public Table.PrimaryKey getPrimaryKey() {
      return primaryKey;
   }

   static {
      profileUuid = INSTANCE.references(Table.varchar$default(INSTANCE, "profile_uuid", 36, (String)null, 4, (Object)null), ProfileTable.INSTANCE.getUuid());
      material = Table.varchar$default(INSTANCE, "material", 50, (String)null, 4, (Object)null);
      data = Table.text$default(INSTANCE, "data", (String)null, false, 6, (Object)null);
      Table var10002 = INSTANCE;
      ProfileSoldItemsTable var10003 = INSTANCE;
      Column var1 = profileUuid;
      Column[] var0 = new Column[1];
      ProfileSoldItemsTable var10006 = INSTANCE;
      var0[0] = material;
      primaryKey = new Table.PrimaryKey(var10002, var1, var0, "PK_ProfileSoldItems");
   }
}
