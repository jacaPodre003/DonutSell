package space.hugster.sell.database;

import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.exposed.sql.Column;
import org.jetbrains.exposed.sql.ColumnSet;
import org.jetbrains.exposed.sql.Database;
import org.jetbrains.exposed.sql.DatabaseConfig;
import org.jetbrains.exposed.sql.DatabaseConnectionAutoRegistration;
import org.jetbrains.exposed.sql.Expression;
import org.jetbrains.exposed.sql.ExpressionWithColumnType;
import org.jetbrains.exposed.sql.FieldSet;
import org.jetbrains.exposed.sql.ISqlExpressionBuilder;
import org.jetbrains.exposed.sql.Op;
import org.jetbrains.exposed.sql.QueriesKt;
import org.jetbrains.exposed.sql.QueryKt;
import org.jetbrains.exposed.sql.ResultRow;
import org.jetbrains.exposed.sql.SchemaUtils;
import org.jetbrains.exposed.sql.SqlExpressionBuilder;
import org.jetbrains.exposed.sql.Table;
import org.jetbrains.exposed.sql.Transaction;
import org.jetbrains.exposed.sql.Database.Companion;
import org.jetbrains.exposed.sql.statements.DeleteStatement;
import org.jetbrains.exposed.sql.statements.InsertStatement;
import org.jetbrains.exposed.sql.statements.UpdateBuilder;
import org.jetbrains.exposed.sql.statements.UpdateStatement;
import org.jetbrains.exposed.sql.transactions.ThreadLocalTransactionManagerKt;
import org.jetbrains.exposed.sql.transactions.TransactionManager;
import org.jetbrains.exposed.sql.transactions.experimental.SuspendedKt;
import org.json.JSONObject;
import space.hugster.sell.data.Profile;
import space.hugster.sell.data.ProfileDataProvider;
import space.hugster.sell.data.StatData;
import space.hugster.sell.database.tables.ProfileSoldItemsTable;
import space.hugster.sell.database.tables.ProfileStatsTable;
import space.hugster.sell.database.tables.ProfileTable;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\bJ\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\rJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J \u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0010H\u0016¨\u0006\u0018"},
   d2 = {"Lspace/hugster/sell/database/MySQLDatabase;", "Lspace/hugster/sell/data/ProfileDataProvider;", "host", "", "database", "user", "password", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "save", "", "profile", "Lspace/hugster/sell/data/Profile;", "(Lspace/hugster/sell/data/Profile;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "load", "createSoldItemDocument", "Lorg/json/JSONObject;", "amount", "", "price", "", "updateSoldItemDocument", "document", "documentToJson", "DonutSell"}
)
public final class MySQLDatabase implements ProfileDataProvider {
   public MySQLDatabase(@NotNull String host, @NotNull String database, @NotNull String user, @NotNull String password) {
      Intrinsics.checkNotNullParameter(host, "host");
      Intrinsics.checkNotNullParameter(database, "database");
      Intrinsics.checkNotNullParameter(user, "user");
      Intrinsics.checkNotNullParameter(password, "password");
      super();
      Companion.connect$default(Database.Companion, "jdbc:mysql://" + host + "/" + database, "com.mysql.cj.jdbc.Driver", user, password, (Function1)null, (DatabaseConfig)null, (DatabaseConnectionAutoRegistration)null, (Function1)null, 240, (Object)null);
      ThreadLocalTransactionManagerKt.transaction$default((Database)null, MySQLDatabase::_init_$lambda$0, 1, (Object)null);
   }

   @Nullable
   public Object save(@NotNull final Profile profile, @NotNull Continuation<? super Unit> $completion) {
      Object var10000 = SuspendedKt.newSuspendedTransaction$default((CoroutineContext)Dispatchers.getIO(), (Database)null, (Integer)null, (Boolean)null, new Function2<Transaction, Continuation<? super Unit>, Object>((Continuation)null) {
         int label;

         public final Object invokeSuspend(Object $result) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
               case 0:
                  ResultKt.throwOnFailure($result);
                  Table $this$insertIgnore$iv = ProfileTable.INSTANCE;
                  Profile var3 = profile;
                  int $i$f$insertIgnore = 0;
                  InsertStatement $this$insertIgnore_u24lambda_u2410$iv = new InsertStatement($this$insertIgnore$iv, true);
                  int var7 = 0;
                  UpdateBuilder it = (UpdateBuilder)$this$insertIgnore_u24lambda_u2410$iv;
                  ProfileTable $this$invokeSuspend_u24lambda_u240 = (ProfileTable)$this$insertIgnore$iv;
                  int var10 = 0;
                  it.set($this$invokeSuspend_u24lambda_u240.getUuid(), var3.getUuid().toString());
                  it.set($this$invokeSuspend_u24lambda_u240.getUsername(), var3.getUsername());
                  $this$insertIgnore_u24lambda_u2410$iv.execute(TransactionManager.Companion.current());
                  $this$insertIgnore$iv = ProfileTable.INSTANCE;
                  var3 = profile;
                  Profile $this$insertIgnore_u24lambda_u2410$iv = profile;
                  Integer limit$iv = null;
                  int $i$f$update = 0;
                  ColumnSet var10000 = (ColumnSet)$this$insertIgnore$iv;
                  SqlExpressionBuilder $this$invokeSuspend_u24lambda_u241 = SqlExpressionBuilder.INSTANCE;
                  ColumnSet var22 = var10000;
                  int var55 = 0;
                  Op var24 = $this$invokeSuspend_u24lambda_u241.eq((ExpressionWithColumnType)ProfileTable.INSTANCE.getUuid(), var3.getUuid().toString());
                  UpdateStatement query$iv = new UpdateStatement(var22, limit$iv, var24);
                  ProfileTable $this$invokeSuspend_u24lambda_u242 = (ProfileTable)$this$insertIgnore$iv;
                  var10 = 0;
                  query$iv.set($this$invokeSuspend_u24lambda_u242.getUsername(), $this$insertIgnore_u24lambda_u2410$iv.getUsername());
                  Integer var72 = (Integer)query$iv.execute(TransactionManager.Companion.current());
                  if (var72 != null) {
                     var72;
                  }

                  $this$insertIgnore$iv = ProfileStatsTable.INSTANCE;
                  Profile limit$ivxxxx = profile;
                  Integer limit$iv = null;
                  int $i$f$deleteWhere = 0;
                  DeleteStatement.Companion var73 = DeleteStatement.Companion;
                  Transaction var10001 = TransactionManager.Companion.current();
                  ISqlExpressionBuilder var10004 = (ISqlExpressionBuilder)SqlExpressionBuilder.INSTANCE;
                  ProfileStatsTable $this$invokeSuspend_u24lambda_u243 = (ProfileStatsTable)$this$insertIgnore$iv;
                  Transaction var21 = var10001;
                  DeleteStatement.Companion var20 = var73;
                  var55 = 0;
                  Op var23 = SqlExpressionBuilder.INSTANCE.eq((ExpressionWithColumnType)$this$invokeSuspend_u24lambda_u243.getProfileUuid(), limit$ivxxxx.getUuid().toString());
                  var20.where(var21, $this$insertIgnore$iv, var23, false, limit$iv);
                  Map $this$update_u24default$ivxx = profile.getStats();
                  Profile limit$ivxx = profile;
                  int $i$f$forEach = 0;

                  for(Map.Entry element$iv : $this$update_u24default$ivxx.entrySet()) {
                     var55 = 0;
                     String type = (String)element$iv.getKey();
                     StatData data = (StatData)element$iv.getValue();
                     Table $this$insert$iv = ProfileStatsTable.INSTANCE;
                     int $i$f$insert = 0;
                     InsertStatement $this$insert_u24lambda_u243$iv = new InsertStatement($this$insert$iv, false, 2, (DefaultConstructorMarker)null);
                     int var15 = 0;
                     ProfileStatsTable $this$invokeSuspend_u24lambda_u245_u24lambda_u244 = (ProfileStatsTable)$this$insert$iv;
                     int var18 = 0;
                     $this$insert_u24lambda_u243$iv.set($this$invokeSuspend_u24lambda_u245_u24lambda_u244.getProfileUuid(), limit$ivxx.getUuid().toString());
                     $this$insert_u24lambda_u243$iv.set(ProfileStatsTable.INSTANCE.getType(), type);
                     $this$insert_u24lambda_u243$iv.set($this$invokeSuspend_u24lambda_u245_u24lambda_u244.getCurrentLevel(), Boxing.boxInt(data.getCurrentLevel()));
                     $this$insert_u24lambda_u243$iv.set($this$invokeSuspend_u24lambda_u245_u24lambda_u244.getEarned(), Boxing.boxDouble(data.getEarned()));
                     $this$insert_u24lambda_u243$iv.execute(TransactionManager.Companion.current());
                  }

                  Table $this$deleteWhere_u24default$iv = ProfileSoldItemsTable.INSTANCE;
                  Profile var41 = profile;
                  Integer limit$iv = null;
                  $i$f$deleteWhere = 0;
                  var73 = DeleteStatement.Companion;
                  var10001 = TransactionManager.Companion.current();
                  var10004 = (ISqlExpressionBuilder)SqlExpressionBuilder.INSTANCE;
                  ProfileSoldItemsTable $this$invokeSuspend_u24lambda_u246 = (ProfileSoldItemsTable)$this$deleteWhere_u24default$iv;
                  var21 = var10001;
                  var20 = var73;
                  var55 = 0;
                  var23 = SqlExpressionBuilder.INSTANCE.eq((ExpressionWithColumnType)$this$invokeSuspend_u24lambda_u246.getProfileUuid(), var41.getUuid().toString());
                  var20.where(var21, $this$deleteWhere_u24default$iv, var23, false, limit$iv);
                  Map $this$deleteWhere_u24default$ivx = profile.getSellHistory().getSoldItems();
                  Profile var37 = profile;
                  MySQLDatabase var42 = MySQLDatabase.this;
                  $i$f$deleteWhere = 0;

                  for(Map.Entry element$iv : $this$deleteWhere_u24default$ivx.entrySet()) {
                     int type = 0;
                     String material = (String)element$iv.getKey();
                     JSONObject document = (JSONObject)element$iv.getValue();
                     Table $this$insert$iv = ProfileSoldItemsTable.INSTANCE;
                     int $i$f$insert = 0;
                     InsertStatement $this$insert_u24lambda_u243$iv = new InsertStatement($this$insert$iv, false, 2, (DefaultConstructorMarker)null);
                     int itxx = 0;
                     ProfileSoldItemsTable $this$invokeSuspend_u24lambda_u248_u24lambda_u247 = (ProfileSoldItemsTable)$this$insert$iv;
                     int var19 = 0;
                     $this$insert_u24lambda_u243$iv.set($this$invokeSuspend_u24lambda_u248_u24lambda_u247.getProfileUuid(), var37.getUuid().toString());
                     $this$insert_u24lambda_u243$iv.set(ProfileSoldItemsTable.INSTANCE.getMaterial(), material);
                     Column var76 = $this$invokeSuspend_u24lambda_u248_u24lambda_u247.getData();
                     Intrinsics.checkNotNull(document, "null cannot be cast to non-null type org.json.JSONObject");
                     $this$insert_u24lambda_u243$iv.set(var76, var42.documentToJson(document));
                     $this$insert_u24lambda_u243$iv.execute(TransactionManager.Companion.current());
                  }

                  return Unit.INSTANCE;
               default:
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
         }

         public final Continuation<Unit> create(Object value, Continuation<?> $completion) {
            return (Continuation)(new <anonymous constructor>($completion));
         }

         public final Object invoke(Transaction p1, Continuation<? super Unit> p2) {
            return ((<undefinedtype>)this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
         }
      }, $completion, 14, (Object)null);
      return var10000 == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? var10000 : Unit.INSTANCE;
   }

   @Nullable
   public Object load(@NotNull final Profile profile, @NotNull Continuation<? super Unit> $completion) {
      Object var10000 = SuspendedKt.newSuspendedTransaction$default((CoroutineContext)Dispatchers.getIO(), (Database)null, (Integer)null, (Boolean)null, new Function2<Transaction, Continuation<? super Unit>, Object>((Continuation)null) {
         int label;

         public final Object invokeSuspend(Object $result) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
               case 0:
                  ResultKt.throwOnFailure($result);
                  ResultRow it = (ResultRow)CollectionsKt.firstOrNull((Iterable)QueryKt.andWhere(QueriesKt.selectAll((FieldSet)ProfileTable.INSTANCE), <undefinedtype>::invokeSuspend$lambda$0));
                  if (it != null) {
                     Profile var4 = profile;
                     int var6 = 0;
                     var4.setUsername((String)it.get((Expression)ProfileTable.INSTANCE.getUsername()));
                  }

                  Iterable $this$forEach$iv = (Iterable)QueryKt.andWhere(QueriesKt.selectAll((FieldSet)ProfileStatsTable.INSTANCE), <undefinedtype>::invokeSuspend$lambda$2);
                  Profile var3 = profile;
                  int $i$f$forEach = 0;

                  for(Object element$iv : $this$forEach$iv) {
                     ResultRow it = (ResultRow)element$iv;
                     int var8 = 0;
                     String type = (String)it.get((Expression)ProfileStatsTable.INSTANCE.getType());
                     var3.getStats().put(type, new StatData(((Number)it.get((Expression)ProfileStatsTable.INSTANCE.getCurrentLevel())).intValue(), ((Number)it.get((Expression)ProfileStatsTable.INSTANCE.getEarned())).doubleValue()));
                  }

                  $this$forEach$iv = (Iterable)QueryKt.andWhere(QueriesKt.selectAll((FieldSet)ProfileSoldItemsTable.INSTANCE), <undefinedtype>::invokeSuspend$lambda$4);
                  var3 = profile;
                  $i$f$forEach = 0;

                  for(Object element$iv : $this$forEach$iv) {
                     ResultRow it = (ResultRow)element$iv;
                     int var20 = 0;
                     String material = (String)it.get((Expression)ProfileSoldItemsTable.INSTANCE.getMaterial());
                     String jsonData = (String)it.get((Expression)ProfileSoldItemsTable.INSTANCE.getData());
                     var3.getSellHistory().getSoldItems().put(material, new JSONObject(jsonData));
                  }

                  return Unit.INSTANCE;
               default:
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
         }

         public final Continuation<Unit> create(Object value, Continuation<?> $completion) {
            return (Continuation)(new <anonymous constructor>($completion));
         }

         public final Object invoke(Transaction p1, Continuation<? super Unit> p2) {
            return ((<undefinedtype>)this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
         }

         private static final Op invokeSuspend$lambda$0(Profile $profile, SqlExpressionBuilder $this$andWhere) {
            return $this$andWhere.eq((ExpressionWithColumnType)ProfileTable.INSTANCE.getUuid(), $profile.getUuid().toString());
         }

         private static final Op invokeSuspend$lambda$2(Profile $profile, SqlExpressionBuilder $this$andWhere) {
            return $this$andWhere.eq((ExpressionWithColumnType)ProfileStatsTable.INSTANCE.getProfileUuid(), $profile.getUuid().toString());
         }

         private static final Op invokeSuspend$lambda$4(Profile $profile, SqlExpressionBuilder $this$andWhere) {
            return $this$andWhere.eq((ExpressionWithColumnType)ProfileSoldItemsTable.INSTANCE.getProfileUuid(), $profile.getUuid().toString());
         }
      }, $completion, 14, (Object)null);
      return var10000 == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? var10000 : Unit.INSTANCE;
   }

   @NotNull
   public JSONObject createSoldItemDocument(int amount, double price) {
      JSONObject $this$createSoldItemDocument_u24lambda_u241 = new JSONObject();
      int var6 = 0;
      $this$createSoldItemDocument_u24lambda_u241.put("amount", amount);
      $this$createSoldItemDocument_u24lambda_u241.put("price", price);
      return $this$createSoldItemDocument_u24lambda_u241;
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

   private static final Unit _init_$lambda$0(Transaction $this$transaction) {
      Intrinsics.checkNotNullParameter($this$transaction, "$this$transaction");
      SchemaUtils var10000 = SchemaUtils.INSTANCE;
      Table[] var1 = new Table[]{ProfileTable.INSTANCE, ProfileStatsTable.INSTANCE, ProfileSoldItemsTable.INSTANCE};
      SchemaUtils.create$default(var10000, var1, false, 2, (Object)null);
      return Unit.INSTANCE;
   }
}
