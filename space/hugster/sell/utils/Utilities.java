package space.hugster.sell.utils;

import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005J\u000e\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0005J\u000e\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007¨\u0006\u0010"},
   d2 = {"Lspace/hugster/sell/utils/Utilities;", "", "<init>", "()V", "parseAmount", "", "input", "", "(Ljava/lang/String;)Ljava/lang/Double;", "calculatePercentage", "current", "goal", "getNicestInteger", "money", "formatItemName", "name", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nUtilities.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Utilities.kt\nspace/hugster/sell/utils/Utilities\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,49:1\n1#2:50\n*E\n"})
public final class Utilities {
   @NotNull
   public static final Utilities INSTANCE = new Utilities();

   private Utilities() {
   }

   @Nullable
   public final Double parseAmount(@NotNull String input) {
      Intrinsics.checkNotNullParameter(input, "input");
      Regex pattern = new Regex("^(\\d+\\.?\\d*)([KkMmBb])?$");
      MatchResult var10000 = Regex.find$default(pattern, (CharSequence)input, 0, 2, (Object)null);
      if (var10000 == null) {
         return null;
      } else {
         MatchResult match = var10000;
         Double var8 = StringsKt.toDoubleOrNull((String)match.getGroupValues().get(1));
         if (var8 == null) {
            return null;
         } else {
            double number = var8;
            String var9 = ((String)match.getGroupValues().get(2)).toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var9, "toUpperCase(...)");
            String var7 = var9;
            int var10;
            switch (var7.hashCode()) {
               case 0:
                  if (!var7.equals("")) {
                     return null;
                  }

                  var10 = 1;
                  break;
               case 66:
                  if (!var7.equals("B")) {
                     return null;
                  }

                  var10 = 1000000000;
                  break;
               case 75:
                  if (!var7.equals("K")) {
                     return null;
                  }

                  var10 = 1000;
                  break;
               case 77:
                  if (var7.equals("M")) {
                     var10 = 1000000;
                     break;
                  }

                  return null;
               default:
                  return null;
            }

            int multiplier = var10;
            return number * (double)multiplier;
         }
      }
   }

   public final double calculatePercentage(double current, double goal) {
      if (goal <= (double)0.0F) {
         return (double)100.0F;
      } else {
         return current >= goal ? (double)100.0F : current / goal * (double)100.0F;
      }
   }

   @NotNull
   public final String getNicestInteger(double money) {
      String var15;
      if (money >= 1.0E12) {
         String var3 = "%.2f";
         Object[] var4 = new Object[]{money / (double)1000000000000L};
         var15 = String.format(var3, Arrays.copyOf(var4, var4.length));
         Intrinsics.checkNotNullExpressionValue(var15, "format(...)");
         var15 = var15 + "T";
      } else if (money >= (double)1.0E9F) {
         String var5 = "%.2f";
         Object[] var10 = new Object[]{money / (double)1000000000};
         var15 = String.format(var5, Arrays.copyOf(var10, var10.length));
         Intrinsics.checkNotNullExpressionValue(var15, "format(...)");
         var15 = var15 + "B";
      } else if (money >= (double)1000000.0F) {
         String var6 = "%.2f";
         Object[] var11 = new Object[]{money / (double)1000000};
         var15 = String.format(var6, Arrays.copyOf(var11, var11.length));
         Intrinsics.checkNotNullExpressionValue(var15, "format(...)");
         var15 = var15 + "M";
      } else if (money >= (double)1000.0F) {
         String var7 = "%.2f";
         Object[] var12 = new Object[]{money / (double)1000};
         var15 = String.format(var7, Arrays.copyOf(var12, var12.length));
         Intrinsics.checkNotNullExpressionValue(var15, "format(...)");
         var15 = var15 + "K";
      } else if (money % (double)1 == (double)0.0F) {
         String var8 = "%.0f";
         Object[] var13 = new Object[]{money};
         var15 = String.format(var8, Arrays.copyOf(var13, var13.length));
         Intrinsics.checkNotNullExpressionValue(var15, "format(...)");
      } else {
         String var9 = "%.2f";
         Object[] var14 = new Object[]{money};
         var15 = String.format(var9, Arrays.copyOf(var14, var14.length));
         Intrinsics.checkNotNullExpressionValue(var15, "format(...)");
      }

      return var15;
   }

   @NotNull
   public final String formatItemName(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      CharSequence var10000 = (CharSequence)StringsKt.replace$default(name, "_", " ", false, 4, (Object)null);
      String[] var2 = new String[]{" "};
      return CollectionsKt.joinToString$default((Iterable)StringsKt.split$default(var10000, var2, false, 0, 6, (Object)null), (CharSequence)" ", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, Utilities::formatItemName$lambda$1, 30, (Object)null);
   }

   private static final CharSequence formatItemName$lambda$1(String word) {
      Intrinsics.checkNotNullParameter(word, "word");
      String var10000 = word.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var10000, "toLowerCase(...)");
      String var1 = var10000;
      if (((CharSequence)var1).length() > 0) {
         StringBuilder var6 = new StringBuilder();
         char it = var1.charAt(0);
         StringBuilder var4 = var6;
         int var3 = 0;
         String var7 = String.valueOf(it);
         Intrinsics.checkNotNull(var7, "null cannot be cast to non-null type java.lang.String");
         var7 = var7.toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var7, "toUpperCase(...)");
         StringBuilder var9 = var4.append((CharSequence)var7);
         var3 = 1;
         String var10001 = var1.substring(var3);
         Intrinsics.checkNotNullExpressionValue(var10001, "substring(...)");
         var10000 = var9.append(var10001).toString();
      } else {
         var10000 = var1;
      }

      return (CharSequence)var10000;
   }
}
