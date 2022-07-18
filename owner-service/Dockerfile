FROM openjdk:17.0.2
WORKDIR /owner-service-docker
ARG JAR_FILE=/target/owner-service-1.0-exec.jar
COPY ${JAR_FILE} .
ENTRYPOINT java -jar "owner-service-1.0-exec.jar"