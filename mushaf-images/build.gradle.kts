// Optional artifact: the Mushaf page line images (assets/quran-images/{page}/{line}.png,
// ~113 MB). Since 0.4.0 they are NOT inside mushaf-core, so apps can deliver them with
// Play Asset Delivery (install-time pack) instead and keep app updates small.
// Add this artifact only if you want the images bundled in your APK/AAB base.
plugins {
    alias(libs.plugins.android.library)
    `maven-publish`
}

android {
    namespace = "com.mushafimad.images"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // PNGs are already compressed - do not let aapt try again.
    androidResources {
        noCompress += "png"
    }

    publishing {
        singleVariant("release")
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = property("GROUP").toString()
                artifactId = "mushaf-images"
                version = property("VERSION_NAME").toString()
            }
        }
    }
}
