package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase que representa la tabla {@code editorial} en la base de datos.
 * Gestiona las casas editoras encargadas de publicar los libros.
 */
@Entity
@Table(name = "editorial")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @NotBlank(message = "El nombre de la editorial no puede estar vacío")
    @Size(max = 150, message = "El nombre de la editorial no puede superar los 150 caracteres")
    @Column(name = "nombre_editorial", nullable = false, length = 150)
    private String nombreEditorial;

    @JsonIgnore
    @OneToMany(mappedBy = "editorial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libros = new ArrayList<>();

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
        return this.idEditorial;
    }

    /**
     * Define el identificador único de la editorial.
     * @param idEditorial El nuevo ID de la editorial.
     */
    public void setIdEditorial(Integer idEditorial) {
        this.idEditorial = idEditorial;
    }

    /**
     * Regresa el nombre de la editorial.
     * @return El nombre de la editorial que publicó el libro.
     */
    public String getNombreEditorial() {
        return this.nombreEditorial;
    }

    /**
     * Define el nombre de la editorial.
     * @param nombreEditorial Nombre de la editorial.
     */
    public void setNombreEditorial(String nombreEditorial) {
        this.nombreEditorial = nombreEditorial;
    }

    public List<Libro> getLibros() {
        return this.libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
