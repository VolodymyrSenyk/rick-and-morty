plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":domain:settings:api"))
    implementation(Config.Libs.inject)
    implementation(Config.Libs.coroutinesCore)
}
