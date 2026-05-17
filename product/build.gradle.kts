plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
    kotlin("kapt")
}

dependencies {
    implementation("com.github.kjylab:my-msa-common:v1.0.6")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")

    runtimeOnly("com.h2database:h2")
}


configurations.all {
    resolutionStrategy {
        force("com.querydsl:querydsl-jpa:5.1.0:jakarta")
        force("com.querydsl:querydsl-apt:5.1.0:jakarta")
    }
}
