package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.List;
import java.util.Optional;
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
     * @param idLibro identificador del libro a buscar
     * @return un Optional con el libro si existe, o vacío si no se encuentra
     */
    @Query("SELECT l FROM Libro l WHERE l.idLibro = :idLibro")
    Optional<Libro> encontrarPorId(@Param("idLibro") Integer idLibro);
    
    /**
     * Busca un libro por su título exacto.
     * @param titulo título del libro a buscar
     * @return un Optional con el libro si existe, o vacío si no se encuentra
     */
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    Optional<Libro> encontrarPorTitulo(@Param("titulo") String titulo);

    /**
     * Busca libros cuyo título contenga la cadena dada (búsqueda parcial).
     * @param titulo cadena a buscar dentro del título
     * @return lista de libros que coinciden con la búsqueda
     */
    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %:titulo%")
    List<Libro> encontrarPorTituloContiene(@Param("titulo") String titulo);

    /**
     * Busca todos los libros de un autor dado.
     * @param idAutor identificador del autor
     * @return lista de libros del autor
     */
    @Query("SELECT l FROM Libro l WHERE l.autor.idAutor = :idAutor")
    List<Libro> encontrarPorAutor(@Param("idAutor") Integer idAutor);

    /**
     * Busca todos los libros de un género dado.
     * @param idGenero identificador del género
     * @return lista de libros del género
     */
    @Query("SELECT l FROM Libro l WHERE l.genero.idGenero = :idGenero")
    List<Libro> encontrarPorGenero(@Param("idGenero") Integer idGenero);

    /**
     * Busca un libro por su ISBN.
     * @param isbn ISBN del libro a buscar
     * @return un Optional con el libro si existe, o vacío si no se encuentra
     */
    @Query("SELECT l FROM Libro l WHERE l.isbn = :isbn")
    Optional<Libro> encontrarPorIsbn(@Param("isbn") String isbn);

    /**
     * Verifica si existe un libro con el ISBN dado.
     * @param isbn ISBN a verificar
     * @return true si existe al menos un libro con ese ISBN, false en caso contrario
     */
    @Query("SELECT COUNT(l) > 0 FROM Libro l WHERE l.isbn = :isbn")
    boolean existePorIsbn(@Param("isbn") String isbn);
}
