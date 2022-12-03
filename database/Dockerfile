FROM maven:3.8.4-openjdk-17 AS build
COPY ./ /app
WORKDIR /app
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17
RUN mkdir /database
WORKDIR /database
COPY --from=build ./app/database/target/database-0.0.1-SNAPSHOT.jar /app
EXPOSE 3306
CMD ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]
