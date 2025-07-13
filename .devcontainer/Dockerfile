FROM amazoncorretto:21-alpine
LABEL authors="Vikram"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} petclinic_services.jar
ENTRYPOINT ["java" ,"-jar","/petclinic_services.jar"]