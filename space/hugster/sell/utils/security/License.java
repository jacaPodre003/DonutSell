package space.hugster.sell.utils.security;

import java.io.Closeable;
import java.net.URI;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.DeserializationStrategy;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.json.JsonElementKt;
import kotlinx.serialization.json.JsonObject;
import kotlinx.serialization.json.JsonPrimitive;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u001a\u001a\u00020\u001bH\u0002J\u0006\u0010\u001c\u001a\u00020\u001bJ\u0006\u0010\u001d\u001a\u00020\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\"\u0010\u0017\u001a\u0004\u0018\u00010\u00052\b\u0010\u0016\u001a\u0004\u0018\u00010\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\rR\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"},
   d2 = {"Lspace/hugster/sell/utils/security/License;", "", "host", "Ljava/net/URI;", "licenseKey", "", "product", "hwid", "<init>", "(Ljava/net/URI;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getHost", "()Ljava/net/URI;", "getLicenseKey", "()Ljava/lang/String;", "getProduct", "getHwid", "response", "Lspace/hugster/sell/utils/security/LicenseResponse;", "getResponse", "()Lspace/hugster/sell/utils/security/LicenseResponse;", "setResponse", "(Lspace/hugster/sell/utils/security/LicenseResponse;)V", "value", "errorMessage", "getErrorMessage", "jwtToken", "authenticate", "", "check", "getErrorDetails", "DonutSell"}
)
@SourceDebugExtension({"SMAP\nLicense.kt\nKotlin\n*S Kotlin\n*F\n+ 1 License.kt\nspace/hugster/sell/utils/security/License\n+ 2 Json.kt\nkotlinx/serialization/json/Json\n*L\n1#1,127:1\n222#2:128\n*S KotlinDebug\n*F\n+ 1 License.kt\nspace/hugster/sell/utils/security/License\n*L\n94#1:128\n*E\n"})
public final class License {
   @NotNull
   private final URI host;
   @NotNull
   private final String licenseKey;
   @NotNull
   private final String product;
   @NotNull
   private final String hwid;
   @Nullable
   private LicenseResponse response;
   @Nullable
   private String errorMessage;
   @Nullable
   private String jwtToken;

   public License(@NotNull URI host, @NotNull String licenseKey, @NotNull String product, @NotNull String hwid) {
      Intrinsics.checkNotNullParameter(host, "host");
      Intrinsics.checkNotNullParameter(licenseKey, "licenseKey");
      Intrinsics.checkNotNullParameter(product, "product");
      Intrinsics.checkNotNullParameter(hwid, "hwid");
      super();
      this.host = host;
      this.licenseKey = licenseKey;
      this.product = product;
      this.hwid = hwid;
   }

   @NotNull
   public final URI getHost() {
      return this.host;
   }

   @NotNull
   public final String getLicenseKey() {
      return this.licenseKey;
   }

   @NotNull
   public final String getProduct() {
      return this.product;
   }

   @NotNull
   public final String getHwid() {
      return this.hwid;
   }

   @Nullable
   public final LicenseResponse getResponse() {
      return this.response;
   }

   public final void setResponse(@Nullable LicenseResponse <set-?>) {
      this.response = <set-?>;
   }

   @Nullable
   public final String getErrorMessage() {
      return this.errorMessage;
   }

   private final boolean authenticate() {
      HttpPost $this$authenticate_u24lambda_u240 = new HttpPost(this.host.resolve("/api/authenticate"));
      int var4 = 0;
      $this$authenticate_u24lambda_u240.setHeader("Content-Type", "application/json");
      $this$authenticate_u24lambda_u240.setEntity((HttpEntity)(new StringEntity("{\n                    \"licenseKey\": \"" + this.licenseKey + "\",\n                    \"product\": \"" + this.product + "\",\n                    \"hwid\": \"" + this.hwid + "\"\n                }", ContentType.APPLICATION_JSON)));
      HttpPost post = $this$authenticate_u24lambda_u240;

      try {
         Closeable $this$authenticate_u24lambda_u240 = (Closeable)HttpClients.createDefault();
         Throwable $this$authenticate_u24lambda_u240 = null;

         label135: {
            boolean var12;
            try {
               CloseableHttpClient client = (CloseableHttpClient)$this$authenticate_u24lambda_u240;
               int var5 = 0;
               CloseableHttpResponse httpResponse = client.execute((HttpUriRequest)post);
               int statusCode = httpResponse.getStatusLine().getStatusCode();
               if (statusCode == 200) {
                  String var25;
                  label106: {
                     String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                     Json.Default var10000 = Json.Default;
                     Intrinsics.checkNotNull(jsonResponse);
                     JsonObject jsonObject = JsonElementKt.getJsonObject(var10000.parseToJsonElement(jsonResponse));
                     JsonElement var23 = (JsonElement)jsonObject.get("token");
                     if (var23 != null) {
                        JsonPrimitive var24 = JsonElementKt.getJsonPrimitive(var23);
                        if (var24 != null) {
                           var25 = var24.getContent();
                           break label106;
                        }
                     }

                     var25 = null;
                  }

                  this.jwtToken = var25;
                  CharSequence var10 = (CharSequence)this.jwtToken;
                  if (var10 == null || var10.length() == 0) {
                     this.errorMessage = "Invalid token received from server";
                     boolean var11 = false;
                     return var11;
                  }

                  var4 = 1;
                  break label135;
               }

               String var10001 = httpResponse.getStatusLine().getReasonPhrase();
               this.errorMessage = "Auth Error: " + var10001 + " (Code: " + statusCode + ")";
               var12 = false;
            } catch (Throwable var16) {
               $this$authenticate_u24lambda_u240 = var16;
               throw var16;
            } finally {
               CloseableKt.closeFinally($this$authenticate_u24lambda_u240, $this$authenticate_u24lambda_u240);
            }

            return var12;
         }

         $this$authenticate_u24lambda_u240 = (boolean)var4;
      } catch (Exception e) {
         this.errorMessage = "Connection Error: " + e.getMessage();
         e.printStackTrace();
         $this$authenticate_u24lambda_u240 = false;
      }

      return $this$authenticate_u24lambda_u240;
   }

   public final boolean check() {
      if (this.jwtToken == null && !this.authenticate()) {
         return false;
      } else {
         HttpPost $this$check_u24lambda_u242 = new HttpPost(this.host.resolve("/api/client"));
         int var4 = 0;
         $this$check_u24lambda_u242.setHeader("Authorization", "Bearer " + this.jwtToken);
         $this$check_u24lambda_u242.setHeader("Content-Type", "application/json");
         $this$check_u24lambda_u242.setEntity((HttpEntity)(new StringEntity("{\n                    \"licenseKey\": \"" + this.licenseKey + "\",\n                    \"product\": \"" + this.product + "\",\n                    \"hwid\": \"" + this.hwid + "\"\n                }", ContentType.APPLICATION_JSON)));
         HttpPost post = $this$check_u24lambda_u242;

         try {
            Closeable $this$check_u24lambda_u242 = (Closeable)HttpClients.createDefault();
            Throwable $this$check_u24lambda_u242 = null;

            label119: {
               boolean var14;
               try {
                  CloseableHttpClient client = (CloseableHttpClient)$this$check_u24lambda_u242;
                  int var5 = 0;
                  CloseableHttpResponse httpResponse = client.execute((HttpUriRequest)post);
                  int statusCode = httpResponse.getStatusLine().getStatusCode();
                  if (statusCode == 200) {
                     String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                     Json this_$iv = (Json)Json.Default;
                     Intrinsics.checkNotNull(jsonResponse);
                     int $i$f$decodeFromString = 0;
                     this_$iv.getSerializersModule();
                     this.response = (LicenseResponse)this_$iv.decodeFromString((DeserializationStrategy)BuiltinSerializersKt.getNullable(LicenseResponse.Companion.serializer()), jsonResponse);
                     LicenseResponse var10000 = this.response;
                     boolean var27;
                     if (var10000 != null) {
                        LicenseResponse it = var10000;
                        int var13 = 0;
                        if (it.getStatus_code() != 200) {
                           String var28 = it.getStatus_msg();
                           this.errorMessage = "License Error: " + var28 + " (Code: " + it.getStatus_code() + ")";
                           var27 = false;
                        } else if (!Intrinsics.areEqual(it.getStatus_overview(), "success")) {
                           String var29 = it.getStatus_overview();
                           this.errorMessage = "License Status: " + var29 + " - " + it.getStatus_msg();
                           var27 = false;
                        } else if (((CharSequence)it.getPlugin_data()).length() == 0) {
                           this.errorMessage = "Invalid plugin data received";
                           var27 = false;
                        } else {
                           var27 = true;
                        }
                     } else {
                        License $this$check_u24lambda_u245_u24lambda_u244 = this;
                        int var26 = 0;
                        $this$check_u24lambda_u245_u24lambda_u244.errorMessage = "Empty response from server";
                        var27 = false;
                     }

                     var4 = var27;
                     break label119;
                  }

                  String var10001 = httpResponse.getStatusLine().getReasonPhrase();
                  this.errorMessage = "License Error: " + var10001 + " (Code: " + statusCode + ")";
                  var14 = false;
               } catch (Throwable var18) {
                  $this$check_u24lambda_u242 = var18;
                  throw var18;
               } finally {
                  CloseableKt.closeFinally($this$check_u24lambda_u242, $this$check_u24lambda_u242);
               }

               return var14;
            }

            $this$check_u24lambda_u242 = (boolean)var4;
         } catch (Exception e) {
            this.errorMessage = "Connection Error: " + e.getMessage();
            e.printStackTrace();
            $this$check_u24lambda_u242 = false;
         }

         return $this$check_u24lambda_u242;
      }
   }

   @NotNull
   public final String getErrorDetails() {
      String var10000 = this.errorMessage;
      if (var10000 == null) {
         var10000 = "No error details available";
      }

      return var10000;
   }
}
