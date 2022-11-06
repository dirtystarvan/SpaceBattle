plugins {
    kotlin("jvm") version "1.7.0"
}

group = "ru.ac1d"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.bio:viktor:1.2.0")

    testImplementation(kotlin("test"))
    testImplementation("org.mockito:mockito-core:4.8.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}