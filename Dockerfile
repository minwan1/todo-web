FROM openjdk:8-jre
COPY target/todo-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]