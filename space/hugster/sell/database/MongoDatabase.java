package space.hugster.sell.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import space.hugster.sell.data.Profile;
import space.hugster.sell.data.ProfileDataProvider;
import space.hugster.sell.data.StatData;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0096@¢\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0096@¢\u0006\u0002\u0010\u0012J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J \u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0015H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lspace/hugster/sell/database/MongoDatabase;", "Lspace/hugster/sell/data/ProfileDataProvider;", "uri", "", "database", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", "mongoClient", "Lcom/mongodb/client/MongoClient;", "db", "Lcom/mongodb/client/MongoDatabase;", "profileCollection", "Lcom/mongodb/client/MongoCollection;", "Lorg/bson/Document;", "save", "", "profile", "Lspace/hugster/sell/data/Profile;", "(Lspace/hugster/sell/data/Profile;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "load", "createSoldItemDocument", "Lorg/json/JSONObject;", "amount", "", "price", "", "updateSoldItemDocument", "document", "documentToJson", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nMongoDatabase.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MongoDatabase.kt\nspace/hugster/sell/database/MongoDatabase\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,91:1\n216#2,2:92\n216#2,2:94\n216#2,2:96\n216#2,2:98\n*S KotlinDebug\n*F\n+ 1 MongoDatabase.kt\nspace/hugster/sell/database/MongoDatabase\n*L\n28#1:92,2\n37#1:94,2\n56#1:96,2\n66#1:98,2\n*E\n"})
public final class MongoDatabase implements ProfileDataProvider {
   @NotNull
   private final MongoClient mongoClient;
   @NotNull
   private final com.mongodb.client.MongoDatabase db;
   @NotNull
   private final MongoCollection<Document> profileCollection;

   public MongoDatabase(@NotNull String uri, @NotNull String database) {
      Intrinsics.checkNotNullParameter(uri, "uri");
      Intrinsics.checkNotNullParameter(database, "database");
      super();
      MongoClient var10001 = MongoClients.create(uri);
      Intrinsics.checkNotNullExpressionValue(var10001, "create(...)");
      this.mongoClient = var10001;
      com.mongodb.client.MongoDatabase var3 = this.mongoClient.getDatabase(database);
      Intrinsics.checkNotNullExpressionValue(var3, "getDatabase(...)");
      this.db = var3;
      MongoCollection var4 = this.db.getCollection("profiles");
      Intrinsics.checkNotNullExpressionValue(var4, "getCollection(...)");
      this.profileCollection = var4;
   }

   @Nullable
   public Object save(@NotNull Profile profile, @NotNull Continuation<? super Unit> $completion) {
      Document $this$save_u24lambda_u245 = new Document();
      int var6 = 0;
      $this$save_u24lambda_u245.append("uuid", profile.getUuid().toString());
      $this$save_u24lambda_u245.append("username", profile.getUsername());
      Document var7 = new Document();
      Document $this$save_u24lambda_u245_u24lambda_u242 = var7;
      String var9 = "stats";
      int var11 = 0;
      Map $this$forEach$iv = profile.getStats();
      int $i$f$forEach = 0;

      for(Map.Entry element$iv : $this$forEach$iv.entrySet()) {
         int var17 = 0;
         String key = (String)element$iv.getKey();
         StatData statData = (StatData)element$iv.getValue();
         Document var20 = new Document();
         int var24 = 0;
         var20.append("currentLevel", Boxing.boxInt(statData.getCurrentLevel()));
         var20.append("earned", Boxing.boxDouble(statData.getEarned()));
         Unit var25 = Unit.INSTANCE;
         $this$save_u24lambda_u245_u24lambda_u242.append(key, var20);
      }

      Unit var26 = Unit.INSTANCE;
      $this$save_u24lambda_u245.append(var9, var7);
      var7 = new Document();
      $this$save_u24lambda_u245_u24lambda_u242 = var7;
      var9 = "sellHistory";
      var11 = 0;
      $this$forEach$iv = profile.getSellHistory().getSoldItems();
      $i$f$forEach = 0;

      for(Map.Entry element$iv : $this$forEach$iv.entrySet()) {
         int var35 = 0;
         String material = (String)element$iv.getKey();
         JSONObject jsonObj = (JSONObject)element$iv.getValue();
         $this$save_u24lambda_u245_u24lambda_u242.append(material, Document.parse(jsonObj.toString()));
      }

      var26 = Unit.INSTANCE;
      $this$save_u24lambda_u245.append(var9, var7);
      this.profileCollection.replaceOne(Filters.eq("uuid", profile.getUuid().toString()), $this$save_u24lambda_u245, (new ReplaceOptions()).upsert(true));
      return Unit.INSTANCE;
   }

   @Nullable
   public Object load(@NotNull Profile profile, @NotNull Continuation<? super Unit> $completion) {
      FindIterable var10000 = this.profileCollection.find(Filters.eq("uuid", profile.getUuid().toString()));
      Intrinsics.checkNotNullExpressionValue(var10000, "find(...)");
      Document var31 = (Document)CollectionsKt.firstOrNull((Iterable)var10000);
      if (var31 != null) {
         Document $this$load_u24lambda_u2411 = var31;
         int var5 = 0;
         Profile $this$load_u24lambda_u2411_u24lambda_u2410 = profile;
         int var8 = 0;
         profile.setUsername($this$load_u24lambda_u2411.getString("username"));
         var31 = (Document)$this$load_u24lambda_u2411.get("stats", Document.class);
         if (var31 != null) {
            Document statsDoc = var31;
            int var10 = 0;
            Map $this$forEach$iv = (Map)statsDoc;
            int $i$f$forEach = 0;

            for(Map.Entry element$iv : $this$forEach$iv.entrySet()) {
               int var16 = 0;
               String key = (String)element$iv.getKey();
               Object value = element$iv.getValue();
               Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.bson.Document");
               Document data = (Document)value;
               Map var20 = $this$load_u24lambda_u2411_u24lambda_u2410.getStats();
               Integer var10002 = data.getInteger("currentLevel");
               Intrinsics.checkNotNullExpressionValue(var10002, "getInteger(...)");
               int var35 = ((Number)var10002).intValue();
               Double var10003 = data.getDouble("earned");
               Intrinsics.checkNotNullExpressionValue(var10003, "getDouble(...)");
               StatData var21 = new StatData(var35, ((Number)var10003).doubleValue());
               var20.put(key, var21);
            }
         }

         var31 = (Document)$this$load_u24lambda_u2411.get("sellHistory", Document.class);
         if (var31 != null) {
            Document soldItemsDoc = var31;
            int var23 = 0;
            Map $this$forEach$iv = (Map)soldItemsDoc;
            int $i$f$forEach = 0;

            for(Map.Entry element$iv : $this$forEach$iv.entrySet()) {
               int var28 = 0;
               String material = (String)element$iv.getKey();
               Object value = element$iv.getValue();
               Map var34 = $this$load_u24lambda_u2411_u24lambda_u2410.getSellHistory().getSoldItems();
               Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.bson.Document");
               var34.put(material, new JSONObject(((Document)value).toJson()));
            }
         }
      }

      return Unit.INSTANCE;
   }

   @NotNull
   public JSONObject createSoldItemDocument(int amount, double price) {
      JSONObject $this$createSoldItemDocument_u24lambda_u2412 = new JSONObject();
      int var6 = 0;
      $this$createSoldItemDocument_u24lambda_u2412.put("amount", amount);
      $this$createSoldItemDocument_u24lambda_u2412.put("price", price);
      return $this$createSoldItemDocument_u24lambda_u2412;
   }

   public void updateSoldItemDocument(@NotNull JSONObject document, int amount, double price) {
      Intrinsics.checkNotNullParameter(document, "document");
      int var7 = 0;
      document.put("amount", document.optInt("amount", 0) + amount);
      document.put("price", document.optDouble("price", (double)0.0F) + price);
   }

   @NotNull
   public String documentToJson(@NotNull JSONObject document) {
      Intrinsics.checkNotNullParameter(document, "document");
      String var10000 = document.toString();
      Intrinsics.checkNotNullExpressionValue(var10000, "toString(...)");
      return var10000;
   }
}
