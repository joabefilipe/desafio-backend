# desafio-backend

# Desafio Backend – API REST com Spring Boot

Este projeto faz parte de um **desafio técnico backend**, com o objetivo de demonstrar conhecimentos em **Java, Spring Boot, segurança, persistência de dados e boas práticas de versionamento**.

A aplicação consiste em uma **API REST** com autenticação baseada em **JWT**, cadastro e login de usuários, e persistência em **PostgreSQL**, seguindo uma arquitetura organizada e extensível.

---

##  Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
    - Spring Web
    - Spring Data JPA
    - Spring Security
    - Validation
- **JWT (JSON Web Token)** – autenticação
- **PostgreSQL** – banco de dados relacional
- **Liquibase** – versionamento do banco de dados
- **Maven** – gerenciamento de dependências
- **Git & GitHub** – versionamento de código

---

##  Arquitetura do Projeto

O projeto segue uma separação clara de responsabilidades:

- `controller` → Camada de entrada (endpoints REST)
- `service` → Regras de negócio
- `repository` → Acesso a dados (JPA)
- `entity` → Entidades JPA
- `security` → Configurações de segurança e filtros JWT
- `dto` → Objetos de transferência de dados
- `exception` → Tratamento de exceções
- `db/changelog` → Scripts de versionamento do banco (Liquibase)

---

##  Segurança

- Autenticação baseada em **JWT**
- Endpoints públicos:
    - `POST /auth/login`
    - `POST /usuario/cadastro`
- Demais endpoints protegidos por autenticação
- Sessão **stateless**
- CSRF desabilitado para APIs REST

---

##  Banco de Dados

- **PostgreSQL**
- Criação e versionamento das tabelas via **Liquibase**
- Configuração definida em `application.properties`

---

## ▶ Como Executar o Projeto

### Pré-requisitos
- Java 17+
- Maven
- PostgreSQL em execução

### Passos
1. Clone o repositório:
   ```bash
   git clone git@github.com:joabefilipe/desafio-backend.git
