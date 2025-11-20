# 🚀 v2_ArqWeb: Aplicação Full-Stack com Monitoramento

Este repositório contém uma aplicação full-stack composta por um backend em **Java/Spring Boot** e um frontend em **React/Vite**, orquestrados via **Docker Compose** juntamente com uma stack de monitoramento (**Prometheus** e **Grafana**).

## 🛠️ Stack Tecnológica

| Componente | Tecnologia | Porta Host | URL de Acesso |
| :--- | :--- | :--- | :--- |
| **Backend** | Java 21 / Spring Boot | `8081` | `http://localhost:8081` |
| **Frontend** | React / Vite / Nginx | `3001` | `http://localhost:3001` |
| **Monitoramento** | Prometheus | `9090` | `http://localhost:9090` |
| **Visualização** | Grafana | `3000` | `http://localhost:3000` |

## 🐳 Pré-requisitos

Para executar o projeto, você precisa ter instalado:

1.  **Docker:** (Incluindo o Docker Compose)

## ⚡ Inicialização Rápida

Na pasta raiz do projeto, execute o comando abaixo para construir as imagens e iniciar todos os quatro serviços na rede `monitoring`:

```bash
docker compose up --build -d


Após a Inicialização:

Aplicação Web: Acesse http://localhost:3001

Dashboard Grafana: Acesse http://localhost:3000

Métricas Prometheus: Acesse http://localhost:9090

Para derrubar e remover os contêineres e a rede:

docker compose down