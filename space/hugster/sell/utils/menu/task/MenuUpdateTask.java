package space.hugster.sell.utils.menu.task;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.utils.menu.Menu;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"},
   d2 = {"Lspace/hugster/sell/utils/menu/task/MenuUpdateTask;", "Lspace/hugster/sell/utils/menu/task/TaskTicker;", "<init>", "()V", "onRun", "", "getTickType", "Lspace/hugster/sell/utils/menu/task/TaskTicker$TickType;", "getStartTick", "", "DonutSell"}
)
public final class MenuUpdateTask extends TaskTicker {
   public MenuUpdateTask() {
      super(0, 20, true);
   }

   public void onRun() {
      Iterator menuIterator = Menu.Companion.getCurrentlyOpenedMenus().entrySet().iterator();

      while(menuIterator.hasNext()) {
         Map.Entry menuMap = (Map.Entry)menuIterator.next();
         UUID uuid = (UUID)menuMap.getKey();
         Menu menu = (Menu)menuMap.getValue();
         if (uuid != null && menu != null) {
            Player var10000 = Bukkit.getPlayer(uuid);
            if (var10000 == null) {
               return;
            }

            Player player = var10000;
            if (menu.getAutoUpdate()) {
               menu.openMenu(player);
            }
         } else {
            menuIterator.remove();
         }
      }

   }

   @NotNull
   public TaskTicker.TickType getTickType() {
      return TaskTicker.TickType.NONE;
   }

   public int getStartTick() {
      return 0;
   }
}
