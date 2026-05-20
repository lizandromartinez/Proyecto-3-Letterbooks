package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.unam.ciencias.myp.letterbooks.modelo.Genero;

/**
 * Repositorio de acceso a datos para la entidad Genero.
 * <p>
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 * </p>
 */
@Repository
public interface GeneroRepositorio extends JpaRepository<Genero, Integer> {

    /**
     * Busca un género por su nombre.
     * @param nombreGenero nombre del género a buscar
     * @return un Optional con el género si existe, o vacío si no se encuentra
     */
    @Query("SELECT g FROM Genero g WHERE g.nombreGenero = :nombreGenero")
    Optional<Genero> encontrarPorNombre(@Param("nombreGenero") String nombreGenero);

    /**
     * Verifica si existe un género con el nombre dado.
     * @param nombreGenero nombre del género a verificar
     * @return true si existe al menos un género con ese nombre, false en caso contrario
     */
    @Query("SELECT COUNT(g) > 0 FROM Genero g WHERE g.nombreGenero = :nombreGenero")
    boolean existePorNombre(@Param("nombreGenero") String nombreGenero);
}
