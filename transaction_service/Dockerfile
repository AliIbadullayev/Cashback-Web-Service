FROM openjdk:17-slim

ARG JAR_FILE=target/*.jar

COPY $JAR_FILE /app.jar

ENTRYPOINT exec java -jar /app.jar