#Build stage
FROM maven:3.8.1-jdk-11-slim AS build
COPY src src
COPY pom.xml .
RUN sed -i 's/localhost/postgres/g' src/main/resources/application.properties && mvn clean package -DskipTests



#Package stage
FROM openjdk:11
COPY --from=build /target/micro-0.0.1-SNAPSHOT.jar /target/micro-0.0.1-SNAPSHOT.jar
ADD /src/main/resources/quartz-config.xml /src/main/resources/quartz-config.xml
ENTRYPOINT ["java","-jar","/target/micro-0.0.1-SNAPSHOT.jar"]

