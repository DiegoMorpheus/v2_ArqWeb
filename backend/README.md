## 2. Conteúdo do Arquivo do Backend (`backend/README.md`)

Copie este texto e salve-o como **`backend/README.md`** dentro da sua pasta `backend`.

```markdown
# 💻 Backend (API REST - Java/Spring Boot)

Este diretório contém o código-fonte da aplicação de backend, responsável por hospedar a API REST, gerenciar a lógica de negócios e interagir com o banco de dados.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (JDK 21+)
* **Framework:** Spring Boot 3+
* **Gerenciador de Dependências:** Maven (`pom.xml`)
* **Contêiner:** Docker
* **Monitoramento:** Integração com Prometheus/Micrometer

## ⚙️ Configuração Local (Sem Docker)

Se você precisar rodar o backend localmente para **debug** sem utilizar o Docker Compose:

1.  **Pré-requisitos:** Certifique-se de ter o JDK 21 e o Maven instalados.
2.  **Compilar:**
    ```bash
    mvn clean install
    ```
3.  **Executar:**
    ```bash
    java -jar target/*.jar
    ```
    *A aplicação será iniciada na porta padrão `8080` (ou na porta configurada no seu `application.properties`).*

---

## 🐳 Execução via Docker Compose (Recomendado)

O backend é um serviço dentro da orquestração principal do Docker Compose. Ele será iniciado na porta **8081** do seu host (mapeada para a porta 8080 interna do contêiner).

**Detalhes do Serviço no Docker Compose (`docker-compose.yml`):**

| Parâmetro | Valor | Descrição |
| :--- | :--- | :--- |
| **Nome do Serviço** | `app` | Usado para comunicação interna na rede Docker. |
| **Portas (Host:Container)** | `8081:8080` | O serviço fica acessível externamente em `http://localhost:8081`. |
| **Rede** | `monitoring` | Permite comunicação com Prometheus e Frontend. |

## 🔗 Endpoints Principais

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/api/v1/health` | Verifica a saúde da aplicação. |
| `GET` | `/actuator/prometheus` | Exporta métricas para o Prometheus. |
| `POST` | `/api/v1/...` | (Exemplo de endpoint de criação/inserção) |


# 📊 Infraestrutura de Monitoramento (Prometheus e Grafana)

Esta seção descreve a stack de monitoramento utilizada para coletar, armazenar e visualizar as métricas da aplicação.

## 🛠️ Componentes

1.  **Prometheus:** Responsável por coletar métricas de *time-series* do backend (serviço `app`).
2.  **Grafana:** Responsável pela visualização das métricas em **Dashboards interativos**.

## 🐳 Detalhes da Configuração

Ambos os serviços rodam na rede Docker `monitoring`.

### 1\. Prometheus

* **Porta de Acesso (Host):** `9090`
* **Acesso:** [http://localhost:9090](http://localhost:9090)
* **Configuração:** Utiliza o arquivo `./prometheus.yml` (na raiz do projeto) para definir os *targets* de coleta.
    * **Target Principal:** O Prometheus está configurado para raspar o endpoint `/actuator/prometheus` do serviço `app` (o backend) no endereço interno `http://app:8080`.

### 2\. Grafana

* **Porta de Acesso (Host):** `3000`
* **Acesso:** [http://localhost:3000](http://localhost:3000)
    * Credenciais padrão (se não forem alteradas): `admin`/`admin`.
* **Persistência:** O volume `grafana-storage` é usado para persistir dashboards e configurações, garantindo que os dados não se percam ao reiniciar o contêiner.

## ➡️ Uso

Após iniciar a stack com `docker compose up -d`:

1.  Acesse o **Grafana** em [http://localhost:3000](http://localhost:3000).
2.  Adicione o **Prometheus** como uma fonte de dados (`Datasource`). O **endereço interno** a ser usado é:
    ```
    http://prometheus:9090
