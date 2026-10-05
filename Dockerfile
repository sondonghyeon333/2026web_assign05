# 1단계: 빌드 단계
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# 소스코드 전체 복사 및 Gradle 실행 권한 부여
COPY . .
RUN chmod +x gradlew

# 스프링 부트 jar 파일 빌드
RUN ./gradlew bootJar --no-daemon

# 2단계: 실행 단계
FROM eclipse-temurin:21-jre
WORKDIR /app

# 빌드 단계에서 만들어진 jar 파일을 실행 파일로 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 스프링 부트 기본 포트8080 개방
EXPOSE 8080

# 컨테이너가 실행될 때 앱 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]