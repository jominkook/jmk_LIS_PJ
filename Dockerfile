# 1. Base image
FROM openjdk:17-jdk-slim

# 2. Application JAR file
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# 3. Expose port
EXPOSE 9090

# 4. Run the application
ENTRYPOINT ["java", "-jar", "/app.jar", "--server.port=9090", "--server.address=0.0.0.0"]