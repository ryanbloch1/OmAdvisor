# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/admin/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}



##---------------Begin: proguard configuration for Android Architecture Components  ----------

# LifecycleObserver's empty constructor is considered to be unused by proguard
-keepclassmembers class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}
# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends android.arch.lifecycle.ViewModel {
    <init>(...);
}
# keep Lifecycle State and Event enums values
-keepclassmembers class android.arch.lifecycle.Lifecycle$State { *; }
-keepclassmembers class android.arch.lifecycle.Lifecycle$Event { *; }
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @android.arch.lifecycle.OnLifecycleEvent *;
}

##---------------End: proguard configuration for Android Architecture Components  ----------



##---------------Begin: proguard configuration for Android Device Names  ----------
# None
##---------------End: proguard configuration for Android Device Names  ----------



##---------------Begin: proguard configuration for Android GIF Drawable  ----------
# None
##---------------End: proguard configuration for Android GIF Drawable  ----------



##---------------Begin: proguard configuration for ChatKit  ----------

-keep class * extends com.stfalcon.chatkit.messages.MessageHolders$OutcomingTextMessageViewHolder {
    public <init>(android.view.View, java.lang.Object);
    public <init>(android.view.View);
}
-keep class * extends com.stfalcon.chatkit.messages.MessageHolders$IncomingTextMessageViewHolder {
    public <init>(android.view.View, java.lang.Object);
    public <init>(android.view.View);
}
-keep class * extends com.stfalcon.chatkit.messages.MessageHolders$IncomingImageMessageViewHolder {
    public <init>(android.view.View, java.lang.Object);
    public <init>(android.view.View);
}
-keep class * extends com.stfalcon.chatkit.messages.MessageHolders$OutcomingImageMessageViewHolder {
    public <init>(android.view.View, java.lang.Object);
    public <init>(android.view.View);
}

##---------------End: proguard configuration for ChatKit  ----------



##---------------Begin: proguard configuration for Circle Image View  ----------
# None
##---------------End: proguard configuration for Circle Image View  ----------



##---------------Begin: proguard configuration for Crashlytics  ----------

-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

##---------------End: proguard configuration for Crashlytics  ----------



##---------------Begin: proguard configuration for Eventbus  ----------

-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

##---------------End: proguard configuration for Eventbus  ----------



##---------------Begin: proguard configuration for Firebase  ----------
# None
##---------------End: proguard configuration for Firebase  ----------



##---------------Begin: proguard configuration for Glide  ----------

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

##---------------End: proguard configuration for Glide  ----------



##---------------Begin: proguard configuration for Gson  ----------

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

##---------------End: proguard configuration for Gson  ----------



##---------------Begin: proguard configuration for Joda-Time  ----------
# None
##---------------End: proguard configuration for Joda-Time  ----------



##---------------Begin: proguard configuration for OKHTTP  ----------

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

##---------------End: proguard configuration for OKHTTP  ----------



##---------------Begin: proguard configuration for OKIO  ----------

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

##---------------End: proguard configuration for OKIO  ----------



##---------------Begin: proguard configuration for Raizlabs DBFlow  ----------

-keep class * extends com.raizlabs.android.dbflow.config.DatabaseHolder { *; }

##---------------End: proguard configuration for Raizlabs DBFlow  ----------



##---------------Begin: proguard configuration for Retrofit  ----------

# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>


# Need to keep our models visible!!!
-keep class com.cobi.puresurveyandroid.model.** { *; }
-keep class com.cobi.puresurveyandroid.model.sales.** { *; }
-keep class com.cobi.puresurveyandroid.model.ViewModel.** { *; }
-keep class com.cobi.puresurveyandroid.util.Constants { *; }
-keep class com.cobi.puresurveyandroid.util.cPriority { *; }

##---------------End: proguard configuration for Retrofit  ----------



##---------------Begin: proguard configuration for Rich Path  ----------
# None
##---------------End: proguard configuration for Rich Path  ----------



##---------------Begin: proguard configuration for Rounded Letter View  ----------
# None
##---------------End: proguard configuration for Rounded Letter View  ----------



##---------------Begin: proguard configuration for Secure Preferences  ----------
# None
##---------------End: proguard configuration for Secure Preferences  ----------



##---------------Begin: proguard configuration for Shortcut Badger  ----------
# None
##---------------End: proguard configuration for Shortcut Badger  ----------



##---------------Begin: proguard configuration for ZXing  ----------
# None
##---------------End: proguard configuration for ZXing  ----------


##---------------Begin: proguard configuration for Pretty Time ----------

-keep class org.ocpsoft.prettytime.i18n.**

##---------------End: proguard configuration for Pretty Time  ----------




-ignorewarnings
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
