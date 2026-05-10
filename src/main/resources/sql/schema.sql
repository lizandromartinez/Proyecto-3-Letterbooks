-- 1. Creamos la base de datos.
DROP DATABASE IF EXISTS letterbooks;
CREATE DATABASE letterbooks;
USE letterbooks;

-- 2. Creamos tablas independientes.
CREATE TABLE Genero (
    id_genero INT AUTO_INCREMENT PRIMARY KEY,
    nombre_genero VARCHAR(100) NOT NULL
);

CREATE TABLE Autor (
    id_autor INT AUTO_INCREMENT PRIMARY KEY,
    nombre_autor VARCHAR(150) NOT NULL
);

CREATE TABLE Editorial (
    id_editorial INT AUTO_INCREMENT PRIMARY KEY,
    nombre_editorial VARCHAR(150) NOT NULL
);

CREATE TABLE Tipo_Accion (
    id_accion INT AUTO_INCREMENT PRIMARY KEY,
    accion VARCHAR(50) NOT NULL 
);

-- 3. Tablas Usuario
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(150) UNIQUE NOT NULL,
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL, 
    rol ENUM('admin', 'usuario') DEFAULT 'usuario'
);

-- 4. Tabla Libro
CREATE TABLE Libro (
    id_libro INT AUTO_INCREMENT PRIMARY KEY,
    id_genero INT,
    id_autor INT,
    id_editorial INT,
    titulo VARCHAR(255) NOT NULL,
    sinopsis TEXT,
    imagen VARCHAR(255),	
    paginas INT,
    ano INT,
    isbn VARCHAR(20),
    promedio_calificacion DECIMAL(3,2) DEFAULT 0.00,
    FOREIGN KEY (id_genero) REFERENCES Genero(id_genero),
    FOREIGN KEY (id_autor) REFERENCES Autor(id_autor),
    FOREIGN KEY (id_editorial) REFERENCES Editorial(id_editorial)
);

-- 5. Tabla Info_Usuario
CREATE TABLE Info_Usuario (
    id_info INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_autor INT,
    id_genero INT, 
    id_libro INT,  
    biografia TEXT,
    avatar VARCHAR(255),
    banner VARCHAR(255),
    reportes INT DEFAULT 0,
    fecha_registro VARCHAR(10),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_autor) REFERENCES Autor(id_autor),
    FOREIGN KEY (id_genero) REFERENCES Genero(id_genero),
    FOREIGN KEY (id_libro) REFERENCES Libro(id_libro)
);

-- 6. Tabla Reseña
CREATE TABLE Resena (
    id_resena INT AUTO_INCREMENT PRIMARY KEY,
    id_libro INT NOT NULL,
    id_usuario INT NOT NULL,
    calificacion TINYINT,
    likes INT DEFAULT 0,
    reportes INT DEFAULT 0,
    texto_resena TEXT,
    fecha_publicacion VARCHAR(10),
    FOREIGN KEY (id_libro) REFERENCES Libro(id_libro),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- 7. Tabla Cita
CREATE TABLE Cita (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    id_resena INT NOT NULL,
    texto TEXT NOT NULL,
    FOREIGN KEY (id_resena) REFERENCES Resena(id_resena) 
);

-- 8. Tabla Comentario
CREATE TABLE Comentario (
    id_comentario INT AUTO_INCREMENT PRIMARY KEY,
    id_resena INT NOT NULL,
    id_usuario INT NOT NULL,
    texto TEXT NOT NULL,
    reportes INT DEFAULT 0,
    FOREIGN KEY (id_resena) REFERENCES Resena(id_resena),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- 9. Tablas de Likes
CREATE TABLE Likes_Resena (
    id_like INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_resena INT NOT NULL,
    fecha VARCHAR(10),
    UNIQUE(id_usuario, id_resena), 
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_resena) REFERENCES Resena(id_resena)
);

CREATE TABLE Likes_Cita (
    id_like INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_cita INT NOT NULL,
    fecha VARCHAR(10),	
    UNIQUE(id_usuario, id_cita),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_cita) REFERENCES Cita(id_cita) 
);

CREATE TABLE Likes_Comentario (
    id_like INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_comentario INT NOT NULL,
    fecha VARCHAR(10),
    UNIQUE(id_usuario, id_comentario),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_comentario) REFERENCES Comentario(id_comentario)
);

-- 10. Tabla Logs
CREATE TABLE Logs (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_tipo_accion INT,
    tipo_entidad VARCHAR(50),
    id_entidad INT, 
    fecha VARCHAR(10),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_tipo_accion) REFERENCES Tipo_Accion(id_accion)
);


