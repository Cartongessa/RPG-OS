plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0" // Plugin for JavaFX
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml")
}

application {
    mainClass = "it.unicam.cs.mpgc.rpg129301.Main"
}

group = "it.unicam.cs.mpgc.rpg129301"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}


dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.test {
    useJUnitPlatform()
}

//sourceSets {
//    main {
//        resources {
//            srcDir("src/main/java")
//        }
//    }
//}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
}