plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = Config.General.applicationId + ".data.datastore"
}

dependencies {
    api(project(":domain:settings"))
    implementation(Config.Libs.hilt)
    implementation(Config.Libs.datastore)
}
