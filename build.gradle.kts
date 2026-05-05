plugins {
    id("application")
}

application {
    mainClass = "it.unicam.cs.mpgc.rpg129301.Main"
}

group = "it.unicam.cs.mpgc.rpg129301"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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