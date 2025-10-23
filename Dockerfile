FROM openjdk:17-jdk-slim
LABEL authors="gtpkt"

WORKDIR /app

COPY target/message_service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]