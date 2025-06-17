# 1. Base image
FROM openjdk:17-jdk-slim

# 2. Application JAR file
COPY build/libs/jmk_LIS_PJ-0.0.1-SNAPSHOT.jar app.jar

# 3. Expose port
EXPOSE 9090

# 4. Run the application
ENTRYPOINT ["java", "-jar", "/app.jar", "--server.port=9090", "--server.address=0.0.0.0"]