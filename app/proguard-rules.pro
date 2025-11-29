# App-specific ProGuard rules

# Keep all classes that might be accessed via reflection
-keep class com.example.myketiap.** { *; }

# Keep custom views
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

# Keep onClick methods in activities
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep R classes
-keep class **.R$* { *; }
