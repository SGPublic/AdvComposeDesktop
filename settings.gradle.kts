pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("jvm") version "1.5.21" apply false
        id("org.jetbrains.compose") version "1.0.0-alpha3" apply false
    }
}
rootProject.name = "AdvComposeDesktop"

