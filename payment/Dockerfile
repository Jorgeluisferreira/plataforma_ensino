# Etapa 1: build com Maven + Quarkus gerando uber-jar
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /build

COPY pom.xml .
COPY src ./src

# Gera o uber-jar
RUN mvn clean package -DskipTests -Dquarkus.package.type=uber-jar

# Etapa 2: imagem final com JRE e JAR executável
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o uber-jar gerado (tem sufixo -runner.jar)
COPY --from=build /build/target/*-runner.jar app.jar

# Copia o application.properties explicitamente (opcional, mas garante que vai)
COPY --from=build /build/target/classes/application.properties ./application.properties

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
