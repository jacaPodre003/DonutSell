package space.hugster.sell.ui;

import java.util.Comparator;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import space.hugster.sell.utils.menu.Button;

@Metadata(
   mv = {2, 1, 0},
   k = 3,
   xi = 48
)
@SourceDebugExtension({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 WorthMenu.kt\nspace/hugster/sell/ui/WorthMenu\n*L\n1#1,102:1\n46#2:103\n*E\n"})
public final class WorthMenu$getAllPagesButtons$$inlined$sortedBy$2<T> implements Comparator {
   public final int compare(T a, T b) {
      String var9;
      label32: {
         Button it = (Button)a;
         int var4 = 0;
         ItemButton var10000 = it instanceof ItemButton ? (ItemButton)it : null;
         if ((it instanceof ItemButton ? (ItemButton)it : null) != null) {
            String var8 = var10000.getCachedName();
            if (var8 != null) {
               var9 = var8.toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var9, "toLowerCase(...)");
               if (var9 != null) {
                  break label32;
               }
            }
         }

         var9 = "";
      }

      Comparable var10 = (Comparable)var9;
      Button it = (Button)b;
      Comparable var5 = var10;
      int var7 = 0;
      ItemButton var11 = it instanceof ItemButton ? (ItemButton)it : null;
      String var13;
      if ((it instanceof ItemButton ? (ItemButton)it : null) != null) {
         var13 = var11.getCachedName();
         if (var13 != null) {
            var13 = var13.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var13, "toLowerCase(...)");
            if (var13 != null) {
               return ComparisonsKt.compareValues(var5, (Comparable)var13);
            }
         }
      }

      var13 = "";
      return ComparisonsKt.compareValues(var5, (Comparable)var13);
   }
}
