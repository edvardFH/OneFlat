FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/api-gateway.jar /app/api-gateway.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
