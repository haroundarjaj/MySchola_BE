# Use an official JDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the current directory contents into the container
COPY ./target/*.jar app.jar

# Expose the application's port (default Spring Boot port is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]