package space.hugster.sell.utils.security;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import space.hugster.sell.utils.CC;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tJ*\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\t2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0002J\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\u0007H\u0002¨\u0006\u0011"},
   d2 = {"Lspace/hugster/sell/utils/security/LicenseValidator;", "", "<init>", "()V", "validate", "", "license", "Lspace/hugster/sell/utils/security/License;", "licenseKey", "", "hwid", "logLicenseError", "", "errorDetails", "response", "Lspace/hugster/sell/utils/security/LicenseResponse;", "logLicenseSuccess", "DonutSell"}
)
public final class LicenseValidator {
   public final boolean validate(@NotNull License license, @NotNull String licenseKey, @NotNull String hwid) {
      Intrinsics.checkNotNullParameter(license, "license");
      Intrinsics.checkNotNullParameter(licenseKey, "licenseKey");
      Intrinsics.checkNotNullParameter(hwid, "hwid");
      CC.INSTANCE.console("");
      CC.INSTANCE.console("&7━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
      CC.INSTANCE.console("&e  License Validation");
      CC.INSTANCE.console("&7━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
      CC.INSTANCE.console("&8  HWID: &f" + hwid);
      CC.INSTANCE.console("");
      LicenseValidator var5 = this;

      Object $this$validate_u24lambda_u240;
      try {
         Result.Companion var14 = Result.Companion;
         LicenseValidator $this$validate_u24lambda_u240 = var5;
         int var7 = 0;
         $this$validate_u24lambda_u240 = Result.constructor-impl(license.check());
      } catch (Throwable var9) {
         Result.Companion var10000 = Result.Companion;
         $this$validate_u24lambda_u240 = Result.constructor-impl(ResultKt.createFailure(var9));
      }

      Object var10 = $this$validate_u24lambda_u240;
      Throwable var15 = Result.exceptionOrNull-impl($this$validate_u24lambda_u240);
      if (var15 != null) {
         Throwable e = var15;
         int var8 = 0;
         CC.INSTANCE.console("&c✗ Connection failed: " + e.getMessage());
         e.printStackTrace();
      }

      Boolean e = false;
      boolean valid = (Boolean)(Result.isFailure-impl(var10) ? e : var10);
      if (!valid) {
         this.logLicenseError(license.getErrorDetails(), license.getResponse(), licenseKey, hwid);
         return false;
      } else {
         this.logLicenseSuccess(license);
         return true;
      }
   }

   private final void logLicenseError(String errorDetails, LicenseResponse response, String licenseKey, String hwid) {
      String var10000;
      label84: {
         CC.INSTANCE.console("");
         CC.INSTANCE.console("&c━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
         CC.INSTANCE.console("&c  ✗ License Validation Failed");
         CC.INSTANCE.console("&c━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
         CC.INSTANCE.console("");
         if (response != null) {
            var10000 = response.getStatus_msg();
            if (var10000 != null) {
               break label84;
            }
         }

         var10000 = "";
      }

      String statusMsg = var10000;
      if (!StringsKt.contains((CharSequence)statusMsg, (CharSequence)"HWID_LIMIT_EXCEEDED", true) && !StringsKt.contains((CharSequence)errorDetails, (CharSequence)"HWID_LIMIT_EXCEEDED", true)) {
         if (!StringsKt.contains((CharSequence)statusMsg, (CharSequence)"IP_LIMIT_EXCEEDED", true) && !StringsKt.contains((CharSequence)errorDetails, (CharSequence)"IP_LIMIT_EXCEEDED", true)) {
            if (!StringsKt.contains((CharSequence)statusMsg, (CharSequence)"IP_NOT_AUTHORIZED", true) && !StringsKt.contains((CharSequence)statusMsg, (CharSequence)"IP_NOT_IN_LICENSE", true) && !StringsKt.contains((CharSequence)errorDetails, (CharSequence)"IP_NOT_AUTHORIZED", true) && !StringsKt.contains((CharSequence)errorDetails, (CharSequence)"IP_NOT_IN_LICENSE", true)) {
               if (!StringsKt.contains((CharSequence)statusMsg, (CharSequence)"INVALID_LICENSE", true) && !StringsKt.contains((CharSequence)errorDetails, (CharSequence)"INVALID_LICENSE", true)) {
                  if (!StringsKt.contains((CharSequence)statusMsg, (CharSequence)"INVALID_PRODUCT", true) && !StringsKt.contains((CharSequence)errorDetails, (CharSequence)"INVALID_PRODUCT", true)) {
                     if (StringsKt.contains((CharSequence)errorDetails, (CharSequence)"Auth Error", true)) {
                        CC.INSTANCE.console("&c  Error: &fAuthentication Failed");
                        CC.INSTANCE.console("&7  " + errorDetails);
                        if (StringsKt.contains$default((CharSequence)errorDetails, (CharSequence)"525", false, 2, (Object)null)) {
                           CC.INSTANCE.console("&8  SSL Handshake Failed (Cloudflare Error)");
                           CC.INSTANCE.console("&8  • Check Cloudflare SSL/TLS settings");
                           CC.INSTANCE.console("&8  • Verify server SSL certificate");
                           CC.INSTANCE.console("&8  • Try changing SSL mode to 'Flexible'");
                        } else if (StringsKt.contains$default((CharSequence)errorDetails, (CharSequence)"500", false, 2, (Object)null)) {
                           CC.INSTANCE.console("&8  Server error - this may indicate:");
                           CC.INSTANCE.console("&8  • Database connection issues");
                           CC.INSTANCE.console("&8  • License configuration problems");
                           CC.INSTANCE.console("&8  • Contact support with error code 500");
                        } else if (!StringsKt.contains$default((CharSequence)errorDetails, (CharSequence)"502", false, 2, (Object)null) && !StringsKt.contains$default((CharSequence)errorDetails, (CharSequence)"503", false, 2, (Object)null)) {
                           if (StringsKt.contains$default((CharSequence)errorDetails, (CharSequence)"522", false, 2, (Object)null) || StringsKt.contains$default((CharSequence)errorDetails, (CharSequence)"523", false, 2, (Object)null) || StringsKt.contains$default((CharSequence)errorDetails, (CharSequence)"524", false, 2, (Object)null)) {
                              CC.INSTANCE.console("&8  Connection timeout (Cloudflare Error)");
                              CC.INSTANCE.console("&8  • Server may be overloaded");
                              CC.INSTANCE.console("&8  • Check if server is responding");
                           }
                        } else {
                           CC.INSTANCE.console("&8  Server temporarily unavailable");
                           CC.INSTANCE.console("&8  • The license server may be down");
                           CC.INSTANCE.console("&8  • Try again in a few minutes");
                        }
                     } else if (StringsKt.contains((CharSequence)errorDetails, (CharSequence)"Connection Error", true)) {
                        CC.INSTANCE.console("&c  Error: &fConnection Failed");
                        CC.INSTANCE.console("&7  Check your internet and firewall settings");
                     } else if (StringsKt.contains((CharSequence)errorDetails, (CharSequence)"Invalid token", true)) {
                        CC.INSTANCE.console("&c  Error: &fInvalid Token");
                        CC.INSTANCE.console("&7  Server communication error");
                     } else {
                        CC.INSTANCE.console("&c  Error: &fUnknown Error");
                        CC.INSTANCE.console("&7  " + errorDetails);
                        if (response != null) {
                           CC.INSTANCE.console("&8  Status: " + response.getStatus_msg());
                           CC.INSTANCE.console("&8  Code: " + response.getStatus_code());
                        }
                     }
                  } else {
                     CC.INSTANCE.console("&c  Error: &fInvalid Product");
                     CC.INSTANCE.console("&7  This license is not for 'Sell'");
                     CC.INSTANCE.console("&8  Verify you purchased the correct license");
                  }
               } else {
                  CC.INSTANCE.console("&c  Error: &fInvalid License");
                  CC.INSTANCE.console("&7  The license key does not exist or is invalid");
                  CC.INSTANCE.console("&8  Key: &f" + licenseKey);
               }
            } else {
               CC.INSTANCE.console("&c  Error: &fIP Not Authorized");
               CC.INSTANCE.console("&7  This IP is not registered to the license");
            }
         } else {
            CC.INSTANCE.console("&c  Error: &fIP Limit Exceeded");
            CC.INSTANCE.console("&7  Maximum IPs reached for this license");
            CC.INSTANCE.console("&7  Contact support to manage your registered IPs");
         }
      } else {
         CC.INSTANCE.console("&c  Error: &fHWID Limit Exceeded");
         CC.INSTANCE.console("&7  Maximum servers reached for this license");
         CC.INSTANCE.console("&8  Current HWID: &f" + hwid);
         CC.INSTANCE.console("&7  Contact support to manage your registered HWIDs");
      }

      CC.INSTANCE.console("");
      CC.INSTANCE.console("&7  Contact support if you need assistance");
      CC.INSTANCE.console("&c━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
      CC.INSTANCE.console("");
   }

   private final void logLicenseSuccess(License license) {
      CC.INSTANCE.console("");
      CC.INSTANCE.console("&a━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
      CC.INSTANCE.console("&a  ✓ License Verified");
      CC.INSTANCE.console("&a━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
      LicenseResponse var10000 = license.getResponse();
      if (var10000 != null) {
         LicenseResponse response = var10000;
         int var3 = 0;
         CC.INSTANCE.console("");
         CC var7 = CC.INSTANCE;
         String var10001 = response.getStatus_overview().toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var10001, "toUpperCase(...)");
         var7.console("&8  Status: &a" + var10001);
         CharSequence var4 = (CharSequence)response.getClientname();
         if (var4 != null && !StringsKt.isBlank(var4)) {
            CC.INSTANCE.console("&8  Client: &f" + response.getClientname());
         }

         var4 = (CharSequence)response.getDiscord_tag();
         if (var4 != null && !StringsKt.isBlank(var4)) {
            CC.INSTANCE.console("&8  Discord: &b" + response.getDiscord_tag());
         }

         var4 = (CharSequence)response.getVersion();
         if (var4 != null && !StringsKt.isBlank(var4)) {
            CC.INSTANCE.console("&8  Version: &e" + response.getVersion());
         }
      }

      CC.INSTANCE.console("");
      CC.INSTANCE.console("&a━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
      CC.INSTANCE.console("");
   }
}
