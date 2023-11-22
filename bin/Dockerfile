FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY /target/*.jar takaful.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/takaful.jar"]