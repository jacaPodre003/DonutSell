package space.hugster.sell.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.DonutSell;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 02\u00020\u0001:\u00010B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u0011\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\u0004\u0010\bJ\u0014\u0010\t\u001a\u00020\u00002\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bJ\u0014\u0010\f\u001a\u00020\u00002\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000bJ,\u0010\f\u001a\u00020\u0000\"\b\b\u0000\u0010\u000f*\u00020\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u00112\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u000bJ\u000e\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u0014J\u0018\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u0014J\u0016\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0006\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010!\u001a\u00020\u0000J\u000e\u0010\"\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020#J\u001f\u0010$\u001a\u00020\u00002\u0012\u0010$\u001a\n\u0012\u0006\b\u0001\u0012\u00020#0%\"\u00020#¢\u0006\u0002\u0010&J\u0014\u0010$\u001a\u00020\u00002\f\u0010$\u001a\b\u0012\u0004\u0012\u00020#0'J\u001f\u0010(\u001a\u00020\u00002\u0012\u0010)\u001a\n\u0012\u0006\b\u0001\u0012\u00020#0%\"\u00020#¢\u0006\u0002\u0010&J\u0014\u0010(\u001a\u00020\u00002\f\u0010)\u001a\b\u0012\u0004\u0012\u00020#0'J\u000e\u0010*\u001a\u00020\u00002\u0006\u0010*\u001a\u00020#J\u001f\u0010+\u001a\u00020\u00002\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020,0%\"\u00020,¢\u0006\u0002\u0010-J\u0006\u0010+\u001a\u00020\u0000J\u001f\u0010.\u001a\u00020\u00002\u0012\u0010+\u001a\n\u0012\u0006\b\u0001\u0012\u00020,0%\"\u00020,¢\u0006\u0002\u0010-J\u0006\u0010.\u001a\u00020\u0000J\u0006\u0010/\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"},
   d2 = {"Lspace/hugster/sell/utils/ItemBuilder;", "", "item", "Lorg/bukkit/inventory/ItemStack;", "<init>", "(Lorg/bukkit/inventory/ItemStack;)V", "material", "Lorg/bukkit/Material;", "(Lorg/bukkit/Material;)V", "edit", "function", "Ljava/util/function/Consumer;", "meta", "metaConsumer", "Lorg/bukkit/inventory/meta/ItemMeta;", "T", "metaClass", "Ljava/lang/Class;", "type", "data", "", "durability", "", "amount", "enchant", "enchantment", "Lorg/bukkit/enchantments/Enchantment;", "level", "upgrade", "pattern", "Lorg/bukkit/inventory/meta/trim/TrimPattern;", "Lorg/bukkit/inventory/meta/trim/TrimMaterial;", "removeEnchant", "removeEnchants", "name", "", "lore", "", "([Ljava/lang/String;)Lspace/hugster/sell/utils/ItemBuilder;", "", "addLore", "lines", "owner", "flags", "Lorg/bukkit/inventory/ItemFlag;", "([Lorg/bukkit/inventory/ItemFlag;)Lspace/hugster/sell/utils/ItemBuilder;", "removeFlags", "build", "Companion", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nItemBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemBuilder.kt\nspace/hugster/sell/utils/ItemBuilder\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,160:1\n11158#2:161\n11493#2,3:162\n37#3:165\n36#3,3:166\n37#3:169\n36#3,3:170\n1863#4,2:173\n1557#4:175\n1628#4,3:176\n1557#4:179\n1628#4,3:180\n*S KotlinDebug\n*F\n+ 1 ItemBuilder.kt\nspace/hugster/sell/utils/ItemBuilder\n*L\n117#1:161\n117#1:162,3\n143#1:165\n143#1:166,3\n153#1:169\n153#1:170,3\n97#1:173,2\n112#1:175\n112#1:176,3\n124#1:179\n124#1:180,3\n*E\n"})
public final class ItemBuilder {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final ItemStack item;

   public ItemBuilder(@NotNull ItemStack item) {
      Intrinsics.checkNotNullParameter(item, "item");
      super();
      this.item = item;
   }

   public ItemBuilder(@NotNull Material material) {
      Intrinsics.checkNotNullParameter(material, "material");
      this(new ItemStack(material));
   }

   @NotNull
   public final ItemBuilder edit(@NotNull Consumer<ItemStack> function) {
      Intrinsics.checkNotNullParameter(function, "function");
      function.accept(this.item);
      return this;
   }

   @NotNull
   public final ItemBuilder meta(@NotNull Consumer<ItemMeta> metaConsumer) {
      Intrinsics.checkNotNullParameter(metaConsumer, "metaConsumer");
      return this.edit(ItemBuilder::meta$lambda$1);
   }

   @NotNull
   public final <T extends ItemMeta> ItemBuilder meta(@NotNull Class<T> metaClass, @NotNull Consumer<T> metaConsumer) {
      Intrinsics.checkNotNullParameter(metaClass, "metaClass");
      Intrinsics.checkNotNullParameter(metaConsumer, "metaConsumer");
      return this.meta(ItemBuilder::meta$lambda$2);
   }

   @NotNull
   public final ItemBuilder type(@NotNull Material material) {
      Intrinsics.checkNotNullParameter(material, "material");
      return this.edit(ItemBuilder::type$lambda$3);
   }

   @NotNull
   public final ItemBuilder data(int data) {
      return this.durability((short)data);
   }

   @NotNull
   public final ItemBuilder durability(short durability) {
      return this.edit(ItemBuilder::durability$lambda$4);
   }

   @NotNull
   public final ItemBuilder amount(int amount) {
      return this.edit(ItemBuilder::amount$lambda$5);
   }

   @NotNull
   public final ItemBuilder enchant(@NotNull Enchantment enchantment, int level) {
      Intrinsics.checkNotNullParameter(enchantment, "enchantment");
      return this.meta(ItemBuilder::enchant$lambda$6);
   }

   // $FF: synthetic method
   public static ItemBuilder enchant$default(ItemBuilder var0, Enchantment var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 1;
      }

      return var0.enchant(var1, var2);
   }

   @NotNull
   public final ItemBuilder upgrade(@NotNull TrimPattern pattern, @NotNull TrimMaterial material) {
      Intrinsics.checkNotNullParameter(pattern, "pattern");
      Intrinsics.checkNotNullParameter(material, "material");
      return this.meta(ItemBuilder::upgrade$lambda$7);
   }

   @NotNull
   public final ItemBuilder removeEnchant(@NotNull Enchantment enchantment) {
      Intrinsics.checkNotNullParameter(enchantment, "enchantment");
      return this.meta(ItemBuilder::removeEnchant$lambda$8);
   }

   @NotNull
   public final ItemBuilder removeEnchants() {
      return this.meta(ItemBuilder::removeEnchants$lambda$10);
   }

   @NotNull
   public final ItemBuilder name(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      return this.meta(ItemBuilder::name$lambda$11);
   }

   @NotNull
   public final ItemBuilder lore(@NotNull String... lore) {
      Intrinsics.checkNotNullParameter(lore, "lore");
      return this.lore(ArraysKt.toList(lore));
   }

   @NotNull
   public final ItemBuilder lore(@NotNull List<String> lore) {
      Intrinsics.checkNotNullParameter(lore, "lore");
      return this.meta(ItemBuilder::lore$lambda$13);
   }

   @NotNull
   public final ItemBuilder addLore(@NotNull String... lines) {
      Intrinsics.checkNotNullParameter(lines, "lines");
      int $i$f$map = 0;
      String[] $this$mapTo$iv$iv = lines;
      Collection destination$iv$iv = (Collection)(new ArrayList(lines.length));
      int $i$f$mapTo = 0;
      int var7 = 0;

      for(int var8 = lines.length; var7 < var8; ++var7) {
         Object item$iv$iv = $this$mapTo$iv$iv[var7];
         int var11 = 0;
         destination$iv$iv.add(CC.INSTANCE.translate((String)item$iv$iv));
      }

      return this.addLore((List)destination$iv$iv);
   }

   @NotNull
   public final ItemBuilder addLore(@NotNull List<String> lines) {
      Intrinsics.checkNotNullParameter(lines, "lines");
      return this.meta(ItemBuilder::addLore$lambda$16);
   }

   @NotNull
   public final ItemBuilder owner(@NotNull String owner) {
      Intrinsics.checkNotNullParameter(owner, "owner");
      return this.meta(SkullMeta.class, ItemBuilder::owner$lambda$17);
   }

   @NotNull
   public final ItemBuilder flags(@NotNull ItemFlag... flags) {
      Intrinsics.checkNotNullParameter(flags, "flags");
      return this.meta(ItemBuilder::flags$lambda$18);
   }

   @NotNull
   public final ItemBuilder flags() {
      Collection $this$toTypedArray$iv = (Collection)ItemBuilder.EntriesMappings.entries$0;
      int $i$f$toTypedArray = 0;
      ItemFlag[] var1 = (ItemFlag[])$this$toTypedArray$iv.toArray(new ItemFlag[0]);
      return this.flags((ItemFlag[])Arrays.copyOf(var1, var1.length));
   }

   @NotNull
   public final ItemBuilder removeFlags(@NotNull ItemFlag... flags) {
      Intrinsics.checkNotNullParameter(flags, "flags");
      return this.meta(ItemBuilder::removeFlags$lambda$19);
   }

   @NotNull
   public final ItemBuilder removeFlags() {
      Collection $this$toTypedArray$iv = (Collection)ItemBuilder.EntriesMappings.entries$0;
      int $i$f$toTypedArray = 0;
      ItemFlag[] var1 = (ItemFlag[])$this$toTypedArray$iv.toArray(new ItemFlag[0]);
      return this.removeFlags((ItemFlag[])Arrays.copyOf(var1, var1.length));
   }

   @NotNull
   public final ItemStack build() {
      return this.item;
   }

   private static final void meta$lambda$1(Consumer $metaConsumer, ItemStack item) {
      Intrinsics.checkNotNullParameter(item, "item");
      ItemMeta meta = item.getItemMeta();
      if (meta != null) {
         int var4 = 0;
         $metaConsumer.accept(meta);
         item.setItemMeta(meta);
      }

   }

   private static final void meta$lambda$2(Class $metaClass, Consumer $metaConsumer, ItemMeta meta) {
      Intrinsics.checkNotNullParameter(meta, "meta");
      if ($metaClass.isInstance(meta)) {
         $metaConsumer.accept($metaClass.cast(meta));
      }

   }

   private static final void type$lambda$3(Material $material, ItemStack it) {
      Intrinsics.checkNotNullParameter(it, "it");
      it.setType($material);
   }

   private static final void durability$lambda$4(short $durability, ItemStack it) {
      Intrinsics.checkNotNullParameter(it, "it");
      it.setDurability($durability);
   }

   private static final void amount$lambda$5(int $amount, ItemStack it) {
      Intrinsics.checkNotNullParameter(it, "it");
      it.setAmount($amount);
   }

   private static final void enchant$lambda$6(Enchantment $enchantment, int $level, ItemMeta it) {
      Intrinsics.checkNotNullParameter(it, "it");
      it.addEnchant($enchantment, $level, true);
   }

   private static final void upgrade$lambda$7(TrimMaterial $material, TrimPattern $pattern, ItemMeta meta) {
      Intrinsics.checkNotNullParameter(meta, "meta");
      if (meta instanceof ArmorMeta) {
         ((ArmorMeta)meta).setTrim(new ArmorTrim($material, $pattern));
      }

   }

   private static final void removeEnchant$lambda$8(Enchantment $enchantment, ItemMeta it) {
      Intrinsics.checkNotNullParameter(it, "it");
      it.removeEnchant($enchantment);
   }

   private static final void removeEnchants$lambda$10(ItemMeta meta) {
      Intrinsics.checkNotNullParameter(meta, "meta");
      Iterable $this$forEach$iv = (Iterable)meta.getEnchants().keySet();
      int $i$f$forEach = 0;

      for(Object element$iv : $this$forEach$iv) {
         Enchantment it = (Enchantment)element$iv;
         int var6 = 0;
         meta.removeEnchant(it);
      }

   }

   private static final void name$lambda$11(String $name, ItemMeta it) {
      Intrinsics.checkNotNullParameter(it, "it");
      it.setDisplayName(CC.INSTANCE.translate($name));
   }

   private static final void lore$lambda$13(List $lore, ItemMeta it) {
      Intrinsics.checkNotNullParameter(it, "it");
      Iterable $this$map$iv = (Iterable)$lore;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         String line = (String)item$iv$iv;
         int var10 = 0;
         destination$iv$iv.add(CC.INSTANCE.translate(line));
      }

      it.setLore((List)destination$iv$iv);
   }

   private static final void addLore$lambda$16(List $lines, ItemMeta meta) {
      Intrinsics.checkNotNullParameter(meta, "meta");
      List var10000 = meta.getLore();
      if (var10000 == null) {
         var10000 = (List)(new ArrayList());
      }

      List lore = var10000;
      Iterable $this$map$iv = (Iterable)$lines;
      int $i$f$map = 0;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      int $i$f$mapTo = 0;

      for(Object item$iv$iv : $this$map$iv) {
         String it = (String)item$iv$iv;
         int var11 = 0;
         destination$iv$iv.add(CC.INSTANCE.translate(it));
      }

      lore.addAll((Collection)((List)destination$iv$iv));
      meta.setLore(lore);
   }

   private static final void owner$lambda$17(String $owner, SkullMeta meta) {
      Intrinsics.checkNotNullParameter(meta, "meta");
      meta.setOwningPlayer(DonutSell.Companion.getINSTANCE().getServer().getOfflinePlayer($owner));
   }

   private static final void flags$lambda$18(ItemFlag[] $flags, ItemMeta it) {
      Intrinsics.checkNotNullParameter(it, "it");
      it.addItemFlags((ItemFlag[])Arrays.copyOf($flags, $flags.length));
   }

   private static final void removeFlags$lambda$19(ItemFlag[] $flags, ItemMeta it) {
      Intrinsics.checkNotNullParameter(it, "it");
      it.removeItemFlags((ItemFlag[])Arrays.copyOf($flags, $flags.length));
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"},
      d2 = {"Lspace/hugster/sell/utils/ItemBuilder$Companion;", "", "<init>", "()V", "copyOf", "Lspace/hugster/sell/utils/ItemBuilder;", "item", "Lorg/bukkit/inventory/ItemStack;", "DonutSell"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final ItemBuilder copyOf(@NotNull ItemStack item) {
         Intrinsics.checkNotNullParameter(item, "item");
         ItemStack var10002 = item.clone();
         Intrinsics.checkNotNullExpressionValue(var10002, "clone(...)");
         return new ItemBuilder(var10002);
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }

   // $FF: synthetic class
   @Metadata(
      mv = {2, 1, 0},
      k = 3,
      xi = 48
   )
   public class EntriesMappings {
      // $FF: synthetic field
      public static final EnumEntries<ItemFlag> entries$0 = EnumEntriesKt.enumEntries((Enum[])ItemFlag.values());
   }
}
