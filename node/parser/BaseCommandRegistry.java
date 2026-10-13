package revxrsal.commands.node.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.CommandPlaceholder;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandFunction;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.command.Potential;
import revxrsal.commands.exception.UnknownCommandException;
import revxrsal.commands.exception.context.ErrorContext;
import revxrsal.commands.node.CommandRegistry;
import revxrsal.commands.reflect.MethodCaller;
import revxrsal.commands.reflect.MethodCallerFactory;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.stream.StringStream;
import revxrsal.commands.util.CommandPaths;
import revxrsal.commands.util.Reflections;

@Internal
public final class BaseCommandRegistry<A extends CommandActor> implements CommandRegistry<A> {
   private final List<ExecutableCommand<A>> children;
   private final List<ExecutableCommand<A>> unmodifiableChildren;
   private final Lamp<A> lamp;

   public BaseCommandRegistry(Lamp<A> lamp, List<ExecutableCommand<A>> children) {
      this.children = children;
      this.lamp = lamp;
      this.unmodifiableChildren = Collections.unmodifiableList(children);
   }

   public BaseCommandRegistry(Lamp<A> lamp) {
      this(lamp, new ArrayList());
   }

   public List<ExecutableCommand<A>> register(@NotNull Class<?> containerClass, Object instance) {
      return this.register(containerClass, instance, (List)null);
   }

   public @NotNull @Unmodifiable List<ExecutableCommand<A>> register(@NotNull Class<?> containerClass, Object instance, @Nullable List<String> orphanPaths) {
      try {
         this.injectDependencies(containerClass, instance);
         List<ExecutableCommand<A>> registered = new ArrayList();

         for(Method method : Reflections.getAllMethods(containerClass, true)) {
            AnnotationList annotations = AnnotationList.create(method).replaceAnnotations(method, this.lamp.annotationReplacers());
            if (!annotations.isEmpty() && this.isCommandMethod(annotations)) {
               if (orphanPaths != null && !annotations.isEmpty()) {
                  if (orphanPaths.isEmpty()) {
                     throw new IllegalArgumentException("Cannot have an OrphanCommand with no paths (supplied from .path())");
                  }

                  String[] values = (String[])orphanPaths.toArray(new String[0]);
                  annotations = annotations.withAnnotations(false, new DynamicCommand(values));
               }

               MethodCaller.BoundMethodCaller caller = MethodCallerFactory.defaultFactory().createFor(method).bindTo(instance);
               CommandFunction fn = CommandFunctionImpl.create(method, annotations, this.lamp, caller);

               for(String path : CommandPaths.parseCommandAnnotations(containerClass, fn)) {
                  MutableStringStream stream = StringStream.createMutable(path);
                  ExecutableCommand<A> target = TreeParser.<A>parse(fn, this.lamp, stream);
                  if (this.lamp.hooks().onCommandRegistered(target)) {
                     this.add(target);
                     registered.add(target);
                  }
               }
            }
         }

         return revxrsal.commands.util.Collections.<ExecutableCommand<A>>copyList(registered);
      } catch (Throwable $ex) {
         throw $ex;
      }
   }

   private boolean isCommandMethod(AnnotationList annotations) {
      return annotations.contains(Command.class) || annotations.contains(Subcommand.class) || annotations.contains(CommandPlaceholder.class);
   }

   private void injectDependencies(Class<?> commandClass, Object instance) {
      for(Field field : commandClass.getDeclaredFields()) {
         if (field.isAnnotationPresent(Dependency.class)) {
            if (!field.isAccessible()) {
               field.setAccessible(true);
            }

            Object dependency = this.lamp.dependency(field.getType());

            try {
               field.set(instance, dependency);
            } catch (IllegalAccessException e) {
               throw new IllegalStateException("Unable to inject dependency value into field " + field.getName(), e);
            }
         }
      }

   }

   private void add(@NotNull ExecutableCommand<A> command) {
      this.children.add(command);
      Collections.sort(this.children);
   }

   public void execute(@NotNull A actor, @NotNull ExecutableCommand<A> command, @NotNull MutableStringStream input) {
      Potential<A> potential = command.test(actor, input);
      if (potential.failed()) {
         potential.handleException();
      } else {
         potential.execute();
      }

   }

   public @NotNull Lamp<A> lamp() {
      return this.lamp;
   }

   public void execute(@NotNull A actor, @NotNull StringStream input) {
      LinkedList<Potential<A>> conflicts = new LinkedList();
      LinkedList<Potential<A>> failed = new LinkedList();
      String firstWord = input.peekUnquotedString();

      for(ExecutableCommand<A> execution : this.children) {
         if (execution.firstNode().name().equalsIgnoreCase(firstWord)) {
            MutableStringStream in = input.toMutableCopy();
            Potential<A> potential = execution.test(actor, in);
            if (conflicts.size() >= this.lamp.dispatcherSettings().maximumFailedAttempts()) {
               break;
            }

            if (potential.successful()) {
               conflicts.add(potential);
            } else {
               failed.add(potential);
            }
         }
      }

      if (conflicts.isEmpty()) {
         if (failed.isEmpty()) {
            this.lamp.handleException(new UnknownCommandException(firstWord), ErrorContext.unknownCommand(actor));
         } else {
            this.lamp.dispatcherSettings().failureHandler().handleFailedAttempts(actor, Collections.unmodifiableList(failed), input);
         }
      } else {
         Collections.sort(conflicts);
         ((Potential)conflicts.getFirst()).execute();
      }
   }

   public @NotNull @UnmodifiableView List<ExecutableCommand<A>> commands() {
      return this.unmodifiableChildren;
   }

   public void unregister(@NotNull ExecutableCommand<A> execution) {
      this.children.remove(execution);
   }

   public boolean any(@NotNull Predicate<@NotNull ExecutableCommand<A>> matches) {
      return revxrsal.commands.util.Collections.any(this.children, matches);
   }

   public @NotNull List<ExecutableCommand<A>> filter(@NotNull Predicate<@NotNull ExecutableCommand<A>> filterPredicate) {
      return revxrsal.commands.util.Collections.<ExecutableCommand<A>>filter(this.children, filterPredicate);
   }

   public void unregisterIf(@NotNull Predicate<ExecutableCommand<A>> matches) {
      this.children.removeIf(matches);
   }

   public @NotNull Iterator<ExecutableCommand<A>> iterator() {
      return revxrsal.commands.util.Collections.<ExecutableCommand<A>>unmodifiableIterator(this.children.iterator());
   }

   private static final class DynamicCommand implements Command {
      private final String[] value;

      private DynamicCommand(String[] value) {
         this.value = value;
      }

      public Class<? extends Annotation> annotationType() {
         return Command.class;
      }

      public String[] value() {
         return this.value;
      }

      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else if (obj != null && obj.getClass() == this.getClass()) {
            DynamicCommand that = (DynamicCommand)obj;
            return Objects.equals(this.value, that.value);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.value});
      }

      public String toString() {
         return "DynamicCommand[value=" + Arrays.toString(this.value) + ']';
      }
   }
}
