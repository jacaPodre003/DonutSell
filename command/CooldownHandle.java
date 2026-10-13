package revxrsal.commands.command;

import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.ApiStatus.Experimental;
import revxrsal.commands.exception.CooldownException;

@Experimental
public interface CooldownHandle {
   @CheckReturnValue
   @Contract(
      pure = true,
      value = "_, _ -> new"
   )
   @NotNull CooldownHandle withCooldown(long var1, @NotNull TimeUnit var3);

   boolean isOnCooldown();

   long elapsedMillis();

   void cooldown();

   void requireNotOnCooldown() throws CooldownException;

   void requireNotOnCooldown(long var1, @NotNull TimeUnit var3) throws CooldownException;

   void removeCooldown();

   default long elapsed(@NotNull TimeUnit outputUnit) {
      return outputUnit.convert(this.elapsedMillis(), TimeUnit.MILLISECONDS);
   }

   void cooldown(@Range(
   from = 1L,
   to = Long.MAX_VALUE
) long var1, @NotNull TimeUnit var3);

   long remainingTimeMillis();

   long remainingTime(@NotNull TimeUnit var1);

   default long remainingTimeMillis(long cooldownValue, @NotNull TimeUnit cooldownUnit) {
      long cooldownMillis = cooldownUnit.toMillis(cooldownValue);
      return cooldownMillis - this.elapsedMillis();
   }

   default long remainingTime(long cooldownValue, @NotNull TimeUnit cooldownUnit) {
      return this.remainingTime(cooldownValue, cooldownUnit, cooldownUnit);
   }

   default long remainingTime(long cooldownValue, @NotNull TimeUnit cooldownUnit, @NotNull TimeUnit outputUnit) {
      long cooldownMillis = cooldownUnit.toMillis(cooldownValue);
      return outputUnit.convert(cooldownMillis - this.elapsedMillis(), TimeUnit.MILLISECONDS);
   }
}
