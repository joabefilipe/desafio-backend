README.md
# Desafio Técnico – Sistema de Gerenciamento de Tarefas

Este projeto consiste em um **sistema completo de gerenciamento de tarefas**, desenvolvido como parte de um desafio técnico, utilizando **Spring Boot no backend** e **Vue 3 + TypeScript no frontend**.

A aplicação permite autenticação de usuários, cadastro, edição, listagem e exclusão de tarefas, com controle de acesso via **JWT** e persistência em banco de dados **PostgreSQL**.

---

## Arquitetura do Sistema

[ Vue 3 + TypeScript ]
↓
API REST
[ Spring Boot + JWT ]
↓
PostgreSQL

Todo o ambiente é orquestrado via **Docker Compose**.

---

## Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Swagger
- Liquibase
- MapStruct
- Docker

### Frontend
- Vue 3
- TypeScript
- Vite
- Axios
- Vue Router
- Pinia
- Lucide Icons
- CSS (tema escuro)

---

## Funcionalidades
### Usuários
- Login com autenticação JWT
- Cadastro de usuário
- Controle de acesso por perfil

### Tarefas
- Listagem de tarefas em cards
- Paginação com scroll infinito
- Filtro por título e status
- Criação de tarefas
- Edição de tarefas
- Exclusão de tarefas com confirmação

---

## Como executar o projeto
### Pré-requisitos
- Docker
- Docker Compose

### Subir toda a aplicação

Na raiz do projeto:

```bash
docker compose up --build
```

Acessos
Serviço	URL
Front-end	http://localhost:5173

Back-end	http://localhost:8080

Banco	localhost:5433 (PostgreSQL)
Estrutura do Projeto
desafio-tecnico/
├── docker-compose.yml
├── README.md
├── back-end/
│   └── README.md
└── front-end/
    └── README.md

##  Usuários de Teste

A aplicação já inicia com **dados populados no banco de dados** para facilitar os testes.

### Usuários cadastrados
```
{
  "login": "admin",
  "password": "123461"
}
```
Tempo de Desenvolvimento

Tempo total aproximado: 18 horas

Autor

João Felipe
Desafio Técnico – Full Stack
2026
