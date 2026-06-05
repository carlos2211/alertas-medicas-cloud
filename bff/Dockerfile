# ─── Etapa 1: Compilar con Maven ───────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copia el pom.xml y descarga dependencias (cacheable)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente y compila
COPY src ./src
RUN mvn clean package -DskipTests -B

# ─── Etapa 2: Imagen final liviana ─────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Crea usuario no-root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia el jar compilado
COPY --from=builder /app/target/bff-*.jar app.jar

# Variables de entorno requeridas (se inyectan en docker-compose o DockerLab)
ENV AZURE_B2C_TENANT_NAME=""
ENV AZURE_B2C_TENANT_ID=""
ENV AZURE_B2C_CLIENT_ID=""
ENV AZURE_B2C_FLOW_NAME="B2C_1_signupsignin"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
