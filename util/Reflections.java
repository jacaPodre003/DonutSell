package revxrsal.commands.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import revxrsal.commands.annotation.CommandPriority;

public final class Reflections {
   private Reflections() {
      Preconditions.cannotInstantiate(Reflections.class);
   }

   public static List<Method> getAllMethods(Class<?> c) {
      return getAllMethods(c, false);
   }

   public static List<Method> getAllMethods(Class<?> c, boolean sort) {
      List<Method> methods = new ArrayList();

      for(Class<?> current = c; current != null && current != Object.class; current = current.getSuperclass()) {
         java.util.Collections.addAll(methods, current.getDeclaredMethods());
      }

      if (sort) {
         methods.sort((o1, o2) -> {
            CommandPriority a1 = (CommandPriority)o1.getAnnotation(CommandPriority.class);
            CommandPriority a2 = (CommandPriority)o2.getAnnotation(CommandPriority.class);
            if (a1 != null && a2 != null) {
               return Integer.compare(a1.value(), a2.value());
            } else {
               CommandPriority.Low l1 = (CommandPriority.Low)o1.getAnnotation(CommandPriority.Low.class);
               CommandPriority.Low l2 = (CommandPriority.Low)o2.getAnnotation(CommandPriority.Low.class);
               if (l1 != null) {
                  return 1;
               } else {
                  return l2 != null ? -1 : 0;
               }
            }
         });
      }

      return methods;
   }

   public static List<Class<?>> getTopClasses(Class<?> c) {
      List<Class<?>> classes = new ArrayList();
      classes.add(c);

      for(Class<?> enclosingClass = c.getEnclosingClass(); enclosingClass != null; enclosingClass = enclosingClass.getEnclosingClass()) {
         classes.add(enclosingClass);
      }

      java.util.Collections.reverse(classes);
      return classes;
   }
}
