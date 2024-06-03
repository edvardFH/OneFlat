FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/accommodation-catalog-service.jar /app/accommodation-catalog-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "accommodation-catalog-service.jar"]
