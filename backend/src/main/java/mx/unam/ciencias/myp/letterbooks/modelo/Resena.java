package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa una reseña de un libro escrita por un usuario.
 * Mapea la tabla "resena" de la base de datos MariaDB.
 */
@Entity
@Table(name = "resena")
public class Resena {

    /* ID único de la reseña. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resena")
    private Integer idResena;

    /* Libro al que pertenece la reseña. */
    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    /* Usuario autor de la reseña. */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /* Calificación que el usuario le da al libro. */
    @Column(name = "calificacionLibro")
    private Integer calificacionLibro;

    /* Calificación promedio de la reseña (dada por otros usuarios). */
    @Column(name = "calificacionResena")
    private Integer calificacionResena;

    /* Cantidad de 'me gusta' recibidos. */
    @Column(name = "likes")
    private Integer likes = 0;

    /* Cantidad de reportes por infracciones. */
    @Column(name = "reportes")
    private Integer reportes = 0;

    /* Cuerpo/texto de la reseña. */
    @Column(name = "texto_resena", columnDefinition = "TEXT")
    private String textoResena;

    /* Fecha de publicación almacenada como texto (YYYY-MM-DD). */
    @Column(name = "fecha_publicacion", length = 10)
    private String fechaPublicacion;

    /**
     * Obtiene el id único de la reseña.
     * @return idResena id de la reseña.
     */
    public Integer getIdResena() {
        return idResena;
    }

    /**
     * Define el id único de la reseña.
     * @param idResena id de la reseña.
     */
    public void setIdResena(Integer idResena) {
        this.idResena = idResena;
    }

    /**
     * Obtiene el libro reseñado.
     * @return libro el Libro.
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * Define el libro que se está reseñando.
     * @param libro el Libro.
     */
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    /**
     * Obtiene el usuario que escribió la reseña.
     * @return usuario el Usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define el usuario autor de la reseña.
     * @param usuario el Usuario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la calificación otorgada al libro.
     * @return calificacionLibro la calificación del libro.
     */
    public Integer getCalificacionLibro() {
        return calificacionLibro;
    }

    /**
     * Define la calificación otorgada al libro.
     * @param calificacionLibro la calificación.
     */
    public void setCalificacionLibro(Integer calificacionLibro) {
        this.calificacionLibro = calificacionLibro;
    }

    /**
     * Obtiene la calificación general que tiene la reseña.
     * @return calificacionResena la calificación de la reseña.
     */
    public Integer getCalificacionResena() {
        return calificacionResena;
    }

    /**
     * Define la calificación de la reseña.
     * @param calificacionResena la calificación.
     */
    public void setCalificacionResena(Integer calificacionResena) {
        this.calificacionResena = calificacionResena;
    }

    /**
     * Obtiene la cantidad de 'me gusta' de la reseña.
     * @return likes número de likes.
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * Define la cantidad de 'me gusta' de la reseña.
     * @param likes número de likes.
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * Obtiene la cantidad de reportes de la reseña.
     * @return reportes número de reportes.
     */
    public Integer getReportes() {
        return reportes;
    }

    /**
     * Define la cantidad de reportes de la reseña.
     * @param reportes número de reportes.
     */
    public void setReportes(Integer reportes) {
        this.reportes = reportes;
    }

    /**
     * Obtiene el texto de la reseña.
     * @return textoResena la reseña.
     */
    public String getTextoResena() {
        return textoResena;
    }

    /**
     * Define el texto de la reseña.
     * @param textoResena la reseña.
     */
    public void setTextoResena(String textoResena) {
        this.textoResena = textoResena;
    }

    /**
     * Obtiene la fecha de publicación.
     * @return fechaPublicacion la fecha en formato YYYY-MM-DD.
     */
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Define la fecha de publicación de la reseña.
     * @param fechaPublicacion la fecha en formato YYYY-MM-DD.
     */
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}
