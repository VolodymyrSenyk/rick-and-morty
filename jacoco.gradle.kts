tasks.register("codeCoverageTests") {
    group = "reporting"
    description = "Run unit tests for JaCoCo code coverage report creating."

    subprojects.mapNotNull { subproject ->
        subproject.tasks.withType(Test::class)
            .firstOrNull { it.name in arrayOf("testDebugUnitTest", "testProdDebugUnitTest") }
    }.forEach { dependsOn(it.path) }
}

tasks.register("codeCoverageReport") {
    group = "reporting"
    description = "Generate project level JaCoCo code coverage report."

    val runTests = tasks.getByName("codeCoverageTests")
    val mergeJacocoReports = tasks.getByName("mergeJacocoReports")

    dependsOn(runTests, mergeJacocoReports)
    mergeJacocoReports.mustRunAfter(runTests)
}

tasks.register<JacocoReport>("mergeJacocoReports") {
    group = "reporting"
    description = "Merge JaCoCo code coverage reports for ViewModel classes."

    reports {
        xml.required.set(true)
        html.required.set(true)
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/aggregate/xml/report.xml"))
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/aggregate/html"))
    }
    sourceDirectories.setFrom(subprojects.flatMap { listOf(it.layout.projectDirectory.dir("src/main")) })
    classDirectories.setFrom(
        files(
            subprojects.flatMap {
                listOf(
                    fileTree(it.layout.buildDirectory.dir("intermediates/javac")) {
                        include("**/*ViewModel.class")
                        exclude("**/Base*.class")
                    },
                    fileTree(it.layout.buildDirectory.dir("/tmp/kotlin-classes")) {
                        include("**/*ViewModel.class")
                        exclude("**/Base*.class")
                    }
                )
            }
        )
    )
    executionData.setFrom(
        files(subprojects.flatMap { listOf(fileTree(it.layout.buildDirectory) { include("**/*.exec") }) })
    )
}
