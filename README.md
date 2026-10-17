# AutoEscola3ESS — Checkpoint 5

API REST desenvolvida em Spring Boot para gerenciamento e agendamento de instruções de uma auto-escola, como parte da disciplina **SOA e WebServices** .

## Integrantes

- **Werbeth Nunes** — RM 559067
- **Thamiris Almeida** — RM 559155
- **Fabrini Soares** — RM 557813
- **Guilherme Cezarino Simões** — RM 557724

## Sobre o projeto

O projeto consiste em uma API REST para uma auto-escola, contemplando o gerenciamento de:

- Alunos;
- Instrutores;
- Usuários;
- Instruções;
- Agendamento e cancelamento de instruções.

No Checkpoint 5 foram implementados e/ou configurados:

- Integração com a API externa ViaCEP;
- Documentação automática com Swagger/OpenAPI;
- Configuração de CORS;
- Autenticação e autorização utilizando JWT;
- Tratamento estruturado de erros;
- Testes automatizados.

## Tecnologias utilizadas

- Java 25
- Spring Boot 4.0.5
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- MySQL
- Flyway
- Swagger / OpenAPI
- ViaCEP
- Maven
- JUnit

---

# Como executar o projeto

## Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

- Java 17 ou superior;
- MySQL;.

O projeto possui Maven Wrapper, portanto não é necessário instalar o Maven separadamente.

## 1. Clonar o projeto

```bash
git clone URL_DO_REPOSITORIO
```

Entre na pasta do projeto:

```bash
cd AutoEscola3ESS_CP5
```

## 2. Criar o banco de dados

O projeto utiliza MySQL.

Crie o banco de dados:

```sql
CREATE DATABASE autoescola3ess_test;
```

As tabelas **não precisam ser criadas manualmente**.

O projeto utiliza o **Flyway** para controlar e executar automaticamente as migrations do banco de dados.

As migrations estão localizadas em:

```text
src/main/resources/db/migration
```

Ao iniciar a aplicação, o Flyway verifica as migrations e cria/atualiza as estruturas necessárias no banco.

## 3. Configuração do banco

A conexão com o banco de dados está definida no arquivo:

```text
src/main/resources/application.properties
```

As credenciais devem corresponder ao MySQL utilizado no ambiente em que o projeto será executado.

## 4. Executar a aplicação

No Windows, utilizando o Maven Wrapper:

```bash
.\mvnw.cmd spring-boot:run
```

Ou, caso o Maven esteja instalado:

```bash
mvn spring-boot:run
```

A aplicação será executada na porta:

```text
8080
```

Base da API:

```text
http://localhost:8080
```

---

# Swagger

Com a aplicação em execução, a documentação da API pode ser acessada através do Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

O Swagger permite visualizar e testar os endpoints da API diretamente pelo navegador.

## Autenticação no Swagger

A API utiliza autenticação JWT para os endpoints protegidos.

### 1. Primeiro acesso

Na primeira execução, caso ainda não exista nenhum usuário com perfil `ADMIN`, a aplicação cria automaticamente um usuário administrador:

```text
Login: admin
Senha: admin
Perfil: ADMIN
```

A senha é armazenada utilizando BCrypt.

Caso já exista um usuário com perfil `ADMIN`, nenhum novo administrador é criado.

### 2. Realizar login

No Swagger, localize:

```text
POST /login
```

Utilize:

```json
{
  "login": "admin",
  "senha": "admin"
}
```

Após executar, a API retornará um token JWT.

### 3. Autorizar o Swagger

Copie o token retornado pelo login.

No topo da página do Swagger, clique em:

**Authorize**

Informe o token no formato:

```text
Bearer SEU_TOKEN
```

Depois clique em **Authorize**.

A partir desse momento, será possível testar os endpoints protegidos diretamente pelo Swagger.

---

# Endpoints

## Autenticação

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/login` | Realiza login e retorna o token JWT |

## Usuários

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/usuarios` | Lista usuários |
| POST | `/usuarios` | Cadastra usuário |
| PUT | `/usuarios` | Atualiza usuário |
| PUT | `/usuarios/minha-senha` | Altera a própria senha |
| DELETE | `/usuarios/{id}` | Exclui usuário |

## Instrutores

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/instrutores` | Lista instrutores |
| GET | `/instrutores/{id}` | Consulta instrutor por ID |
| POST | `/instrutores` | Cadastra instrutor |
| PUT | `/instrutores` | Atualiza instrutor |
| DELETE | `/instrutores/{id}` | Inativa instrutor |

## Alunos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/alunos` | Lista alunos |
| GET | `/alunos/{id}` | Consulta aluno por ID |
| POST | `/alunos` | Cadastra aluno |
| PUT | `/alunos` | Atualiza aluno |
| DELETE | `/alunos/{id}` | Inativa aluno |

## Instruções

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/instrucoes` | Agenda uma instrução |
| DELETE | `/instrucoes/{id}` | Cancela uma instrução |

## Endereço

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/enderecos/cep/{cep}` | Consulta endereço através da ViaCEP |

## Health Check

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/health-check` | Verifica se a aplicação está funcionando |

---

# Funcionalidades

## Usuários

- Cadastro de usuários;
- Login;
- Autenticação com JWT;
- Autorização por perfil;
- Senhas armazenadas com BCrypt;
- Listagem de usuários;
- Atualização de usuários;
- Exclusão de usuários;
- Alteração da própria senha.

Perfis disponíveis:

- `ADMIN`
- `USER`

## Instrutores

A API permite:

- Cadastrar instrutores;
- Consultar instrutores;
- Consultar instrutor por ID;
- Atualizar instrutores;
- Inativar instrutores;
- Listar instrutores com paginação e ordenação.

Os dados do instrutor incluem nome, e-mail, telefone, CNH, especialidade e endereço.

## Alunos

A API permite:

- Cadastrar alunos;
- Consultar alunos;
- Consultar aluno por ID;
- Atualizar alunos;
- Inativar alunos;
- Listar alunos com paginação e ordenação.

Os dados do aluno incluem nome, e-mail, telefone, CPF e endereço.

## Instruções

A API permite realizar o agendamento e cancelamento de instruções.

Entre as regras de negócio implementadas estão:

- Instruções de segunda-feira a sábado;
- Horário permitido entre 06:00 e 21:00;
- Duração de 1 hora;
- Antecedência mínima de 30 minutos para agendamento;
- Não permite aluno inativo;
- Não permite instrutor inativo;
- Máximo de duas instruções para o mesmo aluno no mesmo dia;
- Não permite que um instrutor tenha duas instruções no mesmo horário;
- Possibilidade de seleção automática de instrutor quando ele não é informado;
- Cancelamento de instrução com motivo;
- Regra de antecedência para cancelamento.

---

# Integração com ViaCEP

A aplicação possui integração com a API externa **ViaCEP** para consulta de endereços.

Endpoint:

```text
GET /enderecos/cep/{cep}
```

Exemplo:

```text
GET /enderecos/cep/03021000
```

A aplicação consulta o CEP informado e retorna os dados de endereço.

Quando o CEP não é encontrado, a API retorna uma resposta de erro adequada.

---

# Autenticação e autorização

A autenticação da API é realizada utilizando **JWT (JSON Web Token)**.

O usuário realiza login através de:

```text
POST /login
```

Após o login, o token JWT deve ser enviado nas requisições aos endpoints protegidos.

O controle de acesso utiliza os perfis:

```text
ADMIN
USER
```

Operações administrativas possuem restrição de acesso de acordo com o perfil do usuário autenticado.

---

# Tratamento de erros

A aplicação possui tratamento centralizado de exceções para retornar respostas adequadas em situações como:

- Dados inválidos;
- Recursos não encontrados;
- Regras de negócio não atendidas;
- CEP não encontrado;
- Falhas na autenticação;
- Falhas de autorização;
- Erros relacionados ao serviço externo.

---

# CORS

A API possui configuração de **CORS (Cross-Origin Resource Sharing)** para permitir o consumo da API por aplicações clientes autorizadas.

---

# Testes automatizados

O projeto possui testes automatizados utilizando JUnit e Spring Boot Test.

Para executar os testes:

```bash
.\mvnw.cmd test
```

Para executar a validação completa do projeto:

```bash
.\mvnw.cmd clean verify
```

---

# Build

Para gerar o build da aplicação:

```bash
.\mvnw.cmd clean package
```

O arquivo `.jar` será gerado no diretório:

```text
target/
```

---

# Estrutura do projeto

A aplicação segue uma organização em camadas:

```text
src
├── main
│   ├── java
│   │   └── br.com.fiap3ess.autoescola3ess
│   │       ├── controller
│   │       ├── domain
│   │       ├── infrastructure
│   │       ├── service
│   │       ├── shared
│   │       └── ...
│   │
│   └── resources
│       ├── db
│       │   └── migration
│       └── application.properties
│
└── test
    └── java
```

---

# Migrations

As migrations do banco de dados são gerenciadas pelo Flyway.

Localização:

```text
src/main/resources/db/migration
```

As migrations são executadas automaticamente durante a inicialização da aplicação.

Dessa forma, não é necessário criar manualmente as tabelas, índices e demais estruturas utilizadas pela API.

---

# Principais códigos HTTP

A API utiliza códigos HTTP de acordo com o resultado das operações:

| Código | Significado |
|---|---|
| `200 OK` | Operação realizada com sucesso |
| `201 Created` | Recurso criado com sucesso |
| `204 No Content` | Operação realizada sem conteúdo de retorno |
| `400 Bad Request` | Dados inválidos ou regra de negócio não atendida |
| `401 Unauthorized` | Autenticação necessária ou inválida |
| `403 Forbidden` | Usuário sem permissão |
| `404 Not Found` | Recurso não encontrado |

---

# Tecnologias e ferramentas

O projeto foi desenvolvido utilizando:

- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **JWT**
- **MySQL**
- **Flyway**
- **Swagger/OpenAPI**
- **ViaCEP**
- **Maven**
- **JUnit**

---

# Considerações finais

O projeto reúne as funcionalidades desenvolvidas nos checkpoints anteriores e as demandas do Checkpoint 5 da disciplina SOA e WebServices.

A API disponibiliza operações de gerenciamento de alunos, instrutores, usuários e instruções, além de autenticação JWT, integração com serviço externo, documentação Swagger/OpenAPI, CORS, tratamento de erros e testes automatizados.
