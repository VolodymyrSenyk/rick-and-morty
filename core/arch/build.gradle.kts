plugins {
    kotlin("jvm")
}

dependencies {
    implementation(Config.Libs.inject)
    api(Config.Libs.coroutinesCore)
    api(Config.Libs.kermit)
}
