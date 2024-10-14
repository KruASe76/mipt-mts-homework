repositories {
    mavenCentral()
}

plugins {
    application
    jacoco
    id("com.gradleup.shadow") version "8.3.2"
}


dependencies {
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly("org.jetbrains:annotations:25.0.0")
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "me.kruase.mipt.Main"
}


tasks.build {
    dependsOn(tasks.jacocoTestCoverageVerification)
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

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "PACKAGE"
            excludes = listOf("me.kruase.mipt")

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = BigDecimal.valueOf(0.55)
            }
        }
    }
}


version = "1.4"
