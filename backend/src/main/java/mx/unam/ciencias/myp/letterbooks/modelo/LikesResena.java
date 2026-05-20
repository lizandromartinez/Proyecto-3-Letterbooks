package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa un like dado a una reseña en el sistema.
 * Mapea la tabla "likes_resena" de la base de datos MariaDB.
 */
@Entity
@Table(name = "likes_resena")
public class LikesResena {

    /* ID único del like. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_like")
    private Integer idLike;

    /* Usuario que realizó el like. */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /* Reseña asociada al like. */
    @ManyToOne
    @JoinColumn(name = "id_resena", nullable = false)
    private Resena resena;

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
     * Obtiene la reseña asociada al like.
     * @return resena asociada al like.
     */
    public Resena getResena() {
	return resena;
    }

    /**
     * Define la reseña asociada al like.
     * @param resena reseña asociada.
     */
    public void setResena(Resena resena) {
	this.resena = resena;
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
