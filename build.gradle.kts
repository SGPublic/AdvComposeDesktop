import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlin.collections.mapOf

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    `java-library`
    `maven-publish`
    signing
}

val verRelease = "0.1.0-alpha1-build2-kotlin-1.5.21-compose-1.0.0-alpha3"
val verSnapshot = "$verRelease-SNAPSHOT"

group = "io.github.sgpublic"
version = verRelease

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)

    implementation("org.reflections:reflections:0.9.12")

    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

java {
    withJavadocJar()
    withSourcesJar()
}

val signingProperties = file("./gradle.properties")
if (signingProperties.exists()) {
    publishing {
        val creation = fun(publicationName: String) {
            publications {
                create<MavenPublication>(publicationName){
                    artifactId = project.name
                    from(components["java"])
                    pom {
                        name.set(artifactId)
                        description.set("Jetpack Compose for Desktop 的二次封装。")
                        url.set("https://github.com/SGPublic/AdvComposeDesktop")
                        licenses {
                            license {
                                name.set("The Apache License, Version 2.0")
                                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            }
                        }
                        developers {
                            developer {
                                id.set("sgpublic")
                                name.set("SGPublic")
                                email.set("sgpublic2002@gmail.com")
                            }
                        }
                        scm {
                            connection.set("scm:git:https://github.com/SGPublic/AdvComposeDesktop.git")
                            developerConnection.set("scm:git:https://github.com/SGPublic/AdvComposeDesktop.git")
                            url.set("https://github.com/SGPublic/AdvComposeDesktop")
                        }
                    }
                }
            }
            @Suppress("LocalVariableName")
            repositories {
                val NEXUS_USERNAME: String by project
                val NEXUS_PASSWORD: String by project
                val mavenCreation = fun(ver: String) {
                    maven {
                        name = ver
                        url = if (ver == "Snapshot") {
                            uri("https://oss.sonatype.org/content/repositories/snapshots")
                        } else {
                            uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                        }
                        credentials {
                            username = NEXUS_USERNAME
                            password = NEXUS_PASSWORD
                        }
                    }
                }
                mavenCreation("Release")
                mavenCreation("Snapshot")
            }
        }
        creation(project.name)
    }

    signing {
        sign(publishing.publications[project.name])
    }
}