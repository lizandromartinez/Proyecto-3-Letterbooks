package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.unam.ciencias.myp.letterbooks.modelo.Editorial;

/**
 * Repositorio de acceso a datos para la entidad Editorial.
 * <p>
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 * </p>
 */
@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, Integer> {

    /**
     * Busca una editorial por su nombre.
     * @param nombreEditorial nombre de la editorial a buscar
     * @return un Optional con la editorial si existe, o vacío si no se encuentra
     */
    @Query("SELECT e FROM Editorial e WHERE e.nombreEditorial = :nombreEditorial")
    Optional<Editorial> encontrarPorNombre(@Param("nombreEditorial") String nombreEditorial);

    /**
     * Verifica si existe una editorial con el nombre dado.
     * @param nombreEditorial nombre de la editorial a verificar
     * @return true si existe al menos una editorial con ese nombre, false en caso contrario
     */
    @Query("SELECT COUNT(e) > 0 FROM Editorial e WHERE e.nombreEditorial = :nombreEditorial")
    boolean existePorNombre(@Param("nombreEditorial") String nombreEditorial);
}
