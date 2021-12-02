import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    application
    idea
}

group = "me.steveodb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.strikt:strikt-core:0.33.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "16"
}

tasks {
//    sourceSets {
//        main {
//            java.srcDirs("src/main/kotlin")
//        }
//        test {
//            java.srcDirs("src/test/kotlin")
//        }
//    }

    wrapper {
        gradleVersion = "7.3.1"
    }
}

application {
    mainClass.set("MainKt")
}