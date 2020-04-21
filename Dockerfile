# Base build image
FROM maven:3.6.3-jdk-14 AS maven

COPY ./pom.xml ./pom.xml

RUN mvn dependency:go-offline

COPY ./src ./src

RUN mvn package -DskipTests

# Final base image
FROM openjdk:13-jre-alpine

WORKDIR /demo

# copy the build artifact from maven image
COPY --from=maven target/demo-0.0.1-SNAPSHOT.jar ./

EXPOSE 8080

CMD ["java", "-jar", "./demo-0.0.1-SNAPSHOT.jar"]