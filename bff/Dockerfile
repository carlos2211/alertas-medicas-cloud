FROM amazoncorretto:21-alpine
WORKDIR /app
COPY target/bff-1.0.0.jar app.jar
COPY src/main/resources/wallet /app/wallet
ENV TNS_ADMIN="/app/wallet"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]