package revxrsal.commands.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Classes {
   private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER;
   private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE;

   private Classes() {
      Preconditions.cannotInstantiate(Classes.class);
   }

   public static Class<?> getType(@NotNull Object o) {
      return o instanceof Class ? (Class)o : o.getClass();
   }

   public static <T> Class<T> wrap(Class<T> type) {
      Preconditions.notNull(type, "type");
      Class<T> wrapped = (Class)PRIMITIVE_TO_WRAPPER.get(type);
      return wrapped == null ? type : wrapped;
   }

   public static @Nullable Type arrayComponentType(Type type) {
      if (type instanceof GenericArrayType) {
         return ((GenericArrayType)type).getGenericComponentType();
      } else {
         return type instanceof Class ? ((Class)type).getComponentType() : null;
      }
   }

   public static <T> Class<T> unwrap(Class<T> type) {
      Preconditions.notNull(type, "type");
      Class<T> unwrapped = (Class)WRAPPER_TO_PRIMITIVE.get(type);
      return unwrapped == null ? type : unwrapped;
   }

   public static boolean isWrapperType(Class<?> type) {
      Preconditions.notNull(type, "type");
      return WRAPPER_TO_PRIMITIVE.containsKey(type);
   }

   public static Class<?> getRawType(Type type) {
      if (type instanceof Class) {
         return (Class)type;
      } else if (type instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType)type;
         Type rawType = parameterizedType.getRawType();
         if (!(rawType instanceof Class)) {
            throw new IllegalStateException("Expected a Class, found a " + rawType);
         } else {
            return (Class)rawType;
         }
      } else if (type instanceof GenericArrayType) {
         Type componentType = ((GenericArrayType)type).getGenericComponentType();
         return Array.newInstance(getRawType(componentType), 0).getClass();
      } else if (type instanceof TypeVariable) {
         return Object.class;
      } else if (type instanceof WildcardType) {
         return getRawType(((WildcardType)type).getUpperBounds()[0]);
      } else {
         String className = type == null ? "null" : type.getClass().getName();
         throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
      }
   }

   /** @deprecated */
   @Deprecated
   @Contract("_,_ -> param2")
   public static Type getFirstGeneric(@NotNull Class<?> cl, @NotNull Type fallback) {
      return fallback;
   }

   public static Type getFirstGeneric(@NotNull Type genericType, @NotNull Type fallback) {
      try {
         return ((ParameterizedType)genericType).getActualTypeArguments()[0];
      } catch (ClassCastException var3) {
         return fallback;
      }
   }

   public static boolean isClassPresent(@NotNull String name) {
      try {
         Class.forName(name);
         return true;
      } catch (Throwable var2) {
         return false;
      }
   }

   private static void addPrimitive(Map<Class<?>, Class<?>> forward, Map<Class<?>, Class<?>> backward, Class<?> key, Class<?> value) {
      forward.put(key, value);
      backward.put(value, key);
   }

   public static void checkRetention(@NotNull Class<? extends Annotation> type) {
      if (!type.isAnnotationPresent(Retention.class) || ((Retention)type.getAnnotation(Retention.class)).value() != RetentionPolicy.RUNTIME) {
         throw new IllegalArgumentException("Tried to check for annotation @" + type.getName() + ", but it does not have @Retention(RetentionPolicy.RUNTIME)! As such, it may be present but we cannot see it.");
      }
   }

   static {
      Map<Class<?>, Class<?>> primToWrap = new LinkedHashMap(16);
      Map<Class<?>, Class<?>> wrapToPrim = new LinkedHashMap(16);
      addPrimitive(primToWrap, wrapToPrim, Boolean.TYPE, Boolean.class);
      addPrimitive(primToWrap, wrapToPrim, Byte.TYPE, Byte.class);
      addPrimitive(primToWrap, wrapToPrim, Character.TYPE, Character.class);
      addPrimitive(primToWrap, wrapToPrim, Double.TYPE, Double.class);
      addPrimitive(primToWrap, wrapToPrim, Float.TYPE, Float.class);
      addPrimitive(primToWrap, wrapToPrim, Integer.TYPE, Integer.class);
      addPrimitive(primToWrap, wrapToPrim, Long.TYPE, Long.class);
      addPrimitive(primToWrap, wrapToPrim, Short.TYPE, Short.class);
      addPrimitive(primToWrap, wrapToPrim, Void.TYPE, Void.class);
      PRIMITIVE_TO_WRAPPER = java.util.Collections.unmodifiableMap(primToWrap);
      WRAPPER_TO_PRIMITIVE = java.util.Collections.unmodifiableMap(wrapToPrim);
   }
}
