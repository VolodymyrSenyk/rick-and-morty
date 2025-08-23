import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

apply(from = rootProject.file("repositories.gradle.kts"))

plugins {
    id("com.google.devtools.ksp") version Config.Versions.ksp apply false
}

buildscript {

    repositories {
        google {
            content {
                includeGroupByRegex("android.arch.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("com.google.*")
                includeGroup("com.android")
            }
        }
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Config.Versions.gradlePlugin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Config.Versions.kotlinLanguage}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Config.Versions.navigation}")
        classpath(kotlin("gradle-plugin", Config.Versions.kotlinLanguage))
    }
}

subprojects {
    project.plugins.configure(project)

    buildscript {
        apply(from = rootProject.file("repositories.gradle.kts"))
    }

    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }
    }
}

fun PluginContainer.configure(project: Project) {
    whenPluginAdded {
        when (this) {
            is AppPlugin -> project.extensions.getByType<AppExtension>().applyCommons()
            is LibraryPlugin -> project.extensions.getByType<LibraryExtension>().applyCommons()
        }
    }
}

fun AppExtension.applyCommons() {
    compileSdkVersion(Config.Android.targetSdkVersion)

    defaultConfig.apply {
        minSdk = Config.Android.minSdkVersion
        targetSdk = Config.Android.targetSdkVersion
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures.apply {
        buildConfig = true
    }
}

fun LibraryExtension.applyCommons() {
    compileSdk = Config.Android.targetSdkVersion

    defaultConfig.apply {
        minSdk = Config.Android.minSdkVersion
        targetSdk = Config.Android.targetSdkVersion
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName

        consumerProguardFiles("proguard-rules.pro")
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures.apply {
        buildConfig = true
    }
}

tasks.register<Delete>("clean") {
    group = "build"
    description = "Delete build directories."

    delete(layout.buildDirectory)
    subprojects.forEach { subproject ->
        delete(subproject.layout.buildDirectory)
    }
}
