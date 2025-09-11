# Rick and Morty Wiki – Jetpack Compose MVI Sample

![Language](https://img.shields.io/badge/Kotlin-2.2.20-blue)
[![CI](https://github.com/VolodymyrSenyk/rick-and-morty/actions/workflows/ci.yml/badge.svg)](https://github.com/VolodymyrSenyk/rick-and-morty/actions/workflows/ci.yml)

## About

This is a sample Android application built with **Jetpack Compose**, showcasing a custom **MVI**
architecture and modern Android development practices.

The app integrates with the [Rick and Morty API](https://rickandmortyapi.com/) and provides
multiple screens with paginated data and detailed content views.

The project serves as a practical example for building Android applications using
**Jetpack Compose** and **MVI** architecture. It demonstrates how to structure a multimodular
codebase, apply **Clean Architecture**, integrate with a public API, handle pagination and maintain
code quality with static analysis, testing and **CI** tools.

## Features

- Kotlin
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- Multimodular project structure
- Clean Architecture + MVI
- [Hilt](https://dagger.dev/hilt/)
- Kotlin Coroutines
- [Retrofit](https://github.com/square/retrofit) + [OkHttp](https://github.com/square/okhttp)
- Jetpack Compose
- Jetpack Compose Navigation + Single Activity Architecture
- Material Design 3
- Shared Element Transitions
- [Coil](https://github.com/coil-kt/coil)
- [Kermit](https://github.com/touchlab/Kermit)
- [StrictMode](https://developer.android.com/reference/android/os/StrictMode)
- [LeakCanary](https://square.github.io/leakcanary/)
- [Detekt](https://github.com/detekt/detekt)
- [Gradle Doctor](https://github.com/runningcode/gradle-doctor)
- [JaCoCo](https://www.eclemma.org/jacoco/)
- [SonarQube](https://www.sonarsource.com/products/sonarqube/)
- [GitHub Actions CI](https://github.com/features/actions)
- [JUnit 5](https://junit.org/) + [MockK](https://mockk.io/)
- Jetpack Compose Testing + Robot Pattern

## Demo

### App core functionality

<img src="/gifs/core.gif" width="25%" alt="Core Demo"/>

### Dynamic theme changing

<img src="/gifs/theme.gif" width="25%" alt="Theme Demo"/>

### Filter

<img src="/gifs/filter.gif"  width="25%" alt="Filter Demo"/>

### Search

<img src="/gifs/search.gif" width="25%" alt="Search Demo"/>

## Download APK

[![Download APK](https://img.shields.io/badge/Download-APK-blue?style=for-the-badge&logo=android)](https://github.com/VolodymyrSenyk/rick-and-morty/releases/latest)

## Usage

## Clone and Explore

1. Clone this repository

```bash
git clone https://github.com/VolodymyrSenyk/rick-and-morty.git
```

2. Add your `keystore` files
3. Create a `Secret.kt` file and place it next to `Config.kt`

```kotlin
object Secret {

    object SignIn {

        const val debugKeyStoreFile = "../YOUR_DEBUG_KEYSTORE_FILE"
        const val debugKeyStorePassword = "YOUR_DEBUG_KEYSTORE_PASSWORD"
        const val debugKeyAlias = "YOUR_DEBUG_KEY_ALIAS"
        const val debugKeyPassword = "YOUR_DEBUG_KEY_PASSWORD"

        const val releaseKeyStoreFile = "../YOUR_RELEASE_KEYSTORE_FILE"
        const val releaseKeyStorePassword = "YOUR_RELEASE_KEYSTORE_PASSWORD"
        const val releaseKeyAlias = "YOUR_RELEASE_KEY_ALIAS"
        const val releaseKeyPassword = "YOUR_RELEASE_KEY_PASSWORD"
    }

    object SonarQube {
        const val token = "YOUR_SONAR_QUBE_TOKEN_FOR_CURRENT_PROJECT" // if you want to use SonarQube
    }
}
```

### Use as Template

1. Click [![Use this template](https://img.shields.io/badge/-Use%20this%20template-brightgreen)](https://github.com/VolodymyrSenyk/rick-and-morty/generate)
2. Replace `applicationId` and `versionName` in `Config.kt` with your values
3. Add your `keystore` files
4. Create a `Secret.kt` file and place it next to `Config.kt`

```kotlin
object Secret {

    object SignIn {

        const val debugKeyStoreFile = "../YOUR_DEBUG_KEYSTORE_FILE"
        const val debugKeyStorePassword = "YOUR_DEBUG_KEYSTORE_PASSWORD"
        const val debugKeyAlias = "YOUR_DEBUG_KEY_ALIAS"
        const val debugKeyPassword = "YOUR_DEBUG_KEY_PASSWORD"

        const val releaseKeyStoreFile = "../YOUR_RELEASE_KEYSTORE_FILE"
        const val releaseKeyStorePassword = "YOUR_RELEASE_KEYSTORE_PASSWORD"
        const val releaseKeyAlias = "YOUR_RELEASE_KEY_ALIAS"
        const val releaseKeyPassword = "YOUR_RELEASE_KEY_PASSWORD"
    }

    object SonarQube {
        const val token = "YOUR_SONAR_QUBE_TOKEN_FOR_CURRENT_PROJECT" // if you want to use SonarQube
    }
}
```

## Use as Library (via GitHub Packages)

1. Add the following code to your `settings.gradle.kts`

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/VolodymyrSenyk/rick-and-morty")
            credentials {
                username = providers.gradleProperty("gpr.user").orNull ?: System.getenv("USERNAME")
                password = providers.gradleProperty("gpr.key").orNull ?: System.getenv("TOKEN")
            }
        }
    }
}
```

2. Add your credentials to the global `gradle.properties` file

```txt
gpr.user=YOUR_USERNAME
gpr.key=YOUR_PERSONAL_ACCESS_TOKEN_WITH_PACKAGES_READ_PERMISSION
```

3. Import the required modules, for example:

```kotlin
val senykFoundationVersion = "2.2.0" // check latest version in Releases

dependencies {
    implementation("com.github.VolodymyrSenyk:core-arch:$senykFoundationVersion")
    implementation("com.github.VolodymyrSenyk:core-arch-android:$senykFoundationVersion")
    implementation("com.github.VolodymyrSenyk:core-navigation-compose:$senykFoundationVersion")
    implementation("com.github.VolodymyrSenyk:core-test-util:$senykFoundationVersion")
    implementation("com.github.VolodymyrSenyk:core-ui-test-util-compose:$senykFoundationVersion")
    implementation("com.github.VolodymyrSenyk:domain-settings:$senykFoundationVersion")
    implementation("com.github.VolodymyrSenyk:domain-settings-api:$senykFoundationVersion")
    implementation("com.github.VolodymyrSenyk:feature-settings-presentation:$senykFoundationVersion")
}
```

## License

This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.
Feel free to use it as a learning resource or project starter.
