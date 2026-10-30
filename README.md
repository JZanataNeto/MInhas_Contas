# Minhas Contas

Aplicativo desktop para gestão de contas a pagar, desenvolvido com **Java** e **JavaFX**. Permite cadastrar, visualizar, editar e excluir contas com persistência de dados em arquivo **CSV**.

## Funcionalidades

- **Cadastrar** novas contas com concessionária, descrição e data de vencimento.
- **Visualizar** todas as contas em uma tabela.
- **Editar** contas existentes selecionando-as na tabela.
- **Excluir** contas individualmente.
- **Persistência** automática dos dados no arquivo `dados.csv`.

## Requisitos

- **JDK 9** ou superior.
- **Maven 3.x** (ou o wrapper `mvnw` incluído no projeto).

## Instalação

### 1. Clonar ou baixar o projeto

Baixe os arquivos do projeto e extraia em um diretório de sua escolha.

### 2. Verificar o JDK

Certifique-se de ter o **JDK 9** ou superior instalado:

```bash
java -version
```

Caso o Java não esteja instalado, baixe e instale a partir do [site oficial da Oracle](https://www.oracle.com/java/technologies/downloads/) ou use uma distribuição open-source como [Adoptium (Eclipse Temurin)](https://adoptium.net/).

### 3. Instalar as dependências

Navegue até o diretório do projeto e execute:

```bash
mvn clean install
```

No Windows, use o wrapper Maven incluído:

```powershell
.\mvnw.cmd clean install
```

O Maven baixará automaticamente o **JavaFX 21.0.6** e demais dependências declaradas no `pom.xml`.

### 4. Verificar a instalação

Após a instalação bem-sucedida, execute a aplicação para confirmar que tudo está configurado corretamente:

```bash
mvn javafx:run
```

A janela do aplicativo **Minhas Contas** deve abrir sem erros.

## Como executar

### A partir do código-fonte

#### Compilar o projeto

```bash
mvn clean compile
```

#### Executar a aplicação

```bash
mvn javafx:run
```

No Windows, use o wrapper Maven incluído:

```powershell
.\mvnw.cmd javafx:run
```

### A partir do Release

1. Acesse a página de [Releases](../../releases) do projeto.
2. Baixe o arquivo `.jar` mais recente disponível.
3. Execute o arquivo baixado usando o comando:

```bash
java -jar MInhas_Contas-1.0-SNAPSHOT.jar
```

No Windows, também é possível executar clicando duas vezes no arquivo `.jar`, desde que o Java esteja corretamente configurado no sistema.

## Estrutura do projeto

```
MInhas_Contas/
├── dados.csv                              # Arquivo de persistência dos dados
├── pom.xml                                # Configuração do Maven
├── mvnw / mvnw.cmd                        # Wrapper Maven
└── src/main/java/com/joaozanata/minhas_contas/
    ├── ContasApplication.java             # Classe principal (entrada JavaFX)
    ├── ContasController.java              # Controlador da interface (lógica da tela)
    ├── ContasService.java                 # Interface do serviço (contrato CRUD)
    ├── ContasCSVService.java              # Implementação do serviço com CSV
    ├── Conta.java                         # Modelo de dados (entidade Conta)
    ├── Launcher.java                      # Launcher alternativo JavaFX
    └── contas.fxml                        # Layout da interface (FXML)
```

## Modelo de dados

A entidade **Conta** possui os seguintes campos:

| Campo            | Tipo   | Descrição                  |
|------------------|--------|----------------------------|
| `id`             | `int`  | Identificador único        |
| `concessionaria` | `String` | Nome da concessionária   |
| `descricao`      | `String` | Descrição da conta       |
| `dataVencimento` | `Date` | Data de vencimento         |

## Formato do arquivo CSV

Os dados são salvos no arquivo `dados.csv` com separador `;`:

```
id;concessionaria;descricao;dd/MM/yyyy
```

## Tecnologias utilizadas

- **Java 9** (módulos JPMS)
- **JavaFX 21.0.6** (interface gráfica)
- **Maven** (gerenciamento de dependências e build)
- **JUnit 5** (testes)

## Autor

Desenvolvido por **João Zanata** (`com.joaozanata`).
