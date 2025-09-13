plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = Config.General.applicationId + ".arch.android"
}

dependencies {
    api(project(":core:arch"))
    implementation(Config.Libs.inject)
    implementation(Config.Libs.lifecycle)
}
