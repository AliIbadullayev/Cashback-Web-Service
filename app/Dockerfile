FROM openjdk:17-slim

ARG JAR_FILE=target/*.jar

COPY $JAR_FILE /app.jar

COPY data/actors.xml /data/actors.xml

ENTRYPOINT exec java -jar /app.jar