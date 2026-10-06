# 💰 FinTrack — Sistema de Finanças Pessoais

Aplicação desktop desenvolvida em **Java 21** com interface gráfica em **JavaFX**, utilizando arquitetura **MVC (Model-View-Controller)** e o padrão **DAO** para persistência de dados. O projeto faz parte das entregas práticas do módulo intermediário de desenvolvimento em Java.

---

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Interface Gráfica:** JavaFX, FXML, Scene Builder e CSS
* **Banco de Dados:** SQLite (padrão local) / Compatível com MySQL via JDBC
* **Persistência:** Padrão DAO (`PreparedStatement` e `ResultSet`) para maior segurança contra SQL Injection
* **Estruturas Avançadas:** Generics e curingas (`?`, `? extends T`, `? super T`)
* **Testes Automatizados:** JUnit 5, com suporte a banco de dados em memória
* **Gerenciador de Dependências:** Maven

---

## 📂 Arquitetura do Projeto

O projeto está organizado seguindo princípios de **separação de responsabilidades**, utilizando MVC, DAO e uma camada de serviços:

```text
fintrack/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controller/   # Controladores das telas JavaFX
│   │   │   ├── dao/          # Conexão e classes de persistência (DAO)
│   │   │   ├── model/        # Entidades do sistema (Transacao)
│   │   │   ├── service/      # Repositórios genéricos e regras de negócio
│   │   │   └── view/         # Classe principal (FinApp)
│   │   └── resources/
│   │       ├── css/           # Folhas de estilo da interface
│   │       └── fxml/          # Layouts das telas (.fxml)
│   └── test/
│       └── java/              # Testes unitários com JUnit
├── data/                      # Pasta onde o banco fintrack.db é gerado
└── pom.xml                    # Configuração e dependências do Maven
```

---

## ▶️ Como Executar

### Pré-requisitos

* **JDK 21** ou superior
* **Maven**
* **JavaFX** configurado através do Maven

### Executando os testes

Na raiz do projeto, execute:

```bash
mvn clean test
```

Esse comando limpa os arquivos compilados anteriormente, compila o projeto e executa os testes automatizados.

### Executando a aplicação

Após isso, execute:

```bash
mvn javafx:run
```

A aplicação será iniciada através do plugin do JavaFX configurado no `pom.xml`.

O banco de dados SQLite será criado localmente na pasta `data/`.

---

## 🧪 Testes

O projeto utiliza **JUnit 5** para testes automatizados, incluindo testes relacionados à persistência dos dados.

Os testes podem ser executados com:

```bash
mvn test
```

---

## 🏗️ Arquitetura

O sistema utiliza diferentes camadas para manter o código organizado:

* **Model:** representa as entidades e dados do sistema.
* **View:** contém as telas e interface gráfica em JavaFX/FXML.
* **Controller:** responsável pela interação entre a interface e as demais camadas.
* **DAO:** responsável pelo acesso e persistência dos dados no banco.
* **Service:** concentra regras de negócio e operações reutilizáveis.

Essa organização facilita a manutenção, os testes e a evolução da aplicação.

---

## 👨‍💻 Autor

**Caio**

Projeto desenvolvido como parte das atividades práticas do módulo intermediário de desenvolvimento em Java.
