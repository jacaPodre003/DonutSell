package space.hugster.sell.utils;

import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"},
   d2 = {"Lspace/hugster/sell/utils/Config;", "", "name", "", "plugin", "Lorg/bukkit/plugin/java/JavaPlugin;", "<init>", "(Ljava/lang/String;Lorg/bukkit/plugin/java/JavaPlugin;)V", "config", "Lorg/bukkit/configuration/file/FileConfiguration;", "getConfig", "()Lorg/bukkit/configuration/file/FileConfiguration;", "configFile", "Ljava/io/File;", "wasCreated", "", "save", "", "reload", "DonutSell"}
)
public final class Config {
   @NotNull
   private final String name;
   @NotNull
   private final JavaPlugin plugin;
   @NotNull
   private final FileConfiguration config;
   @NotNull
   private final File configFile;
   private boolean wasCreated;

   public Config(@NotNull String name, @NotNull JavaPlugin plugin) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(plugin, "plugin");
      super();
      this.name = name;
      this.plugin = plugin;
      File var10003 = this.plugin.getDataFolder();
      this.configFile = new File(var10003 + File.separator + this.name + ".yml");
      if (!this.configFile.exists()) {
         try {
            this.configFile.getParentFile().mkdirs();
            this.plugin.saveResource(this.name + ".yml", false);
            this.wasCreated = true;
         } catch (IOException e) {
            this.plugin.getLogger().severe("Could not create " + this.name + ".yml!");
            e.printStackTrace();
         }
      }

      this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
   }

   @NotNull
   public final FileConfiguration getConfig() {
      return this.config;
   }

   public final void save() {
      try {
         this.config.save(this.configFile);
      } catch (IOException e) {
         this.plugin.getLogger().severe("Could not save " + this.name + ".yml!");
         e.printStackTrace();
      }

   }

   public final void reload() {
      try {
         this.config.load(this.configFile);
      } catch (Exception e) {
         this.plugin.getLogger().severe("Could not reload " + this.name + ".yml!");
         e.printStackTrace();
      }

   }
}
