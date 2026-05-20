package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa un like dado a un comentario en el sistema.
 * Mapea la tabla "likes_comentario" de la base de datos MariaDB.
 */
@Entity
@Table(name = "likes_comentario")
public class LikesComentario {

    /* ID único del like. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_like")
    private Integer idLike;
    
    /* Usuario que realizó el like. */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /* Comentario asociado al like. */
    @ManyToOne
    @JoinColumn(name = "id_comentario", nullable = false)
    private Comentario comentario;
    
    /* Fecha en la que se realizó el like. */
    @Column(name = "fecha", length = 10)
    private String fecha;

    /**
     * Obtiene el id único del like.
     * @return idLike id entero.
     */
    public Integer getIdLike() {
	return idLike;
    }

    /**
     * Define el id único del like.
     * @param idLike id del like.
     */
    public void setIdLike(Integer idLike) {
	this.idLike = idLike;
    }

    /**
     * Obtiene el usuario que realizó el like.
     * @return usuario asociado al like.
     */
    public Usuario getUsuario() {
	return usuario;
    }

    /**
     * Define el usuario que realizó el like.
     * @param usuario usuario asociado.
     */
    public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
    }

    /**
     * Obtiene el comentario asociado al like.
     * @return comentario asociado al like.
     */
    public Comentario getComentario() {
	return comentario;
    }

    /**
     * Define el comentario asociado al like.
     * @param comentario comentario asociado.
     */
    public void setComentario(Comentario comentario) {
	this.comentario = comentario;
    }

    /**
     * Obtiene la fecha en la que se realizó el like.
     * @return fecha fecha del like.
     */
    public String getFecha() {
	return fecha;
    }

    /**
     * Define la fecha en la que se realizó el like.
     * @param fecha fecha del like.
     */
    public void setFecha(String fecha) {
	this.fecha = fecha;
    }
}
