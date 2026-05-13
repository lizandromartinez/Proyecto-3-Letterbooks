# Etapa de compilación
FROM node:20 AS build
WORKDIR /app
COPY package*.json ./
# Instalar dependencias
RUN npm install
COPY . .
# Construir la aplicación para producción
RUN npm run build

# Etapa de servidor web para servir los archivos estáticos
FROM nginx:alpine
# Copiar los estáticos generados por Vite (por defecto van a /dist)
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
