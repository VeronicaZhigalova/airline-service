FROM amazoncorretto:17-alpine3.18-full

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]