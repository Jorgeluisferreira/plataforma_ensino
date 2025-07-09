FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests -Dquarkus.package.type=uber-jar

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/target/*-runner.jar app.jar
COPY --from=build /build/target/classes/application.properties ./application.properties

COPY --from=build /build/src/main/resources/privateKey.pem ./src/main/resources/privateKey.pem
COPY publicKey.pem ./publicKey.pem

EXPOSE 8082
CMD ["java", "-jar", "app.jar"]