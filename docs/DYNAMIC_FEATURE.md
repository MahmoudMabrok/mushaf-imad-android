# Using Mushaf as an On-Demand Dynamic Feature

This guide shows the **minimal** steps to use the library as a Play Feature Delivery on-demand module so the large Quran assets are not included in the base APK.

## Why

The library ships with ~100MB+ of Quran page images + Realm database. Putting it in an on-demand dynamic feature means:

- New users download a much smaller base app
- Users who never open the Mushaf never download the assets
- Updates to the base app stay small

## Minimal Setup (3 steps)

### 1. Create a dynamic feature module

In your app project, create a new module (e.g. `:mushaf`) with the `com.android.dynamic-feature` plugin.

`mushaf/build.gradle.kts`:

```kotlin
plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "your.app.mushaf"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":app")) // or your base module
    implementation("com.github.MahmoudMabrok.mushaf-imad-android:mushaf-ui:0.1") // or the version you use
    implementation("com.google.android.play:feature-delivery:2.1.0")
    // Compose BOM + UI as needed by the library
}
```

In the **base** module (`app/build.gradle.kts`):

```kotlin
android {
    // ...
    dynamicFeatures += setOf(":mushaf")
}
```

### 2. Manifest for on-demand delivery

`mushaf/src/main/AndroidManifest.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:dist="http://schemas.android.com/apk/distribution">

    <dist:module
        dist:title="@string/mushaf_feature_title">
        <dist:delivery>
            <dist:on-demand />
        </dist:delivery>
        <dist:fusing dist:include="false" />
    </dist:module>

    <application />
</manifest>
```

Add the string in your base module:

```xml
<string name="mushaf_feature_title">Mushaf</string>
```

### 3. Request the module when needed

Use `SplitInstallManager` (or the Play Core API) to download the feature before navigating to the Mushaf screen:

```kotlin
val splitInstallManager = SplitInstallManagerFactory.create(context)

val request = SplitInstallRequest.newBuilder()
    .addModule("mushaf")
    .build()

splitInstallManager.startInstall(request)
    .addOnSuccessListener { /* navigate to Mushaf */ }
    .addOnFailureListener { /* show error */ }
```

Once the module is installed, you can use `MushafView` / `MushafWithPlayerView` normally. The library auto-initializes via ContentProvider even inside a dynamic feature.

## Notes

- Keep the feature module thin — it should only contain the dependency and any thin wrappers you need.
- Users who already installed the module will receive deltas on future updates of that module.
- For even smaller updates, consider moving the images to a Play Asset Delivery Asset Pack (future work).

## Example

See the `sample` module in this repository for a full working app. You can also look at how the feature is wired in real projects that use this library.
