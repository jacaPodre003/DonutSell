package revxrsal.commands.parameter.builtins;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.util.Classes;

public final class ListParameterTypeFactory extends CollectionParameterTypeFactory {
   public static final ListParameterTypeFactory INSTANCE = new ListParameterTypeFactory();

   private ListParameterTypeFactory() {
   }

   protected boolean matchType(@NotNull Type type, @NotNull Class<?> rawType) {
      return rawType == List.class || rawType == Collection.class;
   }

   protected Type getElementType(@NotNull Type type) {
      return Classes.getFirstGeneric(type, String.class);
   }

   protected Object convert(List<Object> items, Type componentType) {
      return items;
   }
}
