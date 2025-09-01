plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "feature.settings.viewmodel"
}

dependencies {
    implementation(project(":core:arch-android"))
    implementation(project(":domain:settings"))

    implementation(Config.Libs.hilt)
    ksp(Config.Libs.hiltCompiler)
}
