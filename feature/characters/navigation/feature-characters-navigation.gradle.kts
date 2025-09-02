plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version Config.Versions.kotlinLanguage
}

android {
    namespace = "feature.characters.navigation"
}

dependencies {
    api(project(":core:navigation-compose"))
    api(project(":domain:characters:api"))
    implementation(Config.Libs.serialization)
}
