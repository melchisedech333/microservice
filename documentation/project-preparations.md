
# Microservice

## Etapas

Executar testes.

```
./mvnw test
```

Gerar pacote do Microservice.

```
./mvnw clean package
./mvnw clean package -DskipTests
```

Executar pacote Java localmente.

```
java -jar target/microservice-1.0.0.jar
```

Gerar imagem Docker.

```
./mvnw spring-boot:build-image -Dmodule.image.name=microservice:1.0.0
```

Executar imagem Docker.

```
docker run --rm -p 8080:8080 microservice:1.0.0
```

## Links

- https://start.spring.io/
