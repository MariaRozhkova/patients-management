FROM openjdk:17

ENV APP_NAME patients

COPY ${APP_NAME}/build/libs/${APP_NAME}-*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
