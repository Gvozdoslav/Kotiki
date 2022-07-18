FROM openjdk:17.0.2
WORKDIR /cat-service-docker
ARG JAR_FILE=/target/cat-service-1.0-exec.jar
COPY ${JAR_FILE} .
ENTRYPOINT java -jar "cat-service-1.0-exec.jar"