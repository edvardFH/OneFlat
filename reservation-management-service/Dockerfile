FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/reservation-management-service.jar /app/reservation-management-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "reservation-management-service.jar"]
