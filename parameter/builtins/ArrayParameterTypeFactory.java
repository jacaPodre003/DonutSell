package revxrsal.commands.parameter.builtins;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.util.Classes;

public final class ArrayParameterTypeFactory extends CollectionParameterTypeFactory {
   public static final ArrayParameterTypeFactory INSTANCE = new ArrayParameterTypeFactory();

   protected boolean matchType(@NotNull Type type, @NotNull Class<?> rawType) {
      Type elementType = Classes.arrayComponentType(type);
      return elementType != null;
   }

   protected Type getElementType(@NotNull Type type) {
      return Classes.arrayComponentType(type);
   }

   protected Object convert(List<Object> items, Type componentType) {
      Class<?> arrayComponentType = Classes.getRawType(componentType);
      Object array = Array.newInstance(arrayComponentType, items.size());

      for(int i = 0; i < items.size(); ++i) {
         Array.set(array, i, items.get(i));
      }

      return array;
   }
}
