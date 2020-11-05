FROM maven:3.5.2-jdk-8-alpine

COPY /target/simpleapi-1.0.jar simpleapi.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/simpleapi.jar"]