package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa una calificación realizada a una reseña en el sistema.
 * Mapea la tabla "calificacion_resena" de la base de datos MariaDB.
 */
@Entity
@Table(name = "calificacion_resena")
public class CalificacionResena {

    /* ID único de la calificación de reseña. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion_resena")
    private Integer idCalificacionResena;

    /* Usuario que realizó la calificación. */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /* Reseña asociada a la calificación. */
    @ManyToOne
    @JoinColumn(name = "id_resena", nullable = false)
    private Resena resena;

    /* Valor de la calificación otorgada. */
    @Column(name = "calificacion", nullable = false)
    private Byte calificacion;

    /* Fecha en la que se realizó la calificación. */
    @Column(name = "fecha", length = 10)
    private String fecha;

    /**
     * Obtiene el id único de la calificación de reseña.
     * @return idCalificacionResena id entero.
     */
    public Integer getIdCalificacionResena() {
	return idCalificacionResena;
    }

    /**
     * Define el id único de la calificación de reseña.
     * @param idCalificacionResena id de la calificación.
     */
    public void setIdCalificacionResena(Integer idCalificacionResena) {
	this.idCalificacionResena = idCalificacionResena;
    }

    /**
     * Obtiene el usuario que realizó la calificación.
     * @return usuario asociado a la calificación.
     */
    public Usuario getUsuario() {
	return usuario;
    }

    /**
     * Define el usuario que realizó la calificación.
     * @param usuario usuario asociado.
     */
    public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
    }

    /**
     * Obtiene la reseña asociada a la calificación.
     * @return resena asociada a la calificación.
     */
    public Resena getResena() {
	return resena;
    }

    /**
     * Define la reseña asociada a la calificación.
     * @param resena reseña asociada.
     */
    public void setResena(Resena resena) {
	this.resena = resena;
    }

    /**
     * Obtiene el valor de la calificación.
     * @return calificacion valor de la calificación.
     */
    public Byte getCalificacion() {
	return calificacion;
    }
    
    /**
     * Define el valor de la calificación.
     * @param calificacion valor de la calificación.
     */
    public void setCalificacion(Byte calificacion) {
	this.calificacion = calificacion;
    }

    /**
     * Obtiene la fecha en la que se realizó la calificación.
     * @return fecha fecha de la calificación.
     */
    public String getFecha() {
	return fecha;
    }

    /**
     * Define la fecha en la que se realizó la calificación.
     * @param fecha fecha de la calificación.
     */
    public void setFecha(String fecha) {
	this.fecha = fecha;
    }
}
