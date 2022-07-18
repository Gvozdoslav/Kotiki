FROM openjdk:17.0.2
WORKDIR /foreign-service-docker
ARG JAR_FILE=target/foreign-service-1.0-exec.jar
COPY ${JAR_FILE} .
EXPOSE 8080
ENTRYPOINT java -jar "foreign-service-1.0-exec.jar"