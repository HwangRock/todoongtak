# 1번째 스테이지 - 빌드
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN ./gradlew --no-daemon dependencies || true

COPY src src
RUN ./gradlew --no-daemon clean build -x test

# 2번째 스테이지 - 런
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
COPY --from=build /app/build/dependencies/ /app/libs/

ENV JAVA_OPTS="-Duser.timezone=Asia/Seoul"
EXPOSE 1220
CMD ["sh","-c","exec java $JAVA_OPTS -cp /app/app.jar:/app/libs/* Main"]
