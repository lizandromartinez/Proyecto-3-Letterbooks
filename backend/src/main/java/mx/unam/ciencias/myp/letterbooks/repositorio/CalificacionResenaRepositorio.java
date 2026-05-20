package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.unam.ciencias.myp.letterbooks.modelo.CalificacionResena;

/**
 * Repositorio de acceso a datos para la entidad CalificacionResena.
 * <p>
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 * </p>
 */
@Repository
public interface CalificacionResenaRepositorio extends JpaRepository<CalificacionResena, Integer> {

    /**
     * Busca todas las calificaciones dadas por un usuario a reseñas.
     * @param idUsuario identificador del usuario
     * @return lista de calificaciones del usuario
     */
    @Query("SELECT c FROM CalificacionResena c WHERE c.usuario.idUsuario = :idUsuario")
    List<CalificacionResena> encontrarPorUsuario(@Param("idUsuario") Integer idUsuario);

    /**
     * Busca todas las calificaciones de una reseña dada.
     * @param idResena identificador de la reseña
     * @return lista de calificaciones de la reseña
     */
    @Query("SELECT c FROM CalificacionResena c WHERE c.resena.idResena = :idResena")
    List<CalificacionResena> encontrarPorResena(@Param("idResena") Integer idResena);

    /**
     * Busca la calificación de un usuario a una reseña específica.
     * @param idUsuario identificador del usuario
     * @param idResena identificador de la reseña
     * @return un Optional con la calificación si existe, o vacío si no se encuentra
     */
    @Query("SELECT c FROM CalificacionResena c WHERE c.usuario.idUsuario = :idUsuario AND c.resena.idResena = :idResena")
    Optional<CalificacionResena> encontrarPorUsuarioYResena(@Param("idUsuario") Integer idUsuario, @Param("idResena") Integer idResena);

    /**
     * Verifica si un usuario ya calificó una reseña dada.
     * @param idUsuario identificador del usuario
     * @param idResena identificador de la reseña
     * @return true si el usuario ya calificó esa reseña, false en caso contrario
     */
    @Query("SELECT COUNT(c) > 0 FROM CalificacionResena c WHERE c.usuario.idUsuario = :idUsuario AND c.resena.idResena = :idResena")
    boolean existePorUsuarioYResena(@Param("idUsuario") Integer idUsuario, @Param("idResena") Integer idResena);
}
