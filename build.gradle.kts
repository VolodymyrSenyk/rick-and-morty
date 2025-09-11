import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import org.sonarqube.gradle.SonarTask

apply(from = rootProject.file("jacoco.gradle.kts"))

plugins {
    id("com.google.devtools.ksp") version Config.Versions.ksp apply false
    id("io.gitlab.arturbosch.detekt") version Config.Versions.detekt
    id("com.osacky.doctor") version Config.Versions.gradleDoctor
    id("com.github.ben-manes.versions") version Config.Versions.versionsPlugin
    id("org.sonarqube") version Config.Versions.sonarqube
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    jacoco
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
        classpath("org.jetbrains.kotlin:compose-compiler-gradle-plugin:${Config.Versions.kotlinLanguage}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Config.Versions.hilt}")
        classpath(kotlin("gradle-plugin", Config.Versions.kotlinLanguage))
    }
}

subprojects {
    project.plugins.configure(project)
    apply(plugin = "jacoco")
    plugins.withId("com.android.library") {
        apply(plugin = "maven-publish")
    }
    plugins.withId("org.jetbrains.kotlin.jvm") {
        apply(plugin = "maven-publish")
        apply(plugin = "com.github.johnrengelman.shadow")
    }

    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        jvmArgs = listOf("-XX:+EnableDynamicAgentLoading")
        configure<JacocoTaskExtension> {
            isIncludeNoLocationClasses = false
            excludes = listOf("jdk.internal.*")
        }
    }

    plugins.withId("org.jetbrains.kotlin.jvm") {
        tasks.named<ShadowJar>("shadowJar") {
            archiveClassifier.set("")
            val applicationIdPrefix = Config.General.applicationId
            val projectName = project.name
            val projectGroup = project.group.toString().substringAfter(".")
            val destination = "$applicationIdPrefix.$projectGroup.$projectName"
            relocate(projectName, destination)
        }
    }
}

fun PluginContainer.configure(project: Project) {
    whenPluginAdded {
        when (this) {
            is AppPlugin -> project.extensions.getByType<AppExtension>().applyCommons()
            is LibraryPlugin -> project.extensions.getByType<LibraryExtension>().applyCommons()
            is MavenPublishPlugin -> project.configurePublishing()
        }
    }
}

fun AppExtension.applyCommons() {
    compileSdkVersion(Config.General.targetSdkVersion)

    defaultConfig.apply {
        minSdk = Config.General.minSdkVersion
        targetSdk = Config.General.targetSdkVersion
        versionCode = Config.General.versionCode
        versionName = Config.General.versionName
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures.apply {
        buildConfig = true
        compose = true
    }

    @Suppress("UnstableApiUsage")
    composeOptions {
        kotlinCompilerExtensionVersion = Config.Versions.kotlinComposeCompilerExtension
    }
}

fun LibraryExtension.applyCommons() {
    compileSdk = Config.General.targetSdkVersion

    defaultConfig.apply {
        minSdk = Config.General.minSdkVersion
        targetSdk = Config.General.targetSdkVersion
        versionCode = Config.General.versionCode
        versionName = Config.General.versionName

        consumerProguardFiles("proguard-rules.pro")
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures.apply {
        buildConfig = true
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

fun Project.configurePublishing() {
    configure<PublishingExtension> {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/VolodymyrSenyk/rick-and-morty")
                credentials {
                    username = project.findProperty("gpr.user") as? String ?: System.getenv("USERNAME")
                    password = project.findProperty("gpr.key") as? String ?: System.getenv("TOKEN")
                }
            }
        }
        publications {
            register<MavenPublication>("gpr") {
                groupId = "com.github.VolodymyrSenyk"
                artifactId = project.path.removePrefix(":").replace(":", "-")
                version = Config.General.versionName
                afterEvaluate {
                    if (project.plugins.hasPlugin("com.android.library")) {
                        from(components["release"])
                    } else {
                        from(components["java"])
                    }
                }
            }
        }
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
    exclude("**/build/**")
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

sonarqube {
    properties {
        property("sonar.projectName", "RickAndMorty")
        property("sonar.projectKey", "RickAndMorty")
        property("sonar.projectVersion", Config.General.versionName)
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", Secret.SonarQube.token)

        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.verbose", true)

        property("detekt.sonar.kotlin.config.path", "$rootDir/detekt/detekt.yml")
        property("sonar.kotlin.detekt.reportPaths", "$rootDir/build/reports/detekt/detekt.xml")

        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.jacoco.reportPath", "$rootDir/build/jacoco/*.exec")
        property("sonar.coverage.jacoco.xmlReportPaths", "$rootDir/build/reports/jacoco/aggregate/xml/report.xml")
        property("sonar.coverage.exclusions", testCoverageExclusions)

        property("sonar.issuesReport.html.enable", true)
        property("sonar.issuesReport.console.enable", true)
    }
}

tasks.named<SonarTask>("sonar") {
    dependsOn("codeCoverageReport", "detektAll")
}

// Exclusions to check code coverage only for ViewModel classes
val testCoverageExclusions = listOf(
    "**/*App.kt",
    "**/*Activity.kt",
    "**/core/**",
    "**/di/**",
    "**/data/**",
    "**/domain/**",
    "**/navigation/**",
    "**/components/**",
    "**/ui/**",
)
