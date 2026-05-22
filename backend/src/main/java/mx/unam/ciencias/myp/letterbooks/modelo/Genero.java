import jakarta.persistence.*;

/**
 * Clase que representa la tabla {@code genero} en la base de datos.
 * Define las categorías literarias a las que pueden pertenecer los libros.
 * Mantiene una relación de uno a muchos con la clase {@link Libro}.
 */
@Entity
@Table(name = "genero")
public class Genero {

    /**
     * Identificador único del género.
     * Mapea a la llave primaria {@code id_genero}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Integer idGenero;

    /**
     * Nombre del género.
     * Este campo es obligatorio.
     */
    @Column(name = "nombre_genero", nullable = false, length = 100)
    private String nombreGenero;


    /**
     * Constructor.
     */
    public Genero() {
    }

    /**
     * Regresa el identificador único del genero en la base de datos.
     * @return El ID del genero.
     */
    public Integer getIdGenero() {

    }

    /**
     * Asigna el identificador único del genero.
     * @param idGenero El nuevo ID del genero.
     */
    public void setIdGenero(Integer idGenero) {

    }

    /**
     * Regresa el nombre del género.
     * @return El nombre del libro.
     */
    public String getNombreGenero() {

    }

    /**
     * Define el nombre del género.
     * @param nombreGenero Nombre del género.
     */
    public void setNombreGenero(String nombreGenero) {

    }

}
