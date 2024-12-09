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
    testImplementation("org.mockito:mockito-core:5.14.2")
    testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")

    compileOnly("org.jetbrains:annotations:25.0.0")

    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.12")

    implementation("com.sparkjava:spark-core:2.9.4")
    implementation("com.sparkjava:spark-template-freemarker:2.7.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.1")
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
                minimum = BigDecimal.valueOf(0.6)
            }
        }
    }
}


version = "1.6"
