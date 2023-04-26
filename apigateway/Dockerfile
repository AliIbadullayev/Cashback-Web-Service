FROM openjdk:17-slim

ARG JAR_FILE=target/*.jar

COPY $JAR_FILE /gateway.jar

ENTRYPOINT exec java -jar /gateway.jar