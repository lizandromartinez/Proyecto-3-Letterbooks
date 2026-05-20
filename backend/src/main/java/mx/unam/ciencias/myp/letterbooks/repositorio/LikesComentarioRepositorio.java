package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.unam.ciencias.myp.letterbooks.modelo.LikesComentario;

/**
 * Repositorio de acceso a datos para la entidad LikesComentario.
 * <p>
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 * </p>
 */
@Repository
public interface LikesComentarioRepositorio extends JpaRepository<LikesComentario, Integer> {

    /**
     * Busca todos los likes dados por un usuario a comentarios.
     * @param idUsuario identificador del usuario
     * @return lista de likes del usuario a comentarios
     */
    @Query("SELECT l FROM LikesComentario l WHERE l.usuario.idUsuario = :idUsuario")
    List<LikesComentario> encontrarPorUsuario(@Param("idUsuario") Integer idUsuario);

    /**
     * Busca todos los likes de un comentario dado.
     * @param idComentario identificador del comentario
     * @return lista de likes del comentario
     */
    @Query("SELECT l FROM LikesComentario l WHERE l.comentario.idComentario = :idComentario")
    List<LikesComentario> encontrarPorComentario(@Param("idComentario") Integer idComentario);

    /**
     * Verifica si un usuario ya dio like a un comentario dado.
     * @param idUsuario identificador del usuario
     * @param idComentario identificador del comentario
     * @return true si el usuario ya dio like a ese comentario, false en caso contrario
     */
    @Query("SELECT COUNT(l) > 0 FROM LikesComentario l WHERE l.usuario.idUsuario = :idUsuario AND l.comentario.idComentario = :idComentario")
    boolean existePorUsuarioYComentario(@Param("idUsuario") Integer idUsuario, @Param("idComentario") Integer idComentario);
}
