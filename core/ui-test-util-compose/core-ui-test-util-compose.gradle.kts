plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = Config.General.applicationId + ".uitestutil.compose"
}

dependencies {
    implementation(project(":core:arch"))
    implementation(Config.Libs.uiautomator)
    api(platform(Config.Libs.composeBom))
    api(Config.Libs.composeUiTest)
    api(Config.Libs.composeUiTestJUnit)
}
