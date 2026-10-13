package space.hugster.sell.utils.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0002J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0005H\u0002J\n\u0010\b\u001a\u0004\u0018\u00010\u0005H\u0002J$\u0010\t\u001a\u0004\u0018\u00010\u00052\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005H\u0002J\u0010\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005H\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002¨\u0006\u0011"},
   d2 = {"Lspace/hugster/sell/utils/security/HWIDUtil;", "", "<init>", "()V", "getHWID", "", "getMotherboardSerial", "getProcessorID", "getDiskSerial", "runCommand", "command", "", "key", "hash", "input", "isWindows", "", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nHWIDUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HWIDUtil.kt\nspace/hugster/sell/utils/security/HWIDUtil\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,89:1\n1#2:90\n*E\n"})
public final class HWIDUtil {
   @NotNull
   public static final HWIDUtil INSTANCE = new HWIDUtil();

   private HWIDUtil() {
   }

   @NotNull
   public final String getHWID() {
      String[] var2 = new String[]{this.getMotherboardSerial(), this.getProcessorID(), this.getDiskSerial()};
      String identifiers = CollectionsKt.joinToString$default((Iterable)CollectionsKt.listOfNotNull(var2), (CharSequence)"-", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
      return this.hash(identifiers);
   }

   private final String getMotherboardSerial() {
      String var10000;
      if (this.isWindows()) {
         String[] var1 = new String[]{"wmic", "baseboard", "get", "serialnumber"};
         var10000 = this.runCommand(CollectionsKt.listOf(var1), "SerialNumber");
      } else {
         String[] var2 = new String[]{"/sys/class/dmi/id/board_serial", "/sys/class/dmi/id/product_serial", "/sys/devices/virtual/dmi/id/board_serial", "/proc/device-tree/serial-number"};
         List possiblePaths = CollectionsKt.listOf(var2);

         for(String path : (Iterable)possiblePaths) {
            int var5 = 0;

            String var10;
            try {
               label46: {
                  File it = new File(path);
                  int var8 = 0;
                  File var14 = it.exists() ? it : null;
                  if (var14 != null) {
                     String var15 = FilesKt.readText$default(var14, (Charset)null, 1, (Object)null);
                     if (var15 != null) {
                        var15 = StringsKt.trim((CharSequence)var15).toString();
                        if (var15 != null) {
                           CharSequence var13 = (CharSequence)var15;
                           CharSequence var18;
                           if (var13.length() == 0) {
                              int var9 = 0;
                              var18 = null;
                           } else {
                              var18 = var13;
                           }

                           var10000 = (String)var18;
                           break label46;
                        }
                     }
                  }

                  var10000 = null;
               }

               var10 = var10000;
            } catch (Exception var11) {
               var10 = null;
            }

            if (var10 != null) {
               var10000 = var10;
               return var10000;
            }
         }

         var10000 = null;
      }

      return var10000;
   }

   private final String getProcessorID() {
      String var10000;
      if (this.isWindows()) {
         String[] var1 = new String[]{"wmic", "cpu", "get", "ProcessorId"};
         var10000 = this.runCommand(CollectionsKt.listOf(var1), "ProcessorId");
      } else {
         String[] var2 = new String[]{"cat", "/proc/cpuinfo"};
         var10000 = this.runCommand(CollectionsKt.listOf(var2), "Serial");
      }

      return var10000;
   }

   private final String getDiskSerial() {
      String var10000;
      if (this.isWindows()) {
         String[] var1 = new String[]{"wmic", "diskdrive", "get", "serialnumber"};
         var10000 = this.runCommand(CollectionsKt.listOf(var1), "SerialNumber");
      } else {
         String[] var2 = new String[]{"lsblk", "-o", "SERIAL"};
         var10000 = this.runCommand(CollectionsKt.listOf(var2), "SERIAL");
      }

      return var10000;
   }

   private final String runCommand(List<String> command, String key) {
      String process;
      try {
         Process process = (new ProcessBuilder(command)).start();
         BufferedReader reader = new BufferedReader((Reader)(new InputStreamReader(process.getInputStream())));
         List lines = TextStreamsKt.readLines((Reader)reader);
         String var11;
         if (lines.size() > 1) {
            String result = StringsKt.trim((CharSequence)((String)lines.get(1))).toString();
            CharSequence var7 = (CharSequence)result;
            CharSequence var10000;
            if (var7.length() == 0) {
               int var8 = 0;
               var10000 = null;
            } else {
               var10000 = var7;
            }

            var11 = (String)var10000;
         } else {
            var11 = null;
         }

         process = var11;
      } catch (Exception var9) {
         process = null;
      }

      return process;
   }

   // $FF: synthetic method
   static String runCommand$default(HWIDUtil var0, List var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      return var0.runCommand(var1, var2);
   }

   private final String hash(String input) {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      Charset var10001 = StandardCharsets.UTF_8;
      Intrinsics.checkNotNullExpressionValue(var10001, "UTF_8");
      byte[] var5 = input.getBytes(var10001);
      Intrinsics.checkNotNullExpressionValue(var5, "getBytes(...)");
      byte[] hashBytes = md.digest(var5);
      String var10000 = (new BigInteger(1, hashBytes)).toString(16);
      Intrinsics.checkNotNullExpressionValue(var10000, "toString(...)");
      return StringsKt.padStart(var10000, 64, '0');
   }

   private final boolean isWindows() {
      String var10000 = System.getProperty("os.name");
      Intrinsics.checkNotNullExpressionValue(var10000, "getProperty(...)");
      var10000 = var10000.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var10000, "toLowerCase(...)");
      return StringsKt.contains$default((CharSequence)var10000, (CharSequence)"win", false, 2, (Object)null);
   }
}
