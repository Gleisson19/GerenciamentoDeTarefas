# Gerenciamento de Tarefas - Backend Java

Projeto de API RESTful para gerenciamento de tarefas internas, desenvolvido em Java 21 com Spring Boot.

---

## 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Bean Validation (Jakarta Validation)
- MySQL (via Docker ou local)
- Maven
- Docker / Docker Compose

---

## 📦 Funcionalidades

- CRUD de **usuários** (criar, listar, buscar, atualizar, deletar)
- CRUD de **tarefas** (criar, listar, buscar, atualizar status)
- Validações de campos obrigatórios e formato de email
- Controle de status das tarefas com preenchimento automático de data de conclusão
- Tratamento global de erros (validação, JSON inválido, argumentos inválidos)

---

## ⚡ Endpoints

### Usuários

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/users` | Cria um novo usuário |
| GET | `/users/{userId}` | Busca usuário por ID |
| GET | `/users` | Lista todos os usuários |
| PATCH | `/users/{userId}` | Atualiza usuário (nome e/ou senha) |
| DELETE | `/users/{userId}` | Deleta usuário |

### Tarefas

| Método | Endpoint | Descrição                                           |
|--------|----------|-----------------------------------------------------|
| POST | `/tasks` | Cria uma nova tarefa                                |
| GET | `/tasks` | Lista tarefas (filtro opcional: `status`, `userId`) |
| GET | `/tasks/{taskId}` | Busca tarefa por ID                                 |
| PATCH | `/tasks/{taskId}/status` | Atualiza status da tarefa                           |

---

## 🐳 Rodando com Docker

1. Build e start dos containers:

```bash
docker-compose up --build