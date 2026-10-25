# Delivery Modes Comparison

How you ship the Mushaf affects **base APK size**, **update size**, and **how much work** you do in the host app.

| Mode | Base APK size | Update size (code change) | Maintainability | When to use |
|------|---------------|---------------------------|-----------------|-------------|
| **1. Library directly** | Large (~150MB+) | Large (whole library) | Easiest | Prototyping, apps that always need the Mushaf |
| **2. Dynamic Feature** | Small | Medium (delta of feature module) | Medium | Most production apps |
| **3. Asset Pack** | Smallest | Smallest (code only) | Highest setup | Apps that update the Mushaf code often |

## 1. Library directly (bundled)

```kotlin
dependencies {
    implementation("com.github.MahmoudMabrok.mushaf-imad-android:mushaf-ui:0.1")
}
```

- Images + Realm + code are inside the base APK.
- Zero extra setup.
- Every user downloads everything on first install.
- Any change (even a one-line Kotlin fix) produces a large update.

## 2. Dynamic Feature (on-demand)

- Create a `:mushaf` module with `com.android.dynamic-feature`.
- Put `<dist:on-demand />` + `fusing="false"` in its manifest.
- Request the module with `SplitInstallManager` before opening the Mushaf.

**Pros**
- New users download a small base APK.
- Users who never open the Mushaf never download the images.

**Cons**
- You must handle the install flow.
- If you change code inside the feature, users who already have it get a non-trivial delta (because the images are still inside the same module).

See [DYNAMIC_FEATURE.md](DYNAMIC_FEATURE.md) and the `:mushaf-feature` sample module.

## 3. Asset Pack (on-demand)

- Move `quran-images/` into an Asset Pack (`deliveryType = on-demand`).
- Keep the feature module thin (code only).
- The library loads images from the Asset Pack when present, and falls back to bundled assets otherwise.

**Pros**
- Smallest possible updates when you only change Kotlin code.
- Images are downloaded only when needed.
- Better delta patching for the image pack.

**Cons**
- More moving parts (Asset Pack + feature module).
- You must request the pack (via `AssetPackManager`) before or while opening the Mushaf.

See [ASSET_PACK.md](ASSET_PACK.md).

## Sample app

The `sample` module contains a **Delivery Modes** screen that demonstrates all three:

1. Direct (bundled)
2. Dynamic Feature
3. Asset Pack

Run:

```bash
./gradlew :sample:installDebug
```

Then open **Delivery Modes Demo** from the home screen.
