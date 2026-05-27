package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase que representa la tabla {@code autor} en la base de datos.
 * Almacena los datos de los escritores de las obras. Mantiene una relación 
 * bidireccional de uno a muchos con la clase {@link Libro}.
 */
@Entity
@Table(name = "autor")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    /**
     * Breve biografía del autor. 
     */
    @Column(columnDefinition = "TEXT")
    private String biografia;

    /**
     * Fecha de nacimiento del autor en formato YYYY-MM-DD.
     */
    @Column(name = "fecha_nacimiento", length = 10)
    private String fechaNacimiento;

    /**
     * Ruta de la foto del autor.
     */
    @Size(max = 255, message = "La ruta de la foto del autor no puede superar los 255 caracteres.")
    @Column(name = "foto", length = 255)
    private String foto;

    @JsonIgnore
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libros = new ArrayList<>();

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

    /**
     * Regresa la biografía del autor.
     * @return La biografía detallada del autor.
     */
    public String getBiografia() {
        return this.biografia;
    }

    /**
     * Define la biografía del autor.
     * @param biografia Biografía del autor.
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    /**
     * Regresa la fecha de nacimiento del autor.
     * @return La fecha de nacimiento en formato YYYY-MM-DD.
     */
    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    /**
     * Define la fecha de nacimiento del autor.
     * @param fechaNacimiento Fecha de nacimiento (YYYY-MM-DD).
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Regresa la ruta de la fotografía del autor.
     * @return La URL o ruta interna de la foto.
     */
    public String getFoto() {
        return this.foto;
    }

    /**
     * Define la ruta de la fotografía del autor.
     * @param foto URL o ruta interna de la foto.
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * Regresa la colección de libros asociados a este autor.
     * @return Lista de instancias de {@link Libro}.
     */
    public List<Libro> getLibros() {
        return this.libros;
    }

    /**
     * Define la colección de libros asociados a este autor.
     * @param libros Lista de libros.
     */
    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
