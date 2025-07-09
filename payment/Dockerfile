# Etapa 1: build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests -Dquarkus.package.type=uber-jar

# Etapa 2: imagem final
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/target/*-runner.jar app.jar
COPY --from=build /build/target/classes/application.properties ./application.properties

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]