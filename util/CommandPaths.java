package revxrsal.commands.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandFunction;

public final class CommandPaths {
   private CommandPaths() {
      Preconditions.cannotInstantiate(CommandPaths.class);
   }

   public static @NotNull List<String> parseCommandAnnotations(@NotNull Class<?> container, @NotNull CommandFunction function) {
      List<String> commands = new ArrayList();
      List<String> subcommands = new ArrayList();
      Command commandAnnotation = (Command)function.annotations().require(Command.class, "Method " + function.name() + " does not have a parent command! You might have forgotten one of the following:\n- @Command on the method or class\n- implement OrphanCommand");
      Preconditions.notEmpty(commandAnnotation.value(), "@Command#value() cannot be an empty array!");
      java.util.Collections.addAll(commands, commandAnnotation.value());
      List<String> parentSubcommandAliases = new ArrayList();

      for(Class<?> topClass : Reflections.getTopClasses(container)) {
         AnnotationList annotations = AnnotationList.create(topClass).replaceAnnotations(topClass, function.lamp().annotationReplacers());
         Subcommand ps = (Subcommand)annotations.get(Subcommand.class);
         if (ps != null) {
            java.util.Collections.addAll(parentSubcommandAliases, ps.value());
         }
      }

      Subcommand subcommandAnnotation = (Subcommand)function.annotations().get(Subcommand.class);
      if (subcommandAnnotation != null) {
         if (parentSubcommandAliases.isEmpty()) {
            java.util.Collections.addAll(subcommands, subcommandAnnotation.value());
         } else {
            for(String parentSubcommandAlias : parentSubcommandAliases) {
               Arrays.stream(subcommandAnnotation.value()).map((v) -> parentSubcommandAlias + ' ' + v).forEach(subcommands::add);
            }
         }
      }

      List<String> paths = new ArrayList();

      for(String command : commands) {
         if (!subcommands.isEmpty()) {
            for(String subcommand : subcommands) {
               paths.add(command + ' ' + subcommand);
            }
         } else {
            paths.add(command);
         }
      }

      return paths;
   }
}
