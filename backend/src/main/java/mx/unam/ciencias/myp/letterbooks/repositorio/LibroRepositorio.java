package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.unam.ciencias.myp.letterbooks.modelo.Libro;

/**
 * Repositorio de acceso a datos para la clase Libro.
 * Extiende a JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Integer> {

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
    ArrayList<Libro> findByAno(Integer ano);

    /**
     * Busca todos los libros que tengan ese autor específico.
     * @param ano Año de publicación
     * @return un ArrayList con los libros encontrados
     */
    ArrayList<Libro> findByAno(Integer ano);

    /**
     * Busca todos los libros escritos por un autor específico filtrando por su nombre.
     * @param nombreAutor nombre completo o parcial del autor
     * @return un ArrayList con los libros de ese autor
     */
    ArrayList<Libro> findByAutorNombreAutor(String nombreAutor);

    /**
     * Busca todos los libros que pertenecen a un género literario específico.
     * @param nombreGenero nombre de la categoría o género
     * @return un ArrayList con los libros de ese género
     */
    ArrayList<Libro> findByGeneroNombreGenero(String nombreGenero);

    /**
     * Busca todos los libros publicados por una editorial específica.
     * @param nombreEditorial nombre de la casa editora
     * @return un ArrayList con los libros de esa editorial
     */
    ArrayList<Libro> findByEditorialNombreEditorial(String nombreEditorial);
}
