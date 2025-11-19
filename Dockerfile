# Etapa 1: Build da aplicação
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/v2_ArqWeb-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
