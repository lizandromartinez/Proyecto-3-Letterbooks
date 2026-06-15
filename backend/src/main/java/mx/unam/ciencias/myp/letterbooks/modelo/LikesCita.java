package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa un 'me gusta' dado a una cita específica.
 * Mapea la tabla "likes_cita" de la base de datos MariaDB.
 */
@Entity
@Table(name = "likes_cita")
public class LikesCita {

    /* ID único del like. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_like")
    private Integer idLike;

    /* Usuario que dió el like. */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /* Cita que recibió el like. */
    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;

    /* Fecha en la que se otorgó el like. */
    @Column(name = "fecha", length = 10)
    private String fecha;

    /**
     * Obtiene el id del like.
     * @return idLike el id.
     */
    public Integer getIdLike() {
        return idLike;
    }

    /**
     * Define el id del like.
     * @param idLike el id.
     */
    public void setIdLike(Integer idLike) {
        this.idLike = idLike;
    }

    /**
     * Obtiene el usuario que dio el like.
     * @return usuario el usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Define el usuario que da el like.
     * @param usuario el usuario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la cita que recibió el like.
     * @return cita la cita.
     */
    public Cita getCita() {
        return cita;
    }

    /**
     * Define la cita que recibe el like.
     * @param cita la cita.
     */
    public void setCita(Cita cita) {
        this.cita = cita;
    }

    /**
     * Obtiene la fecha del like.
     * @return fecha en formato cadena.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Define la fecha del like.
     * @param fecha en formato cadena.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
