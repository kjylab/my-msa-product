FROM eclipse-temurin:21.0.9_10-jdk-jammy AS build
WORKDIR /app

ARG GITHUB_ACTOR
ARG GITHUB_TOKEN

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY product-service/build.gradle.kts product-service/
COPY product/build.gradle.kts product/

RUN GITHUB_ACTOR=${GITHUB_ACTOR} GITHUB_TOKEN=${GITHUB_TOKEN} ./gradlew dependencies --no-daemon

COPY . .

RUN GITHUB_ACTOR=${GITHUB_ACTOR} GITHUB_TOKEN=${GITHUB_TOKEN} ./gradlew :product-service:bootJar -x test --no-daemon

FROM eclipse-temurin:21.0.9_10-jre-jammy
WORKDIR /app

RUN useradd -ms /bin/bash springuser
USER springuser

COPY --from=build /app/product-service/build/libs/product-service.jar app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=staging", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "app.jar"]

EXPOSE 8080