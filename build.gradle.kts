plugins {
    java
    application
    id("com.gradleup.shadow") version "8.3.2"
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "me.kruase.mipt.Main"
}


tasks.shadowJar {
    archiveClassifier = ""
}

tasks.distTar {
    dependsOn(tasks.shadowJar)
}

tasks.distZip {
    dependsOn(tasks.shadowJar)
}

tasks.startShadowScripts {
    dependsOn(tasks.jar)
}

tasks.startScripts {
    dependsOn(tasks.shadowJar)
}


version = "1.2"
