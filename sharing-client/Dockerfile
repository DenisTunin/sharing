FROM openjdk:14
EXPOSE 8080
ARG JAR_FILE=target/sharing-client-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]