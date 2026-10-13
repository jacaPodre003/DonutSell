package revxrsal.commands.command;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.Cooldown;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.exception.CooldownException;
import revxrsal.commands.hook.PostCommandExecutedHook;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ContextParameter;
import revxrsal.commands.process.CommandCondition;
import revxrsal.commands.util.Classes;

@Internal
public final class ThreadExecutorCooldownCondition implements CommandCondition<CommandActor>, PostCommandExecutedHook<CommandActor>, ContextParameter.Factory<CommandActor> {
   private static final ScheduledExecutorService COOLDOWN_POOL = Executors.newSingleThreadScheduledExecutor();
   private final Map<UUID, Map<Integer, Long>> cooldowns = new ConcurrentHashMap();

   public void onPostExecuted(@NotNull ExecutableCommand<CommandActor> command, @NotNull ExecutionContext<CommandActor> context) {
      Cooldown cooldown = (Cooldown)command.annotations().get(Cooldown.class);
      if (cooldown != null && cooldown.value() != 0L) {
         Map<Integer, Long> spans = (Map)this.cooldowns.computeIfAbsent(context.actor().uniqueId(), (u) -> new ConcurrentHashMap());
         spans.put(command.hashCode(), System.currentTimeMillis());
         COOLDOWN_POOL.schedule(() -> (Long)spans.remove(command.hashCode()), cooldown.value(), cooldown.unit());
      }
   }

   public void test(@NotNull ExecutionContext<CommandActor> context) {
      Cooldown cooldown = (Cooldown)context.command().annotations().get(Cooldown.class);
      if (cooldown != null && cooldown.value() != 0L) {
         UUID uuid = context.actor().uniqueId();
         Map<Integer, Long> spans = (Map)this.cooldowns.get(uuid);
         if (spans != null) {
            Long created = (Long)spans.get(context.command().hashCode());
            if (created != null) {
               long passed = System.currentTimeMillis() - created;
               long left = cooldown.unit().toMillis(cooldown.value()) - passed;
               if (left > 0L && left < 1000L) {
                  left = 1000L;
               }

               throw new CooldownException(left);
            }
         }
      }
   }

   @Nullable
   public <T> ContextParameter<CommandActor, T> create(@NotNull Type parameterType, @NotNull AnnotationList annotations, @NotNull Lamp<CommandActor> lamp) {
      Class<?> rawType = Classes.getRawType(parameterType);
      if (!CooldownHandle.class.isAssignableFrom(rawType)) {
         return null;
      } else {
         Cooldown cooldown = (Cooldown)annotations.get(Cooldown.class);
         return (parameter, context) -> {
            if (context.command().annotations().contains(Cooldown.class)) {
               throw new IllegalArgumentException("Cannot have both @Cooldown and CooldownHandle in one command. Either put @Cooldown on the CooldownHandle parameter (@Cooldown(...) CooldownHandle handle), or remove @Cooldown entirely.");
            } else {
               return new BasicHandle(context.actor().uniqueId(), context.command().hashCode(), cooldown);
            }
         };
      }
   }

   private class BasicHandle implements CooldownHandle {
      private final UUID actor;
      private final int hashCode;
      private final @Nullable Cooldown cooldown;

      public BasicHandle(UUID actor, @Nullable int hashCode, Cooldown cooldown) {
         this.actor = actor;
         this.hashCode = hashCode;
         this.cooldown = cooldown;
      }

      public @NotNull CooldownHandle withCooldown(long cooldownValue, @NotNull TimeUnit unit) {
         return ThreadExecutorCooldownCondition.this.new BasicHandle(this.actor, this.hashCode, new DynamicCooldown(cooldownValue, unit));
      }

      public boolean isOnCooldown() {
         Map<Integer, Long> spans = (Map)ThreadExecutorCooldownCondition.this.cooldowns.get(this.actor);
         if (spans == null) {
            return false;
         } else {
            Long created = (Long)spans.get(this.hashCode);
            return created != null;
         }
      }

      public long elapsedMillis() {
         Map<Integer, Long> spans = (Map)ThreadExecutorCooldownCondition.this.cooldowns.get(this.actor);
         if (spans == null) {
            return 0L;
         } else {
            Long created = (Long)spans.get(this.hashCode);
            return created == null ? 0L : System.currentTimeMillis() - created;
         }
      }

      public void cooldown() {
         if (this.cooldown == null) {
            throw new IllegalArgumentException("cooldown() can only be used if the parameter has @Cooldown on it, otherwise use cooldown(duration, unit) or other overloads.");
         } else {
            this.cooldown(this.cooldown.value(), this.cooldown.unit());
         }
      }

      public void requireNotOnCooldown() {
         if (this.cooldown == null) {
            throw new IllegalArgumentException("requireNotOnCooldown() can only be used if the parameter has @Cooldown on it, otherwise use requireNotOnCooldown(duration, unit) or other overloads.");
         } else {
            this.requireNotOnCooldown(this.cooldown.value(), this.cooldown.unit());
         }
      }

      public void requireNotOnCooldown(long cooldownValue, @NotNull TimeUnit cooldownUnit) {
         long elapsed = this.elapsedMillis();
         if (elapsed != 0L) {
            long left = cooldownUnit.toMillis(cooldownValue) - elapsed;
            if (left > 0L && left < 1000L) {
               left = 1000L;
            }

            throw new CooldownException(left);
         }
      }

      public void removeCooldown() {
         Map<Integer, Long> spans = (Map)ThreadExecutorCooldownCondition.this.cooldowns.get(this.actor);
         if (spans != null) {
            spans.remove(this.hashCode);
         }
      }

      public void cooldown(@Range(
   from = 1L,
   to = Long.MAX_VALUE
) long duration, @NotNull TimeUnit unit) {
         Map<Integer, Long> spans = (Map)ThreadExecutorCooldownCondition.this.cooldowns.computeIfAbsent(this.actor, (u) -> new ConcurrentHashMap());
         spans.put(this.hashCode, System.currentTimeMillis());
         ThreadExecutorCooldownCondition.COOLDOWN_POOL.schedule(() -> (Long)spans.remove(this.hashCode), duration, unit);
      }

      public long remainingTimeMillis() {
         if (this.cooldown == null) {
            throw new IllegalArgumentException("remainingTimeMillis() can only be used if the parameter has @Cooldown on it, otherwise use remainingTimeMillis(duration, unit) or other overloads.");
         } else {
            return this.remainingTime(this.cooldown.value(), this.cooldown.unit(), TimeUnit.MILLISECONDS);
         }
      }

      public long remainingTime(@NotNull TimeUnit outputUnit) {
         return outputUnit.convert(this.remainingTimeMillis(), TimeUnit.MILLISECONDS);
      }

      private class DynamicCooldown implements Cooldown {
         private final long value;
         private final TimeUnit unit;

         public DynamicCooldown(long value, TimeUnit unit) {
            this.value = value;
            this.unit = unit;
         }

         public long value() {
            return this.value;
         }

         public TimeUnit unit() {
            return this.unit;
         }

         public Class<? extends Annotation> annotationType() {
            return Cooldown.class;
         }
      }
   }
}
