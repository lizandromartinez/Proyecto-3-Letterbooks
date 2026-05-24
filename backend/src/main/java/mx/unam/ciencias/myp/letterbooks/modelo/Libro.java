package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Clase que representa la tabla {@code libro} en la base de datos.
 * Esta clase mapea la información de los libros, incluyendo sus características
 * principales y sus relaciones con el género, autor y editorial mediante llaves foráneas.
 */
@Entity
@Table(name = "libro")
public class Libro {

    /**
     * Identificador único del libro. 
     * Mapea a la llave primaria autoincremental {@code id_libro}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Integer idLibro;

    /**
     * Género al que pertenece el libro.
     * Carga de tipo perezosa (LAZY).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_genero")
    private Genero genero;

    /**
     * Autor que escribió el libro.
     * Carga de tipo perezosa (LAZY).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Autor autor;

    /**
     * Editorial encargada de la publicación del libro.
     * Carga de tipo perezosa (LAZY).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_editorial")
    private Editorial editorial;

    /**
     * Título oficial del libro. Este campo es obligatorio.
     */
    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 255, message = "El título no puede superar los 255 caracteres")
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    /**
     * Resumen o sinopsis detallada del contenido del libro.
     */
    @Size(max = 10000, message = "La sinopsis supera el límite de caracteres permitido")
    @Column(name = "sinopsis", columnDefinition = "TEXT")
    private String sinopsis;

    /**
     * Ruta de la imagen de la portada del libro.
     */
    @Size(max = 255, message = "La ruta de la imagen no puede superar los 255 caracteres")
    @Column(name = "imagen", length = 255)
    private String imagen;

    /**
     * Cantidad total de páginas que componen al libro.
     */
    @Min(value = 1, message = "El libro debe tener al menos 1 página")
    @Column(name = "paginas")
    private Integer paginas;

    /**
     * Año de publicación del libro.
     */
    @Max(value = 2026, message = "El año de publicación no puede ser en el futuro")
    @Column(name = "ano")
    private Integer ano;

    /**
     * Código ISBN (International Standard Book Number) identificador del libro.
     */
    @Size(max = 20, message = "El ISBN no puede superar los 20 caracteres")
    @Column(name = "isbn", length = 20)
    private String isbn;

    /**
     * Contador de reportes o denuncias que ha recibido el libro.
     */
    @PositiveOrZero(message = "Los reportes no pueden ser un número negativo")
    @Column(name = "reportes")
    private Integer reportes = 0;

    /**
     * Calificación promedio asignada por los usuarios.
     * Mapeamos a Double en la entidad Java para mantener compatibilidad con las operaciones aritméticas de ResenaServicio.
     */
    @Column(name = "promedio_calificacion")
    private Double promedioCalificacion = 0.00;

    /**
     * Constructor de la clase.
     */
    public Libro() {
    }

    /**
     * Regresa el identificador único del libro en la base de datos.
     * @return El ID del libro.
     */
    public Integer getIdLibro() {
        return this.idLibro;
    }
    
    /**
     * Define el identificador único del libro.
     * @param idLibro El nuevo ID del libro.
     */
    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    /**
     * Regresa el género del libro.
     * @return El género del libro.
     */
    public Genero getGenero() {
        return this.genero;
    }

    /**
     * Define el género del libro.
     * @param genero Genero del libro.
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * Regresa el autor del libro.
     * @return El autor del libro.
     */
    public Autor getAutor() {
        return this.autor;
    }

    /**
     * Define el autor del libro.
     * @param autor Autor del libro.
     */
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    /**
     * Regresa la editorial asociada al libro.
     * @return La editorial del libro.
     */
    public Editorial getEditorial() {
        return this.editorial;
    }

    /**
     * Define la editorial del libro.
     * @param editorial Editorial del libro.
     */
    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    /**
     * Regresa el título del libro.
     * @return El título del libro.
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * Define el titulo del libro.
     * @param titulo Titulo del libro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Regresa la sinopsis del libro.
     * @return Texto con la sinopsis.
     */
    public String getSinopsis() {
        return this.sinopsis;
    }

    /**
     * Define la sinopsis del libro.
     * @param sinopsis Sinopsis del libro.
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    /**
     * Regresa la ruta de la imagen de portada del libro.
     * @return ruta con la ruta de la imagen.
     */
    public String getImagen() {
        return this.imagen;
    }

    /**
     * Define la ruta de imagen de portada del libro.
     * @param imagen Imagen de portada del libro.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Regresa el número de páginas del libro.
     * @return Cantidad de páginas del libro.
     */
    public Integer getPaginas() {
        return this.paginas;
    }

    /**
     * Define el numero de páginas del libro.
     * @param paginas Paginas del libro.
     */
    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }

    /**
     * Regresa el año de publicación del libro.
     * @return El año de publicación del libro.
     */
    public Integer getAno() {
        return this.ano;
    }

    /**
     * Define el año de publicación del libro.
     * @param ano Año de publicación del libro.
     */
    public void setAno(Integer ano) {
        this.ano = ano;
    }

    /**
     * Regresa ISBN del libro.
     * @return El código ISBN del libro.
     */    
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * Define el isbn del libro.
     * @param isbn ISBN del libro.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Regresa la cantidad de reportes que tiene el libro.
     * @return Número de reportes.
     */
    public Integer getReportes() {
        return this.reportes;
    }

    /**
     * Define los reportes del libro.
     * @param reportes Reportes que tiene el libro.
     */
    public void setReportes(Integer reportes) {
        this.reportes = reportes;
    }

    /**
     * Regresa la calificación promedio del libro.
     * @return El promedio de la calificación del libro.
     */
    public Double getPromedioCalificacion() {
        return this.promedioCalificacion;
    }

    /**
     * Define la calificación promedio del libro.
     * @param promedioCalificacion Calificacion del libro.
     */
    public void setPromedioCalificacion(Double promedioCalificacion) { 
        this.promedioCalificacion = promedioCalificacion;
    }
}
