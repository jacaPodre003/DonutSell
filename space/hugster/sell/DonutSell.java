package space.hugster.sell;

import com.cjcrafter.foliascheduler.FoliaCompatibility;
import com.cjcrafter.foliascheduler.ServerImplementation;
import java.net.URI;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.autocomplete.SuggestionProviders;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import space.hugster.sell.commands.DonutSellCommand;
import space.hugster.sell.commands.SellCommand;
import space.hugster.sell.commands.WorthCommand;
import space.hugster.sell.commands.suggests.WorthItemSuggestions;
import space.hugster.sell.commands.suggests.annotations.WorthItem;
import space.hugster.sell.data.LevelSystem;
import space.hugster.sell.data.ProfileDataProvider;
import space.hugster.sell.database.DatabaseLoader;
import space.hugster.sell.listeners.ProfileListener;
import space.hugster.sell.managers.ProfileManager;
import space.hugster.sell.packets.WorthPacket;
import space.hugster.sell.sell.SellServiceManager;
import space.hugster.sell.utils.Config;
import space.hugster.sell.utils.WorthCache;
import space.hugster.sell.utils.menu.ButtonListener;
import space.hugster.sell.utils.security.HWIDUtil;
import space.hugster.sell.utils.security.License;
import space.hugster.sell.utils.security.LicenseValidator;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 52\u00020\u0001:\u00015B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u00100\u001a\u000201H\u0016J\b\u00102\u001a\u000201H\u0016J\b\u00103\u001a\u000201H\u0002J\b\u00104\u001a\u000201H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R \u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001a\u0010'\u001a\u00020(X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001a\u0010-\u001a\u00020(X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010*\"\u0004\b/\u0010,¨\u00066"},
   d2 = {"Lspace/hugster/sell/DonutSell;", "Lorg/bukkit/plugin/java/JavaPlugin;", "<init>", "()V", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "getCoroutineScope", "()Lkotlinx/coroutines/CoroutineScope;", "database", "Lspace/hugster/sell/data/ProfileDataProvider;", "getDatabase", "()Lspace/hugster/sell/data/ProfileDataProvider;", "setDatabase", "(Lspace/hugster/sell/data/ProfileDataProvider;)V", "profileManager", "Lspace/hugster/sell/managers/ProfileManager;", "getProfileManager", "()Lspace/hugster/sell/managers/ProfileManager;", "setProfileManager", "(Lspace/hugster/sell/managers/ProfileManager;)V", "lamp", "Lrevxrsal/commands/Lamp;", "Lrevxrsal/commands/bukkit/actor/BukkitCommandActor;", "getLamp", "()Lrevxrsal/commands/Lamp;", "setLamp", "(Lrevxrsal/commands/Lamp;)V", "foliaScheduler", "Lcom/cjcrafter/foliascheduler/ServerImplementation;", "getFoliaScheduler", "()Lcom/cjcrafter/foliascheduler/ServerImplementation;", "setFoliaScheduler", "(Lcom/cjcrafter/foliascheduler/ServerImplementation;)V", "economy", "Lnet/milkbowl/vault/economy/Economy;", "getEconomy", "()Lnet/milkbowl/vault/economy/Economy;", "setEconomy", "(Lnet/milkbowl/vault/economy/Economy;)V", "mainConfig", "Lspace/hugster/sell/utils/Config;", "getMainConfig", "()Lspace/hugster/sell/utils/Config;", "setMainConfig", "(Lspace/hugster/sell/utils/Config;)V", "worthConfig", "getWorthConfig", "setWorthConfig", "onEnable", "", "onDisable", "vault", "files", "Companion", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nDonutSell.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DonutSell.kt\nspace/hugster/sell/DonutSell\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,144:1\n1863#2,2:145\n1863#2,2:147\n*S KotlinDebug\n*F\n+ 1 DonutSell.kt\nspace/hugster/sell/DonutSell\n*L\n104#1:145,2\n108#1:147,2\n*E\n"})
public final class DonutSell extends JavaPlugin {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final CoroutineScope coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus((CoroutineContext)SupervisorKt.SupervisorJob$default((Job)null, 1, (Object)null)));
   public ProfileDataProvider database;
   public ProfileManager profileManager;
   public Lamp<BukkitCommandActor> lamp;
   public ServerImplementation foliaScheduler;
   @Nullable
   private Economy economy;
   public Config mainConfig;
   public Config worthConfig;
   public static DonutSell INSTANCE;

   @NotNull
   public final CoroutineScope getCoroutineScope() {
      return this.coroutineScope;
   }

   @NotNull
   public final ProfileDataProvider getDatabase() {
      ProfileDataProvider var10000 = this.database;
      if (var10000 != null) {
         return var10000;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("database");
         return null;
      }
   }

   public final void setDatabase(@NotNull ProfileDataProvider <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.database = <set-?>;
   }

   @NotNull
   public final ProfileManager getProfileManager() {
      ProfileManager var10000 = this.profileManager;
      if (var10000 != null) {
         return var10000;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("profileManager");
         return null;
      }
   }

   public final void setProfileManager(@NotNull ProfileManager <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.profileManager = <set-?>;
   }

   @NotNull
   public final Lamp<BukkitCommandActor> getLamp() {
      Lamp var10000 = this.lamp;
      if (var10000 != null) {
         return var10000;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("lamp");
         return null;
      }
   }

   public final void setLamp(@NotNull Lamp<BukkitCommandActor> <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.lamp = <set-?>;
   }

   @NotNull
   public final ServerImplementation getFoliaScheduler() {
      ServerImplementation var10000 = this.foliaScheduler;
      if (var10000 != null) {
         return var10000;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("foliaScheduler");
         return null;
      }
   }

   public final void setFoliaScheduler(@NotNull ServerImplementation <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.foliaScheduler = <set-?>;
   }

   @Nullable
   public final Economy getEconomy() {
      return this.economy;
   }

   public final void setEconomy(@Nullable Economy <set-?>) {
      this.economy = <set-?>;
   }

   @NotNull
   public final Config getMainConfig() {
      Config var10000 = this.mainConfig;
      if (var10000 != null) {
         return var10000;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("mainConfig");
         return null;
      }
   }

   public final void setMainConfig(@NotNull Config <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.mainConfig = <set-?>;
   }

   @NotNull
   public final Config getWorthConfig() {
      Config var10000 = this.worthConfig;
      if (var10000 != null) {
         return var10000;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("worthConfig");
         return null;
      }
   }

   public final void setWorthConfig(@NotNull Config <set-?>) {
      Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
      this.worthConfig = <set-?>;
   }

   public void onEnable() {
      Companion.setINSTANCE(this);
      this.files();
      String licenseKey = this.getConfig().getString("LICENSE-KEY");
      CharSequence var2 = (CharSequence)licenseKey;
      if (var2 == null || StringsKt.isBlank(var2)) {
         this.getLogger().severe("LICENSE-KEY not found in config.yml");
         this.getLogger().severe("Please configure your license key in the config.yml file");
         this.getServer().getPluginManager().disablePlugin((Plugin)this);
      } else {
         String hwid = HWIDUtil.INSTANCE.getHWID();
         License license = new License(new URI("https://license.hugster.net"), licenseKey, "Sell", hwid);
         LicenseValidator validator = new LicenseValidator();
         if (!validator.validate(license, licenseKey, hwid)) {
            this.getServer().getPluginManager().disablePlugin((Plugin)this);
         } else {
            this.setDatabase(DatabaseLoader.INSTANCE.loadDatabase(this));
            this.setProfileManager(new ProfileManager());
            this.setFoliaScheduler((new FoliaCompatibility((Plugin)this)).getServerImplementation());
            this.setLamp(BukkitLamp.builder(this).suggestionProviders(DonutSell::onEnable$lambda$1).build());
            Listener[] var6 = new Listener[]{new ProfileListener(), new ButtonListener(this)};
            List listeners = CollectionsKt.listOf(var6);
            Iterable $this$forEach$iv = new Object[]{new SellCommand(), new WorthCommand(), new DonutSellCommand()};
            List commands = CollectionsKt.listOf($this$forEach$iv);
            $this$forEach$iv = (Iterable)listeners;
            int $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               Listener it = (Listener)element$iv;
               int var12 = 0;
               this.getServer().getPluginManager().registerEvents(it, (Plugin)this);
            }

            $this$forEach$iv = (Iterable)commands;
            $i$f$forEach = 0;

            for(Object element$iv : $this$forEach$iv) {
               int var21 = 0;
               Lamp var10000 = this.getLamp();
               Object[] var13 = new Object[]{element$iv};
               var10000.register(var13);
            }

            this.vault();
            SellServiceManager.Companion.getInstance();
            WorthCache.INSTANCE.loadCache();
            if (this.getConfig().getBoolean("SETTINGS.WORTH-LORE", true)) {
               new WorthPacket();
            }

            LevelSystem.INSTANCE.initialize();
         }
      }
   }

   public void onDisable() {
      try {
         this.getProfileManager().saveAllProfiles();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   private final void vault() {
      RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
      if (rsp != null) {
         this.economy = (Economy)rsp.getProvider();
      }

   }

   private final void files() {
      this.setMainConfig(new Config("config", this));
      this.setWorthConfig(new Config("worth", this));
   }

   private static final SuggestionProvider onEnable$lambda$1$lambda$0(WorthItem it) {
      return WorthItemSuggestions.INSTANCE.getProvider();
   }

   private static final void onEnable$lambda$1(SuggestionProviders.Builder providers) {
      providers.addProviderFactory(SuggestionProvider.Factory.forAnnotation(WorthItem.class, DonutSell::onEnable$lambda$1$lambda$0));
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"},
      d2 = {"Lspace/hugster/sell/DonutSell$Companion;", "", "<init>", "()V", "INSTANCE", "Lspace/hugster/sell/DonutSell;", "getINSTANCE", "()Lspace/hugster/sell/DonutSell;", "setINSTANCE", "(Lspace/hugster/sell/DonutSell;)V", "DonutSell"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final DonutSell getINSTANCE() {
         DonutSell var10000 = DonutSell.INSTANCE;
         if (var10000 != null) {
            return var10000;
         } else {
            Intrinsics.throwUninitializedPropertyAccessException("INSTANCE");
            return null;
         }
      }

      public final void setINSTANCE(@NotNull DonutSell <set-?>) {
         Intrinsics.checkNotNullParameter(<set-?>, "<set-?>");
         DonutSell.INSTANCE = <set-?>;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
