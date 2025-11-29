# ProGuard rules for Myket IAP and security

# Keep IAB classes
-keep class com.myket.billing.** { *; }
-keep class com.android.vending.billing.** { *; }

# Keep Security classes
-keep class com.example.myketiap.Security { *; }
-keep class com.example.myketiap.BillingManager { *; }

# Keep Purchase and related classes
-keep class com.myket.billing.Purchase { *; }
-keep class com.myket.billing.IabHelper { *; }
-keep class com.myket.billing.IabResult { *; }
-keep class com.myket.billing.Inventory { *; }

# Keep Gson classes
-keep class com.google.gson.** { *; }
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Keep Retrofit
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Keep OkHttp
-keep class okhttp3.** { *; }
-keep class okio.** { *; }

# Keep network security
-keep class javax.net.ssl.** { *; }
-keep class java.security.** { *; }

# Keep RSA and security classes
-keep class java.security.** { *; }
-keep class javax.crypto.** { *; }

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# General Android rules
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# Keep native methods
-keepclasseswithmembers class * {
    native <methods>;
}

# Keep parcelable
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
