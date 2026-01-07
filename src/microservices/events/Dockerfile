# Build stage: build Spring Boot jar using official Gradle image
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /workspace

# Copy build files first to leverage Docker layer caching
COPY build.gradle settings.gradle ./
COPY gradle gradle

# Copy sources
COPY src src

# Build the bootable jar
RUN gradle --no-daemon clean bootJar

# Runtime stage: run the built jar on a lightweight JRE base
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy only the Spring Boot fat jar (exclude *-plain.jar)
COPY --from=builder /workspace/build/libs/*-SNAPSHOT.jar /app/app.jar

EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
