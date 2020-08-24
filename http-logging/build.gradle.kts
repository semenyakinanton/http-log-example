import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "http.logging"

dependencies {
    compileOnly("org.slf4j:slf4j-api:1.7.30")
    compileOnly("org.springframework.boot:spring-boot-autoconfigure:2.3.1.RELEASE")
    compileOnly("org.springframework:spring-webmvc:5.2.1.RELEASE")
    compileOnly("org.apache.tomcat.embed:tomcat-embed-core:9.0.27")
    compileOnly("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("com.google.guava:guava:28.1-jre")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
