package space.hugster.sell.commands.suggests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.node.ExecutionContext;
import space.hugster.sell.ui.ItemButton;
import space.hugster.sell.utils.WorthCache;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"},
   d2 = {"Lspace/hugster/sell/commands/suggests/WorthItemSuggestions;", "", "<init>", "()V", "provider", "Lrevxrsal/commands/autocomplete/SuggestionProvider;", "Lrevxrsal/commands/bukkit/actor/BukkitCommandActor;", "getProvider", "()Lrevxrsal/commands/autocomplete/SuggestionProvider;", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nWorthItemSuggestions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WorthItemSuggestions.kt\nspace/hugster/sell/commands/suggests/WorthItemSuggestions\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,18:1\n808#2,11:19\n774#2:30\n865#2,2:31\n1557#2:33\n1628#2,3:34\n*S KotlinDebug\n*F\n+ 1 WorthItemSuggestions.kt\nspace/hugster/sell/commands/suggests/WorthItemSuggestions\n*L\n12#1:19,11\n13#1:30\n13#1:31,2\n16#1:33\n16#1:34,3\n*E\n"})
public final class WorthItemSuggestions {
   @NotNull
   public static final WorthItemSuggestions INSTANCE = new WorthItemSuggestions();
   @NotNull
   private static final SuggestionProvider<BukkitCommandActor> provider = WorthItemSuggestions::provider$lambda$2;

   private WorthItemSuggestions() {
   }

   @NotNull
   public final SuggestionProvider<BukkitCommandActor> getProvider() {
      return provider;
   }

   private static final Collection provider$lambda$2(ExecutionContext context) {
      Intrinsics.checkNotNullParameter(context, "context");
      Iterable $this$filterIsInstance$iv = (Iterable)WorthCache.INSTANCE.getAllButtons();
      int $i$f$filterIsInstance = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$filterIsInstanceTo = 0;

      for(Object element$iv$iv : $this$filterIsInstance$iv) {
         if (element$iv$iv instanceof ItemButton) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      $this$filterIsInstance$iv = (Iterable)((List)destination$iv$iv);
      $i$f$filterIsInstance = 0;
      destination$iv$iv = (Collection)(new ArrayList());
      $i$f$filterIsInstanceTo = 0;

      for(Object element$iv$iv : $this$filterIsInstance$iv) {
         ItemButton button = (ItemButton)element$iv$iv;
         int var9 = 0;
         if (!StringsKt.startsWith$default(button.getCachedName(), "&e", false, 2, (Object)null)) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      $this$filterIsInstance$iv = (Iterable)((List)destination$iv$iv);
      $i$f$filterIsInstance = 0;
      destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filterIsInstance$iv, 10)));
      $i$f$filterIsInstanceTo = 0;

      for(Object item$iv$iv : $this$filterIsInstance$iv) {
         ItemButton it = (ItemButton)item$iv$iv;
         int var24 = 0;
         destination$iv$iv.add(it.getCachedName());
      }

      return (Collection)((List)destination$iv$iv);
   }
}
