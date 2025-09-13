rootProject.name = "rick-and-morty"

apply(from = file("repositories.gradle.kts"))

// Add project modules
include(":app")

include(":core:arch")
include(":core:arch-android")
include(":core:navigation-compose")
include(":core:ui")
include(":core:test-util")
include(":core:ui-test-util-compose")

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

include(":feature:imageviewer:navigation")
include(":feature:imageviewer:ui")

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
