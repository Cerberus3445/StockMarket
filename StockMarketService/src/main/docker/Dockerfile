FROM openjdk:21
LABEL authors="cerbreus3445"

VOLUME /tmp
WORKDIR /app
ADD maven/stock-market-0.0.1-SNAPSHOT.jar /app/stockservice.jar
EXPOSE 6060
ENTRYPOINT ["java", "-jar", "stockservice.jar"]