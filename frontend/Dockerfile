# --- ETAPA 1: Construcción ---
FROM node:20-alpine AS build

WORKDIR /app

# Copiar archivos de dependencias primero para aprovechar el cache de Docker
COPY package*.json ./
RUN npm install

# Copiar el resto del código
COPY . .

# Generar la carpeta /dist con los archivos optimizados
RUN npm run build

# --- ETAPA 2: Servidor de Producción ---
FROM nginx:stable-alpine

# Copiar los archivos compilados desde la etapa anterior a la carpeta de Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Copiar una configuración personalizada de Nginx (opcional pero recomendado)
# COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]