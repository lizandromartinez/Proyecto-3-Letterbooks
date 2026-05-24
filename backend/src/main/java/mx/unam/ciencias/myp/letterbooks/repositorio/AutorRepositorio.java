package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.unam.ciencias.myp.letterbooks.modelo.Autor;

/**
 * Repositorio de acceso a datos para la entidad Autor.
 * <p>
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 * </p>
 */
@Repository
public interface AutorRepositorio extends JpaRepository<Autor, Integer> {

    /**
     * Busca un autor por su identificador único.
     * @param idAutor identificador del autor a buscar
     * @return un Optional con el autor si existe, o vacío si no se encuentra
     */
    @Query("SELECT a FROM Autor a WHERE a.idAutor = :idAutor")
    Optional<Autor> encontrarPorId(@Param("idAutor") Integer idAutor);
    
    /**
     * Busca un autor por su nombre.
     * @param nombreAutor nombre del autor a buscar
     * @return un Optional con el autor si existe, o vacío si no se encuentra
     */
    @Query("SELECT a FROM Autor a WHERE a.nombreAutor = :nombreAutor")
    Optional<Autor> encontrarPorNombre(@Param("nombreAutor") String nombreAutor);

    /**
     * Busca autores cuyo nombre contenga la cadena dada (búsqueda parcial).
     * @param nombre cadena a buscar dentro del nombre del autor
     * @return lista de autores que coinciden con la búsqueda
     */
    @Query("SELECT a FROM Autor a WHERE a.nombreAutor LIKE %:nombre%")
    List<Autor> encontrarPorNombreContiene(@Param("nombre") String nombre);

    /**
     * Verifica si existe un autor con el nombre dado.
     * @param nombreAutor nombre del autor a verificar
     * @return true si existe al menos un autor con ese nombre, false en caso contrario
     */
    @Query("SELECT COUNT(a) > 0 FROM Autor a WHERE a.nombreAutor = :nombreAutor")
    boolean existePorNombre(@Param("nombreAutor") String nombreAutor);
}
