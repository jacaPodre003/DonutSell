package revxrsal.commands.brigadier;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.Lamp;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.command.CommandPermission;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.node.ParameterNode;
import revxrsal.commands.stream.MutableStringStream;
import revxrsal.commands.stream.StringStream;
import revxrsal.commands.util.Collections;
import revxrsal.commands.util.Permutations;
import revxrsal.commands.util.Strings;

public final class BrigadierParser<S, A extends CommandActor> {
   private final @NotNull BrigadierConverter<A, S> converter;

   public BrigadierParser(@NotNull BrigadierConverter<A, S> converter) {
      this.converter = converter;
   }

   public static <S> void addChild(CommandNode<S> p, CommandNode<S> node) {
      if (node instanceof RootCommandNode) {
         throw new UnsupportedOperationException("Cannot add a RootCommandNode as a child to any other CommandNode");
      } else {
         CommandNode<S> child = (CommandNode)Nodes.getChildren(p).get(node.getName());
         if (child != null) {
            if (node.getCommand() != null) {
               Nodes.setCommand(child, node.getCommand());
            }

            Nodes.setRequirement(child, child.getRequirement().or(node.getRequirement()));

            for(CommandNode<S> grandchild : node.getChildren()) {
               addChild(child, grandchild);
            }
         } else {
            Nodes.getChildren(p).put(node.getName(), node);
            if (node instanceof LiteralCommandNode) {
               Map<String, LiteralCommandNode<S>> literals = Nodes.getLiterals(p);
               if (literals != null) {
                  literals.put(node.getName(), node);
               } else {
                  Nodes.setHasLiterals(p, true);
               }
            } else if (node instanceof ArgumentCommandNode) {
               Nodes.getArguments(p).put(node.getName(), node);
            }
         }

      }
   }

   @NotNull
   public LiteralCommandNode<S> createNode(@NotNull ExecutableCommand<A> command) {
      BNode<S> firstNode = BNode.<S>literal(command.firstNode().name());
      firstNode.requires(this.createRequirement(command.permission(), command.lamp()));
      BNode<S> lastNode = firstNode;
      List<revxrsal.commands.node.CommandNode<A>> nodes = command.nodes();

      for(int i = 1; i < nodes.size(); ++i) {
         revxrsal.commands.node.CommandNode<A> node = (revxrsal.commands.node.CommandNode)nodes.get(i);
         BNode<S> elementNode;
         if (node.isLiteral()) {
            elementNode = BNode.<S>literal(node.name());
            elementNode.requires(this.createRequirement(command.permission(), command.lamp()));
         } else {
            if (!(node instanceof ParameterNode)) {
               throw new UnsupportedOperationException();
            }

            ParameterNode<A, ?> parameter = (ParameterNode)node;
            if (parameter.isSwitch() || parameter.isFlag()) {
               break;
            }

            elementNode = BNode.<S>of(this.ofParameter(parameter));
            if (parameter.isOptional()) {
               lastNode.executes(this.createAction(command));
            }
         }

         lastNode.then(elementNode);
         lastNode = elementNode;
      }

      if (!command.containsFlags()) {
         lastNode.executes(this.createAction(command));
         return (LiteralCommandNode)firstNode.asBrigadierNode();
      } else {
         List<ParameterNode<A, Object>> flags = Collections.<ParameterNode<A, Object>>filter(command.parameters().values(), (v) -> v.isFlag() || v.isSwitch());
         if (command.flagCount() > 4) {
            List<BNode<S>> addOptionalsTo = new ArrayList();

            for(ParameterNode<A, Object> parameter : flags) {
               if (parameter.isSwitch()) {
                  BNode<S> ofSwitch = this.ofSwitch(parameter);
                  addOptionalsTo.forEach((genNode) -> {
                     genNode.then(ofSwitch);
                     genNode.executes(this.createAction(command));
                  });
                  lastNode.then(ofSwitch);
                  lastNode.executes(this.createAction(command));
                  addOptionalsTo.add(ofSwitch);
               } else if (parameter.isFlag()) {
                  BNode<S> ofFlag = this.ofFlag(parameter);
                  addOptionalsTo.forEach((genNode) -> genNode.then(ofFlag));
                  if (parameter.isOptional()) {
                     if (addOptionalsTo.isEmpty()) {
                        lastNode.executes(this.createAction(command));
                     } else {
                        addOptionalsTo.forEach((genNode) -> genNode.executes(this.createAction(command)));
                     }
                  }

                  lastNode.then(ofFlag);
                  addOptionalsTo.add(ofFlag.nextChild());
                  if (parameter.isRequired()) {
                     lastNode = ofFlag.nextChild();
                  }
               }
            }

            if (!addOptionalsTo.isEmpty()) {
               addOptionalsTo.forEach((genNode) -> genNode.executes(this.createAction(command)));
            } else {
               lastNode.executes(this.createAction(command));
            }

            return (LiteralCommandNode)firstNode.asBrigadierNode();
         } else {
            List<RootCommandNode<S>> roots = new ArrayList();

            for(List<ParameterNode<A, Object>> p : Permutations.generatePermutations(flags)) {
               RootCommandNode<S> root = new RootCommandNode();
               roots.add(root);
               BNode<S> thisNode = BNode.<S>of(root);

               for(ParameterNode<A, Object> parameter : p) {
                  if (parameter.isSwitch()) {
                     BNode<S> ofSwitch = this.ofSwitch(parameter);
                     thisNode.then(ofSwitch);
                     thisNode.executes(this.createAction(command));
                     thisNode = ofSwitch;
                  } else {
                     BNode<S> ofFlag = this.ofFlag(parameter);
                     thisNode.then(ofFlag);
                     if (parameter.isOptional()) {
                        thisNode.executes(this.createAction(command));
                     }

                     thisNode = ofFlag.nextChild();
                  }
               }

               thisNode.executes(this.createAction(command));
            }

            for(RootCommandNode<S> root : roots) {
               for(CommandNode<S> child : root.getChildren()) {
                  lastNode.then(child);
               }
            }

            return (LiteralCommandNode)firstNode.asBrigadierNode();
         }
      }
   }

   private <T> @NotNull ArgumentCommandNode<S, T> ofParameter(ParameterNode<A, T> parameter) {
      RequiredArgumentBuilder<S, T> builder = RequiredArgumentBuilder.argument(parameter.name(), this.converter.getArgumentType(parameter));
      return ((RequiredArgumentBuilder)builder.suggests(this.createSuggestionProvider(parameter)).requires(this.createRequirement(parameter.permission(), parameter.lamp()))).build();
   }

   private <T> BNode<S> ofFlag(ParameterNode<A, T> parameter) {
      ArgumentCommandNode<S, T> ofParameter = this.ofParameter(parameter);
      return BNode.literal("--" + parameter.flagName()).then(ofParameter);
   }

   private @NotNull BNode<S> ofSwitch(@NotNull ParameterNode<A, ?> parameter) {
      return BNode.<S>literal("--" + parameter.switchName()).requires(this.createRequirement(parameter.permission(), parameter.lamp())).executes(this.createAction(parameter.lamp()));
   }

   public @NotNull Predicate<S> createRequirement(@NotNull CommandPermission<A> permission, @NotNull Lamp<A> lamp) {
      return permission == CommandPermission.alwaysTrue() ? (x) -> true : (o) -> {
         A actor = this.converter.createActor(o, lamp);
         return permission.isExecutableBy(actor);
      };
   }

   public @NotNull Command<S> createAction(@NotNull Lamp<A> lamp) {
      return (a) -> {
         MutableStringStream input = StringStream.createMutable(a.getInput());
         if (input.peekUnquotedString().contains(":")) {
            input = StringStream.createMutable(Strings.stripNamespace(a.getInput()));
         }

         A actor = this.converter.createActor(a.getSource(), lamp);
         lamp.dispatch(actor, (StringStream)input);
         return 1;
      };
   }

   public @NotNull Command<S> createAction(@NotNull ExecutableCommand<A> command) {
      return (a) -> {
         MutableStringStream input = StringStream.createMutable(a.getInput());
         if (input.peekUnquotedString().contains(":")) {
            input = StringStream.createMutable(Strings.stripNamespace(a.getInput()));
         }

         A actor = this.converter.createActor(a.getSource(), command.lamp());
         command.execute(actor, input);
         return 1;
      };
   }

   public @Nullable SuggestionProvider<S> createSuggestionProvider(@NotNull ParameterNode<A, ?> parameter) {
      return BrigadierAdapter.createSuggestionProvider(parameter, this.converter);
   }
}
