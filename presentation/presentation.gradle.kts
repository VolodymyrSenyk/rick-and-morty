plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = Config.Android.applicationId + ".presentation"

    defaultConfig {
        applicationId = Config.Android.applicationId
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file(Secret.SignIn.debugKeyStoreFile)
            storePassword = Secret.SignIn.debugKeyStorePassword
            keyAlias = Secret.SignIn.debugKeyAlias
            keyPassword = Secret.SignIn.debugKeyPassword
        }

        create("release") {
            storeFile = file(Secret.SignIn.releaseKeyStoreFile)
            storePassword = Secret.SignIn.releaseKeyStorePassword
            keyAlias = Secret.SignIn.releaseKeyAlias
            keyPassword = Secret.SignIn.releaseKeyPassword
        }
    }

    buildTypes {

        getByName("debug") {
            versionNameSuffix = "-debug"
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions.add("env")
    productFlavors {

        create("dev") {
            dimension = "env"
            versionNameSuffix = "-dev"
            applicationIdSuffix = ".dev"
        }

        create("prod") {
            dimension = "env"
        }
    }

    hilt {
        enableAggregatingTask = true
    }

    lint {
        abortOnError = false
    }

    buildFeatures.apply {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":data"))

    // UI
    implementation(Config.Libs.ktx)
    implementation(Config.Libs.material)
    implementation(Config.Libs.appcompat)
    implementation(Config.Libs.fragmentKtx)
    implementation(Config.Libs.constraintLayout)
    implementation(Config.Libs.swipeRefreshLayout)
    implementation(Config.Libs.adapterDelegates)
    implementation(Config.Libs.sdp)
    implementation(Config.Libs.glide)

    // DI
    implementation(Config.Libs.hilt)
    ksp(Config.Libs.hiltCompiler)

    // Lifecycle
    implementation(Config.Libs.lifecycle)

    // Networking
    implementation(Config.Libs.okhttp)
    implementation(Config.Libs.okhttpLogging)
    implementation(Config.Libs.retrofit)
    implementation(Config.Libs.retrofitGson)

    // Navigation
    implementation(Config.Libs.navigationFragment)
    implementation(Config.Libs.navigationUi)

    // Development
    debugImplementation(Config.Libs.leakCanary)
}
