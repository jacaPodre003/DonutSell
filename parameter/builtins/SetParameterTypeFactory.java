package revxrsal.commands.parameter.builtins;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.util.Classes;

public final class SetParameterTypeFactory extends CollectionParameterTypeFactory {
   public static final SetParameterTypeFactory INSTANCE = new SetParameterTypeFactory();

   private SetParameterTypeFactory() {
   }

   protected boolean matchType(@NotNull Type type, @NotNull Class<?> rawType) {
      return rawType == Set.class;
   }

   protected Type getElementType(@NotNull Type type) {
      return Classes.getFirstGeneric(type, String.class);
   }

   protected Object convert(List<Object> items, Type componentType) {
      return new LinkedHashSet(items);
   }

   protected boolean preventsDuplicates() {
      return true;
   }
}
