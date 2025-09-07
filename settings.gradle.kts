rootProject.name = "rick-and-morty"

// Add project modules
include(":app")

include(":core:arch")
include(":core:arch-android")
include(":core:navigation-compose")
include(":core:test-util")
include(":core:ui")

include(":data:datastore")
include(":data:network")

include(":domain:characters:api")
include(":domain:characters")

include(":domain:settings:api")
include(":domain:settings")

include(":feature:characters:navigation")
include(":feature:characters:presentation")
include(":feature:characters:ui")

include(":feature:settings:presentation")

include(":feature:splash:presentation")

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
