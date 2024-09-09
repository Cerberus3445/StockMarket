FROM openjdk:21
LABEL authors="cerbreus3445"

VOLUME /tmp
WORKDIR /app
ADD maven/web-service-0.0.1-SNAPSHOT.jar /app/webservice.jar
EXPOSE 9095
ENTRYPOINT ["java", "-jar", "webservice.jar"]