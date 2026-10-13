package space.hugster.sell.ui;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;
import space.hugster.sell.utils.menu.Button;

@Metadata(
   mv = {2, 1, 0},
   k = 3,
   xi = 48
)
@SourceDebugExtension({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 WorthMenu.kt\nspace/hugster/sell/ui/WorthMenu\n*L\n1#1,102:1\n43#2:103\n*E\n"})
public final class WorthMenu$getAllPagesButtons$$inlined$sortedBy$1<T> implements Comparator {
   public final int compare(T a, T b) {
      Button it = (Button)a;
      int var4 = 0;
      Comparable var10000 = (Comparable)(it instanceof ItemButton ? (ItemButton)it : null) != null ? (it instanceof ItemButton ? (ItemButton)it : null).getPrice() : (double)0.0F;
      it = (Button)b;
      Comparable var5 = var10000;
      var4 = 0;
      return ComparisonsKt.compareValues(var5, (Comparable)(it instanceof ItemButton ? (ItemButton)it : null) != null ? (it instanceof ItemButton ? (ItemButton)it : null).getPrice() : (double)0.0F);
   }
}
