# Use the official Gradle image to build the application
FROM gradle:7.5.1-jdk17 AS build

# Copy the project files to the container
COPY --chown=gradle:gradle . /home/gradle/project

# Set the working directory
WORKDIR /home/gradle/project

# Run the Gradle build
RUN gradle build --no-daemon

# Use the official OpenJDK image for the runtime
FROM openjdk:17-jdk-slim

# Copy the built JAR file from the build stage
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
