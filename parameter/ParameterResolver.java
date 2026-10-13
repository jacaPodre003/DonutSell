package revxrsal.commands.parameter;

import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;

public final class ParameterResolver<A extends CommandActor, T> {
   private final Object resolver;

   private ParameterResolver(Object resolver) {
      if (resolver instanceof ParameterType && resolver instanceof ContextParameter) {
         throw new IllegalArgumentException("A ParameterResolver cannot wrap an object that is both a ParameterType and a ContextParameter.");
      } else if (!(resolver instanceof ParameterType) && !(resolver instanceof ContextParameter)) {
         throw new IllegalArgumentException("A ParameterResolver cannot wrap an object that is not a ParameterType or a ContextParameter.");
      } else {
         this.resolver = resolver;
      }
   }

   public static <A extends CommandActor, T> @NotNull ParameterResolver<A, T> parameterType(@NotNull ParameterType<A, T> type) {
      return new ParameterResolver<A, T>(type);
   }

   public static <A extends CommandActor, T> @NotNull ParameterResolver<A, T> contextParameter(@NotNull ContextParameter<A, T> type) {
      return new ParameterResolver<A, T>(type);
   }

   public boolean consumesInput() {
      return this.isParameterType();
   }

   public boolean isParameterType() {
      return this.resolver instanceof ParameterType;
   }

   public boolean isContextParameter() {
      return this.resolver instanceof ContextParameter;
   }

   public @NotNull ParameterType<A, T> requireParameterType() {
      return this.requireParameterType("Expected a ParameterType, received a ContextResolver (resolver: " + this.resolver + ")");
   }

   public @NotNull ParameterType<A, T> requireParameterType(Type typeHint) {
      return this.requireParameterType("Expected a ParameterType, received a ContextResolver (resolver: " + this.resolver + ", type: " + typeHint + ")");
   }

   public @NotNull ContextParameter<A, T> requireContextParameter() {
      return this.requireContextParameter("Expected a ContextResolver, received a ParameterType (resolver: " + this.resolver + ")");
   }

   public @NotNull ContextParameter<A, T> requireContextParameter(Type typeHint) {
      return this.requireContextParameter("Expected a ContextResolver, received a ParameterType (resolver: " + this.resolver + ", type: " + typeHint + ")");
   }

   public @NotNull ParameterType<A, T> requireParameterType(@NotNull String errorMessage) {
      if (!this.isParameterType()) {
         throw new IllegalStateException(errorMessage);
      } else {
         return (ParameterType)this.resolver;
      }
   }

   public @NotNull ContextParameter<A, T> requireContextParameter(@NotNull String errorMessage) {
      if (!this.isContextParameter()) {
         throw new IllegalStateException(errorMessage);
      } else {
         return (ContextParameter)this.resolver;
      }
   }
}
