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
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.7.0")

    implementation("org.jetbrains.bio:viktor:1.2.0")

    implementation("org.slf4j:slf4j-api:2.0.3")
    implementation("ch.qos.logback:logback-classic:1.4.4")
    implementation("ch.qos.logback:logback-core:1.4.4")

    testImplementation(kotlin("test"))
    testImplementation("org.mockito:mockito-core:4.8.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}
