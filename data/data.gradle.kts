plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "data"
}

dependencies {
    api(project(":domain"))
    implementation(Config.Libs.inject)
    implementation(Config.Libs.gson)
    implementation(Config.Libs.retrofit)
    implementation(Config.Libs.retrofitRxJava)
}
