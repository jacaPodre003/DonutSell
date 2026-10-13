package space.hugster.sell.utils.menu.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.utils.menu.Menu;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"},
   d2 = {"Lspace/hugster/sell/utils/menu/event/MenuOpenEvent;", "Lspace/hugster/sell/utils/menu/event/BaseEvent;", "menu", "Lspace/hugster/sell/utils/menu/Menu;", "<init>", "(Lspace/hugster/sell/utils/menu/Menu;)V", "getMenu", "()Lspace/hugster/sell/utils/menu/Menu;", "DonutSell"}
)
public final class MenuOpenEvent extends BaseEvent {
   @NotNull
   private final Menu menu;

   public MenuOpenEvent(@NotNull Menu menu) {
      Intrinsics.checkNotNullParameter(menu, "menu");
      super();
      this.menu = menu;
   }

   @NotNull
   public final Menu getMenu() {
      return this.menu;
   }
}
