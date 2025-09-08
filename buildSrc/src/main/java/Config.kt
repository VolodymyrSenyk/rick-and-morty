@file:Suppress("ConstPropertyName")

object Config {

    object Android {

        const val applicationId = "com.senyk.rickandmorty"

        const val versionName = "2.1.1"
        val versionCode = calculateVersionName()

        const val minSdkVersion = 26
        const val targetSdkVersion = 36
    }

    object Versions {

        // Build
        const val gradlePlugin = "8.12.2"
        const val kotlinLanguage = "2.2.10"
        const val kotlinComposeCompilerExtension = "1.5.15"
        const val serialization = "1.9.0"
        const val annotation = "1.9.1"
        const val ksp = "2.2.10-2.0.2"

        // Arch components
        const val lifecycle = "2.9.3"
        const val navigation = "2.9.3"
        const val composeNavigationHilt = "1.2.0"

        // UI
        const val material = "1.12.0"
        const val splashscreen = "1.0.1"
        const val composeConstraintlayout = "1.1.1"
        const val composeBom = "2025.07.00"
        const val accompanist = "0.36.0"

        // Dependency injection
        const val inject = "1"
        const val hilt = "2.57.1"

        // Persistence
        const val room = "2.7.2"
        const val datastore = "1.1.7"

        // Networking
        const val okhttp = "5.1.0"
        const val retrofit = "3.0.0"

        // Multithreading
        const val coroutines = "1.10.2"

        // Utils
        const val gson = "2.13.1"
        const val coil = "3.3.0"

        // Development
        const val kermit = "2.0.4"
        const val leakCanary = "2.14"
        const val detekt = "1.23.8"
        const val gradleDoctor = "0.12.0"
        const val versionsPlugin = "0.52.0"
        const val sonarqube = "6.2.0.5505"

        // Testing
        const val jUnitBom = "5.13.4"
        const val mockK = "1.14.5"
        const val coroutinesTest = "1.10.2"
        const val turbine = "1.2.1"

        // UI testing
        const val testOrchestrator = "1.6.1"
        const val uiautomator = "2.3.0"
    }

    object Libs {

        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinLanguage}"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
        const val annotation = "androidx.annotation:annotation:${Versions.annotation}"
        const val lifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

        // UI
        const val material = "com.google.android.material:material:${Versions.material}"
        const val splashscreen = "androidx.core:core-splashscreen:${Versions.splashscreen}"
        const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
        const val composeUi = "androidx.compose.ui:ui"
        const val composeActivity = "androidx.activity:activity-compose"
        const val composeMaterial = "androidx.compose.material3:material3"
        const val composeMaterialWindowSize = "androidx.compose.material3:material3-window-size-class-android"
        const val composeTooling = "androidx.compose.ui:ui-tooling"
        const val composePreview = "androidx.compose.ui:ui-tooling-preview"
        const val composeConstraintlayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraintlayout}"
        const val composeExtendedIcons = "androidx.compose.material:material-icons-extended"
        const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
        const val composeNavigationHilt = "androidx.hilt:hilt-navigation-compose:${Versions.composeNavigationHilt}"
        const val accompanistNavigationAnimation = "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanist}"

        // DI
        const val inject = "javax.inject:javax.inject:${Versions.inject}"
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

        // Persistence
        const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        const val datastore = "androidx.datastore:datastore-preferences:${Versions.datastore}"

        // Networking
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

        // Multithreading
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

        // Utils
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
        const val coil = "io.coil-kt.coil3:coil-compose:${Versions.coil}"
        const val coilNetwork = "io.coil-kt.coil3:coil-network-okhttp:${Versions.coil}"

        // Development
        const val kermit = "co.touchlab:kermit:${Versions.kermit}"
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

        // Testing
        const val jUnitBom = "org.junit:junit-bom:${Versions.jUnitBom}"
        const val jupiterApi = "org.junit.jupiter:junit-jupiter-api"
        const val jupiterParams = "org.junit.jupiter:junit-jupiter-params"
        const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine"
        const val junitPlatformLauncher = "org.junit.platform:junit-platform-launcher"
        const val mockK = "io.mockk:mockk:${Versions.mockK}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
        const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"

        // UI testing
        const val testOrchestrator = "androidx.test:orchestrator:${Versions.testOrchestrator}"
        const val uiautomator = "androidx.test.uiautomator:uiautomator:${Versions.uiautomator}"
        const val composeUiTest = "androidx.compose.ui:ui-test"
        const val composeUiTestJUnit = "androidx.compose.ui:ui-test-junit4"
        const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest"
    }

    private fun calculateVersionName(): Int {
        val parts = Android.versionName.split(".")
        val v1 = Integer.parseInt(parts[0])
        val v2 = Integer.parseInt(parts[1])
        val v3 = Integer.parseInt(parts[2])
        return v1 * 1000_000 + v2 * 1000 + v3
    }
}
