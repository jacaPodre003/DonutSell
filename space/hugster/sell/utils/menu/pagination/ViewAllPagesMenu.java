package space.hugster.sell.utils.menu.pagination;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.utils.menu.Button;
import space.hugster.sell.utils.menu.Menu;
import space.hugster.sell.utils.menu.buttons.BackButton;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001c\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"},
   d2 = {"Lspace/hugster/sell/utils/menu/pagination/ViewAllPagesMenu;", "Lspace/hugster/sell/utils/menu/Menu;", "menu", "Lspace/hugster/sell/utils/menu/pagination/PaginatedMenu;", "<init>", "(Lspace/hugster/sell/utils/menu/pagination/PaginatedMenu;)V", "getMenu", "()Lspace/hugster/sell/utils/menu/pagination/PaginatedMenu;", "getTitle", "", "player", "Lorg/bukkit/entity/Player;", "getButtons", "", "", "Lspace/hugster/sell/utils/menu/Button;", "DonutSell"}
)
public final class ViewAllPagesMenu extends Menu {
   @NotNull
   private final PaginatedMenu menu;

   public ViewAllPagesMenu(@NotNull PaginatedMenu menu) {
      Intrinsics.checkNotNullParameter(menu, "menu");
      super();
      this.menu = menu;
   }

   @NotNull
   public final PaginatedMenu getMenu() {
      return this.menu;
   }

   @NotNull
   public String getTitle(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      return "Jump to page";
   }

   @NotNull
   public Map<Integer, Button> getButtons(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      Map buttons = (Map)(new LinkedHashMap());
      buttons.put(0, new BackButton(this.menu));
      int index = 10;
      int i = 1;
      int var5 = this.menu.getPages(player);
      if (i <= var5) {
         while(true) {
            buttons.put(index++, new JumpToPageButton(i, this.menu, this.menu.getPage() == i));
            if ((index - 8) % 9 == 0) {
               index += 2;
            }

            if (i == var5) {
               break;
            }

            ++i;
         }
      }

      return buttons;
   }
}
