package space.hugster.sell.utils;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tJ\u0010\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\tJ\u0010\u0010\u000e\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\tR\u0018\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007¨\u0006\u000f"},
   d2 = {"Lspace/hugster/sell/utils/CC;", "", "<init>", "()V", "hexPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "Ljava/util/regex/Pattern;", "translate", "", "text", "console", "", "msg", "broadcast", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nCC.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CC.kt\nspace/hugster/sell/utils/CC\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,45:1\n1#2:46\n1863#3,2:47\n*S KotlinDebug\n*F\n+ 1 CC.kt\nspace/hugster/sell/utils/CC\n*L\n42#1:47,2\n*E\n"})
public final class CC {
   @NotNull
   public static final CC INSTANCE = new CC();
   private static final Pattern hexPattern = Pattern.compile("&#[A-Fa-f0-9]{6}");

   private CC() {
   }

   @NotNull
   public final String translate(@NotNull String text) {
      Intrinsics.checkNotNullParameter(text, "text");
      String result = text;
      Matcher matcher = hexPattern.matcher((CharSequence)text);

      while(matcher.find()) {
         try {
            String color = matcher.group();
            Intrinsics.checkNotNull(color);
            String hexColor = StringsKt.replace$default(StringsKt.replace$default(color, "&", "", false, 4, (Object)null), "x", "#", false, 4, (Object)null);
            ChatColor bungeeColor = ChatColor.of(hexColor);
            String var10002 = bungeeColor.toString();
            Intrinsics.checkNotNullExpressionValue(var10002, "toString(...)");
            result = StringsKt.replace$default(result, color, var10002, false, 4, (Object)null);
         } catch (Exception var7) {
         }
      }

      String var10000 = org.bukkit.ChatColor.translateAlternateColorCodes('&', result);
      Intrinsics.checkNotNullExpressionValue(var10000, "translateAlternateColorCodes(...)");
      return var10000;
   }

   public final void console(@Nullable String msg) {
      if (msg != null) {
         int var3 = 0;
         Bukkit.getServer().getConsoleSender().sendMessage(INSTANCE.translate(msg));
      }

   }

   public final void broadcast(@Nullable String msg) {
      if (msg != null) {
         int var3 = 0;
         String translatedMsg = INSTANCE.translate(msg);
         Collection var10000 = Bukkit.getOnlinePlayers();
         Intrinsics.checkNotNullExpressionValue(var10000, "getOnlinePlayers(...)");
         Iterable $this$forEach$iv = (Iterable)var10000;
         int $i$f$forEach = 0;

         for(Object element$iv : $this$forEach$iv) {
            Player player = (Player)element$iv;
            int var10 = 0;
            player.sendMessage(translatedMsg);
         }
      }

   }
}
