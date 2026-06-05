# API Attus

API REST desenvolvida com Spring Boot para gerenciamento de clientes, vendedores e pedidos.

---

## ForntEnd disponível em
[Attus FrontEnd](https://github.com/wesleygomesc6/attus-front-end)

## Tecnologias

- Java 17
- Spring Boot 4.0.6
- Spring Data JPA
- H2 Database (em memória)
- Hibernate Validator
- SpringDoc OpenAPI 3 (Swagger UI)
- Lombok
- JUnit 5 + Mockito

---

## Pré-requisitos

- JDK 17 instalado
- Git

> Não é necessário instalar o Maven — o projeto inclui o Maven Wrapper (`mvnw.cmd`).

---

## Clonando o projeto

```bash
git clone https://github.com/wesleygomesc6/attus-back-end.git
cd api
```
---

## Executando o projeto

```powershell
.\mvnw.cmd spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

Ou gere o `.jar` e execute:

```powershell
# Gerar o jar
.\mvnw.cmd package

# Executar
java -jar target/api-0.0.1-SNAPSHOT.jar
```

---

## Executando os testes

```powershell
# Rodar todos os testes
.\mvnw.cmd test

# Rodar um arquivo de teste específico
.\mvnw.cmd test -Dtest=AtualizarPedidoTest

# Rodar um método específico
.\mvnw.cmd test -Dtest=AtualizarPedidoTest#naoDeveAlterarPedidoConcluido
```

**Pelo IntelliJ IDEA:**
- Clique com botão direito na pasta `src/test` → **Run 'All Tests'**
- Ou clique no ícone ▶ ao lado de qualquer classe ou método de teste

---

## Documentação da API (Swagger)

Com a aplicação rodando, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Console do banco H2

```
http://localhost:8080/h2-console
```

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:testdb` |
| Username | `sa` |
| Password | *(vazio)* |

---

## Endpoints

### Clientes
| Método | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos os clientes |
| POST | `/clientes` | Cadastra um novo cliente |
| PUT | `/clientes/{id}` | Atualiza um cliente |
| DELETE | `/clientes/{id}` | Remove um cliente |

### Vendedores
| Método | Rota | Descrição |
|---|---|---|
| GET | `/vendedores` | Lista todos os vendedores |
| POST | `/vendedores` | Cadastra um novo vendedor |
| PUT | `/vendedores/{id}` | Atualiza um vendedor |
| DELETE | `/vendedores/{id}` | Remove um vendedor |

### Pedidos
| Método | Rota | Descrição |
|---|---|---|
| GET | `/pedidos` | Lista todos os pedidos |
| POST | `/pedidos` | Cadastra um novo pedido |
| PUT | `/pedidos/{id}` | Atualiza um pedido |
| DELETE | `/pedidos/{id}` | Remove um pedido |

---

## Regras de negócio — Pedidos

O campo `status` segue o enum `StatusPedido` com os seguintes valores:

```
PENDENTE → EM_ANALISE → EM_ANDAMENTO → CONCLUIDO
                     ↘ CANCELADO
                     ↘ REJEITADO
```

| Regra | Detalhe |
|---|---|
| Cancelar | Apenas se estiver `PENDENTE` ou `EM_ANALISE` |
| Concluir | Apenas se estiver `EM_ANDAMENTO` |
| Pedido concluído | Não pode ser alterado em nenhum campo |

---

## Dados iniciais

O banco é populado automaticamente ao iniciar a aplicação com:

- 3 vendedores
- 2 clientes
- 3 pedidos (nos status `PENDENTE`, `EM_ANDAMENTO` e `EM_ANALISE`)

---

## Estrutura do projeto

```
src/
├── main/java/attus/guppy/io/api/
│   ├── config/          # Swagger, CORS, tratamento de exceções
│   ├── controllers/     # ClienteController, PedidoController, VendedorController
│   ├── dtos/            # Records de entrada e saída por entidade
│   ├── models/          # Entidades JPA (Cliente, Pedido, Vendedor, StatusPedido)
│   ├── repositories/    # Interfaces JpaRepository
│   └── services/        # Um service por ação (Cadastrar, Atualizar, Listar, Deletar)
└── test/java/attus/guppy/io/api/
    └── services/        # Testes unitários com JUnit 5 e Mockito
```