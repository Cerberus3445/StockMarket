FROM openjdk:21
LABEL authors="cerbreus3445"

VOLUME /tmp
WORKDIR /app
ADD maven/demo-trading-0.0.1-SNAPSHOT.jar /app/demotradingservice.jar
EXPOSE 5050
ENTRYPOINT ["java", "-jar", "demotradingservice.jar"]