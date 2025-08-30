rootProject.name = "rick-and-morty"

// Add project modules
include(":core:arch")
include(":core:arch-android")
include(":core:ui")

include(":domain")
include(":data")
include(":presentation")

// Add ability to use a module name as part of a build script name
fun ProjectDescriptor.applyCustomBuildFileNames(prefix: String = "") {
    val fileName = if (prefix.isEmpty()) name else "$prefix-$name"
    buildFileName = "$fileName.gradle.kts"
    children.forEach { subProject ->
        subProject.applyCustomBuildFileNames(prefix = fileName)
    }
}

rootProject.children.forEach { subProject ->
    subProject.applyCustomBuildFileNames()
}
