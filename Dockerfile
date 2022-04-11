FROM openjdk:11
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#COPY src src
#RUN ./mvnw package -DskipTests

VOLUME /tmp
COPY target/*.jar app.jar
ADD /src/main/resources/quartz-config.xml /src/main/resources/quartz-config.xml
ENTRYPOINT ["java","-jar","/app.jar"]