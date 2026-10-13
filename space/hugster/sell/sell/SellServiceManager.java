package space.hugster.sell.sell;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.hugster.sell.DonutSell;
import space.hugster.sell.sell.services.ProgressUpdateService;
import space.hugster.sell.sell.services.SellProcessingService;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u001c\u001a\u00020\u001dH\u0002R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\n\u001a\u00020\u000bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006\u001f"},
   d2 = {"Lspace/hugster/sell/sell/SellServiceManager;", "", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "getPlugin", "()Lspace/hugster/sell/DonutSell;", "plugin$delegate", "Lkotlin/Lazy;", "categoryManager", "Lspace/hugster/sell/sell/ItemCategoryManager;", "getCategoryManager", "()Lspace/hugster/sell/sell/ItemCategoryManager;", "setCategoryManager", "(Lspace/hugster/sell/sell/ItemCategoryManager;)V", "sellProcessingService", "Lspace/hugster/sell/sell/services/SellProcessingService;", "getSellProcessingService", "()Lspace/hugster/sell/sell/services/SellProcessingService;", "setSellProcessingService", "(Lspace/hugster/sell/sell/services/SellProcessingService;)V", "progressUpdateService", "Lspace/hugster/sell/sell/services/ProgressUpdateService;", "getProgressUpdateService", "()Lspace/hugster/sell/sell/services/ProgressUpdateService;", "setProgressUpdateService", "(Lspace/hugster/sell/sell/services/ProgressUpdateService;)V", "initialize", "", "Companion", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nSellServiceManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SellServiceManager.kt\nspace/hugster/sell/sell/SellServiceManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,37:1\n1#2:38\n*E\n"})
public final class SellServiceManager {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final Lazy plugin$delegate;
   public ItemCategoryManager categoryManager;
   public SellProcessingService sellProcessingService;
   public ProgressUpdateService progressUpdateService;
   @Nullable
   private static volatile SellServiceManager INSTANCE;

   private SellServiceManager() {
      this.plugin$delegate = LazyKt.lazy(SellServiceManager::plugin_delegate$lambda$0);
   }

   private final DonutSell getPlugin() {
      Lazy var1 = this.plugin$delegate;
      return (DonutSell)var1.getValue();
   }

   @NotNull
   public final ItemCategoryManager getCategoryManager() {
      ItemCategoryManager var10000 = this.categoryManager;
      if (var10000 != null) {
         return var10000;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("categoryManager");
         return null;
      }
   }

   public final void setCategoryManager(@NotNull ItemCategoryManager <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.categoryManager = <set-?>;
   }

   @NotNull
   public final SellProcessingService getSellProcessingService() {
      SellProcessingService var10000 = this.sellProcessingService;
      if (var10000 != null) {
         return var10000;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("sellProcessingService");
         return null;
      }
   }

   public final void setSellProcessingService(@NotNull SellProcessingService <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.sellProcessingService = <set-?>;
   }

   @NotNull
   public final ProgressUpdateService getProgressUpdateService() {
      ProgressUpdateService var10000 = this.progressUpdateService;
      if (var10000 != null) {
         return var10000;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("progressUpdateService");
         return null;
      }
   }

   public final void setProgressUpdateService(@NotNull ProgressUpdateService <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.progressUpdateService = <set-?>;
   }

   private final void initialize() {
      ItemCategoryManager var1 = new ItemCategoryManager(this.getPlugin());
      int var3 = 0;
      var1.loadItemCategories();
      this.setCategoryManager(var1);
      this.setSellProcessingService(new SellProcessingService(this.getPlugin(), this.getCategoryManager()));
      this.setProgressUpdateService(new ProgressUpdateService());
   }

   private static final DonutSell plugin_delegate$lambda$0() {
      return DonutSell.Companion.getINSTANCE();
   }

   // $FF: synthetic method
   public static final SellServiceManager access$getINSTANCE$cp() {
      return INSTANCE;
   }

   // $FF: synthetic method
   public static final void access$setINSTANCE$cp(SellServiceManager var0) {
      INSTANCE = var0;
   }

   // $FF: synthetic method
   public static final void access$initialize(SellServiceManager $this) {
      $this.initialize();
   }

   // $FF: synthetic method
   public SellServiceManager(DefaultConstructorMarker $constructor_marker) {
      this();
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0006\u001a\u00020\u0005R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"},
      d2 = {"Lspace/hugster/sell/sell/SellServiceManager$Companion;", "", "<init>", "()V", "INSTANCE", "Lspace/hugster/sell/sell/SellServiceManager;", "getInstance", "DonutSell"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final SellServiceManager getInstance() {
         // $FF: Couldn't be decompiled
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
