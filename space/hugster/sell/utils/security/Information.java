package space.hugster.sell.utils.security;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 1, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\r\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0014\u0010\u0004\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0014\u0010\f\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0014\u0010\u000e\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0014\u0010\u0010\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007¨\u0006\u0012"},
   d2 = {"Lspace/hugster/sell/utils/security/Information;", "", "<init>", "()V", "userId", "", "getUserId", "()Ljava/lang/String;", "username", "getUsername", "resourceId", "getResourceId", "resourceVersion", "getResourceVersion", "downloadTimeStamp", "getDownloadTimeStamp", "nonce", "getNonce", "DonutSell"}
)
public final class Information {
   @NotNull
   public static final Information INSTANCE = new Information();
   @NotNull
   private static final String userId = "669438";
   @NotNull
   private static final String username = "notstopid";
   @NotNull
   private static final String resourceId = "66038";
   @NotNull
   private static final String resourceVersion = "246663";
   @NotNull
   private static final String downloadTimeStamp = "1777565062";
   @NotNull
   private static final String nonce = "b6da7fadd6e0adc399e8a5677d9aade3";

   private Information() {
   }

   @NotNull
   public final String getUserId() {
      return userId;
   }

   @NotNull
   public final String getUsername() {
      return username;
   }

   @NotNull
   public final String getResourceId() {
      return resourceId;
   }

   @NotNull
   public final String getResourceVersion() {
      return resourceVersion;
   }

   @NotNull
   public final String getDownloadTimeStamp() {
      return downloadTimeStamp;
   }

   @NotNull
   public final String getNonce() {
      return nonce;
   }
}
