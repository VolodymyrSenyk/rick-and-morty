plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "testutil"
}

dependencies {
    api(platform(Config.Libs.jUnitBom))
    api(Config.Libs.jupiterApi)
    api(Config.Libs.mockK)
    api(Config.Libs.coroutinesTest)
    implementation(Config.Libs.annotation)
    runtimeOnly(Config.Libs.jupiterEngine)
    runtimeOnly(Config.Libs.junitPlatformLauncher)
}
