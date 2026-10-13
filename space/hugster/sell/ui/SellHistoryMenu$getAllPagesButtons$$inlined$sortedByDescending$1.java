package space.hugster.sell.ui;

import java.util.Comparator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;
import org.json.JSONObject;

@Metadata(
   mv = {2, 1, 0},
   k = 3,
   xi = 48
)
@SourceDebugExtension({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareByDescending$1\n+ 2 SellHistoryMenu.kt\nspace/hugster/sell/ui/SellHistoryMenu\n*L\n1#1,121:1\n31#2:122\n*E\n"})
public final class SellHistoryMenu$getAllPagesButtons$$inlined$sortedByDescending$1<T> implements Comparator {
   public final int compare(T a, T b) {
      Map.Entry it = (Map.Entry)b;
      int var4 = 0;
      Comparable var10000 = (Comparable)((JSONObject)it.getValue()).optInt("amount", 0);
      it = (Map.Entry)a;
      Comparable var5 = var10000;
      var4 = 0;
      return ComparisonsKt.compareValues(var5, (Comparable)((JSONObject)it.getValue()).optInt("amount", 0));
   }
}
