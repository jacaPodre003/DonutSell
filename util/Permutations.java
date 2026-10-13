package revxrsal.commands.util;

import java.util.ArrayList;
import java.util.List;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ParameterNode;

public final class Permutations {
   private Permutations() {
      Preconditions.cannotInstantiate(Permutations.class);
   }

   public static <A extends CommandActor> List<List<ParameterNode<A, Object>>> generatePermutations(List<ParameterNode<A, Object>> list) {
      List<ParameterNode<A, Object>> required = new ArrayList();
      List<ParameterNode<A, Object>> optional = new ArrayList();

      for(ParameterNode<A, Object> node : list) {
         if (node.isRequired()) {
            required.add(node);
         } else {
            optional.add(node);
         }
      }

      List<List<ParameterNode<A, Object>>> truePermutations = new ArrayList();
      List<List<ParameterNode<A, Object>>> falsePermutations = new ArrayList();
      permute(required, 0, truePermutations);
      permute(optional, 0, falsePermutations);
      List<List<ParameterNode<A, Object>>> result = new ArrayList();

      for(List<ParameterNode<A, Object>> tp : truePermutations) {
         for(List<ParameterNode<A, Object>> fp : falsePermutations) {
            List<ParameterNode<A, Object>> combined = new ArrayList(tp);
            combined.addAll(fp);
            result.add(combined);
         }
      }

      return result;
   }

   private static <A extends CommandActor> void permute(List<ParameterNode<A, Object>> list, int start, List<List<ParameterNode<A, Object>>> result) {
      if (start == list.size()) {
         result.add(new ArrayList(list));
      } else {
         for(int i = start; i < list.size(); ++i) {
            java.util.Collections.swap(list, start, i);
            permute(list, start + 1, result);
            java.util.Collections.swap(list, start, i);
         }

      }
   }
}
