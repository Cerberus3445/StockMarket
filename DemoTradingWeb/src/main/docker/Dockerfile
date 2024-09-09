FROM openjdk:21
LABEL authors="cerbreus3445"

VOLUME /tmp
WORKDIR /app
ADD maven/DemoTradingWeb-0.0.1-SNAPSHOT.jar /app/demotradingwebservice.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demotradingwebservice.jar"]