package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa un género en el sistema.
 * Mapea la tabla "genero" de la base de datos MariaDB.
 */
@Entity
@Table(name = "genero")
public class Genero {

    /* ID único del género. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Integer idGenero;
    
    /* Nombre del género. */
    @Column(name = "nombre_genero", nullable = false, length = 100)
    private String nombreGenero;

    /**
     * Obtiene el id único del género.
     * @return idGenero id entero.
     */
    public Integer getIdGenero() {
	return idGenero;
    }

    /**
     * Define el id único del género.
     * @param idGenero id del género.
     */
    public void setIdGenero(Integer idGenero) {
	this.idGenero = idGenero;
    }

    /**
     * Obtiene el nombre del género.
     * @return nombreGenero nombre del género.
     */
    public String getNombreGenero() {
	return nombreGenero;
    }

    /**
     * Define el nombre del género.
     * @param nombreGenero nombre del género.
     */
    public void setNombreGenero(String nombreGenero) {
	this.nombreGenero = nombreGenero;
    }    
}
