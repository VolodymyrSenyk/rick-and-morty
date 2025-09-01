plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "data.datastore"
}

dependencies {
    api(project(":domain:settings"))
    implementation(Config.Libs.hilt)
    implementation(Config.Libs.datastore)
}
