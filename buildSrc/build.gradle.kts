plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral {
        content {
            includeGroupByRegex("org.jetbrains.*")
            includeGroup("net.java.dev.jna")
        }
    }
}
