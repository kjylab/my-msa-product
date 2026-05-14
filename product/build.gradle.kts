plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
}

dependencies {
    implementation("com.github.kjylab:my-msa-common:v1.0.6")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("com.h2database:h2")
}
