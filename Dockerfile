FROM openjdk:17

ENV APP_NAME patients-management

COPY build/libs/${APP_NAME}-*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
