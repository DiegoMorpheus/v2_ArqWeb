# 🌐 Frontend (Aplicação Web - React/Vite)

Este diretório contém a aplicação web desenvolvida em **React**, responsável pela interface do usuário e por consumir os dados da **API REST** do backend.

## 🛠️ Tecnologias Utilizadas

* **Framework:** React
* **Build Tool:** Vite
* **Gerenciador de Dependências:** npm (Node.js)
* **Linguagem:** JavaScript/TypeScript (se houver)
* **Contêiner:** Docker (servido via Nginx)

## ⚙️ Configuração Local (Sem Docker)

Se você precisar rodar o frontend localmente para **desenvolvimento**:

1.  **Pré-requisitos:** Certifique-se de ter o Node.js (v20+) e o npm instalados.
2.  **Instalar Dependências:**
    ```bash
    npm install
    ```
3.  **Executar em Modo de Desenvolvimento:**
    ```bash
    npm run dev
    ```
    *A aplicação geralmente será iniciada em `http://localhost:5173` e incluirá **Hot Reloading**.*

---

## 🐳 Execução via Docker Compose (Recomendado)

O frontend é um serviço dentro da orquestração principal do Docker Compose. Ele é compilado e servido por um contêiner **Nginx**.

**Detalhes do Serviço no Docker Compose (`docker-compose.yml`):**

| Parâmetro | Valor | Descrição |
| :--- | :--- | :--- |
| **Nome do Serviço** | `frontend` | Usado para comunicação interna na rede Docker. |
| **Portas (Host:Container)** | `3001:80` | O frontend fica acessível externamente em `http://localhost:3001`. |
| **Rede** | `monitoring` | Permite comunicação com o Backend e a infraestrutura. |

## 🔗 Configuração de Comunicação com o Backend

A aplicação frontend está configurada para buscar a API do backend através da **variável de ambiente** (ex: `VITE_APP_BACKEND_URL`) durante a fase de build.

Quando rodando via Docker Compose, a URL interna utilizada é:



http://app:8080


*O nome do host `app` é o nome do serviço do backend no `docker-compose.yml`, e `8080` é a porta interna do contêiner Java.*
