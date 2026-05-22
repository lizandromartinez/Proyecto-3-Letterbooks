import jakarta.persistence.*;

/**
 * Clase que representa la tabla {@code editorial} en la base de datos.
 * Gestiona las casas editoras encargadas de publicar los libros.
 */
@Entity
@Table(name = "editorial")
public class Editorial {

    /**
     * Identificador único de la editorial. Mapea a la llave primaria {@code id_editorial}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_editorial")
    private Integer idEditorial;

    /**
     * Nombre de la casa editorial. Este campo es obligatorio.
     */
    @Column(name = "nombre_editorial", nullable = false, length = 150)
    private String nombreEditorial;

    /**
     * Constructor.
     */
    public Editorial() {
    }

    /**
     * Regresa el identificador único de la editorial en la base de datos.
     * @return El ID de la editorial.
     */
    public Integer getIdEditorial() {

    }

    /**
     * Define el identificador único de la editorial.
     * @param idEditorial El nuevo ID de la editorial.
     */
    public void setIdEditorial(Integer idEditorial) {

    }

    /**
     * Regresa el nombre de la editorial.
     * @return El nombre de la editorial que publicó el libro.
     */
    public String getNombreEditorial() {

    }

    /**
     * Define el nombre de la editorial.
     * @param nombreEditorial Nombre de la editorial.
     */
    public void setNombreEditorial(String nombreEditorial) {

    }

}
