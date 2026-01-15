# Front-end – Desafio Técnico (Gerenciamento de Tarefas)

Este projeto é o **front-end** do desafio técnico de gerenciamento de tarefas.  
A aplicação foi desenvolvida em **Vue 3 + TypeScript**, consumindo uma **API REST em Spring Boot**, com autenticação via **JWT**.

O sistema permite:

- Autenticação de usuários (login)
- Cadastro de usuários
- Listagem de tarefas com paginação e scroll infinito
- Criação, edição e exclusão de tarefas
- Filtros por título e status
- Interface responsiva e componentizada

---

## Tecnologias Utilizadas

- **Vue 3**
- **TypeScript**
- **Vite**
- **Axios**
- **Vue Router**
- **Pinia**
- **Lucide Icons**
- **CSS puro (Dark Theme)**

---

## Funcionalidades

### Autenticação

- Tela de **login**
- Cadastro de usuário
- Armazenamento de token JWT no `localStorage`
- Interceptor Axios para envio automático do token

### Tarefas

- Listagem de tarefas em **cards**
- Paginação com **scroll infinito**
- Filtro por:
  - **Título**
  - **Status (ATIVO / DESATIVADO)**
- Cadastro de nova tarefa
- Edição de tarefa existente (mesmo formulário)
- Exclusão de tarefa com modal de confirmação

---

## Estrutura do Projeto

```bash
src/
├── api/                 # Comunicação com a API (Axios)
├── components/          # Componentes reutilizáveis
│   ├── TarefaModal.vue
│   ├── ConfirmarExclusaoModal.vue
│   └── FiltroTarefa.vue
├── views/               # Telas principais
│   ├── LoginView.vue
│   ├── TarefaView.vue
│   └── TarefaCadastroEdicaoView.vue
├── router/              # Configuração de rotas
├── stores/              # Pinia (auth)
├── style.css            # Estilos globais
├── App.vue
└── main.ts
```
