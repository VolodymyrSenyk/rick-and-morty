plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":domain:characters:api"))
    implementation(Config.Libs.inject)
}
