import jakarta.persistence.*;

/**
 * Clase que representa la tabla {@code autor} en la base de datos.
 * Almacena los datos de los escritores de las obras. Mantiene una relación 
 * bidireccional de uno a muchos con la clase {@link Libro}.
 */
@Entity
@Table(name = "autor")
public class Autor {

    /**
     * Identificador único del autor. Mapea a la llave primaria {@code id_autor}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Integer idAutor;

    /**
     * Nombre completo del autor. Este campo es obligatorio.
     */
    @Column(name = "nombre_autor", nullable = false, length = 150)
    private String nombreAutor;


    /**
     * Constructor.
     */
    public Autor() {
    }

    /**
     * Regresa el identificador único del autor en la base de datos.
     * @return El ID del autor.
     */
    public Integer getIdAutor() {

    }

    /**
     * Define el identificador único del autor.
     * @param idAutor El nuevo ID del autor.
     */
    public void setIdAutor(Integer idAutor) {

    }

    /**
     * Regresa el nombre del autor.
     * @return El nombre del autor del libro.
     */
    public String getNombreAutor() {

    }

    /**
     * Define el nombre del autor.
     * @param nombreAutor Nombre del autor.
     */
    public void setNombreAutor(String nombreAutor) {

    }

}
