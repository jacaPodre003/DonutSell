package revxrsal.commands.brigadier.types;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.util.Preconditions;

public final class ArgumentTypes<A extends CommandActor> {
   private static final List<ArgumentTypeFactory<?>> HIGHEST_PRIORITY;
   private static final List<ArgumentTypeFactory<?>> DEFAULT_FACTORIES;
   private final List<ArgumentTypeFactory<? super A>> factories;
   private final int lastIndex;

   public ArgumentTypes(@NotNull Builder<A> builder) {
      List<ArgumentTypeFactory<? super A>> factories = new ArrayList(builder.factories.size() + DEFAULT_FACTORIES.size());
      factories.addAll(HIGHEST_PRIORITY);
      factories.addAll(builder.factories);
      factories.addAll(DEFAULT_FACTORIES);
      this.factories = factories;
      this.lastIndex = builder.lastIndex;
   }

   @Contract(
      value = "-> new",
      pure = true
   )
   public static <A extends CommandActor> @NotNull Builder<A> builder() {
      return new Builder<A>();
   }

   public @NotNull ArgumentType<?> type(@NotNull ParameterNode<A, ?> parameter) {
      Preconditions.notNull(parameter, "parameter");

      for(ArgumentTypeFactory<? super A> factory : this.factories) {
         ArgumentType<?> provider = factory.getArgumentType(parameter);
         if (provider != null) {
            return provider;
         }
      }

      return parameter.isGreedy() ? StringArgumentType.greedyString() : StringArgumentType.string();
   }

   @Contract(
      value = "-> new",
      pure = true
   )
   public @NotNull Builder<A> toBuilder() {
      Builder<A> result = new Builder<A>();

      for(int i = HIGHEST_PRIORITY.size(); i < this.lastIndex; ++i) {
         result.addTypeFactory((ArgumentTypeFactory)this.factories.get(i));
      }

      int i = this.lastIndex;

      for(int limit = this.factories.size() - DEFAULT_FACTORIES.size(); i < limit; ++i) {
         result.addTypeFactoryLast((ArgumentTypeFactory)this.factories.get(i));
      }

      return result;
   }

   static {
      HIGHEST_PRIORITY = Collections.singletonList(BTypeFactory.INSTANCE);
      DEFAULT_FACTORIES = Arrays.asList(DefaultTypeFactories.LONG, DefaultTypeFactories.INTEGER, DefaultTypeFactories.SHORT, DefaultTypeFactories.BYTE, DefaultTypeFactories.DOUBLE, DefaultTypeFactories.FLOAT, DefaultTypeFactories.BOOLEAN, DefaultTypeFactories.CHAR, DefaultTypeFactories.STRING);
   }

   public static class Builder<A extends CommandActor> {
      private final List<ArgumentTypeFactory<? super A>> factories = new ArrayList();
      private int lastIndex = 0;

      public @NotNull Builder<A> addType(Class<?> type, @NotNull ArgumentType<?> argumentType) {
         Preconditions.notNull(type, "type");
         this.addTypeFactory(ArgumentTypeFactory.forType(type, argumentType));
         return this;
      }

      public @NotNull Builder<A> addTypeLast(Class<?> type, @NotNull ArgumentType<?> argumentType) {
         Preconditions.notNull(type, "type");
         return this.addTypeFactoryLast(ArgumentTypeFactory.forType(type, argumentType));
      }

      public @NotNull Builder<A> addTypeFactory(@NotNull ArgumentTypeFactory<? super A> factory) {
         Preconditions.notNull(factory, "factory");
         this.factories.add(this.lastIndex++, factory);
         return this;
      }

      public @NotNull Builder<A> addTypeFactoryLast(@NotNull ArgumentTypeFactory<? super A> factory) {
         Preconditions.notNull(factory, "factory");
         this.factories.add(factory);
         return this;
      }

      @Contract(
         value = "-> new",
         pure = true
      )
      public @NotNull ArgumentTypes<A> build() {
         return new ArgumentTypes<A>(this);
      }
   }
}
