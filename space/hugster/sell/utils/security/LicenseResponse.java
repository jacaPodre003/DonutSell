package space.hugster.sell.utils.security;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.SerializationStrategy;
import kotlinx.serialization.UnknownFieldException;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeDecoder;
import kotlinx.serialization.encoding.CompositeEncoder;
import kotlinx.serialization.encoding.Decoder;
import kotlinx.serialization.encoding.Encoder;
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Serializable
@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\b\u0018\u0000 <2\u00020\u0001:\u0002;<Bi\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003¢\u0006\u0004\b\u000f\u0010\u0010B\u0087\u0001\b\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0006\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\u0004\b\u000f\u0010\u0014J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0006HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\u0081\u0001\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u0003HÆ\u0001J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00101\u001a\u00020\u0006HÖ\u0001J\t\u00102\u001a\u00020\u0003HÖ\u0001J%\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u00002\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u000209H\u0001¢\u0006\u0002\b:R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0016R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u0011\u0010\u000e\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0016¨\u0006="},
   d2 = {"Lspace/hugster/sell/utils/security/LicenseResponse;", "", "status_msg", "", "status_overview", "status_code", "", "status_id", "version", "clientname", "discord_username", "discord_tag", "discord_id", "main_class", "plugin_data", "<init>", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "seen0", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "getStatus_msg", "()Ljava/lang/String;", "getStatus_overview", "getStatus_code", "()I", "getStatus_id", "getVersion", "getClientname", "getDiscord_username", "getDiscord_tag", "getDiscord_id", "getMain_class", "getPlugin_data", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "component11", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "write$Self$DonutSell", "$serializer", "Companion", "DonutSell"}
)
public final class LicenseResponse {
   @NotNull
   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);
   @NotNull
   private final String status_msg;
   @NotNull
   private final String status_overview;
   private final int status_code;
   @NotNull
   private final String status_id;
   @Nullable
   private final String version;
   @Nullable
   private final String clientname;
   @Nullable
   private final String discord_username;
   @Nullable
   private final String discord_tag;
   @Nullable
   private final String discord_id;
   @NotNull
   private final String main_class;
   @NotNull
   private final String plugin_data;

   public LicenseResponse(@NotNull String status_msg, @NotNull String status_overview, int status_code, @NotNull String status_id, @Nullable String version, @Nullable String clientname, @Nullable String discord_username, @Nullable String discord_tag, @Nullable String discord_id, @NotNull String main_class, @NotNull String plugin_data) {
      Intrinsics.checkNotNullParameter(status_msg, "status_msg");
      Intrinsics.checkNotNullParameter(status_overview, "status_overview");
      Intrinsics.checkNotNullParameter(status_id, "status_id");
      Intrinsics.checkNotNullParameter(main_class, "main_class");
      Intrinsics.checkNotNullParameter(plugin_data, "plugin_data");
      super();
      this.status_msg = status_msg;
      this.status_overview = status_overview;
      this.status_code = status_code;
      this.status_id = status_id;
      this.version = version;
      this.clientname = clientname;
      this.discord_username = discord_username;
      this.discord_tag = discord_tag;
      this.discord_id = discord_id;
      this.main_class = main_class;
      this.plugin_data = plugin_data;
   }

   @NotNull
   public final String getStatus_msg() {
      return this.status_msg;
   }

   @NotNull
   public final String getStatus_overview() {
      return this.status_overview;
   }

   public final int getStatus_code() {
      return this.status_code;
   }

   @NotNull
   public final String getStatus_id() {
      return this.status_id;
   }

   @Nullable
   public final String getVersion() {
      return this.version;
   }

   @Nullable
   public final String getClientname() {
      return this.clientname;
   }

   @Nullable
   public final String getDiscord_username() {
      return this.discord_username;
   }

   @Nullable
   public final String getDiscord_tag() {
      return this.discord_tag;
   }

   @Nullable
   public final String getDiscord_id() {
      return this.discord_id;
   }

   @NotNull
   public final String getMain_class() {
      return this.main_class;
   }

   @NotNull
   public final String getPlugin_data() {
      return this.plugin_data;
   }

   @NotNull
   public final String component1() {
      return this.status_msg;
   }

   @NotNull
   public final String component2() {
      return this.status_overview;
   }

   public final int component3() {
      return this.status_code;
   }

   @NotNull
   public final String component4() {
      return this.status_id;
   }

   @Nullable
   public final String component5() {
      return this.version;
   }

   @Nullable
   public final String component6() {
      return this.clientname;
   }

   @Nullable
   public final String component7() {
      return this.discord_username;
   }

   @Nullable
   public final String component8() {
      return this.discord_tag;
   }

   @Nullable
   public final String component9() {
      return this.discord_id;
   }

   @NotNull
   public final String component10() {
      return this.main_class;
   }

   @NotNull
   public final String component11() {
      return this.plugin_data;
   }

   @NotNull
   public final LicenseResponse copy(@NotNull String status_msg, @NotNull String status_overview, int status_code, @NotNull String status_id, @Nullable String version, @Nullable String clientname, @Nullable String discord_username, @Nullable String discord_tag, @Nullable String discord_id, @NotNull String main_class, @NotNull String plugin_data) {
      Intrinsics.checkNotNullParameter(status_msg, "status_msg");
      Intrinsics.checkNotNullParameter(status_overview, "status_overview");
      Intrinsics.checkNotNullParameter(status_id, "status_id");
      Intrinsics.checkNotNullParameter(main_class, "main_class");
      Intrinsics.checkNotNullParameter(plugin_data, "plugin_data");
      return new LicenseResponse(status_msg, status_overview, status_code, status_id, version, clientname, discord_username, discord_tag, discord_id, main_class, plugin_data);
   }

   // $FF: synthetic method
   public static LicenseResponse copy$default(LicenseResponse var0, String var1, String var2, int var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, int var12, Object var13) {
      if ((var12 & 1) != 0) {
         var1 = var0.status_msg;
      }

      if ((var12 & 2) != 0) {
         var2 = var0.status_overview;
      }

      if ((var12 & 4) != 0) {
         var3 = var0.status_code;
      }

      if ((var12 & 8) != 0) {
         var4 = var0.status_id;
      }

      if ((var12 & 16) != 0) {
         var5 = var0.version;
      }

      if ((var12 & 32) != 0) {
         var6 = var0.clientname;
      }

      if ((var12 & 64) != 0) {
         var7 = var0.discord_username;
      }

      if ((var12 & 128) != 0) {
         var8 = var0.discord_tag;
      }

      if ((var12 & 256) != 0) {
         var9 = var0.discord_id;
      }

      if ((var12 & 512) != 0) {
         var10 = var0.main_class;
      }

      if ((var12 & 1024) != 0) {
         var11 = var0.plugin_data;
      }

      return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
   }

   @NotNull
   public String toString() {
      return "LicenseResponse(status_msg=" + this.status_msg + ", status_overview=" + this.status_overview + ", status_code=" + this.status_code + ", status_id=" + this.status_id + ", version=" + this.version + ", clientname=" + this.clientname + ", discord_username=" + this.discord_username + ", discord_tag=" + this.discord_tag + ", discord_id=" + this.discord_id + ", main_class=" + this.main_class + ", plugin_data=" + this.plugin_data + ")";
   }

   public int hashCode() {
      int result = this.status_msg.hashCode();
      result = result * 31 + this.status_overview.hashCode();
      result = result * 31 + Integer.hashCode(this.status_code);
      result = result * 31 + this.status_id.hashCode();
      result = result * 31 + (this.version == null ? 0 : this.version.hashCode());
      result = result * 31 + (this.clientname == null ? 0 : this.clientname.hashCode());
      result = result * 31 + (this.discord_username == null ? 0 : this.discord_username.hashCode());
      result = result * 31 + (this.discord_tag == null ? 0 : this.discord_tag.hashCode());
      result = result * 31 + (this.discord_id == null ? 0 : this.discord_id.hashCode());
      result = result * 31 + this.main_class.hashCode();
      result = result * 31 + this.plugin_data.hashCode();
      return result;
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof LicenseResponse)) {
         return false;
      } else {
         LicenseResponse var2 = (LicenseResponse)other;
         if (!Intrinsics.areEqual(this.status_msg, var2.status_msg)) {
            return false;
         } else if (!Intrinsics.areEqual(this.status_overview, var2.status_overview)) {
            return false;
         } else if (this.status_code != var2.status_code) {
            return false;
         } else if (!Intrinsics.areEqual(this.status_id, var2.status_id)) {
            return false;
         } else if (!Intrinsics.areEqual(this.version, var2.version)) {
            return false;
         } else if (!Intrinsics.areEqual(this.clientname, var2.clientname)) {
            return false;
         } else if (!Intrinsics.areEqual(this.discord_username, var2.discord_username)) {
            return false;
         } else if (!Intrinsics.areEqual(this.discord_tag, var2.discord_tag)) {
            return false;
         } else if (!Intrinsics.areEqual(this.discord_id, var2.discord_id)) {
            return false;
         } else if (!Intrinsics.areEqual(this.main_class, var2.main_class)) {
            return false;
         } else {
            return Intrinsics.areEqual(this.plugin_data, var2.plugin_data);
         }
      }
   }

   // $FF: synthetic method
   @JvmStatic
   public static final void write$Self$DonutSell(LicenseResponse self, CompositeEncoder output, SerialDescriptor serialDesc) {
      output.encodeStringElement(serialDesc, 0, self.status_msg);
      output.encodeStringElement(serialDesc, 1, self.status_overview);
      output.encodeIntElement(serialDesc, 2, self.status_code);
      output.encodeStringElement(serialDesc, 3, self.status_id);
      output.encodeNullableSerializableElement(serialDesc, 4, (SerializationStrategy)StringSerializer.INSTANCE, self.version);
      output.encodeNullableSerializableElement(serialDesc, 5, (SerializationStrategy)StringSerializer.INSTANCE, self.clientname);
      output.encodeNullableSerializableElement(serialDesc, 6, (SerializationStrategy)StringSerializer.INSTANCE, self.discord_username);
      output.encodeNullableSerializableElement(serialDesc, 7, (SerializationStrategy)StringSerializer.INSTANCE, self.discord_tag);
      output.encodeNullableSerializableElement(serialDesc, 8, (SerializationStrategy)StringSerializer.INSTANCE, self.discord_id);
      output.encodeStringElement(serialDesc, 9, self.main_class);
      output.encodeStringElement(serialDesc, 10, self.plugin_data);
   }

   // $FF: synthetic method
   public LicenseResponse(int seen0, String status_msg, String status_overview, int status_code, String status_id, String version, String clientname, String discord_username, String discord_tag, String discord_id, String main_class, String plugin_data, SerializationConstructorMarker serializationConstructorMarker) {
      if (2047 != (2047 & seen0)) {
         PluginExceptionsKt.throwMissingFieldException(seen0, 2047, LicenseResponse.$serializer.INSTANCE.getDescriptor());
      }

      super();
      this.status_msg = status_msg;
      this.status_overview = status_overview;
      this.status_code = status_code;
      this.status_id = status_id;
      this.version = version;
      this.clientname = clientname;
      this.discord_username = discord_username;
      this.discord_tag = discord_tag;
      this.discord_id = discord_id;
      this.main_class = main_class;
      this.plugin_data = plugin_data;
   }

   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¨\u0006\u0007"},
      d2 = {"Lspace/hugster/sell/utils/security/LicenseResponse$Companion;", "", "<init>", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lspace/hugster/sell/utils/security/LicenseResponse;", "DonutSell"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final KSerializer<LicenseResponse> serializer() {
         return (KSerializer)LicenseResponse.$serializer.INSTANCE;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }

   /** @deprecated */
   // $FF: synthetic class
   @Deprecated(
      message = "This synthesized declaration should not be used directly",
      level = DeprecationLevel.HIDDEN
   )
   @Metadata(
      mv = {2, 1, 0},
      k = 1,
      xi = 48,
      d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J\u0015\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0006¢\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"},
      d2 = {"space/hugster/sell/utils/security/LicenseResponse.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lspace/hugster/sell/utils/security/LicenseResponse;", "<init>", "()V", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "DonutSell"}
   )
   public class $serializer implements GeneratedSerializer<LicenseResponse> {
      @NotNull
      public static final $serializer INSTANCE = new $serializer();
      @NotNull
      private static final SerialDescriptor descriptor;

      private $serializer() {
      }

      public final void serialize(@NotNull Encoder encoder, @NotNull LicenseResponse value) {
         Intrinsics.checkNotNullParameter(encoder, "encoder");
         Intrinsics.checkNotNullParameter(value, "value");
         SerialDescriptor var3 = descriptor;
         CompositeEncoder var4 = encoder.beginStructure(var3);
         LicenseResponse.write$Self$DonutSell(value, var4, var3);
         var4.endStructure(var3);
      }

      @NotNull
      public final LicenseResponse deserialize(@NotNull Decoder decoder) {
         Intrinsics.checkNotNullParameter(decoder, "decoder");
         SerialDescriptor var2 = descriptor;
         boolean var3 = true;
         int var5 = 0;
         String var6 = null;
         String var7 = null;
         int var8 = 0;
         String var9 = null;
         String var10 = null;
         String var11 = null;
         String var12 = null;
         String var13 = null;
         String var14 = null;
         String var15 = null;
         String var16 = null;
         CompositeDecoder var17 = decoder.beginStructure(var2);
         if (var17.decodeSequentially()) {
            var6 = var17.decodeStringElement(var2, 0);
            var5 |= 1;
            var7 = var17.decodeStringElement(var2, 1);
            var5 |= 2;
            var8 = var17.decodeIntElement(var2, 2);
            var5 |= 4;
            var9 = var17.decodeStringElement(var2, 3);
            var5 |= 8;
            var10 = (String)var17.decodeNullableSerializableElement(var2, 4, (DeserializationStrategy)StringSerializer.INSTANCE, var10);
            var5 |= 16;
            var11 = (String)var17.decodeNullableSerializableElement(var2, 5, (DeserializationStrategy)StringSerializer.INSTANCE, var11);
            var5 |= 32;
            var12 = (String)var17.decodeNullableSerializableElement(var2, 6, (DeserializationStrategy)StringSerializer.INSTANCE, var12);
            var5 |= 64;
            var13 = (String)var17.decodeNullableSerializableElement(var2, 7, (DeserializationStrategy)StringSerializer.INSTANCE, var13);
            var5 |= 128;
            var14 = (String)var17.decodeNullableSerializableElement(var2, 8, (DeserializationStrategy)StringSerializer.INSTANCE, var14);
            var5 |= 256;
            var15 = var17.decodeStringElement(var2, 9);
            var5 |= 512;
            var16 = var17.decodeStringElement(var2, 10);
            var5 |= 1024;
         } else {
            while(var3) {
               int var4 = var17.decodeElementIndex(var2);
               switch (var4) {
                  case -1:
                     var3 = false;
                     break;
                  case 0:
                     var6 = var17.decodeStringElement(var2, 0);
                     var5 |= 1;
                     break;
                  case 1:
                     var7 = var17.decodeStringElement(var2, 1);
                     var5 |= 2;
                     break;
                  case 2:
                     var8 = var17.decodeIntElement(var2, 2);
                     var5 |= 4;
                     break;
                  case 3:
                     var9 = var17.decodeStringElement(var2, 3);
                     var5 |= 8;
                     break;
                  case 4:
                     var10 = (String)var17.decodeNullableSerializableElement(var2, 4, (DeserializationStrategy)StringSerializer.INSTANCE, var10);
                     var5 |= 16;
                     break;
                  case 5:
                     var11 = (String)var17.decodeNullableSerializableElement(var2, 5, (DeserializationStrategy)StringSerializer.INSTANCE, var11);
                     var5 |= 32;
                     break;
                  case 6:
                     var12 = (String)var17.decodeNullableSerializableElement(var2, 6, (DeserializationStrategy)StringSerializer.INSTANCE, var12);
                     var5 |= 64;
                     break;
                  case 7:
                     var13 = (String)var17.decodeNullableSerializableElement(var2, 7, (DeserializationStrategy)StringSerializer.INSTANCE, var13);
                     var5 |= 128;
                     break;
                  case 8:
                     var14 = (String)var17.decodeNullableSerializableElement(var2, 8, (DeserializationStrategy)StringSerializer.INSTANCE, var14);
                     var5 |= 256;
                     break;
                  case 9:
                     var15 = var17.decodeStringElement(var2, 9);
                     var5 |= 512;
                     break;
                  case 10:
                     var16 = var17.decodeStringElement(var2, 10);
                     var5 |= 1024;
                     break;
                  default:
                     throw new UnknownFieldException(var4);
               }
            }
         }

         var17.endStructure(var2);
         return new LicenseResponse(var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, (SerializationConstructorMarker)null);
      }

      @NotNull
      public final SerialDescriptor getDescriptor() {
         return descriptor;
      }

      @NotNull
      public final KSerializer<?>[] childSerializers() {
         KSerializer[] var1 = new KSerializer[]{StringSerializer.INSTANCE, StringSerializer.INSTANCE, IntSerializer.INSTANCE, StringSerializer.INSTANCE, BuiltinSerializersKt.getNullable((KSerializer)StringSerializer.INSTANCE), BuiltinSerializersKt.getNullable((KSerializer)StringSerializer.INSTANCE), BuiltinSerializersKt.getNullable((KSerializer)StringSerializer.INSTANCE), BuiltinSerializersKt.getNullable((KSerializer)StringSerializer.INSTANCE), BuiltinSerializersKt.getNullable((KSerializer)StringSerializer.INSTANCE), StringSerializer.INSTANCE, StringSerializer.INSTANCE};
         return var1;
      }

      static {
         PluginGeneratedSerialDescriptor var0 = new PluginGeneratedSerialDescriptor("space.hugster.sell.utils.security.LicenseResponse", INSTANCE, 11);
         var0.addElement("status_msg", false);
         var0.addElement("status_overview", false);
         var0.addElement("status_code", false);
         var0.addElement("status_id", false);
         var0.addElement("version", false);
         var0.addElement("clientname", false);
         var0.addElement("discord_username", false);
         var0.addElement("discord_tag", false);
         var0.addElement("discord_id", false);
         var0.addElement("main_class", false);
         var0.addElement("plugin_data", false);
         descriptor = (SerialDescriptor)var0;
      }
   }
}
