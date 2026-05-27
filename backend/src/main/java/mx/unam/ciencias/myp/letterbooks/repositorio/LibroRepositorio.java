package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.unam.ciencias.myp.letterbooks.modelo.Libro;

/**
 * Repositorio de acceso a datos para la entidad Libro.
 * <p>
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 * </p>
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Integer> {

    /**
     * Busca un libro por su identificador único.
     * 
     * @param idLibro identificador del libro a buscar
     * @return un Optional con el libro si existe, o vacío si no se encuentra
     */
    @Query("SELECT l FROM Libro l WHERE l.idLibro = :idLibro")
    Optional<Libro> encontrarPorId(@Param("idLibro") Integer idLibro);

    /**
     * Busca un libro por su título exacto.
     * 
     * @param titulo título del libro a buscar
     * @return un Optional con el libro si existe, o vacío si no se encuentra
     */
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    Optional<Libro> encontrarPorTitulo(@Param("titulo") String titulo);

    /**
     * Busca libros cuyo título contenga la cadena dada (búsqueda parcial).
     * 
     * @param titulo cadena a buscar dentro del título
     * @return lista de libros que coinciden con la búsqueda
     */
    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %:titulo%")
    List<Libro> encontrarPorTituloContiene(@Param("titulo") String titulo);

    /**
     * Busca todos los libros de un autor dado.
     * 
     * @param idAutor identificador del autor
     * @return lista de libros del autor
     */
    @Query("SELECT l FROM Libro l WHERE l.autor.idAutor = :idAutor")
    List<Libro> encontrarPorAutor(@Param("idAutor") Integer idAutor);

    /**
     * Busca todos los libros de un género dado.
     * 
     * @param idGenero identificador del género
     * @return lista de libros del género
     */
    @Query("SELECT l FROM Libro l WHERE l.genero.idGenero = :idGenero")
    List<Libro> encontrarPorGenero(@Param("idGenero") Integer idGenero);

    /**
     * Busca un libro por su ISBN.
     * 
     * @param isbn ISBN del libro a buscar
     * @return un Optional con el libro si existe, o vacío si no se encuentra
     */
    @Query("SELECT l FROM Libro l WHERE l.isbn = :isbn")
    Optional<Libro> encontrarPorIsbn(@Param("isbn") String isbn);

    /**
     * Verifica si existe un libro con el ISBN dado.
     * 
     * @param isbn ISBN a verificar
     * @return true si existe al menos un libro con ese ISBN, false en caso
     *         contrario
     */
    @Query("SELECT COUNT(l) > 0 FROM Libro l WHERE l.isbn = :isbn")
    boolean existePorIsbn(@Param("isbn") String isbn);

    /**
     * Busca un libro por su título exacto.
     * @param titulo Título del libro a buscar
     * @return un Optional con el libro si existe, o vacío si no se encuentra
     */
    Optional<Libro> findByTitulo(String titulo);

    /**
     * Busca un libro por su código único ISBN.
     * @param isbn Código identificador del libro
     * @return un Optional con el libro si existe, o vacío si no se encuentra
     */
    Optional<Libro> findByIsbn(String isbn);

    /**
     * Verifica si existe un libro registrado con el código ISBN dado.
     * @param isbn Código ISBN a verificar
     * @return true si el ISBN ya existe en la base de datos, false en caso contrario
     */
    boolean existsByIsbn(String isbn);

    /**
     * Busca todos los libros que pertenezcan a un año de publicación específico.
     * @param ano Año de publicación
     * @return un ArrayList con los libros encontrados
     */
    List<Libro> findByAno(Integer ano);

    /**
     * Busca todos los libros escritos por un autor específico filtrando por su nombre.
     * @param nombreAutor nombre completo o parcial del autor
     * @return un ArrayList con los libros de ese autor
     */
    List<Libro> findByAutorNombreAutor(String nombreAutor);

    /**
     * Busca todos los libros que pertenecen a un género literario específico.
     * @param nombreGenero nombre de la categoría o género
     * @return un ArrayList con los libros de ese género
     */
    List<Libro> findByGeneroNombreGenero(String nombreGenero);

    /**
     * Busca todos los libros publicados por una editorial específica.
     * @param nombreEditorial nombre de la casa editora
     * @return un ArrayList con los libros de esa editorial
     */
    List<Libro> findByEditorialNombreEditorial(String nombreEditorial);

    /**
     * Obtiene los libros con mayor calificación promedio.
     * @param pageable paginación con límite de resultados
     * @return lista de libros ordenados por popularidad
     */
    List<Libro> findAllByOrderByPromedioCalificacionDescIdLibroAsc(Pageable pageable);

    /**
     * Busca un libro por ID cargando autor, género y editorial.
     * @param idLibro identificador del libro
     * @return libro con relaciones inicializadas
     */
    @Query("SELECT l FROM Libro l "
         + "LEFT JOIN FETCH l.autor "
         + "LEFT JOIN FETCH l.genero "
         + "LEFT JOIN FETCH l.editorial "
         + "WHERE l.idLibro = :idLibro")
    Optional<Libro> encontrarPorIdConRelaciones(@Param("idLibro") Integer idLibro);
}
