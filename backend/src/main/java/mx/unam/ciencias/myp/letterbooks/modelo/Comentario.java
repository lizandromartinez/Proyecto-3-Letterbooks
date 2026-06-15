package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa un comentario en el sistema.
 * Mapea la tabla "comentario" de la base de datos MariaDB.
 */
@Entity
@Table(name = "comentario")
public class Comentario {

    /* ID único del comentario. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Integer idComentario;
    
    /* Reseña asociada al comentario. */
    @ManyToOne
    @JoinColumn(name = "id_resena", nullable = false)
    private Resena resena;

    /* Usuario que realizó el comentario. */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    /* Texto contenido en el comentario. */
    @Column(name = "texto", nullable = false, columnDefinition = "TEXT")
    private String texto;

    /* Cantidad de reportes del comentario. */
    @Column(name = "reportes", columnDefinition = "INT DEFAULT 0")
    private Integer reportes = 0;

    /**
     * Obtiene el id único del comentario.
     * @return idComentario id entero.
     */
    public Integer getIdComentario() {
	return idComentario;
    }

    /**
     * Define el id único del comentario.
     * @param idComentario id del comentario.
     */
    public void setIdComentario(Integer idComentario) {
	this.idComentario = idComentario;
    }
    
    /**
     * Obtiene la reseña asociada al comentario.
     * @return resena asociada al comentario.
     */
    public Resena getResena() {
	return resena;
    }
    
    /**
     * Define la reseña asociada al comentario.
     * @param resena reseña asociada.
     */
    public void setResena(Resena resena) {
	this.resena = resena;
    }

    /**
     * Obtiene el usuario que realizó el comentario.
     * @return usuario asociado al comentario.
     */
    public Usuario getUsuario() {
	return usuario;
    }

    /**
     * Define el usuario que realizó el comentario.
     * @param usuario usuario asociado.
     */
    public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
    }
    
    /**
     * Obtiene el texto contenido en el comentario.
     * @return texto texto del comentario.
     */
    public String getTexto() {
	return texto;
    }

    /**
     * Define el texto contenido en el comentario.
     * @param texto texto del comentario.
     */
    public void setTexto(String texto) {
	this.texto = texto;
    }

    /**
     * Obtiene la cantidad de reportes del comentario.
     * @return reportes cantidad de reportes.
     */
    public Integer getReportes() {
	return reportes;
    }

    /**
     * Define la cantidad de reportes del comentario.
     * @param reportes cantidad de reportes.
     */
    public void setReportes(Integer reportes) {
	this.reportes = reportes;
    }    
}
