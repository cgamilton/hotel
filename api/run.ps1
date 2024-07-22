mvn clean install
cp target/api-0.0.1-SNAPSHOT.jar src/main/docker
cd .\src\main\docker\
docker-compose down
docker rmi api:latest
docker-compose up