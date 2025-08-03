# ------------ Build Stage ------------
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and resolve dependencies first (to leverage Docker cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source files
COPY src ./src

# Build the project, skipping tests for faster builds
RUN mvn clean package -DskipTests

# ------------ Runtime Stage ------------
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-DwebAllowOthers=true", "-jar", "app.jar"]
