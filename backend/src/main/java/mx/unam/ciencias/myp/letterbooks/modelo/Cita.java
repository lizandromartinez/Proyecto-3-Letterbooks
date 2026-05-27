package mx.unam.ciencias.myp.letterbooks.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Clase que representa una cita en el sistema.
 * Mapea la tabla "cita" de la base de datos MariaDB.
 */
@Entity
@Table(name = "cita")
public class Cita {

    /* ID único de la cita. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer idCita;

    /* Reseña asociada a la cita. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_resena", nullable = false)
    @JsonIgnore
    private Resena resena;

    /* Texto contenido en la cita. */
    @Column(name = "texto", nullable = false, columnDefinition = "TEXT")
    private String texto;

    /* Página del libro donde se encuentra la cita (opcional). */
    @Column(name = "pagina")
    private Integer pagina;

    /* Cantidad de 'me gusta' que ha recibido la cita. */
    @Column(name = "likes")
    private Integer likes = 0;
    
    /**
     * Obtiene el id único de la cita.
     * @return idCita el id.
     */
    public Integer getIdCita() {
        return idCita;
    }

    /**
     * Define el id único de la cita.
     * @param idCita id de la cita.
     */
    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    /**
     * Obtiene la reseña asociada a la cita.
     * @return resena asociada a la cita.
     */
    public Resena getResena() {
        return resena;
    }

    /**
     * Define la reseña asociada a la cita.
     * @param resena reseña asociada.
     */
    public void setResena(Resena resena) {
        this.resena = resena;
    }

    /**
     * Obtiene el texto de la cita.
     * @return texto contenido en la cita.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Define el texto de la cita.
     * @param texto texto de la cita.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Obtiene la página de la cita.
     * @return pagina número de página.
     */
    public Integer getPagina() {
        return pagina;
    }

    /**
     * Define la página de la cita.
     * @param pagina número de página.
     */
    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }

    /**
     * Obtiene el conteo de likes de la cita.
     * @return likes cantidad de likes.
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * Define el conteo de likes de la cita.
     * @param likes cantidad de likes.
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
