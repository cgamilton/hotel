# hotel
Requisitos para rodar a API:
Docker (windows: https://docs.docker.com/desktop/install/windows-install/)
JDK 17
Maven

Após baixar, rodar os comandos na raíz do projeto:
```
mvn clean install
cp target/api-0.0.1-SNAPSHOT.jar src/main/docker
cd .\src\main\docker\
docker-compose up
```
Requisitos para rodar o app angular:
- A ideia inicial era subir no nginx utilizando o docker-compose, mas ocorreram problemas.

Node/npm
Angular
Executar os comandos na raiz do projeto:
npm install
ng serve

