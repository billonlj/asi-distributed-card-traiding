#
# Packaging stage
#
FROM openjdk:8-jdk-alpine
# COPY --from=build /home/app/target/*.jar app.jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]