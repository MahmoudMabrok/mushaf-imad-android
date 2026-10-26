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
include(":mushaf-core")
include(":mushaf-ui")
include(":sample")
include(":sample-delivery")   // Delivery modes demo (Dynamic Feature + Asset Pack)
include(":mushaf_feature")
include(":mushaf-assets")
