package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;

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
    @NotBlank(message = "El nombre del autor no puede estar vacío")
    @Size(max = 150, message = "El nombre del autor no puede superar los 150 caracteres")
    @Column(name = "nombre_autor", nullable = false, length = 150)
    private String nombreAutor;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<Libro> libros = new ArrayList<>();


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
        return this.idAutor;
    }

    /**
     * Define el identificador único del autor.
     * @param idAutor El nuevo ID del autor.
     */
    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    /**
     * Regresa el nombre del autor.
     * @return El nombre del autor del libro.
     */
    public String getNombreAutor() {
        return this.nombreAutor;
    }

    /**
     * Define el nombre del autor.
     * @param nombreAutor Nombre del autor.
     */
    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public ArrayList<Libro> getLibros() {
        return this.libros;
    }

    public void setLibros(ArrayList<Libro> libros) {
        this.libros = libros;
    }

}
