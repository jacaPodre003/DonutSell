package revxrsal.commands.ktx;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.LampBuilderVisitor;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.exception.context.ErrorContext;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ContextParameter;
import revxrsal.commands.parameter.ParameterTypes;

@Metadata(
   mv = {2, 0, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\t\nB\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\bH\u0016¨\u0006\u000b"},
   d2 = {"Lrevxrsal/commands/ktx/SuspendFunctionsSupport;", "Lrevxrsal/commands/LampBuilderVisitor;", "Lrevxrsal/commands/command/CommandActor;", "<init>", "()V", "visit", "", "builder", "Lrevxrsal/commands/Lamp$Builder;", "ContinuationResolver", "BasicContinuation", "common"}
)
public final class SuspendFunctionsSupport implements LampBuilderVisitor<CommandActor> {
   @NotNull
   public static final SuspendFunctionsSupport INSTANCE = new SuspendFunctionsSupport();

   private SuspendFunctionsSupport() {
   }

   public void visit(@NotNull Lamp.Builder<CommandActor> builder) {
      Intrinsics.checkNotNullParameter(builder, "builder");
      ParameterTypes.Builder var10000 = builder.parameterTypes();
      ContinuationResolver var10002 = SuspendFunctionsSupport.ContinuationResolver.INSTANCE;
      Intrinsics.checkNotNull(var10002, "null cannot be cast to non-null type revxrsal.commands.parameter.ContextParameter<revxrsal.commands.command.CommandActor, kotlin.coroutines.Continuation<*>>");
      var10000.addContextParameterLast(Continuation.class, var10002);
   }

   @Metadata(
      mv = {2, 0, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001B\t\b\u0002¢\u0006\u0004\b\u0005\u0010\u0006J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u000bH\u0016¨\u0006\f"},
      d2 = {"Lrevxrsal/commands/ktx/SuspendFunctionsSupport$ContinuationResolver;", "Lrevxrsal/commands/parameter/ContextParameter;", "Lrevxrsal/commands/command/CommandActor;", "Lkotlin/coroutines/Continuation;", "", "<init>", "()V", "resolve", "parameter", "Lrevxrsal/commands/command/CommandParameter;", "context", "Lrevxrsal/commands/node/ExecutionContext;", "common"}
   )
   private static final class ContinuationResolver implements ContextParameter<CommandActor, Continuation<? super Object>> {
      @NotNull
      public static final ContinuationResolver INSTANCE = new ContinuationResolver();

      @NotNull
      public Continuation<Object> resolve(@NotNull CommandParameter parameter, @NotNull ExecutionContext<CommandActor> context) {
         Intrinsics.checkNotNullParameter(parameter, "parameter");
         Intrinsics.checkNotNullParameter(context, "context");
         return new BasicContinuation<Object>(ContinuationResolver::resolve$lambda$0);
      }

      private static final Unit resolve$lambda$0(ExecutionContext $context, Throwable it) {
         Intrinsics.checkNotNullParameter($context, "$context");
         Intrinsics.checkNotNullParameter(it, "it");
         $context.lamp().handleException(it, ErrorContext.executingFunction($context));
         return Unit.INSTANCE;
      }
   }

   @Metadata(
      mv = {2, 0, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0004\b\u0007\u0010\bJ\u001b\u0010\r\u001a\u00020\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fH\u0016¢\u0006\u0002\u0010\u0010R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"},
      d2 = {"Lrevxrsal/commands/ktx/SuspendFunctionsSupport$BasicContinuation;", "T", "Lkotlin/coroutines/Continuation;", "handleException", "Lkotlin/Function1;", "", "", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "context", "Lkotlin/coroutines/EmptyCoroutineContext;", "getContext", "()Lkotlin/coroutines/EmptyCoroutineContext;", "resumeWith", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "common"}
   )
   private static final class BasicContinuation<T> implements Continuation<T> {
      @NotNull
      private final Function1<Throwable, Unit> handleException;

      public BasicContinuation(@NotNull Function1<? super Throwable, Unit> handleException) {
         Intrinsics.checkNotNullParameter(handleException, "handleException");
         super();
         this.handleException = handleException;
      }

      @NotNull
      public EmptyCoroutineContext getContext() {
         return EmptyCoroutineContext.INSTANCE;
      }

      public void resumeWith(@NotNull Object result) {
         Throwable var10000 = Result.exceptionOrNull-impl(result);
         if (var10000 == null) {
            int var4 = 0;
         } else {
            Throwable it = var10000;
            int var5 = 0;
            this.handleException.invoke(it);
         }

      }
   }
}
