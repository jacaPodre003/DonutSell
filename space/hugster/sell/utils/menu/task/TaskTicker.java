package space.hugster.sell.utils.menu.task;

import com.cjcrafter.foliascheduler.FoliaCompatibility;
import com.cjcrafter.foliascheduler.ServerImplementation;
import com.cjcrafter.foliascheduler.TaskImplementation;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.hugster.sell.DonutSell;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u0000 \"2\u00020\u0001:\u0002\"#B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\b\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0019H&J\b\u0010\u001b\u001a\u00020\u0019H\u0016J\u0006\u0010\u001c\u001a\u00020\u0019J\b\u0010\u001d\u001a\u00020\u001eH&J\b\u0010\u001f\u001a\u00020\u0003H&J\b\u0010 \u001a\u00020\u0019H\u0002J\b\u0010!\u001a\u00020\u0019H\u0002R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0014\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"},
   d2 = {"Lspace/hugster/sell/utils/menu/task/TaskTicker;", "", "delay", "", "period", "async", "", "<init>", "(IIZ)V", "plugin", "Lspace/hugster/sell/DonutSell;", "getPlugin", "()Lspace/hugster/sell/DonutSell;", "plugin$delegate", "Lkotlin/Lazy;", "scheduler", "Lcom/cjcrafter/foliascheduler/ServerImplementation;", "getScheduler", "()Lcom/cjcrafter/foliascheduler/ServerImplementation;", "scheduler$delegate", "ticks", "finishPreRun", "taskId", "Lcom/cjcrafter/foliascheduler/TaskImplementation;", "run", "", "onRun", "preRun", "cancel", "getTickType", "Lspace/hugster/sell/utils/menu/task/TaskTicker$TickType;", "getStartTick", "countUp", "countDown", "Companion", "TickType", "DonutSell"}
)
public abstract class TaskTicker {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final Lazy plugin$delegate = LazyKt.lazy(TaskTicker::plugin_delegate$lambda$0);
   @NotNull
   private final Lazy scheduler$delegate = LazyKt.lazy(TaskTicker::scheduler_delegate$lambda$1);
   private int ticks;
   private boolean finishPreRun;
   @Nullable
   private TaskImplementation<?> taskId;
   @NotNull
   private static final List<TaskTicker> tickers = (List)(new ArrayList());

   public TaskTicker(int delay, int period, boolean async) {
      Function1 task = TaskTicker::_init_$lambda$2;
      this.taskId = async ? this.getScheduler().async().runAtFixedRate(TaskTicker::_init_$lambda$3, (long)delay, (long)period) : this.getScheduler().global().runAtFixedRate(TaskTicker::_init_$lambda$4, (long)delay, (long)period);
      tickers.add(this);
   }

   private final DonutSell getPlugin() {
      Lazy var1 = this.plugin$delegate;
      return (DonutSell)var1.getValue();
   }

   private final ServerImplementation getScheduler() {
      Lazy var1 = this.scheduler$delegate;
      return (ServerImplementation)var1.getValue();
   }

   private final void run() {
      if (!this.finishPreRun) {
         this.ticks = this.getStartTick();
         this.preRun();
         this.finishPreRun = true;
      }

      this.onRun();
      switch (TaskTicker.WhenMappings.$EnumSwitchMapping$0[this.getTickType().ordinal()]) {
         case 1 -> this.countUp();
         case 2 -> this.countDown();
      }

   }

   public abstract void onRun();

   public void preRun() {
   }

   public final synchronized void cancel() {
      TaskImplementation var10000 = this.taskId;
      if (var10000 != null) {
         var10000.cancel();
      }

      tickers.remove(this);
   }

   @NotNull
   public abstract TickType getTickType();

   public abstract int getStartTick();

   private final void countUp() {
      int var1 = this.ticks++;
   }

   private final void countDown() {
      int var1 = this.ticks;
      this.ticks = var1 + -1;
   }

   private static final DonutSell plugin_delegate$lambda$0() {
      return DonutSell.Companion.getINSTANCE();
   }

   private static final ServerImplementation scheduler_delegate$lambda$1(TaskTicker this$0) {
      ServerImplementation var10000 = (new FoliaCompatibility((Plugin)this$0.getPlugin())).getServerImplementation();
      Intrinsics.checkNotNullExpressionValue(var10000, "getServerImplementation(...)");
      return var10000;
   }

   private static final Unit _init_$lambda$2(TaskTicker this$0, TaskImplementation it) {
      Intrinsics.checkNotNullParameter(it, "it");
      this$0.run();
      return Unit.INSTANCE;
   }

   private static final void _init_$lambda$3(Function1 $tmp0, TaskImplementation p0) {
      $tmp0.invoke(p0);
   }

   private static final void _init_$lambda$4(Function1 $tmp0, TaskImplementation p0) {
      $tmp0.invoke(p0);
   }

   @NotNull
   public static final List<TaskTicker> getTickers() {
      return Companion.getTickers();
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\"\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0007\u0010\u0003\u001a\u0004\b\b\u0010\t¨\u0006\n"},
      d2 = {"Lspace/hugster/sell/utils/menu/task/TaskTicker$Companion;", "", "<init>", "()V", "tickers", "", "Lspace/hugster/sell/utils/menu/task/TaskTicker;", "getTickers$annotations", "getTickers", "()Ljava/util/List;", "DonutSell"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final List<TaskTicker> getTickers() {
         return TaskTicker.tickers;
      }

      /** @deprecated */
      // $FF: synthetic method
      @JvmStatic
      public static void getTickers$annotations() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"},
      d2 = {"Lspace/hugster/sell/utils/menu/task/TaskTicker$TickType;", "", "<init>", "(Ljava/lang/String;I)V", "COUNT_UP", "COUNT_DOWN", "NONE", "DonutSell"}
   )
   public static enum TickType {
      COUNT_UP,
      COUNT_DOWN,
      NONE;

      // $FF: synthetic field
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

      @NotNull
      public static EnumEntries<TickType> getEntries() {
         return $ENTRIES;
      }

      // $FF: synthetic method
      private static final TickType[] $values() {
         TickType[] var0 = new TickType[]{COUNT_UP, COUNT_DOWN, NONE};
         return var0;
      }
   }

   // $FF: synthetic class
   @Metadata(
      mv = {2, 1, 0},
      k = 3,
      xi = 48
   )
   public class WhenMappings {
      // $FF: synthetic field
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[TaskTicker.TickType.values().length];

         try {
            var0[TaskTicker.TickType.COUNT_UP.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[TaskTicker.TickType.COUNT_DOWN.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }
}
