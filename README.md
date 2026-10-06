# 💰 FinTrack — Sistema de Finanças Pessoais

Aplicação desktop desenvolvida em Java 21 com interface gráfica em JavaFX, arquitetura MVC (Model-View-Controller) e padrão DAO para persistência de dados. O projeto faz parte das entregas práticas do módulo intermediário de desenvolvimento em Java.

---

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Interface Gráfica:** JavaFX (com arquivos `.FXML`, Scene Builder e CSS)
* **Banco de Dados:** SQLite (padrão local) / Compatível com MySQL via JDBC
* **Persistência:** Padrão DAO (`PreparedStatement` e `ResultSet` para segurança contra SQL Injection)
* **Estruturas Avançadas:** Generics e curingas (`?`, `? extends T`, `? super T`)
* **Testes Automatizados:** JUnit 5 (com suporte a banco em memória)
* **Gerenciador de Dependências:** Maven

---

## 📂 Arquitetura do Projeto

O projeto está organizado seguindo os princípios de separação de responsabilidades:

```text
fintrack/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controller/   # Controladores das telas JavaFX
│   │   │   ├── dao/          # Conexão e classes de persistência (DAO)
│   │   │   ├── model/        # Entidades do sistema (Transacao)
│   │   │   ├── service/      # Repositório genérico e regras de negócio
│   │   │   └── view/         # Classe principal (FinApp)
│   │   └── resources/
│   │       ├── css/          # Folhas de estilo da interface
│   │       └── fxml/         # Layouts das telas (.fxml)
│   └── test/
│       └── java/             # Testes unitários com JUnit
├── data/                     # Pasta onde o banco fintrack.db é gerado
└── pom.xml                   # Configuração e dependências do Maven

## ▶️ Como Executar

```bash
mvn clean test
mvn javafx:run

## 👨‍💻 Autor

Caio