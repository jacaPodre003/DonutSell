package revxrsal.commands.parameter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.help.Help;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.builtins.ArrayParameterTypeFactory;
import revxrsal.commands.parameter.builtins.EnumParameterTypeFactory;
import revxrsal.commands.parameter.builtins.ListParameterTypeFactory;
import revxrsal.commands.parameter.builtins.OptionalParameterTypeFactory;
import revxrsal.commands.parameter.builtins.ParseWithParameterTypeFactory;
import revxrsal.commands.parameter.builtins.SetParameterTypeFactory;
import revxrsal.commands.parameter.builtins.ValuesParameterTypeFactory;
import revxrsal.commands.parameter.primitives.BooleanParameterType;
import revxrsal.commands.parameter.primitives.ByteParameterType;
import revxrsal.commands.parameter.primitives.CharParameterType;
import revxrsal.commands.parameter.primitives.DoubleParameterType;
import revxrsal.commands.parameter.primitives.FloatParameterType;
import revxrsal.commands.parameter.primitives.IntParameterType;
import revxrsal.commands.parameter.primitives.LongParameterType;
import revxrsal.commands.parameter.primitives.ShortParameterType;
import revxrsal.commands.parameter.primitives.UUIDParameterType;
import revxrsal.commands.stream.StringStream;

public final class ParameterTypes<A extends CommandActor> {
   private static final List<ParameterFactory> HIGHEST_PRIORITY_FACTORIES;
   private static final List<ParameterFactory> DEFAULT_FACTORIES;
   private final List<ParameterFactory> factories;
   private final int lastIndex;

   private ParameterTypes(@NotNull Builder<A> builder) {
      List<ParameterFactory> factories = new ArrayList(builder.factories.size() + DEFAULT_FACTORIES.size());
      factories.addAll(HIGHEST_PRIORITY_FACTORIES);
      factories.addAll(builder.factories);
      factories.addAll(DEFAULT_FACTORIES);
      this.factories = factories;
      this.lastIndex = builder.lastIndex;
   }

   private static boolean consumesInput(@NotNull ParameterFactory factory) {
      return factory instanceof ParameterType.Factory;
   }

   private static <A extends CommandActor, T> @Nullable ParameterResolver<A, T> toParameterResolver(Type type, AnnotationList annotations, Lamp<A> lamp, ParameterFactory factory) {
      if (factory instanceof ParameterType.Factory) {
         ParameterType<A, T> parameterType = ((ParameterType.Factory)factory).create(type, annotations, lamp);
         if (parameterType != null) {
            return ParameterResolver.<A, T>parameterType(parameterType);
         }
      } else if (factory instanceof ContextParameter.Factory) {
         ContextParameter<A, T> contextParameter = ((ContextParameter.Factory)factory).create(type, annotations, lamp);
         if (contextParameter != null) {
            return ParameterResolver.<A, T>contextParameter(contextParameter);
         }
      }

      return null;
   }

   @Contract(
      value = "-> new",
      pure = true
   )
   public static <A extends CommandActor> @NotNull Builder<A> builder() {
      return new Builder<A>();
   }

   public <T> @NotNull ParameterResolver<A, T> resolver(Type type, AnnotationList annotations, Lamp<A> lamp) {
      for(ParameterFactory factory : this.factories) {
         ParameterResolver<A, T> parameterType = toParameterResolver(type, annotations, lamp, factory);
         if (parameterType != null) {
            return parameterType;
         }
      }

      throw new IllegalArgumentException("Failed to find a parameter resolver for type " + type);
   }

   public <T> ParameterResolver<A, T> findNextResolver(Type type, AnnotationList annotations, ParameterFactory skipPast, Lamp<A> lamp) {
      int skipPastIndex = this.factories.indexOf(skipPast);
      if (skipPastIndex == -1) {
         throw new IllegalArgumentException("Don't know how to skip past unknown resolver factory: " + skipPastIndex + " (it isn't registered?)");
      } else {
         int i = skipPastIndex + 1;

         for(int size = this.factories.size(); i < size; ++i) {
            ParameterFactory factory = (ParameterFactory)this.factories.get(i);
            if (consumesInput(skipPast) == consumesInput(factory)) {
               ParameterResolver<A, T> parameterType = toParameterResolver(type, annotations, lamp, factory);
               if (parameterType != null) {
                  return parameterType;
               }
            }
         }

         throw new IllegalArgumentException("Failed to find the next resolver for type " + type + " with annotations " + annotations);
      }
   }

   @Contract(
      value = "-> new",
      pure = true
   )
   public @NotNull Builder<A> toBuilder() {
      Builder<A> result = new Builder<A>();

      for(int i = HIGHEST_PRIORITY_FACTORIES.size(); i < this.lastIndex; ++i) {
         result.addFactory((ParameterFactory)this.factories.get(i));
      }

      int i = this.lastIndex;

      for(int limit = this.factories.size() - DEFAULT_FACTORIES.size(); i < limit; ++i) {
         result.addFactoryLast((ParameterFactory)this.factories.get(i));
      }

      return result;
   }

   static {
      HIGHEST_PRIORITY_FACTORIES = Collections.singletonList(ParseWithParameterTypeFactory.INSTANCE);
      DEFAULT_FACTORIES = Arrays.asList(ArrayParameterTypeFactory.INSTANCE, ListParameterTypeFactory.INSTANCE, SetParameterTypeFactory.INSTANCE, EnumParameterTypeFactory.INSTANCE, ValuesParameterTypeFactory.INSTANCE, OptionalParameterTypeFactory.INSTANCE, ParameterType.Factory.forType(Integer.TYPE, new IntParameterType()), ParameterType.Factory.forType(Double.TYPE, new DoubleParameterType()), ParameterType.Factory.forType(Long.TYPE, new LongParameterType()), ParameterType.Factory.forType(Float.TYPE, new FloatParameterType()), ParameterType.Factory.forType(Byte.TYPE, new ByteParameterType()), ParameterType.Factory.forType(Short.TYPE, new ShortParameterType()), ParameterType.Factory.forType(Character.TYPE, new CharParameterType()), ParameterType.Factory.forType(Boolean.TYPE, new BooleanParameterType()), ParameterType.Factory.forType(UUID.class, new UUIDParameterType()), ParameterType.Factory.forType(String.class, StringParameterType.single()), ContextParameter.Factory.forType(StringStream.class, (parameter, context) -> context.input()), ContextParameter.Factory.forType(ExecutableCommand.class, (parameter, context) -> context.command()), ContextParameter.Factory.forType(Lamp.class, (parameter, context) -> context.lamp()), ContextParameter.Factory.forType(ExecutionContext.class, (parameter, context) -> context), ContextParameter.Factory.forTypeAndSubclasses(CommandActor.class, (parameter, context) -> context.actor()), ContextParameter.Factory.forType(Help.RelatedCommands.class, (parameter, context) -> context.command().relatedCommands(context.actor())), ContextParameter.Factory.forType(Help.SiblingCommands.class, (parameter, context) -> context.command().siblingCommands(context.actor())), ContextParameter.Factory.forType(Help.ChildrenCommands.class, (parameter, context) -> context.command().childrenCommands(context.actor())));
   }

   public static class Builder<A extends CommandActor> {
      private final List<ParameterFactory> factories = new ArrayList();
      private int lastIndex = 0;

      public <T> Builder<A> addParameterType(@NotNull Class<T> parameterClass, @NotNull ParameterType<? super A, T> type) {
         return this.addFactory(ParameterType.Factory.forType(parameterClass, type));
      }

      public <T> Builder<A> addParameterTypeLast(@NotNull Class<T> parameterClass, @NotNull ParameterType<? super A, T> parameterType) {
         return this.addFactoryLast(ParameterType.Factory.forType(parameterClass, parameterType));
      }

      public Builder<A> addParameterTypeFactory(ParameterType.@NotNull Factory<? super A> factory) {
         return this.addFactory(factory);
      }

      public Builder<A> addParameterTypeFactoryLast(ParameterType.@NotNull Factory<? super A> factory) {
         return this.addFactoryLast(factory);
      }

      public <T> Builder<A> addContextParameter(@NotNull Class<T> parameterClass, @NotNull ContextParameter<? super A, T> type) {
         return this.addFactory(ContextParameter.Factory.forType(parameterClass, type));
      }

      public <T> Builder<A> addContextParameterLast(@NotNull Class<T> parameterClass, @NotNull ContextParameter<? super A, T> contextParameter) {
         return this.addFactoryLast(ContextParameter.Factory.forType(parameterClass, contextParameter));
      }

      public Builder<A> addContextParameterFactory(ContextParameter.@NotNull Factory<? super A> factory) {
         return this.addFactory(factory);
      }

      public Builder<A> addContextParameterFactoryLast(ContextParameter.@NotNull Factory<? super A> factory) {
         return this.addFactoryLast(factory);
      }

      private Builder<A> addFactory(@NotNull ParameterFactory factory) {
         this.factories.add(this.lastIndex++, factory);
         return this;
      }

      private Builder<A> addFactoryLast(@NotNull ParameterFactory factory) {
         this.factories.add(factory);
         return this;
      }

      @Contract(
         pure = true,
         value = "-> new"
      )
      public @NotNull ParameterTypes<A> build() {
         return new ParameterTypes<A>(this);
      }
   }
}
