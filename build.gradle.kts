plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.cizetux"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://jitpack.io")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    val paperVersion = "1.19-R0.1-SNAPSHOT"
    val jdaVersion = "v5.0.0-beta.12"

    implementation("com.github.discord-jda:JDA:$jdaVersion")
    //compileOnly("io.papermc.paper:paper-api:$paperVersion")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {

    test {
        useJUnitPlatform()
    }

    shadowJar {
        archiveFileName.set("${project.name}.jar")
        destinationDirectory.set(file("C:\\Users\\Epistaxis\\Desktop\\Local Server - 1.19\\plugins"))
    }
}