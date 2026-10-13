package revxrsal.commands;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.ApiStatus.Internal;
import revxrsal.commands.annotation.dynamic.AnnotationReplacer;
import revxrsal.commands.annotation.list.AnnotationList;
import revxrsal.commands.autocomplete.AutoCompleter;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.autocomplete.SuggestionProviders;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.command.CommandPermission;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.command.PermissionConditionChecker;
import revxrsal.commands.command.ThreadExecutorCooldownCondition;
import revxrsal.commands.exception.CommandExceptionHandler;
import revxrsal.commands.exception.CommandInvocationException;
import revxrsal.commands.exception.DefaultExceptionHandler;
import revxrsal.commands.exception.SelfHandledException;
import revxrsal.commands.exception.ThrowableFromCommand;
import revxrsal.commands.exception.context.ErrorContext;
import revxrsal.commands.hook.Hooks;
import revxrsal.commands.ktx.KotlinFeatureRegistry;
import revxrsal.commands.node.CommandRegistry;
import revxrsal.commands.node.DispatcherSettings;
import revxrsal.commands.node.ParameterNamingStrategy;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.node.parser.BaseCommandRegistry;
import revxrsal.commands.orphan.OrphanCommand;
import revxrsal.commands.orphan.OrphanRegistry;
import revxrsal.commands.orphan.Orphans;
import revxrsal.commands.parameter.CommandActorSenderResolver;
import revxrsal.commands.parameter.LengthChecker;
import revxrsal.commands.parameter.ParameterFactory;
import revxrsal.commands.parameter.ParameterResolver;
import revxrsal.commands.parameter.ParameterTypes;
import revxrsal.commands.parameter.RangeChecker;
import revxrsal.commands.process.CommandCondition;
import revxrsal.commands.process.MessageSender;
import revxrsal.commands.process.ParameterValidator;
import revxrsal.commands.process.SenderResolver;
import revxrsal.commands.response.CompletionStageResponseHandler;
import revxrsal.commands.response.OptionalResponseHandler;
import revxrsal.commands.response.ResponseHandler;
import revxrsal.commands.response.SupplierResponseHandler;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.stream.StringStream;
import revxrsal.commands.util.Classes;
import revxrsal.commands.util.Collections;
import revxrsal.commands.util.Preconditions;

public final class Lamp<A extends CommandActor> {
   private final Map<Class<? extends Annotation>, Set<AnnotationReplacer<?>>> annotationReplacers;
   private final ParameterNamingStrategy parameterNamingStrategy;
   private final ParameterTypes<A> parameterTypes;
   private final SuggestionProviders<A> suggestionProviders;
   private final Hooks<A> hooks;
   private final List<SenderResolver<? super A>> senderResolvers;
   private final List<ParameterValidator<A, Object>> validators;
   private final List<CommandCondition<? super A>> commandConditions;
   private final List<ResponseHandler.Factory<? super A>> responseHandlers;
   private final List<CommandPermission.Factory<? super A>> permissionFactories;
   private final MessageSender<? super A, String> messageSender;
   private final MessageSender<? super A, String> errorSender;
   private final Map<Class<?>, Supplier<Object>> dependencies;
   private final CommandExceptionHandler<A> exceptionHandler;
   private final DispatcherSettings<A> dispatcherSettings;
   private final BaseCommandRegistry<A> tree;
   private final AutoCompleter<A> autoCompleter;

   public Lamp(Builder<A> builder) {
      this.annotationReplacers = Collections.<Class<? extends Annotation>, Set<AnnotationReplacer<?>>>copyMap(builder.annotationReplacers);
      this.senderResolvers = Collections.<SenderResolver<A>>copyList(builder.senderResolvers);
      this.validators = Collections.<ParameterValidator<A, Object>>copyList(builder.validators);
      this.responseHandlers = Collections.<ResponseHandler.Factory<A>>copyList(builder.responseHandlers);
      this.commandConditions = Collections.<CommandCondition<A>>copyList(builder.conditions);
      this.permissionFactories = Collections.<CommandPermission.Factory<A>>copyList(builder.permissionFactories);
      this.dependencies = Collections.<Class<?>, Supplier<Object>>copyMap(builder.dependencies);
      this.messageSender = builder.messageSender;
      this.errorSender = builder.errorSender;
      this.parameterNamingStrategy = builder.namingStrategy;
      this.parameterTypes = builder.parameterTypes.build();
      this.suggestionProviders = builder.suggestionProviders.build();
      this.hooks = builder.hooks.build();
      this.exceptionHandler = builder.exceptionHandler;
      this.dispatcherSettings = builder.dispatcherSettings.build();
      this.tree = new BaseCommandRegistry<A>(this);
      this.autoCompleter = AutoCompleter.<A>create(this);
   }

   public static <A extends CommandActor> @NotNull Builder<A> builder() {
      return new Builder<A>();
   }

   public <T> @NotNull ParameterResolver<A, T> resolver(@NotNull CommandParameter parameter) {
      return this.resolver(parameter.fullType(), parameter.annotations());
   }

   public <T> @NotNull ParameterResolver<A, T> resolver(@NotNull Type type) {
      return this.resolver(type, AnnotationList.empty());
   }

   public <T> @NotNull ParameterResolver<A, T> resolver(@NotNull Type type, @NotNull AnnotationList annotations) {
      return this.parameterTypes.resolver(type, annotations, this);
   }

   public <T> @NotNull ParameterResolver<A, T> findNextResolver(@NotNull Type type, @NotNull AnnotationList annotations, @NotNull ParameterFactory skipPast) {
      return this.parameterTypes.findNextResolver(type, annotations, skipPast, this);
   }

   public @NotNull SuggestionProvider<A> suggestionProvider(Type type) {
      return this.suggestionProvider(type, AnnotationList.empty());
   }

   public @NotNull SuggestionProvider<A> suggestionProvider(Type type, AnnotationList annotations) {
      return this.suggestionProviders.provider(type, annotations, this);
   }

   public @NotNull SuggestionProvider<A> suggestionProvider(CommandParameter parameter) {
      return this.suggestionProviders.provider(parameter, this);
   }

   public SuggestionProvider<A> findNextSuggestionProvider(Type type, AnnotationList annotations, SuggestionProvider.Factory<? super A> skipPast, Lamp<A> lamp) {
      return this.suggestionProviders.findNextProvider(type, annotations, skipPast, lamp);
   }

   public <T> @NotNull ResponseHandler<A, T> responseHandler(@NotNull Type type, @NotNull AnnotationList annotations) {
      for(ResponseHandler.Factory<? super A> responseHandler : this.responseHandlers) {
         ResponseHandler<A, Object> handler = responseHandler.create(type, annotations, this);
         if (handler != null) {
            return handler;
         }
      }

      return ResponseHandler.<A, T>noOp();
   }

   public @NotNull @Unmodifiable List<ExecutableCommand<A>> register(Object... instances) {
      List<ExecutableCommand<A>> registered = new ArrayList();

      for(Object instance : instances) {
         Class<?> commandClass = instance instanceof Class ? (Class)instance : instance.getClass();
         if (instance instanceof OrphanCommand) {
            throw new IllegalArgumentException("You cannot register an OrphanCommand directly! You must wrap it using Orphans.path(...).handler(OrphanCommand)");
         }

         if (instance instanceof Orphans) {
            throw new IllegalArgumentException("You forgot to call .handler(OrphanCommand) in your Orphans.path(...)!");
         }

         if (instance instanceof OrphanRegistry) {
            OrphanRegistry registry = (OrphanRegistry)instance;
            commandClass = registry.handler().getClass();
            Object var9 = registry.handler();
            registered.addAll(this.tree.register(commandClass, var9, registry.paths()));
         } else {
            registered.addAll(this.tree.register(commandClass, instance));
         }
      }

      return registered;
   }

   public void unregister(@NotNull ExecutableCommand<A> execution) {
      if (this.hooks.onCommandUnregistered(execution)) {
         this.tree.unregister(execution);
      }

   }

   public void unregisterAllCommands() {
      Hooks var10001 = this.hooks;
      this.tree.unregisterIf(var10001::onCommandUnregistered);
   }

   public void unregisterIf(@NotNull Predicate<ExecutableCommand<A>> commandPredicate) {
      this.tree.unregisterIf((command) -> commandPredicate.test(command) && this.hooks.onCommandUnregistered(command));
   }

   public void dispatch(@NotNull A actor, String input) {
      MutableStringStream stream = StringStream.createMutable(input);
      this.tree.execute(actor, stream);
   }

   public void dispatch(@NotNull A actor, StringStream input) {
      MutableStringStream stream = input.isMutable() ? (MutableStringStream)input : input.toMutableCopy();
      this.tree.execute(actor, stream);
   }

   public @NotNull CommandRegistry<A> registry() {
      return this.tree;
   }

   public @Unmodifiable @NotNull Map<Class<? extends Annotation>, Set<AnnotationReplacer<?>>> annotationReplacers() {
      return this.annotationReplacers;
   }

   public @Unmodifiable @NotNull List<CommandCondition<? super A>> commandConditions() {
      return this.commandConditions;
   }

   public @NotNull ParameterNamingStrategy parameterNamingStrategy() {
      return this.parameterNamingStrategy;
   }

   public @NotNull ParameterTypes<A> parameterTypes() {
      return this.parameterTypes;
   }

   public @NotNull SuggestionProviders<A> suggestionProviders() {
      return this.suggestionProviders;
   }

   public @NotNull Hooks<A> hooks() {
      return this.hooks;
   }

   public @Unmodifiable @NotNull List<SenderResolver<? super A>> senderResolvers() {
      return this.senderResolvers;
   }

   public @Unmodifiable @NotNull List<ParameterValidator<A, Object>> parameterValidators() {
      return this.validators;
   }

   public <T> @NotNull T dependency(@NotNull Class<T> type) {
      Supplier<T> supplier = (Supplier)this.dependencies.get(type);
      if (supplier == null) {
         throw new IllegalStateException("Cannot find a suitable dependency for type " + type);
      } else {
         T value = (T)supplier.get();
         return value;
      }
   }

   @Internal
   public <T> void validate(A actor, T value, ParameterNode<A, T> parameter) {
      for(ParameterValidator<A, Object> validator : this.parameterValidators()) {
         validator.validate(actor, value, parameter, this);
      }

   }

   public @NotNull AutoCompleter<A> autoCompleter() {
      return this.autoCompleter;
   }

   public void handleException(@NotNull Throwable throwable, @NotNull ErrorContext<A> errorContext) {
      Preconditions.notNull(throwable, "throwable");

      try {
         if (throwable instanceof SelfHandledException) {
            SelfHandledException<A> she = (SelfHandledException)throwable;
            she.handle(errorContext);
         }

         if (throwable.getClass().isAnnotationPresent(ThrowableFromCommand.class)) {
            this.exceptionHandler.handleException(throwable, errorContext);
         } else {
            this.dispatcherSettings.stackTraceSanitizer().sanitize(throwable);
            this.exceptionHandler.handleException(new CommandInvocationException(throwable), errorContext);
         }

      } catch (Throwable t) {
         throw new IllegalStateException("The CommandExceptionHandler threw an exception", t);
      }
   }

   public @NotNull CommandPermission<A> createPermission(AnnotationList annotations) {
      for(CommandPermission.Factory<? super A> permissionFactory : this.permissionFactories) {
         CommandPermission<A> permission = permissionFactory.create(annotations, this);
         if (permission != null) {
            return permission;
         }
      }

      return CommandPermission.<A>alwaysTrue();
   }

   public @NotNull MessageSender<? super A, String> messageSender() {
      return this.messageSender;
   }

   public @NotNull MessageSender<? super A, String> errorSender() {
      return this.errorSender;
   }

   public @NotNull DispatcherSettings<A> dispatcherSettings() {
      return this.dispatcherSettings;
   }

   @Contract("_ -> this")
   public @NotNull Lamp<A> accept(@NotNull LampVisitor<A> visitor) {
      visitor.visit(this);
      return this;
   }

   public static class Builder<A extends CommandActor> {
      private final ParameterTypes.Builder<A> parameterTypes = ParameterTypes.<A>builder();
      private final SuggestionProviders.Builder<A> suggestionProviders = SuggestionProviders.<A>builder();
      private final Hooks.Builder<A> hooks = Hooks.<A>builder();
      private final List<ParameterValidator<A, Object>> validators = new ArrayList();
      private final List<ResponseHandler.Factory<? super A>> responseHandlers = new ArrayList();
      private final Map<Class<? extends Annotation>, Set<AnnotationReplacer<?>>> annotationReplacers = new LinkedHashMap();
      private final List<SenderResolver<? super A>> senderResolvers = new ArrayList();
      private final List<CommandCondition<? super A>> conditions = new ArrayList();
      private final List<CommandPermission.Factory<A>> permissionFactories = new ArrayList();
      private final Map<Class<?>, Supplier<Object>> dependencies = new HashMap();
      private DispatcherSettings.Builder<A> dispatcherSettings = DispatcherSettings.<A>builder();
      private MessageSender<? super A, String> messageSender = CommandActor::sendRawMessage;
      private MessageSender<? super A, String> errorSender = CommandActor::sendRawError;
      private CommandExceptionHandler<A> exceptionHandler = new DefaultExceptionHandler<A>();
      private ParameterNamingStrategy namingStrategy = ParameterNamingStrategy.lowerCaseWithSpace();

      public Builder() {
         this.parameterValidator(Number.class, RangeChecker.INSTANCE);
         this.parameterValidator(String.class, LengthChecker.INSTANCE);
         this.senderResolver(CommandActorSenderResolver.INSTANCE);
         this.responseHandler(SupplierResponseHandler.INSTANCE);
         this.responseHandler(CompletionStageResponseHandler.INSTANCE);
         this.responseHandler(OptionalResponseHandler.INSTANCE);
         this.commandCondition(PermissionConditionChecker.INSTANCE);
         ThreadExecutorCooldownCondition cooldownCondition = new ThreadExecutorCooldownCondition();
         this.commandCondition(cooldownCondition);
         this.parameterTypes().addContextParameterFactoryLast(cooldownCondition);
         this.hooks().onPostCommandExecuted(cooldownCondition);
         this.accept(KotlinFeatureRegistry.INSTANCE);
      }

      public @NotNull Builder<A> parameterNamingStrategy(ParameterNamingStrategy namingStrategy) {
         this.namingStrategy = (ParameterNamingStrategy)Preconditions.notNull(namingStrategy, "naming strategy");
         return this;
      }

      public ParameterTypes.@NotNull Builder<A> parameterTypes() {
         return this.parameterTypes;
      }

      public @NotNull Builder<A> parameterTypes(@NotNull Consumer<ParameterTypes.Builder<A>> consumer) {
         Preconditions.notNull(consumer, "consumer");
         consumer.accept(this.parameterTypes);
         return this;
      }

      public SuggestionProviders.@NotNull Builder<A> suggestionProviders() {
         return this.suggestionProviders;
      }

      public @NotNull Builder<A> suggestionProviders(@NotNull Consumer<SuggestionProviders.Builder<A>> consumer) {
         Preconditions.notNull(consumer, "consumer");
         consumer.accept(this.suggestionProviders);
         return this;
      }

      public @NotNull Builder<A> dispatcherSettings(@NotNull Consumer<DispatcherSettings.Builder<A>> consumer) {
         Preconditions.notNull(consumer, "consumer");
         consumer.accept(this.dispatcherSettings);
         return this;
      }

      public DispatcherSettings.@NotNull Builder<A> dispatcherSettings() {
         return this.dispatcherSettings;
      }

      public @NotNull Builder<A> dispatcherSettings(@NotNull DispatcherSettings<A> settings) {
         Preconditions.notNull(settings, "dispatcher settings");
         this.dispatcherSettings = settings.toBuilder();
         return this;
      }

      public Hooks.@NotNull Builder<A> hooks() {
         return this.hooks;
      }

      public @NotNull Builder<A> hooks(@NotNull Consumer<Hooks.Builder<A>> consumer) {
         Preconditions.notNull(consumer, "consumer");
         consumer.accept(this.hooks);
         return this;
      }

      public <T extends Annotation> Builder<A> annotationReplacer(@NotNull Class<T> annotationType, @NotNull AnnotationReplacer<T> replacer) {
         Preconditions.notNull(annotationType, "annotation type");
         Preconditions.notNull(replacer, "annotation replacer");
         Classes.checkRetention(annotationType);
         ((Set)this.annotationReplacers.computeIfAbsent(annotationType, (k) -> new HashSet())).add(replacer);
         return this;
      }

      public Builder<A> senderResolver(@NotNull SenderResolver<? super A> resolver) {
         Preconditions.notNull(resolver, "sender resolver");
         this.senderResolvers.add(resolver);
         return this;
      }

      public <T> Builder<A> parameterValidator(Class<T> type, @NotNull ParameterValidator<? super A, T> validator) {
         Preconditions.notNull(type, "type");
         Preconditions.notNull(validator, "parameter validator");
         Class<?> wrapped = Classes.wrap(type);
         this.validators.add((ParameterValidator)(actor, value, parameter, lamp) -> {
            if (wrapped.isAssignableFrom(Classes.wrap(parameter.type()))) {
               validator.validate(actor, value, parameter, lamp);
            }
         });
         return this;
      }

      public <T> Builder<A> responseHandler(Class<T> type, @NotNull ResponseHandler<? super A, T> responseHandler) {
         Preconditions.notNull(type, "type");
         return this.responseHandler(ResponseHandler.Factory.forType(type, responseHandler));
      }

      public Builder<A> responseHandler(ResponseHandler.@NotNull Factory<? super A> responseHandler) {
         Preconditions.notNull(responseHandler, "response handler");
         this.responseHandlers.add(responseHandler);
         return this;
      }

      public Builder<A> commandCondition(@NotNull CommandCondition<? super A> condition) {
         Preconditions.notNull(condition, "command condition");
         this.conditions.add(condition);
         return this;
      }

      public Builder<A> permissionFactory(CommandPermission.@NotNull Factory<? super A> factory) {
         Preconditions.notNull(factory, "permission factory");
         this.permissionFactories.add(factory);
         return this;
      }

      public <T extends Annotation> Builder<A> permissionForAnnotation(@NotNull Class<T> annotationType, @NotNull Function<@NotNull T, @Nullable CommandPermission<A>> permissionCreator) {
         Preconditions.notNull(annotationType, "annotation type");
         Preconditions.notNull(permissionCreator, "permission creator");
         this.permissionFactories.add(CommandPermission.Factory.forAnnotation(annotationType, permissionCreator));
         return this;
      }

      public Builder<A> exceptionHandler(@NotNull CommandExceptionHandler<A> handler) {
         Preconditions.notNull(handler, "exception handler");
         this.exceptionHandler = handler;
         return this;
      }

      public <T> Builder<A> dependency(Class<T> dependencyType, @NotNull T dependency) {
         Preconditions.notNull(dependencyType, "dependency type");
         Preconditions.notNull(dependency, "dependency");
         this.dependencies.put(dependencyType, (Supplier)() -> dependency);
         return this;
      }

      public <T> Builder<A> dependency(Class<T> dependencyType, @NotNull Supplier<T> dependency) {
         Preconditions.notNull(dependencyType, "dependency type");
         Preconditions.notNull(dependency, "dependency");
         this.dependencies.put(dependencyType, dependency);
         return this;
      }

      public Builder<A> defaultMessageSender(@NotNull MessageSender<? super A, String> messageSender) {
         Preconditions.notNull(messageSender, "message sender");
         this.messageSender = messageSender;
         return this;
      }

      public Builder<A> defaultErrorSender(@NotNull MessageSender<? super A, String> messageSender) {
         Preconditions.notNull(messageSender, "message sender");
         this.errorSender = messageSender;
         return this;
      }

      @Contract("_ -> this")
      public @NotNull Builder<A> accept(@NotNull LampBuilderVisitor<? super A> visitor) {
         visitor.visit(this);
         return this;
      }

      @Contract(
         pure = true,
         value = "-> new"
      )
      public Lamp<A> build() {
         return new Lamp<A>(this);
      }
   }
}
