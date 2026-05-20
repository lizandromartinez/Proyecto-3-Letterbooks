package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa a una editorial en el sistema.
 * Mapea la tabla "editorial" de la base de datos MariaDB.
 */
@Entity
@Table(name = "editorial")
public class Editorial {

    /* ID único de la editorial. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_editorial")
    private Integer idEditorial;

    /* Nombre de la editorial. */
    @Column(name = "nombre_editorial", nullable = false, length = 150)
    private String nombreEditorial;
    
    /**
     * Obtiene el id único de la ediotorial.
     * @return idEditorial id entero.
     */
    public Integer getIdEditorial() {
	return idEditorial;
    }

    /**
     * Define el id único de la editorial.
     * @param idEditorial id del usuario.
     */
    public void setIdEditorial(Integer idEditorial) {
	this.idEditorial = idEditorial;
    }
  
    /**
     * Obtiene el nombre de la editorial.
     * @return nombreEditorial con el nombre de la Editorial.
     */
    public String getNombreEditorial() {
	return nombreEditorial;
    }
    
    /**
     * Define el nombre de la editorial.
     * @param nombreEditorial el nombre de la editorial.
     */
    public void setNombreEditorial(String nombreEditorial) {
	this.nombreEditorial = nombreEditorial;
    }    
}
