FROM eclipse-temurin:22-jdk

WORKDIR /app

COPY target/*.jar app.jar

# Command to run the application
CMD ["java", "-jar", "app.jar"]
