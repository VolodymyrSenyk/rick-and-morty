plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = Config.Android.applicationId

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
}

dependencies {
    implementation(project(":core:arch-android"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation-compose"))

    implementation(project(":data:network"))

    implementation(project(":feature:characters:navigation"))
    implementation(project(":feature:characters:ui"))

    implementation(Config.Libs.material)
    implementation(Config.Libs.splashscreen)

    implementation(Config.Libs.hilt)
    ksp(Config.Libs.hiltCompiler)

    implementation(Config.Libs.composeTooling)
    debugImplementation(Config.Libs.composePreview)

    implementation(Config.Libs.okhttp)
    implementation(Config.Libs.okhttpLogging)
    implementation(Config.Libs.retrofit)
    implementation(Config.Libs.retrofitGson)

    debugImplementation(Config.Libs.leakCanary)
}
