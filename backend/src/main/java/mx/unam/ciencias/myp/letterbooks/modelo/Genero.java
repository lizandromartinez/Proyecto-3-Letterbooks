import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;

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
    @NotBlank(message = "El nombre del género no puede estar vacío")
    @Size(max = 100, message = "El nombre del género no puede superar los 100 caracteres")
    @Column(name = "nombre_genero", nullable = false, length = 100)
    private String nombreGenero;

    @OneToMany(mappedBy = "genero", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<Libro> libros = new ArrayList<>();


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
        return this.idGenero;
    }

    /**
     * Asigna el identificador único del genero.
     * @param idGenero El nuevo ID del genero.
     */
    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    /**
     * Regresa el nombre del género.
     * @return El nombre del libro.
     */
    public String getNombreGenero() {
        return this.nombreGenero;
    }

    /**
     * Define el nombre del género.
     * @param nombreGenero Nombre del género.
     */
    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    public ArrayList<Libro> getLibros() {
        return this.libros;
    }

    public void setLibros(ArrayList<Libro> libros) {
        this.libros = libros;
    }

}
