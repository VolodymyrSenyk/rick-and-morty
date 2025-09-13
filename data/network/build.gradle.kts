plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = Config.General.applicationId + ".data.network"
}

dependencies {
    api(project(":domain:characters"))
    implementation(Config.Libs.inject)
    implementation(Config.Libs.gson)
    implementation(Config.Libs.retrofit)
}
