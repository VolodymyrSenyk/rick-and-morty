plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "core.ui"
}

dependencies {
    implementation(project(":core:arch-android"))
    api(project(":domain:settings:api"))
    implementation(Config.Libs.ktx)
    api(platform(Config.Libs.composeBom))
    api(Config.Libs.composeUi)
    api(Config.Libs.composeMaterial)
    implementation(Config.Libs.composeActivity)
    implementation(Config.Libs.composeConstraintlayout)
    implementation(Config.Libs.composeExtendedIcons)
    implementation(Config.Libs.composeMaterialWindowSize)
    implementation(Config.Libs.composeTooling)
    debugImplementation(Config.Libs.composePreview)
}
