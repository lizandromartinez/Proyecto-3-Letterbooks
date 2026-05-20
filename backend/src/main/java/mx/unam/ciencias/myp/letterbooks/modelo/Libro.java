package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Clase que representa un libro en el sistema.
 * Mapea la tabla "libro" de la base de datos MariaDB.
 */
@Entity
@Table(name = "libro")
public class Libro {

    /* ID único del libro. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Integer idLibro;
    
    /* Género asociado al libro. */
    @ManyToOne
    @JoinColumn(name = "id_genero")
    private Genero genero;

    /* Autor asociado al libro. */
    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;
    
    /* Editorial asociada al libro. */
    @ManyToOne
    @JoinColumn(name = "id_editorial")
    private Editorial editorial;

    /* Título del libro. */
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    /* Sinopsis del libro. */
    @Column(name = "sinopsis", columnDefinition = "TEXT")
    private String sinopsis;

    /* Ruta de la imagen de portada del libro. */
    @Column(name = "imagen", length = 255)
    private String imagen;

    /* Cantidad de páginas del libro. */
    @Column(name = "paginas")
    private Integer paginas;
    
    /* Año de publicación del libro. */
    @Column(name = "ano")
    private Integer ano;

    /* ISBN del libro. */
    @Column(name = "isbn", length = 20)
    private String isbn;

    /* Cantidad de reportes del libro. */
    @Column(name = "reportes", columnDefinition = "INT DEFAULT 0")
    private Integer reportes = 0;

    /* Promedio de calificaciones del libro. */
    @Column(name = "promedio_calificacion", precision = 3, scale = 2, columnDefinition = "DECIMAL(3,2) DEFAULT 0.00")
    private BigDecimal promedioCalificacion = BigDecimal.ZERO;
    
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
     * Obtiene el género asociado al libro.
     * @return genero asociado al libro.
     */
    public Genero getGenero() {
	return genero;
    }

    /**
     * Define el género asociado al libro.
     * @param genero género asociado.
     */
    public void setGenero(Genero genero) {
	this.genero = genero;
    }

    /**
     * Obtiene el autor asociado al libro.
     * @return autor asociado al libro.
     */
    public Autor getAutor() {
	return autor;
    }

    /**
     * Define el autor asociado al libro.
     * @param autor autor asociado.
     */
    public void setAutor(Autor autor) {
	this.autor = autor;
    }

    /**
     * Obtiene la editorial asociada al libro.
     * @return editorial asociada al libro.
     */
    public Editorial getEditorial() {
	return editorial;
    }

    /**
     * Define la editorial asociada al libro.
     * @param editorial editorial asociada.
     */
    public void setEditorial(Editorial editorial) {
	this.editorial = editorial;
    }

    /**
     * Obtiene el título del libro.
     * @return titulo título del libro.
     */
    public String getTitulo() {
	return titulo;
    }

    /**
     * Define el título del libro.
     * @param titulo título del libro.
     */
    public void setTitulo(String titulo) {
	this.titulo = titulo;
    }

    /**
     * Obtiene la sinopsis del libro.
     * @return sinopsis sinopsis del libro.
     */
    public String getSinopsis() {
	return sinopsis;
    }

    /**
     * Define la sinopsis del libro.
     * @param sinopsis sinopsis del libro.
     */
    public void setSinopsis(String sinopsis) {
	this.sinopsis = sinopsis;
    }

    /**
     * Obtiene la ruta de la imagen del libro.
     * @return imagen ruta de la imagen.
     */
    public String getImagen() {
	return imagen;
    }

    /**
     * Define la ruta de la imagen del libro.
     * @param imagen ruta de la imagen.
     */
    public void setImagen(String imagen) {
	this.imagen = imagen;
    }

    /**
     * Obtiene la cantidad de páginas del libro.
     * @return paginas cantidad de páginas.
     */
    public Integer getPaginas() {
	return paginas;
    }

    /**
     * Define la cantidad de páginas del libro.
     * @param paginas cantidad de páginas.
     */
    public void setPaginas(Integer paginas) {
	this.paginas = paginas;
    }

    /**
     * Obtiene el año de publicación del libro.
     * @return ano año de publicación.
     */
    public Integer getAno() {
	return ano;
    }

    /**
     * Define el año de publicación del libro.
     * @param ano año de publicación.
     */
    public void setAno(Integer ano) {
	this.ano = ano;
    }

    /**
     * Obtiene el ISBN del libro.
     * @return isbn ISBN del libro.
     */
    public String getIsbn() {
	return isbn;
    }

    /**
     * Define el ISBN del libro.
     * @param isbn ISBN del libro.
     */
    public void setIsbn(String isbn) {
	this.isbn = isbn;
    }

    /**
     * Obtiene la cantidad de reportes del libro.
     * @return reportes cantidad de reportes.
     */
    public Integer getReportes() {
	return reportes;
    }

    /**
     * Define la cantidad de reportes del libro.
     * @param reportes cantidad de reportes.
     */
    public void setReportes(Integer reportes) {
	this.reportes = reportes;
    }

    /**
     * Obtiene el promedio de calificaciones del libro.
     * @return promedioCalificacion promedio de calificaciones.
     */
    public BigDecimal getPromedioCalificacion() {
	return promedioCalificacion;
    }

    /**
     * Define el promedio de calificaciones del libro.
     * @param promedioCalificacion promedio de calificaciones.
     */
    public void setPromedioCalificacion(BigDecimal promedioCalificacion) {
	this.promedioCalificacion = promedioCalificacion;
    }
}
