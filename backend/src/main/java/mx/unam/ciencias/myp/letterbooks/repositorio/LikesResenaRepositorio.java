package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.unam.ciencias.myp.letterbooks.modelo.LikesResena;

/**
 * Repositorio de acceso a datos para la entidad LikesResena.
 * <p>
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 * </p>
 */
@Repository
public interface LikesResenaRepositorio extends JpaRepository<LikesResena, Integer> {

    /**
     * Busca todos los likes dados por un usuario a reseñas.
     * @param idUsuario identificador del usuario
     * @return lista de likes del usuario a reseñas
     */
    @Query("SELECT l FROM LikesResena l WHERE l.usuario.idUsuario = :idUsuario")
    List<LikesResena> encontrarPorUsuario(@Param("idUsuario") Integer idUsuario);

    /**
     * Busca todos los likes de una reseña dada.
     * @param idResena identificador de la reseña
     * @return lista de likes de la reseña
     */
    @Query("SELECT l FROM LikesResena l WHERE l.resena.idResena = :idResena")
    List<LikesResena> encontrarPorResena(@Param("idResena") Integer idResena);

    /**
     * Verifica si un usuario ya dio like a una reseña dada.
     * @param idUsuario identificador del usuario
     * @param idResena identificador de la reseña
     * @return true si el usuario ya dio like a esa reseña, false en caso contrario
     */
    @Query("SELECT COUNT(l) > 0 FROM LikesResena l WHERE l.usuario.idUsuario = :idUsuario AND l.resena.idResena = :idResena")
    boolean existePorUsuarioYResena(@Param("idUsuario") Integer idUsuario, @Param("idResena") Integer idResena);

    /**
     * Busca si un usuario ya le dio like a una reseña específica.
     * @param idUsuario id del usuario
     * @param idResena id de la reseña
     * @return un Optional con el Like si existe
     */
    Optional<LikesResena> findByUsuario_IdUsuarioAndResena_IdResena(Integer idUsuario, Integer idResena);
}
