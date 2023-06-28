plugins {
    id("java")
    id("io.quarkus") version "3.1.1.Final"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
val quarkusVersion="3.1.1.Final"
dependencies {
   implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:${quarkusVersion}"))
    implementation ("io.quarkus:quarkus-arc")
    implementation ("io.quarkus:quarkus-resteasy-reactive")
    implementation ("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation ("io.quarkus:quarkus-jdbc-postgresql")
    implementation ("io.quarkus:quarkus-hibernate-orm-panache")
    implementation ("io.quarkus:quarkus-config-yaml")
    //implementacion de redis
    implementation("io.lettuce:lettuce-core:6.2.4.RELEASE")
    //gson
    implementation("com.google.code.gson:gson:2.8.5")

}

tasks.test {
    useJUnitPlatform()
}