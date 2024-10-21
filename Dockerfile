FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/invoice-api.jar /app/invoice-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "invoice-api.jar"]
