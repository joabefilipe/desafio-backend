# desafio-backend

API REST desenvolvida em **Java 17 + Spring Boot**, com autenticação **JWT**, gerenciamento de usuários e tarefas, persistência em **PostgreSQL** e versionamento do banco via **Liquibase**.

O projeto foi preparado para ser executado com **Docker Compose**, subindo toda a stack com **um único comando**.

---

##  Requisitos da entrega atendidos

- Projeto versionado em repositório Git público 
- `docker-compose up` sobe toda a stack 
- README com descrição e horas utilizadas 

---

## Horas utilizadas no desenvolvimento do back-end (5 horas)

- **Primeira fase (configuração do Git, estrutura inicial do projeto e primeiros commits):**
  - Início: **18:30**
  - Fim: **22:30**
  - Total: **4 horas**

> As próximas etapas (Docker, Liquibase, endpoints, segurança, etc.) foram desenvolvidas dentro do mesmo período do desafio técnico.

---

##  Tecnologias utilizadas

- **Java 17**
- **Spring Boot**
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Validation
- **JWT**
- **PostgreSQL**
- **Liquibase**
- **MapStruct**
- **Swagger/OpenAPI** (springdoc)
- **Docker / Docker Compose**
- **Maven**

---


Estrutura organizada por responsabilidade:

- `controller` → Endpoints REST
- `service` → Regras de negócio
- `repository` → Acesso a dados (Spring Data JPA)
- `entity` → Entidades JPA
- `security` → JWT, filtros e configurações de segurança
- `dto` → Objetos de entrada/saída
- `exception` → Tratamento de exceções
- `db/changelog` → Migrations do banco (Liquibase)

---

## Segurança (JWT)

- Autenticação baseada em **JWT**
- Endpoints públicos:
  - `POST /auth/login`
  - `POST /usuario/cadastro`
- Demais endpoints exigem **Bearer Token**
- API stateless (sem sessão)

---

## Banco de dados e migrations

- **PostgreSQL**
- Criação de tabelas e dados iniciais via **Liquibase**
- Base sobe automaticamente ao executar o Docker Compose

---

## Como executar com Docker (recomendado)

### Pré-requisitos
- Docker + Docker Compose instalados

### Subir a aplicação (stack completa)
Na raiz do projeto:

```bash
docker compose up --build

Credenciais de teste (usuário já criado no banco)
Use essas credenciais para logar e pegar o token:

{
  "login": "admin",
  "password": "123461"
}

Testes da API (Postman)

Para facilitar a validação do desafio, o projeto conta com uma collection do Postman contendo todos os endpoints já configurados e testados.

 Collection disponível em:

/postman/Tarefa.postman_collection.json

Como utilizar

Abra o Postman

Clique em Import

Selecione o arquivo:

postman/Tarefa.postman_collection.json


Execute o endpoint de login:

POST /auth/login


Copie o token JWT retornado

Configure o token como Bearer Token nas requisições protegidas