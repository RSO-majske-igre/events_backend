# Events Backend

Events backend microservice stores data about all events and participants.

## Production version

BaseUrl: http://35.230.83.46:8090

Swagger: http://35.230.83.46:8090/swagger-ui/index.html

## DockerHub

[https://hub.docker.com/r/bizjak3/events_backend](https://hub.docker.com/r/bizjak3/events_backend)

## Running DEV server

1. Compile `mvn package`
2. Run fatjar `java -jar ./api/target/api-0.0.1-SNAPSHOT.jar`
