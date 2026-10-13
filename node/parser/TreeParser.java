package revxrsal.commands.node.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.annotation.Flag;
import revxrsal.commands.annotation.NotSender;
import revxrsal.commands.annotation.Single;
import revxrsal.commands.annotation.Switch;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandFunction;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.node.CommandNode;
import revxrsal.commands.parameter.ParameterResolver;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.parameter.StringParameterType;
import revxrsal.commands.parameter.builtins.SenderContextParameter;
import revxrsal.commands.process.SenderResolver;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.stream.StringStream;
import revxrsal.commands.stream.token.LiteralToken;
import revxrsal.commands.stream.token.ParameterToken;
import revxrsal.commands.stream.token.Token;
import revxrsal.commands.stream.token.TokenParser;

public final class TreeParser<A extends CommandActor> {
   private final LinkedList<MutableCommandNode<A>> nodes = new LinkedList();
   private final @NotNull CommandFunction fn;
   private final Lamp<A> lamp;
   private final Map<String, CommandParameter> methodParameters;
   private final Set<String> usedLongNames = new HashSet();
   private final Set<Character> usedShortNames = new HashSet();
   private boolean requireOptionals;
   private boolean requireFlags;

   private TreeParser(@NotNull CommandFunction fn, @NotNull Lamp<A> lamp) {
      this.fn = fn;
      this.methodParameters = new LinkedHashMap(fn.parametersByName());
      this.lamp = lamp;
   }

   public static <A extends CommandActor> @NotNull ExecutableCommand<A> parse(@NotNull CommandFunction function, @NotNull Lamp<A> lamp, @NotNull MutableStringStream input) {
      TreeParser<A> parser = new TreeParser<A>(function, lamp);
      return parser.parse(input);
   }

   private Execution<A> parse(@NotNull MutableStringStream input) {
      ReflectionAction<A> action = new ReflectionAction<A>(this.fn);
      this.checkNotEmpty(input);

      while(input.hasRemaining()) {
         if (input.peek() == ' ') {
            input.moveForward();
         }

         Token firstToken = TokenParser.parseNextToken(input);
         MutableCommandNode<A> node = this.generateNode(firstToken);
         if (this.isOptional(node)) {
            node.setAction(action);
         }

         this.checkOrder(node, input);
         this.pushNode(node);
      }

      if (!this.methodParameters.isEmpty()) {
         List<CommandParameter> arguments = new ArrayList();

         for(CommandParameter param : this.methodParameters.values()) {
            if (!this.addSenderIfFirst(param, action)) {
               ParameterResolver<A, Object> resolver = this.lamp.resolver(param);
               if (resolver.isParameterType()) {
                  arguments.add(param);
               } else if (resolver.isContextParameter()) {
                  action.addContextParameter(param, resolver.requireContextParameter());
               }
            }
         }

         for(CommandParameter argument : arguments) {
            MutableParameterNode<A, Object> node = this.createParameterNode(argument);
            if (this.isOptional(node)) {
               node.setAction(action);
            }

            this.checkOrder(node, input);
            this.pushNode(node);
         }
      }

      MutableCommandNode<A> last = (MutableCommandNode)this.nodes.getLast();
      last.setLast(true);
      last.setAction(action);
      if (this.isParameter(last)) {
         this.setIfGreedy(this.p(last));
      }

      List<CommandNode<A>> executionNodes = new ArrayList();

      for(MutableCommandNode<A> node : this.nodes) {
         if (this.isParameter(node) && this.p(node).type().isGreedy() && !node.isLast()) {
            throw new IllegalArgumentException("Found a greedy parameter (" + node.getName() + ") in the middle of the command. Greedy parameters can only come at the end of the command.");
         }

         executionNodes.add(node.toNode());
      }

      Execution<A> executableCommand = new Execution<A>(this.fn, executionNodes);
      executableCommand.forEach((nodex) -> {
         ((BaseCommandNode)nodex).setCommand(executableCommand);
         ((BaseCommandNode)nodex).setLamp(this.lamp);
      });
      return executableCommand;
   }

   private boolean addSenderIfFirst(CommandParameter param, ReflectionAction<A> action) {
      if (param.methodIndex() == 0 && !param.hasAnnotation(NotSender.class) && !param.annotations().any((a) -> a.annotationType().isAnnotationPresent(NotSender.ImpliesNotSender.class))) {
         for(SenderResolver<? super A> senderResolver : this.lamp.senderResolvers()) {
            if (senderResolver.isSenderType(param)) {
               SenderContextParameter<? super A, Object> resolver = new SenderContextParameter<A, Object>(senderResolver);
               action.addContextParameter(param, resolver);
               return true;
            }
         }
      }

      return false;
   }

   private boolean isGreedy(MutableParameterNode<A, Object> argument) {
      return argument.isLast() && !argument.parameter().annotations().contains(Single.class);
   }

   private void checkOrder(MutableCommandNode<A> node, @NotNull MutableStringStream src) {
      if (this.requireOptionals) {
         if (this.isLiteral(node)) {
            throw new IllegalArgumentException("Found a literal path (" + node.getName() + ") sitting between optional parameters (full path: " + src.source() + "). Optional parameters must all come successively at the end of the command");
         }

         if (this.isParameter(node) && !this.isOptional(node)) {
            throw new IllegalArgumentException("Found a non-optional parameter (" + node.getName() + ") sitting between optional parameters (full path: " + src.source() + "). Optional parameters must all come successively at the end of the command");
         }
      }

      if (this.requireFlags) {
         if (this.isLiteral(node)) {
            throw new IllegalArgumentException("Found a literal path (" + node.getName() + ") sitting between flag/switch parameters (full path: " + src.source() + "). Flags and switches must all come successively at the end of the command");
         }

         if (this.isParameter(node) && !this.isFlagOrSwitch(node)) {
            throw new IllegalArgumentException("Found a non-flag parameter (" + node.getName() + ") sitting between flags/switches parameters (full path: " + src.source() + "). Flags and switches must all come successively at the end of the command");
         }
      }

   }

   private void checkNotEmpty(StringStream input) {
      if (input.hasFinished()) {
         if (input.source().isEmpty()) {
            throw new IllegalStateException("The input is empty");
         } else {
            throw new IllegalStateException("The input has already been consumed. Called parse() twice?");
         }
      }
   }

   private void pushNode(MutableCommandNode<A> node) {
      if (this.nodes.isEmpty() && !this.isLiteral(node)) {
         throw new IllegalArgumentException("First node must be a literal.");
      } else {
         this.validateFlagName(node);
         this.nodes.addLast(node);
      }
   }

   private void validateFlagName(MutableCommandNode<A> node) {
      if (node instanceof MutableParameterNode) {
         MutableParameterNode<?, ?> parameter = (MutableParameterNode)node;
         Switch switchAnn = (Switch)parameter.parameter().getAnnotation(Switch.class);
         Flag flag = (Flag)parameter.parameter().getAnnotation(Flag.class);
         if (flag != null) {
            this.validate(node, flag.value(), flag.shorthand());
         }

         if (switchAnn != null) {
            this.validate(node, switchAnn.value(), switchAnn.shorthand());
         }

      }
   }

   private void validate(MutableCommandNode<A> node, String value, char shorthand) {
      String name = value.isEmpty() ? node.getName() : value;
      Character shortcut = shorthand == 0 ? name.charAt(0) : shorthand;
      if (!this.usedLongNames.add(name)) {
         throw new IllegalArgumentException("Duplicate flag name: " + value);
      } else if (!this.usedShortNames.add(shortcut)) {
         throw new IllegalArgumentException("Duplicate flag shorthand name: " + shortcut);
      }
   }

   private MutableCommandNode<A> generateNode(@NotNull Token token) {
      if (token instanceof LiteralToken) {
         return this.createLiteralNode((LiteralToken)token);
      } else if (token instanceof ParameterToken) {
         return this.createParameterNode((ParameterToken)token);
      } else {
         throw new IllegalArgumentException("Don't know how to deal with token: " + token);
      }
   }

   private @NotNull MutableLiteralNode<A> createLiteralNode(@NotNull LiteralToken token) {
      return new MutableLiteralNode<A>(token.value());
   }

   private boolean isParameter(@NotNull MutableCommandNode<A> node) {
      return node instanceof MutableParameterNode;
   }

   private boolean isLiteral(@NotNull MutableCommandNode<A> node) {
      return node instanceof MutableLiteralNode;
   }

   private boolean isOptional(MutableCommandNode<A> node) {
      return node instanceof MutableParameterNode && ((MutableParameterNode)node).isOptional();
   }

   private boolean isFlagOrSwitch(MutableCommandNode<A> node) {
      return node instanceof MutableParameterNode && (((MutableParameterNode)node).parameter().hasAnnotation(Switch.class) || ((MutableParameterNode)node).parameter().hasAnnotation(Flag.class));
   }

   private MutableParameterNode<A, Object> p(@NotNull MutableCommandNode<A> node) {
      return (MutableParameterNode)node;
   }

   private @NotNull CommandParameter popParameter(@NotNull String name) {
      CommandParameter parameter = (CommandParameter)this.methodParameters.remove(name);
      return parameter;
   }

   private MutableParameterNode<A, Object> createParameterNode(ParameterToken token) {
      CommandParameter parameter = this.popParameter(token.name());
      return this.createParameterNode(parameter);
   }

   private MutableParameterNode<A, Object> createParameterNode(CommandParameter parameter) {
      ParameterType<A, Object> parameterType = this.lamp.resolver(parameter).requireParameterType(parameter.fullType());
      MutableParameterNode<A, Object> argument = new MutableParameterNode<A, Object>(parameter.name());
      argument.setType(parameterType);
      argument.setParameter(parameter);
      if (parameter.isOptional()) {
         argument.setOptional(true);
         this.requireOptionals = true;
      }

      if (parameter.hasAnnotation(Flag.class) || parameter.hasAnnotation(Switch.class)) {
         this.requireFlags = true;
      }

      argument.setPermission(this.lamp.createPermission(parameter.annotations()));
      this.setSuggestions(argument);
      return argument;
   }

   private void setSuggestions(MutableParameterNode<A, Object> argument) {
      SuggestionProvider<A> provider = this.lamp.suggestionProvider(argument.parameter());
      if (provider != SuggestionProvider.empty()) {
         argument.setSuggestions(provider);
      }

   }

   private void setIfGreedy(@NotNull MutableParameterNode<A, Object> argument) {
      if (this.isGreedy(argument) && argument.type().equals(StringParameterType.single()) && !argument.parameter().hasAnnotation(Flag.class)) {
         argument.setType(StringParameterType.greedy());
      }

   }
}
