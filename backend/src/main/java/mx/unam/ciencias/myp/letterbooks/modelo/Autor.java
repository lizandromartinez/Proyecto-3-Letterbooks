package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa un autor en el sistema.
 * Mapea la tabla "autor" de la base de datos MariaDB.
 */
@Entity
@Table(name = "autor")
public class Autor {

    /* ID único del autor. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Integer idAutor;

    /* Nombre del autor. */
    @Column(name = "nombre_autor", nullable = false, length = 150)
    private String nombreAutor;

    /**
     * Obtiene el id único del autor.
     * @return idAutor id entero.
     */
    public Integer getIdAutor() {
	return idAutor;
    }

    /**
     * Define el id único del autor.
     * @param idAutor id del autor.
     */
    public void setIdAutor(Integer idAutor) {
	this.idAutor = idAutor;
    }

    /**
     * Obtiene el nombre del autor.
     * @return nombreAutor nombre del autor.
     */
    public String getNombreAutor() {
	return nombreAutor;
    }

    /**
     * Define el nombre del autor.
     * @param nombreAutor nombre del autor.
     */
    public void setNombreAutor(String nombreAutor) {
	this.nombreAutor = nombreAutor;
    }
}
