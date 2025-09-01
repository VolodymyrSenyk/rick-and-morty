plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "feature.splash.presentation"
}

dependencies {
    implementation(project(":core:arch-android"))

    implementation(Config.Libs.hilt)
    ksp(Config.Libs.hiltCompiler)

    testImplementation(project(":core:test-util"))
}
