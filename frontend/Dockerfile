# Etapa 1: Build
FROM node:22-alpine AS build


WORKDIR /app

# Copiar package.json e instalar dependências
COPY package.json package-lock.json ./
RUN npm install

# Copiar o restante do código
COPY . .

# Build do projeto (gera pasta dist/)
RUN npm run build

# Etapa 2: Servir com Nginx
FROM nginx:alpine

# Remover configuração default
RUN rm -rf /usr/share/nginx/html/*

# Copiar arquivos gerados pelo Vite
COPY --from=build /app/dist /usr/share/nginx/html

# Copiar configuração customizada do Nginx (opcional)
# COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
