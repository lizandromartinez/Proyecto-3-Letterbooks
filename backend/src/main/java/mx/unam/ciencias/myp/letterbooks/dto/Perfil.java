package mx.unam.ciencias.myp.letterbooks.dto;

import java.util.List;

/**
 * DTO para la transferencia de datos del perfil de un usuario.
 * <p>
 * Contiene la información pública del perfil incluyendo preferencias,
 * actividad y estadísticas del usuario.
 * </p>
 */
public class Perfil {

    /* Nombre de usuario del Usuario asociado al perfil. */
    private String nombreUsuario;
    
    /* Biografía del usuario. */
    private String biografia;

    /* URL del avatar del usuario. */
    private String avatar;

    /* URL del banner del usuario. */
    private String banner;

    /* Fecha de registro del usuario. */
    private String fechaRegistro;

    /* Id del libro favorito del usuario. */
    private Integer idLibro;

    /* Id del autor favorito del usuario. */
    private Integer idAutor;

    /* Id del género favorito del usuario. */
    private Integer idGenero;

    /** Nombre del libro favorito del usuario. */
    private String libroFavorito;

    /* Nombre del autor favorito del usuario. */
    private String autorFavorito;

    /* Nombre del género favorito del usuario. */
    private String generoFavorito;
    
    /* Reseñas a las que el usuario ha dado like. */
    private List<ResenaLikeDTO> resenasLikeadas;

    /* Reseñas que el usuario ha calificado. */
    private List<ResenaCalificadaDTO> resenasCalificadas;

    /* Libros que el usuario ha calificado a través de sus reseñas. */
    private List<LibroCalificadoDTO> librosCalificados;

    /* Comentarios a los que el usuario ha dado like. */
    private List<ComentarioLikeDTO> comentariosLikeados;

    /**
     * DTO interno que representa una reseña likeada por el usuario.
     */
    public static class ResenaLikeDTO {

        /* Identificador de la reseña. */
        private Integer idResena;

        /* Título del libro de la reseña. */
        private String tituloLibro;

        /* Nombre del usuario que escribió la reseña. */
        private String autorResena;

        /* Texto de la reseña. */
        private String textoResena;

        /* Fecha en que el usuario dio like. */
        private String fechaLike;

	/* ID de la reseña */
        public Integer getIdResena() { return idResena; }

	/* Asigna ID de la reseña */
        public void setIdResena(Integer idResena) { this.idResena = idResena; }

	/* Título del libro */
        public String getTituloLibro() { return tituloLibro; }

	/* Asigna título del libro */
        public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }

	/* Autor de la reseña */
        public String getAutorResena() { return autorResena; }

	/* Asigna autor de la reseña */
        public void setAutorResena(String autorResena) { this.autorResena = autorResena; }

	/* Texto de la reseña */
        public String getTextoResena() { return textoResena; }

	/* Asigna texto de la reseña */
        public void setTextoResena(String textoResena) { this.textoResena = textoResena; }

	/* Fecha del like */
        public String getFechaLike() { return fechaLike; }

	/* Asigna fecha del like */
        public void setFechaLike(String fechaLike) { this.fechaLike = fechaLike; }
    }

    /**
     * DTO interno que representa una reseña calificada por el usuario.
     */
    public static class ResenaCalificadaDTO {

        /* Identificador de la reseña. */
        private Integer idResena;

        /* Título del libro de la reseña. */
        private String tituloLibro;

        /* Nombre del usuario que escribió la reseña. */
        private String autorResena;

        /* Texto de la reseña. */
        private String textoResena;

        /* Calificación otorgada por el usuario a la reseña. */
        private Byte calificacion;

        /* Fecha en que el usuario calificó la reseña. */
        private String fechaCalificacion;

	/* ID de la reseña */
        public Integer getIdResena() { return idResena; }

	/* Asigna ID de la reseña */
        public void setIdResena(Integer idResena) { this.idResena = idResena; }

	/* Título del libro */
        public String getTituloLibro() { return tituloLibro; }

	/* Asigna título del libro */
        public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }

	/* Autor de la reseña */
        public String getAutorResena() { return autorResena; }

	/* Asigna autor de la reseña */
        public void setAutorResena(String autorResena) { this.autorResena = autorResena; }

	/* Texto de la reseña */
        public String getTextoResena() { return textoResena; }

	/* Asigna texto de la reseña */
        public void setTextoResena(String textoResena) { this.textoResena = textoResena; }

	/* Calificación de la reseña */
        public Byte getCalificacion() { return calificacion; }

	/* Asigna calificación a la reseña */
        public void setCalificacion(Byte calificacion) { this.calificacion = calificacion; }

	/* Fecha de calificacion de la reseña */
        public String getFechaCalificacion() { return fechaCalificacion; }

	/* Asigna la fecha de calificación de la reseña */
        public void setFechaCalificacion(String fechaCalificacion) { this.fechaCalificacion = fechaCalificacion; }
    }

    /**
     * DTO interno que representa un libro calificado por el usuario a través de una reseña.
     */
    public static class LibroCalificadoDTO {

        /* Identificador del libro. */
        private Integer idLibro;

        /* Título del libro. */
        private String titulo;

        /* Nombre del autor del libro. */
        private String autor;

        /* URL de la imagen de portada del libro. */
        private String imagen;

        /* Calificación otorgada por el usuario al libro en su reseña. */
        private Byte calificacion;

	/* Id del libro calificado. */
        public Integer getIdLibro() { return idLibro; }

	/* Asigna id del libro calificado. */
        public void setIdLibro(Integer idLibro) { this.idLibro = idLibro; }

	/* Título del libro calificado. */
        public String getTitulo() { return titulo; }

	/* Asigna título del libro calificado. */
        public void setTitulo(String titulo) { this.titulo = titulo; }

	/* Autor del libro calificado. */
        public String getAutor() { return autor; }

	/* Asigna autor del libro calificado. */
        public void setAutor(String autor) { this.autor = autor; }

	/* Imagen libro calificado. */
        public String getImagen() { return imagen; }

	/* Asigna imagen del libro calificado. */
        public void setImagen(String imagen) { this.imagen = imagen; }

	/* Calificación del libro calificado. */
        public Byte getCalificacion() { return calificacion; }

	/* Asigna calificación del libro calificado. */
        public void setCalificacion(Byte calificacion) { this.calificacion = calificacion; }
    }

    /**
     * DTO interno que representa un comentario likeado por el usuario.
     */
    public static class ComentarioLikeDTO {

        /* Identificador del comentario. */
        private Integer idComentario;

        /* Texto del comentario. */
        private String texto;

        /* Nombre del usuario que escribió el comentario. */
        private String autorComentario;

        /* Título del libro de la reseña a la que pertenece el comentario. */
        private String tituloLibro;

        /* Fecha en que el usuario dio like al comentario. */
        private String fechaLike;

	/* Id del comentario likeado. */
        public Integer getIdComentario() { return idComentario; }

	/* Asigna Id del comentario likeado. */
        public void setIdComentario(Integer idComentario) { this.idComentario = idComentario; }

	/* Texto del comentario likeado. */
        public String getTexto() { return texto; }

	/* Asigna el texto del comentario likeado. */
        public void setTexto(String texto) { this.texto = texto; }

	/* Autordel comentario likeado. */
        public String getAutorComentario() { return autorComentario; }

	/* Asigna el autor del comentario likeado. */
        public void setAutorComentario(String autorComentario) { this.autorComentario = autorComentario; }

	/* Titulo del libro del comentario likeado. */
        public String getTituloLibro() { return tituloLibro; }

	/* Asigna Titulo del libro del comentario likeado. */
        public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }

	/* Fecha del like del comentario. */
        public String getFechaLike() { return fechaLike; }

	/* Asigna Fecha del like del comentario. */
        public void setFechaLike(String fechaLike) { this.fechaLike = fechaLike; }
    }

    /* Nombre de usuario del usuario asociado al perfil. */
    public String getNombreUsuario() { return nombreUsuario; }

    /* Asigna el nombre de usuario del usuario asociado al perfil. */
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    
    /* Biografía del usuario */
    public String getBiografia() { return biografia; }

    /* Asigna biografía */
    public void setBiografia(String biografia) { this.biografia = biografia; } 

    /* Avatar del usuario */
    public String getAvatar() { return avatar; }

    /* Asigna avatar */
    public void setAvatar(String avatar) { this.avatar = avatar; } 

    /* Banner del usuario */
    public String getBanner() { return banner; }

    /* Asigna banner */
    public void setBanner(String banner) { this.banner = banner; } 

    /* Fecha de registro */
    public String getFechaRegistro() { return fechaRegistro; }

    /* Asigna fecha de registro */
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    /* Id del autor favorito */
    public Integer getIdAutor() { return idAutor; }

    /* Asigna id del autor favorito */
    public void setIdAutor(Integer idAutor) { this.idAutor = idAutor; } 

    /* Id del género favorito */
    public Integer getIdGenero() { return idGenero; }

    /* Asigna el id del género favorito */
    public void setIdGenero(Integer idGenero) { this.idGenero = idGenero; }

    /* Id del libro favorito */
    public Integer getIdLibro() { return idLibro; }

    /* Asigna el id del libro favorito */
    public void setIdLibro(Integer idLibro) { this.idLibro = idLibro; }

    /* Obtiene el nombre del libro favorito del usuario. */
    public String getLibroFavorito() { return libroFavorito; }
    
    /* Establece el nombre del libro favorito del usuario. */
    public void setLibroFavorito(String libroFavorito) { this.libroFavorito = libroFavorito; }

    /* Obtiene el nombre del autor favorito del usuario. */
    public String getAutorFavorito() { return autorFavorito; }

    /* Establece el nombre del autor favorito del usuario. */
    public void setAutorFavorito(String autorFavorito) { this.autorFavorito = autorFavorito; }

    /* Obtiene el género favorito del usuario. */
    public String getGeneroFavorito() { return generoFavorito; }

    /* Establece el género favorito del usuario. */
    public void setGeneroFavorito(String generoFavorito) { this.generoFavorito = generoFavorito; }
    
    /* Reseñas likeadas */
    public List<ResenaLikeDTO> getResenasLikeadas() { return resenasLikeadas; }

    /* Asigna reseñas likeadas */
    public void setResenasLikeadas(List<ResenaLikeDTO> resenasLikeadas) { this.resenasLikeadas = resenasLikeadas; }

    /* Reseñas calificadas */
    public List<ResenaCalificadaDTO> getResenasCalificadas() { return resenasCalificadas; }

    /* Asigna reseñas calificadas */
    public void setResenasCalificadas(List<ResenaCalificadaDTO> resenasCalificadas) { this.resenasCalificadas = resenasCalificadas; }

    /* Libros calificados */
    public List<LibroCalificadoDTO> getLibrosCalificados() { return librosCalificados; }

    /* Asigna libros calificados */
    public void setLibrosCalificados(List<LibroCalificadoDTO> librosCalificados) { this.librosCalificados = librosCalificados; } 

    /* Comentarios likeados */
    public List<ComentarioLikeDTO> getComentariosLikeados() { return comentariosLikeados; }

    /* Asigna comentarios likeados */
    public void setComentariosLikeados(List<ComentarioLikeDTO> comentariosLikeados) { this.comentariosLikeados = comentariosLikeados; } 
}
