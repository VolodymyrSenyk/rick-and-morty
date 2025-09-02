plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "feature.characters.presentation"
}

dependencies {
    api(project(":core:arch-android"))

    implementation(project(":domain:characters"))

    implementation(Config.Libs.hilt)
    ksp(Config.Libs.hiltCompiler)

    testImplementation(project(":core:test-util"))
}
