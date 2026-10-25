pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MushafImad"
include(":mushaf-core")     // Core data layer (no Compose)
include(":mushaf-ui")       // UI components (depends on mushaf-core)
include(":sample")          // Sample app (depends on mushaf-ui)
include(":mushaf-feature")  // Sample on-demand dynamic feature module
include(":mushaf-assets")   // On-demand Asset Pack for Quran images
