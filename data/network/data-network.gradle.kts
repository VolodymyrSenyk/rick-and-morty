plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "data.network"
}

dependencies {
    api(project(":domain"))
    implementation(Config.Libs.inject)
    implementation(Config.Libs.gson)
    implementation(Config.Libs.retrofit)
}
