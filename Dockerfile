###
# 1. OpenJDK 이미지를 기반으로 사용
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. JAR 파일 복사
COPY target/logistic-0.0.1-SNAPSHOT.jar app.jar

# 4. 애플리케이션 실행 포트 설정
EXPOSE 8080

# 5. 애플리케이션 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]