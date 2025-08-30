rootProject.name = "rick-and-morty"

// Add project modules
include(":app")

include(":core:arch")
include(":core:arch-android")
include(":core:ui")
include(":core:navigation-compose")

include(":domain:characters")

include(":data:network")

include(":feature:characters:navigation")
include(":feature:characters:ui")

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
