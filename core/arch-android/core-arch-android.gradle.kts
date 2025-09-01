plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "arch.android"
}

dependencies {
    api(project(":core:arch"))
    implementation(Config.Libs.inject)
    implementation(Config.Libs.lifecycle)
}
