package space.hugster.sell.database.tables;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.exposed.sql.Column;
import org.jetbrains.exposed.sql.Table;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0018\u0010\u000b\u001a\u00060\fR\u00020\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"},
   d2 = {"Lspace/hugster/sell/database/tables/ProfileTable;", "Lorg/jetbrains/exposed/sql/Table;", "<init>", "()V", "uuid", "Lorg/jetbrains/exposed/sql/Column;", "", "getUuid", "()Lorg/jetbrains/exposed/sql/Column;", "username", "getUsername", "primaryKey", "Lorg/jetbrains/exposed/sql/Table$PrimaryKey;", "getPrimaryKey", "()Lorg/jetbrains/exposed/sql/Table$PrimaryKey;", "DonutSell"}
)
public final class ProfileTable extends Table {
   @NotNull
   public static final ProfileTable INSTANCE = new ProfileTable();
   @NotNull
   private static final Column<String> uuid;
   @NotNull
   private static final Column<String> username;
   @NotNull
   private static final Table.PrimaryKey primaryKey;

   private ProfileTable() {
      super("profiles");
   }

   @NotNull
   public final Column<String> getUuid() {
      return uuid;
   }

   @NotNull
   public final Column<String> getUsername() {
      return username;
   }

   @NotNull
   public Table.PrimaryKey getPrimaryKey() {
      return primaryKey;
   }

   static {
      uuid = Table.varchar$default(INSTANCE, "uuid", 36, (String)null, 4, (Object)null);
      username = Table.varchar$default(INSTANCE, "username", 50, (String)null, 4, (Object)null);
      Table var10002 = INSTANCE;
      ProfileTable var10003 = INSTANCE;
      primaryKey = new Table.PrimaryKey(var10002, uuid, new Column[0], (String)null, 4, (DefaultConstructorMarker)null);
   }
}
