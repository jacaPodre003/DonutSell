package revxrsal.commands.bukkit.parameters;

import com.google.common.collect.ForwardingList;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.exception.EmptyEntitySelectorException;
import revxrsal.commands.bukkit.exception.MalformedEntitySelectorException;
import revxrsal.commands.exception.CommandErrorException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.util.Classes;
import revxrsal.commands.util.Preconditions;

public final class EntitySelectorParameterTypeFactory implements ParameterType.Factory<BukkitCommandActor> {
   public <T> @Nullable ParameterType<BukkitCommandActor, T> create(@NotNull Type parameterType, @NotNull AnnotationList annotations, @NotNull Lamp<BukkitCommandActor> lamp) {
      Class<?> rawType = Classes.getRawType(parameterType);
      if (rawType != EntitySelector.class) {
         return null;
      } else {
         Class<? extends Entity> entityClass = Classes.getRawType(Classes.getFirstGeneric(parameterType, Entity.class)).asSubclass(Entity.class);
         return new EntitySelectorParameterType(entityClass);
      }
   }

   static final class EntitySelectorParameterType implements ParameterType<BukkitCommandActor, EntitySelector<?>> {
      private final Class<?> entityType;

      public EntitySelectorParameterType(Class<?> entityType) {
         this.entityType = entityType;
      }

      public EntitySelector<?> parse(@NotNull MutableStringStream input, @NotNull ExecutionContext<BukkitCommandActor> context) {
         String selector = input.readString();

         try {
            List<Entity> c = new ArrayList(Bukkit.getServer().selectEntities(((BukkitCommandActor)context.actor()).sender(), selector));
            c.removeIf((obj) -> !this.entityType.isInstance(obj));
            if (c.isEmpty()) {
               throw new EmptyEntitySelectorException(selector);
            } else {
               return new SelectorList(c);
            }
         } catch (IllegalArgumentException e) {
            throw new MalformedEntitySelectorException(selector, e.getCause().getMessage());
         } catch (NoSuchMethodError var6) {
            throw new CommandErrorException("Entity selectors on legacy versions are not supported yet!", new Object[0]);
         }
      }
   }

   static final class SelectorList<E extends Entity> extends ForwardingList<E> implements EntitySelector<E> {
      private final List<E> entities;

      public SelectorList(List<E> entities) {
         this.entities = (List)Preconditions.notNull(entities, "entities list");
      }

      protected List<E> delegate() {
         return this.entities;
      }
   }
}
