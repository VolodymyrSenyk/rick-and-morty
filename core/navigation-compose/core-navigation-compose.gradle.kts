plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "navigation.compose"
}

dependencies {
    implementation(project(":core:arch"))
    api(Config.Libs.composeNavigation)
    api(Config.Libs.composeNavigationHilt)
    implementation(Config.Libs.coroutinesCore)
}
