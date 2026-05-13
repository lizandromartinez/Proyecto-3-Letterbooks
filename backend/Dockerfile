# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Descargar las dependencias
RUN mvn dependency:go-offline -B
# Copiar el código fuente
COPY src ./src
# Compilar el proyecto saltándose las pruebas para acelerar el build
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre
WORKDIR /app
# Copiar el jar generado desde la etapa de compilación
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
