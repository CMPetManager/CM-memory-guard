# Stage 1: Build the application
FROM openjdk:17-jdk-slim AS builder

WORKDIR /build

# Copy the Maven wrapper files
COPY mvnw .
COPY .mvn .mvn

# Copy the project files
COPY pom.xml .
COPY . .

# Download the Maven dependencies
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the application image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR files from the builder stage
COPY --from=builder /build/memory-guard-core/target/*.jar app.jar

# Expose the necessary ports
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
