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

## 📌 Decisões Técnicas

- Spring Boot 3 + Java 21: escolhido por ser moderno, estável e amplamente utilizado no mercado.

- JPA/Hibernate: abstrai o acesso ao banco de dados relacional, simplificando o mapeamento objeto-relacional.

- Banco de dados MySQL: rodando em container Docker para garantir portabilidade e facilidade de execução.

- Validações com Jakarta Validation: asseguram a integridade dos dados já na entrada (camada DTO).

- DTOs distintos para criação e atualização: evita expor diretamente a entidade User ou Task e melhora a manutenibilidade.

- Enum para Status da Tarefa: restringe valores válidos (PENDENTE, EM_ANDAMENTO, CONCLUIDA) e facilita regras de negócio.

- Atualização automática do completionDate: sempre que uma tarefa é marcada como CONCLUIDA.

- Dockerfile + docker-compose: permitem rodar a aplicação e o banco de dados em qualquer máquina sem necessidade de configuração manual.

- Arquitetura em camadas (Controller, Service, Repository): melhora a organização, testabilidade e manutenção do código.

---

## 🐳 Rodando com Docker

1. Build e start dos containers:

```bash
docker compose up --build

2. A aplicação ficará disponível em:

- API: http://localhost:8080
