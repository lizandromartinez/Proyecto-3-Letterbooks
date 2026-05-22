import jakarta.persistence.*;

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
     * Se utiliza {@link FetchType#LAZY} por rendimiento, evitando cargar 
     * la información del género en memoria a menos que se invoque explícitamente.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_genero")
    private Genero genero;

    /**
     * Autor que escribió el libro.
     * Relación muchos a uno mapeada mediante la llave foránea {@code id_autor}.
     * Carga de tipo perezosa (LAZY).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Autor autor;

    /**
     * Editorial encargada de la publicación del libro.
     * Relación muchos a uno mapeada mediante la llave foránea {@code id_editorial}.
     * Carga de tipo perezosa (LAZY).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_editorial")
    private Editorial editorial;

    /**
     * Título oficial del libro. Este campo es obligatorio y no permite valores nulos.
     */
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    /**
     * Resumen o sinopsis detallada del contenido del libro.
     * Se mapea como un tipo de dato {@code TEXT} en la base de datos para almacenar cadenas largas.
     */
    @Column(name = "sinopsis", columnDefinition = "TEXT")
    private String sinopsis;

    /**
     * Ruta de la imagen de la portada del libro.
     */
    @Column(name = "imagen", length = 255)
    private String imagen;

    /**
     * Cantidad total de páginas que componen al libro.
     */
    @Column(name = "paginas")
    private Integer paginas;

    /**
     * Año de publicación del libro.
     */
    @Column(name = "ano")
    private Integer ano;

    /**
     * Código ISBN (International Standard Book Number) identificador del libro.
     */
    @Column(name = "isbn", length = 20)
    private String isbn;

    /**
     * Contador de reportes o denuncias que ha recibido el libro.
     * Por defecto se inicializa en 0.
     */
    @Column(name = "reportes")
    private Integer reportes = 0;

    /**
     * Calificación promedio asignada por los usuarios.
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

    }
    
    /**
     * Define el identificador único del libro.
     * @param idLibro El nuevo ID del libro.
     */
    public void setIdLibro(Integer idLibro) {

    }

    /**
     * Regresa el género del libro.
     * @return El género del libro.
     */
    public Genero getGenero() {

    }

    /**
     * Define el género del libro.
     * @param genero Genero del libro.
     */
    public void setGenero(Genero genero) {

    }

    /**
     * Regresa el autor del libro.
     * @return El autor del libro.
     */
    public Autor getAutor() {

    }

    /**
     * Define el autor del libro.
     * @param autor Autor del libro.
     */
    public void setAutor(Autor autor) {

    }

    /**
     * Regresa la editorial asociada al libro.
     * @return La editorial del libro.
     */
    public Editorial getEditorial() {
	
    }

    /**
     * Define la editorial del libro.
     * @param editorial Editorial del libro.
     */
    public void setEditorial(Editorial editorial) {

    }

    /**
     * Regresa el título del libro.
     * @return El título del libro.
     */
    public String getTitulo() {

    }

    /**
     * Define el titulo del libro.
     * @param titulo Titulo del libro.
     */
    public void setTitulo(String titulo) {

    }

    /**
     * Regresa la sinopsis del libro.
     * @return Texto con la sinopsis.
     */
    public String getSinopsis() {
	
    }

    /**
     * Define la sinopsis del libro.
     * @param sinopsis Sinopsis del libro.
     */
    public void setSinopsis(String sinopsis) {

    }

    /**
     * Regresa la ruta de la imagen de portada del libro.
     * @return ruta con la ruta de la imagen.
     */
    public String getImagen() {
	
    }

    /**
     * Define la ruta de imagen de portada del libro.
     * @param imagen Imagen de portada del libro.
     */
    public void setImagen(String imagen) {

    }

    /**
     * Regresa el número de páginas del libro.
     * @return Cantidad de páginas del libro.
     */
    public Integer getPaginas() {

    }

    /**
     * Define el numero de páginas del libro.
     * @param paginas Paginas del libro.
     */
    public void setPaginas(Integer paginas) {

    }

    /**
     * Regresa el año de publicación del libro.
     * @return El año de publicación del libro.
     */
    public Integer getAno() {

    }

    /**
     * Define el año de publicación del libro.
     * @param ano Año de publicación del libro.
     */
    public void setAno(Integer ano) {
	
    }

    /**
     * Regresa ISBN del libro.
     * @return El código ISBN del libro.
     */    
    public String getIsbn() {
	
    }

    /**
     * Define el isbn del libro.
     * @param isbn ISBN del libro.
     */
    public void setIsbn(String isbn) {
	
    }

    /**
     * Regresa la cantidad de reportes que tiene el libro.
     * @return Número de reportes.
     */
    public Integer getReportes() {
	
    }

    /**
     * Define los reportes del libro.
     * @param reportes Reportes que tiene el libro.
     */
    public void setReportes(Integer reportes) {

    }

    /**
     * Regresa la calificación promedio del libro.
     * @return Un el promedio de la calificación del libro.
     */
    public Double getPromedioCalificacion() {
	
    }

    /**
     * Define la calificación promedio del libro.
     * @param promedioCalificacion Calificacion del libro.
     */
    public void setPromedioCalificacion(Double promedioCalificacion) { 

    }
    
}
