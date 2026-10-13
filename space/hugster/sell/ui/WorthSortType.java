package space.hugster.sell.ui;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"},
   d2 = {"Lspace/hugster/sell/ui/WorthSortType;", "", "displayName", "", "<init>", "(Ljava/lang/String;ILjava/lang/String;)V", "getDisplayName", "()Ljava/lang/String;", "HIGHEST_PRICE", "LOWEST_PRICE", "ALPHABETICALLY", "DonutSell"}
)
public enum WorthSortType {
   @NotNull
   private final String displayName;
   HIGHEST_PRICE("Highest Price"),
   LOWEST_PRICE("Lowest Price"),
   ALPHABETICALLY("Alphabetically");

   // $FF: synthetic field
   private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

   private WorthSortType(String displayName) {
      this.displayName = displayName;
   }

   @NotNull
   public final String getDisplayName() {
      return this.displayName;
   }

   @NotNull
   public static EnumEntries<WorthSortType> getEntries() {
      return $ENTRIES;
   }

   // $FF: synthetic method
   private static final WorthSortType[] $values() {
      WorthSortType[] var0 = new WorthSortType[]{HIGHEST_PRICE, LOWEST_PRICE, ALPHABETICALLY};
      return var0;
   }
}
