package mx.unam.ciencias.myp.letterbooks.modelo;

import java.util.List;
import jakarta.persistence.*;

/**
 * Clase que representa una reseña en el sistema.
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

    /* Libro asociado a la reseña. */
    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    /* Usuario que realizó la reseña. */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /* Calificación otorgada al libro. */
    @Column(name = "calificacionLibro")
    private Byte calificacionLibro;

    /* Calificación recibida por la reseña. */
    @Column(name = "calificacionResena")
    private Byte calificacionResena;

    /* Cantidad de likes de la reseña. */
    @Column(name = "likes", columnDefinition = "INT DEFAULT 0")
    private Integer likes = 0;

    /* Cantidad de reportes de la reseña. */
    @Column(name = "reportes", columnDefinition = "INT DEFAULT 0")
    private Integer reportes = 0;
    
    /* Texto contenido en la reseña. */
    @Column(name = "texto_resena", columnDefinition = "TEXT")
    private String textoResena;
    
    /* Fecha de publicación de la reseña. */
    @Column(name = "fecha_publicacion", length = 10)
    private String fechaPublicacion;

    /* Lista de comentarios asociados. */
    @OneToMany(mappedBy = "resena")
    private List<Comentario> comentarios;
    
    /**
     * Obtiene el id único de la reseña.
     * @return idResena id entero.
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
     * Obtiene el libro asociado a la reseña.
     * @return libro asociado a la reseña.
     */
    public Libro getLibro() {
	return libro;
    }

    /**
     * Define el libro asociado a la reseña.
     * @param libro libro asociado.
     */
    public void setLibro(Libro libro) {
	this.libro = libro;
    }

    /**
     * Obtiene el usuario que realizó la reseña.
     * @return usuario asociado a la reseña.
     */
    public Usuario getUsuario() {
	return usuario;
    }

    /**
     * Define el usuario que realizó la reseña.
     * @param usuario usuario asociado.
     */
    public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
    }
    
    /**
     * Obtiene la calificación otorgada al libro.
     * @return calificacionLibro calificación del libro.
     */
    public Byte getCalificacionLibro() {
	return calificacionLibro;
    }

    /**
     * Define la calificación otorgada al libro.
     * @param calificacionLibro calificación del libro.
     */
    public void setCalificacionLibro(Byte calificacionLibro) {
	this.calificacionLibro = calificacionLibro;
    }

    /**
     * Obtiene la calificación recibida por la reseña.
     * @return calificacionResena calificación de la reseña.
     */
    public Byte getCalificacionResena() {
	return calificacionResena;
    }

    /**
     * Define la calificación recibida por la reseña.
     * @param calificacionResena calificación de la reseña.
     */
    public void setCalificacionResena(Byte calificacionResena) {
	this.calificacionResena = calificacionResena;
    }

    /**
     * Obtiene la cantidad de likes de la reseña.
     * @return likes cantidad de likes.
     */
    public Integer getLikes() {
	return likes;
    }

    /**
     * Define la cantidad de likes de la reseña.
     * @param likes cantidad de likes.
     */
    public void setLikes(Integer likes) {
	this.likes = likes;
    }

    /**
     * Obtiene la cantidad de reportes de la reseña.
     * @return reportes cantidad de reportes.
     */
    public Integer getReportes() {
	return reportes;
    }

    /**
     * Define la cantidad de reportes de la reseña.
     * @param reportes cantidad de reportes.
     */
    public void setReportes(Integer reportes) {
	this.reportes = reportes;
    }

    /**
     * Obtiene el texto contenido en la reseña.
     * @return textoResena texto de la reseña.
     */
    public String getTextoResena() {
	return textoResena;
    }

    /**
     * Define el texto contenido en la reseña.
     * @param textoResena texto de la reseña.
     */
    public void setTextoResena(String textoResena) {
	this.textoResena = textoResena;
    }

    /**
     * Obtiene la fecha de publicación de la reseña.
     * @return fechaPublicacion fecha de publicación.
     */
    public String getFechaPublicacion() {
	return fechaPublicacion;
    }

    /**
     * Define la fecha de publicación de la reseña.
     * @param fechaPublicacion fecha de publicación.
     */
    public void setFechaPublicacion(String fechaPublicacion) {
	this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Obtiene la lista de comentarios asociados.
     * @return comentarios lista de comentarios.
     */
    public List<Comentario> getComentarios() {
	return comentarios;
    }

    /**
     * Define la lista de comentarios asociados.
     * @param comentarios lista de comentarios.
     */
    public void setComentarios(List<Comentario> comentarios) {
	this.comentarios = comentarios;
    }
}
