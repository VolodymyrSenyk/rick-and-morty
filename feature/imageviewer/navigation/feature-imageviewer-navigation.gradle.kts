plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version Config.Versions.kotlinLanguage
}

android {
    namespace = Config.General.applicationId + ".feature.imageviewer.navigation"
}

dependencies {
    api(project(":core:navigation-compose"))
    implementation(Config.Libs.serialization)
}
