# Rick and Morty Wiki – Jetpack Compose MVI Sample

![Language](https://img.shields.io/badge/Kotlin-2.2.10-blue)
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

Clone this repository

```
git clone https://github.com/VolodymyrSenyk/rick-and-morty.git
```

## License

This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.
Feel free to use it as a learning resource or project starter.
