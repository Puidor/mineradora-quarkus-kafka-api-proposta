# API de Propostas - Mineradora

Sistema simples para gerenciar propostas de compra de minérios de ferro. A aplicação permite a criação, consulta e remoção de propostas, notificando outros sistemas sobre novas propostas via Apache Kafka.

## ✨ Funcionalidades

* **Criação de Propostas:** Registra novas propostas.
* **Consulta de Propostas:** Busca os detalhes de uma proposta específica pelo seu ID.
* **Remoção de Propostas:** Exclui uma proposta do sistema.
* **Integração com Kafka:** Publica um evento no tópico `proposal` sempre que uma nova proposta é criada.

## 🚀 Tecnologias Utilizadas

* **Java 17+**
* **Quarkus:** Framework Java nativo para nuvem.
* **Hibernate ORM com Panache:** Para persistência de dados.
* **PostgreSQL:** Banco de dados relacional.
* **SmallRye Reactive Messaging (Kafka):** Para mensageria assíncrona.
* **Docker & Docker Compose:** Para gerenciamento de dependências (Kafka e PostgreSQL).

## 📋 Pré-requisitos

* JDK 17 ou superior
* Maven 3.8+ ou Gradle
* Docker e Docker Compose

## ⚙️ Como Executar

1.  **Inicie os serviços de dependência (Kafka & PostgreSQL):**

    No terminal, a partir da raiz do projeto, suba os contêineres Docker:
    ```bash
    docker-compose up -d
    ```

2.  **Execute a aplicação Quarkus:**

    Abra outro terminal e execute o seguinte comando:
    ```bash
    ./mvnw quarkus:dev
    ```
    A aplicação estará disponível em `http://localhost:8091`.

## 📡 Endpoints da API

A URL base da API é `http://localhost:8091/api/proposal`.

| Método | Endpoint | Descrição | Exemplo de Body (JSON) |
| :--- | :--- | :--- | :--- |
| `POST` | `/` | Cria uma nova proposta. | `{"customer": "Vale S.A.", "priceTonne": 150.50, "tonnes": 5000, "country": "BR", "proposalValidityDays": 30}` |
| `GET` | `/{id}` | Busca uma proposta por ID. | N/A |
| `DELETE` | `/{id}` | Remove uma proposta por ID. | N/A |