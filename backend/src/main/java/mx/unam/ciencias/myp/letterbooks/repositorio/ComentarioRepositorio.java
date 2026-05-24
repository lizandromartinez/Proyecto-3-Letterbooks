package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.unam.ciencias.myp.letterbooks.modelo.Comentario;

/**
 * Repositorio de acceso a datos para la entidad Comentario.
 * <p>
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 * </p>
 */
@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Integer> {

    /**
     * Busca todos los comentarios de una reseña dada.
     * @param idResena identificador de la reseña
     * @return lista de comentarios de la reseña
     */
    @Query("SELECT c FROM Comentario c WHERE c.resena.idResena = :idResena")
    List<Comentario> encontrarPorResena(@Param("idResena") Integer idResena);

    /**
     * Busca todos los comentarios escritos por un usuario dado.
     * @param idUsuario identificador del usuario
     * @return lista de comentarios del usuario
     */
    @Query("SELECT c FROM Comentario c WHERE c.usuario.idUsuario = :idUsuario")
    List<Comentario> encontrarPorUsuario(@Param("idUsuario") Integer idUsuario);
}
