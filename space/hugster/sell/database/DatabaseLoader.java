package space.hugster.sell.database;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.DonutSell;
import space.hugster.sell.data.ProfileDataProvider;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"},
   d2 = {"Lspace/hugster/sell/database/DatabaseLoader;", "", "<init>", "()V", "loadDatabase", "Lspace/hugster/sell/data/ProfileDataProvider;", "plugin", "Lspace/hugster/sell/DonutSell;", "DonutSell"}
)
public final class DatabaseLoader {
   @NotNull
   public static final DatabaseLoader INSTANCE = new DatabaseLoader();

   private DatabaseLoader() {
   }

   @NotNull
   public final ProfileDataProvider loadDatabase(@NotNull DonutSell plugin) {
      String var12;
      label63: {
         Intrinsics.checkNotNullParameter(plugin, "plugin");
         var12 = plugin.getConfig().getString("DATABASE.TYPE");
         if (var12 != null) {
            var12 = var12.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var12, "toUpperCase(...)");
            if (var12 != null) {
               break label63;
            }
         }

         var12 = "MYSQL";
      }

      ProfileDataProvider var15;
      switch (dbType) {
         case "SQLITE":
            String var20 = plugin.getConfig().getString("DATABASE.SQLITE.FILE");
            if (var20 == null) {
               var20 = "plugins/DonutSell/profiles.db";
            }

            String file = var20;
            var15 = new SQLiteDatabase(file);
            return var15;
            break;
         case "MYSQL":
            String var16 = plugin.getConfig().getString("DATABASE.MYSQL.HOST");
            if (var16 == null) {
               var16 = "localhost";
            }

            String host = var16;
            var16 = plugin.getConfig().getString("DATABASE.MYSQL.DATABASE");
            if (var16 == null) {
               var16 = "donut_sell";
            }

            String databaseName = var16;
            var16 = plugin.getConfig().getString("DATABASE.MYSQL.USER");
            if (var16 == null) {
               var16 = "root";
            }

            String user = var16;
            var16 = plugin.getConfig().getString("DATABASE.MYSQL.PASSWORD");
            if (var16 == null) {
               var16 = "password";
            }

            String password = var16;
            var15 = new MySQLDatabase(host, databaseName, user, password);
            return var15;
            break;
         case "MONGODB":
            String var13 = plugin.getConfig().getString("DATABASE.MONGODB.URI");
            if (var13 == null) {
               var13 = "localhost";
            }

            String uri = var13;
            var13 = plugin.getConfig().getString("DATABASE.MONGODB.DATABASE");
            if (var13 == null) {
               var13 = "donut_sell";
            }

            String databaseName = var13;
            var15 = new MongoDatabase(uri, databaseName);
            return var15;
      }

      String var21 = plugin.getConfig().getString("DATABASE.SQLITE.FILE");
      if (var21 == null) {
         var21 = "plugins/DonutSell/profiles.db";
      }

      String file = var21;
      var15 = new SQLiteDatabase(file);
      return var15;
   }
}
