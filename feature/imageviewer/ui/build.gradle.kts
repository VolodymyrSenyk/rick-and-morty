plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = Config.General.applicationId + ".feature.imageviewer.ui"
}

dependencies {
    implementation(project(":core:ui"))

    implementation(project(":feature:imageviewer:navigation"))

    implementation(project(":feature:settings:presentation"))

    implementation(Config.Libs.hilt)
    ksp(Config.Libs.hiltCompiler)

    implementation(Config.Libs.composeTooling)
    debugImplementation(Config.Libs.composePreview)
}
