# 1번째 스테이지 - 빌드
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /src
COPY . .

RUN set -eux; \
    mkdir -p build/classes; \
    find . -name "*.java" \
      -not -path "./out/*" \
      -not -path "./.idea/*" \
      -not -path "./bench/*" \
      -not -path "./test/*" \
      > sources.txt; \
    javac --release 21 -d build/classes @sources.txt; \
    echo "Main-Class: Main" > build/MANIFEST.MF; \
    jar cfm build/app.jar build/MANIFEST.MF -C build/classes .

# 2번째 스테이지 - 런타임
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /src/build/app.jar /app/app.jar

ENV JAVA_OPTS="-Duser.timezone=Asia/Seoul"
EXPOSE 1220

CMD ["sh","-c","exec java $JAVA_OPTS -jar /app/app.jar"]