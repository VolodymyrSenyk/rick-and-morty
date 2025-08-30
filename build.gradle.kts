import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

apply(from = rootProject.file("repositories.gradle.kts"))

plugins {
    id("com.google.devtools.ksp") version Config.Versions.ksp apply false
    id("io.gitlab.arturbosch.detekt") version Config.Versions.detekt
    id("com.osacky.doctor") version Config.Versions.gradleDoctor
    id("com.github.ben-manes.versions") version Config.Versions.versionsPlugin
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Config.Versions.detekt}")
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
        classpath("org.jetbrains.kotlin:compose-compiler-gradle-plugin:${Config.Versions.kotlinLanguage}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Config.Versions.hilt}")
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

tasks.register<Detekt>("detektAll") {
    group = "verification"
    description = "Run project level detekt analysis."

    buildUponDefaultConfig = true
    allRules = false
    parallel = true
    ignoreFailures = false
    config.setFrom("$projectDir/detekt/detekt.yml")
    baseline = file("$projectDir/detekt/baseline.xml")

    setSource(files(projectDir))
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**", "**/test/**", "**/androidTest/**")
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
        sarif.required.set(false)
        md.required.set(false)

        html.outputLocation = file("$projectDir/build/reports/detekt/detekt.html")
        xml.outputLocation = file("$projectDir/build/reports/detekt/detekt.xml")
        txt.outputLocation = file("$projectDir/build/reports/detekt/detekt.txt")
    }
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA")
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword.any { version.uppercase().contains(it) } || regex.matches(version)
    return !isStable
}
