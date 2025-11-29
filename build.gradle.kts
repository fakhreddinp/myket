// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
        classpath("com.guardsquare:proguard-gradle:7.3.2")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://dl.bintray.com/myket/maven") }
    }
}

// تعریف extension properties
extra.apply {
    set("compileSdkVersion", 34)
    set("minSdkVersion", 21)
    set("targetSdkVersion", 34)
    set("versionCode", 1)
    set("versionName", "1.0.0")
    
    set("appCompatVersion", "1.6.1")
    set("materialVersion", "1.9.0")
    set("myketBillingVersion", "1.6")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
