plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "feature.characters"
}

dependencies {
    implementation(project(":core:arch-android"))
    implementation(project(":core:ui"))

    implementation(project(":domain:characters"))

    implementation(project(":feature:characters:navigation"))

    implementation(project(":feature:settings:viewmodel"))
    implementation(project(":feature:splash:viewmodel"))

    implementation(Config.Libs.hilt)
    ksp(Config.Libs.hiltCompiler)

    implementation(Config.Libs.coil)
    implementation(Config.Libs.coilNetwork)

    implementation(Config.Libs.composeTooling)
    debugImplementation(Config.Libs.composePreview)

    testImplementation(project(":core:test-util"))
}
