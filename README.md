# desafio-objective

Esse projeto usa Quarkus, the Supersonic Subatomic Java Framework.

Para saber mais sobre Quarkus viste <https://quarkus.io/>.

## Pré-requisitos
### Java
- Requisito: **Java 21** ou superior.
### Maven
- Requisito: **3.9.6** ou superior.


## Rodando a aplicação em modo de desenvolvimento
```shell script
git clone https://github.com/paulomd/desafio-objective.git
cd desafio-objective/
mvn quarkus:dev
```

## Testando o endpoint POST /conta
```shell script
curl --request POST \
  --url http://localhost:8080/conta \
  --header 'content-type: application/json' \
  --data '{
  "numero_conta": 234,
  "saldo": 10
}'
```

## Testando o endpoint GET /conta
```shell script
curl --request GET \
  --url 'http://localhost:8080/conta?numero_conta=234'
```

## Testando o endpoint POST /transacao
```shell script
curl --request POST \
  --url http://localhost:8080/transacao \
  --header 'content-type: application/json' \
  --data '{
  "forma_pagamento": "D",
  "numero_conta": 234,
  "valor": 1
}'
```



