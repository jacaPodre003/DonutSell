package revxrsal.commands.bukkit.actor;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.process.MessageSender;

final class BasicBukkitActor implements BukkitCommandActor {
   private static final UUID CONSOLE_UUID = new UUID(0L, 0L);
   private final CommandSender sender;
   private final Plugin plugin;
   private final Optional<BukkitAudiences> audiences;
   private final MessageSender<BukkitCommandActor, ComponentLike> messageSender;
   private final Lamp<BukkitCommandActor> lamp;

   BasicBukkitActor(CommandSender sender, Plugin plugin, Optional<BukkitAudiences> audiences, MessageSender<BukkitCommandActor, ComponentLike> messageSender, Lamp<BukkitCommandActor> lamp) {
      this.sender = sender;
      this.plugin = plugin;
      this.audiences = audiences;
      this.messageSender = messageSender;
      this.lamp = lamp;
   }

   public @NotNull CommandSender sender() {
      return this.sender;
   }

   public void reply(@NotNull ComponentLike message) {
      if (this.messageSender == null) {
         this.audience().ifPresent((a) -> a.sendMessage(message));
      } else {
         this.messageSender.send(this, message);
      }

   }

   public @NotNull Optional<Audience> audience() {
      if (this.sender instanceof Audience) {
         return Optional.of((Audience)this.sender);
      } else if (!this.audiences.isPresent()) {
         return Optional.empty();
      } else {
         BukkitAudiences bukkitAudiences = (BukkitAudiences)this.audiences.get();
         return Optional.of(bukkitAudiences.sender(this.sender()));
      }
   }

   public @NotNull UUID uniqueId() {
      if (this.isPlayer()) {
         return ((Player)this.sender).getUniqueId();
      } else {
         return this.isConsole() ? CONSOLE_UUID : UUID.nameUUIDFromBytes(this.name().getBytes(StandardCharsets.UTF_8));
      }
   }

   public Lamp<BukkitCommandActor> lamp() {
      return this.lamp;
   }

   public Plugin plugin() {
      return this.plugin;
   }

   public Optional<BukkitAudiences> audiences() {
      return this.audiences;
   }

   public MessageSender<BukkitCommandActor, ComponentLike> messageSender() {
      return this.messageSender;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         BasicBukkitActor that = (BasicBukkitActor)obj;
         return Objects.equals(this.sender, that.sender) && Objects.equals(this.plugin, that.plugin) && Objects.equals(this.audiences, that.audiences) && Objects.equals(this.messageSender, that.messageSender) && Objects.equals(this.lamp, that.lamp);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.sender, this.plugin, this.audiences, this.messageSender, this.lamp});
   }

   public String toString() {
      return "BasicBukkitActor[sender=" + this.sender + ", plugin=" + this.plugin + ", audiences=" + this.audiences + ", messageSender=" + this.messageSender + ", lamp=" + this.lamp + ']';
   }
}
