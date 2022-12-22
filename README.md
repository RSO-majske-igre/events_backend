# Events Backend

Events backend microservice stores data about all events and participants.

## Production version

BaseUrl: [http://34.168.23.255:8090](http://34.168.23.255:8090)

Swagger: [http://34.168.23.255:8090/swagger-ui/index.html](http://34.168.23.255:8090/swagger-ui/index.html)

## DockerHub

[https://hub.docker.com/r/bizjak3/events_backend](https://hub.docker.com/r/bizjak3/events_backend)

## Running DEV server

1. Compile `mvn package`
2. Run fatjar `java -jar ./api/target/api-0.0.1-SNAPSHOT.jar`