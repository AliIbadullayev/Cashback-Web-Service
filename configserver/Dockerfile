FROM openjdk:17-slim

ARG JAR_FILE=target/*.jar

COPY $JAR_FILE /config.jar

ENTRYPOINT exec java -jar /config.jar