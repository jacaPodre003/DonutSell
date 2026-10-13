package revxrsal.commands.ktx;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.LampBuilderVisitor;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.reflect.ktx.KotlinConstants;

public enum KotlinFeatureRegistry implements LampBuilderVisitor<CommandActor> {
   INSTANCE;

   public void visit(Lamp.@NotNull Builder<CommandActor> builder) {
      if (KotlinConstants.continuation() != null) {
         builder.accept(SuspendFunctionsSupport.INSTANCE);
      }

   }
}
