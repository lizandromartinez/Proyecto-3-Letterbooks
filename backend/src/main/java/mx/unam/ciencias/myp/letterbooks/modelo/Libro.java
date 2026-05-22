package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa a un libro en el sistema.
 * Mapea la tabla "libro" de la base de datos MariaDB.
 * Version auxiliar para reseñas
 */
@Entity
@Table(name = "libro")
public class Libro {

    /* ID único del libro. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Integer idLibro;

    /* Título del libro. */
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    /* Promedio de calificaciones del libro. */
    @Column(name = "promedio_calificacion", precision = 3, scale = 2)
    private Double promedioCalificacion = 0.00;
    
    /**
     * Obtiene el id único del libro.
     * @return idLibro id entero.
     */
    public Integer getIdLibro() {
        return idLibro;
    }

    /**
     * Define el id único del libro.
     * @param idLibro id del libro.
     */
    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    /**
     * Obtiene el título del libro.
     * @return titulo el título del libro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define el título del libro.
     * @param titulo el título del libro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el promedio de calificación del libro.
     * @return promedioCalificacion el promedio de la calificación.
     */
    public Double getPromedioCalificacion() {
        return promedioCalificacion;
    }

    /**
     * Define el promedio de calificación del libro.
     * @param promedioCalificacion el promedio de la calificación.
     */
    public void setPromedioCalificacion(Double promedioCalificacion) {
        this.promedioCalificacion = promedioCalificacion;
    }
}
