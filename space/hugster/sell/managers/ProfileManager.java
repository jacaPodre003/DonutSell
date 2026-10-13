package space.hugster.sell.managers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.hugster.sell.DonutSell;
import space.hugster.sell.data.Profile;
import space.hugster.sell.data.ProfileDataProvider;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0018\u001a\u00020\u0011J\u000e\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u0012J\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00120\u001cJ\u0006\u0010\u001d\u001a\u00020\u0014J\u0006\u0010\u001e\u001a\u00020\u0014R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\t\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"},
   d2 = {"Lspace/hugster/sell/managers/ProfileManager;", "", "<init>", "()V", "plugin", "Lspace/hugster/sell/DonutSell;", "getPlugin", "()Lspace/hugster/sell/DonutSell;", "plugin$delegate", "Lkotlin/Lazy;", "database", "Lspace/hugster/sell/data/ProfileDataProvider;", "getDatabase", "()Lspace/hugster/sell/data/ProfileDataProvider;", "database$delegate", "profiles", "", "Ljava/util/UUID;", "Lspace/hugster/sell/data/Profile;", "createProfile", "", "player", "Lorg/bukkit/entity/Player;", "getProfile", "uuid", "removeProfile", "profile", "getAllProfiles", "", "saveAllProfiles", "clearAllProfiles", "DonutSell"}
)
public final class ProfileManager {
   @NotNull
   private final Lazy plugin$delegate = LazyKt.lazy(ProfileManager::plugin_delegate$lambda$0);
   @NotNull
   private final Lazy database$delegate = LazyKt.lazy(ProfileManager::database_delegate$lambda$1);
   @NotNull
   private final Map<UUID, Profile> profiles = (Map)(new ConcurrentHashMap());

   private final DonutSell getPlugin() {
      Lazy var1 = this.plugin$delegate;
      return (DonutSell)var1.getValue();
   }

   private final ProfileDataProvider getDatabase() {
      Lazy var1 = this.database$delegate;
      return (ProfileDataProvider)var1.getValue();
   }

   private final void createProfile(Player player) {
      UUID var10002 = player.getUniqueId();
      Intrinsics.checkNotNullExpressionValue(var10002, "getUniqueId(...)");
      final Profile $this$createProfile_u24lambda_u242 = new Profile(var10002);
      int var5 = 0;
      if (((CharSequence)$this$createProfile_u24lambda_u242.getUsername()).length() == 0) {
         $this$createProfile_u24lambda_u242.setUsername(player.getName());
      }

      this.profiles.put(player.getUniqueId(), $this$createProfile_u24lambda_u242);
      BuildersKt.launch$default(this.getPlugin().getCoroutineScope(), (CoroutineContext)null, (CoroutineStart)null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>((Continuation)null) {
         int label;

         public final Object invokeSuspend(Object $result) {
            Object var2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
               case 0:
                  ResultKt.throwOnFailure($result);
                  ProfileDataProvider var10000 = ProfileManager.this.getDatabase();
                  Profile var10001 = $this$createProfile_u24lambda_u242;
                  Continuation var10002 = (Continuation)this;
                  this.label = 1;
                  if (var10000.load(var10001, var10002) == var2) {
                     return var2;
                  }
                  break;
               case 1:
                  ResultKt.throwOnFailure($result);
                  break;
               default:
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            return Unit.INSTANCE;
         }

         public final Continuation<Unit> create(Object value, Continuation<?> $completion) {
            return (Continuation)(new <anonymous constructor>($completion));
         }

         public final Object invoke(CoroutineScope p1, Continuation<? super Unit> p2) {
            return ((<undefinedtype>)this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
         }
      }, 3, (Object)null);
   }

   @Nullable
   public final Profile getProfile(@NotNull UUID uuid) {
      Intrinsics.checkNotNullParameter(uuid, "uuid");
      Player player = Bukkit.getPlayer(uuid);
      Profile var10000;
      if (player != null && player.isOnline()) {
         if (!this.profiles.containsKey(uuid)) {
            this.createProfile(player);
         }

         var10000 = (Profile)this.profiles.get(uuid);
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public final void removeProfile(@NotNull final Profile profile) {
      Intrinsics.checkNotNullParameter(profile, "profile");
      this.profiles.remove(profile.getUuid());
      BuildersKt.launch$default(this.getPlugin().getCoroutineScope(), (CoroutineContext)null, (CoroutineStart)null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>((Continuation)null) {
         int label;

         public final Object invokeSuspend(Object $result) {
            Object var2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
               case 0:
                  ResultKt.throwOnFailure($result);
                  ProfileDataProvider var10000 = ProfileManager.this.getDatabase();
                  Profile var10001 = profile;
                  Continuation var10002 = (Continuation)this;
                  this.label = 1;
                  if (var10000.save(var10001, var10002) == var2) {
                     return var2;
                  }
                  break;
               case 1:
                  ResultKt.throwOnFailure($result);
                  break;
               default:
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            return Unit.INSTANCE;
         }

         public final Continuation<Unit> create(Object value, Continuation<?> $completion) {
            return (Continuation)(new <anonymous constructor>($completion));
         }

         public final Object invoke(CoroutineScope p1, Continuation<? super Unit> p2) {
            return ((<undefinedtype>)this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
         }
      }, 3, (Object)null);
   }

   @NotNull
   public final Collection<Profile> getAllProfiles() {
      return this.profiles.values();
   }

   public final void saveAllProfiles() {
      BuildersKt.runBlocking$default((CoroutineContext)null, new Function2<CoroutineScope, Continuation<? super Unit>, Object>((Continuation)null) {
         Object L$0;
         Object L$1;
         int label;

         public final Object invokeSuspend(Object $result) {
            Object var9 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            ProfileManager var3;
            Iterator var5;
            switch (this.label) {
               case 0:
                  ResultKt.throwOnFailure($result);
                  Iterable $this$forEach$iv = (Iterable)ProfileManager.this.getAllProfiles();
                  var3 = ProfileManager.this;
                  int $i$f$forEach = 0;
                  var5 = $this$forEach$iv.iterator();
                  break;
               case 1:
                  int $i$f$forEach = 0;
                  int var8 = 0;
                  var5 = (Iterator)this.L$1;
                  var3 = (ProfileManager)this.L$0;
                  ResultKt.throwOnFailure($result);
                  break;
               default:
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            while(var5.hasNext()) {
               Object element$iv = var5.next();
               Profile profile = (Profile)element$iv;
               int var11 = 0;
               ProfileDataProvider var10000 = var3.getDatabase();
               this.L$0 = var3;
               this.L$1 = var5;
               this.label = 1;
               if (var10000.save(profile, this) == var9) {
                  return var9;
               }
            }

            return Unit.INSTANCE;
         }

         public final Continuation<Unit> create(Object value, Continuation<?> $completion) {
            return (Continuation)(new <anonymous constructor>($completion));
         }

         public final Object invoke(CoroutineScope p1, Continuation<? super Unit> p2) {
            return ((<undefinedtype>)this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
         }
      }, 1, (Object)null);
   }

   public final void clearAllProfiles() {
      this.profiles.clear();
   }

   private static final DonutSell plugin_delegate$lambda$0() {
      return DonutSell.Companion.getINSTANCE();
   }

   private static final ProfileDataProvider database_delegate$lambda$1(ProfileManager this$0) {
      return this$0.getPlugin().getDatabase();
   }
}
