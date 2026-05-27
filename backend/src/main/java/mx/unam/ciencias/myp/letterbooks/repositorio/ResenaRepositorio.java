package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.unam.ciencias.myp.letterbooks.modelo.Resena;

/**
 * Repositorio de acceso a datos para la entidad Resena.
 * <p>
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 * </p>
 */
@Repository
public interface ResenaRepositorio extends JpaRepository<Resena, Integer> {

    /**
     * Busca todas las reseñas de un libro dado.
     * @param idLibro identificador del libro
     * @return lista de reseñas del libro
     */
    @Query("SELECT r FROM Resena r WHERE r.libro.idLibro = :idLibro")
    List<Resena> encontrarPorLibro(@Param("idLibro") Integer idLibro);

    /**
     * Busca todas las reseñas escritas por un usuario dado.
     * @param idUsuario identificador del usuario
     * @return lista de reseñas del usuario
     */
    @Query("SELECT r FROM Resena r WHERE r.usuario.idUsuario = :idUsuario")
    List<Resena> encontrarPorUsuario(@Param("idUsuario") Integer idUsuario);

    /**
     * Verifica si un usuario ya escribió una reseña para un libro dado.
     * @param idUsuario identificador del usuario
     * @param idLibro identificador del libro
     * @return true si ya existe una reseña de ese usuario para ese libro, false en caso contrario
     */
    @Query("SELECT COUNT(r) > 0 FROM Resena r WHERE r.usuario.idUsuario = :idUsuario AND r.libro.idLibro = :idLibro")
    boolean existePorUsuarioYLibro(@Param("idUsuario") Integer idUsuario, @Param("idLibro") Integer idLibro);

    /**
     * Busca y regresa todas las reseñas asociadas a un libro específico.
     * @param idLibro el identificador único del libro.
     * @return una lista con las reseñas pertenecientes al libro.
     */
    List<Resena> findByLibro_IdLibro(Integer idLibro);

    /**
     * Busca las reseñas de un libro ordenadas de la más reciente a la más antigua.
     * @param idLibro identificador del libro
     * @return lista de reseñas ordenadas por fecha de publicación descendente
     */
    List<Resena> findByLibro_IdLibroOrderByFechaPublicacionDesc(Integer idLibro);

    /**
     * Busca las reseñas de un libro con el autor cargado, más recientes primero.
     * @param idLibro identificador del libro
     * @return lista de reseñas con usuario inicializado
     */
    @Query("SELECT r FROM Resena r JOIN FETCH r.usuario u WHERE r.libro.idLibro = :idLibro ORDER BY r.fechaPublicacion DESC")
    List<Resena> encontrarPorLibroConUsuario(@Param("idLibro") Integer idLibro);

    /**
     * Obtiene las reseñas más recientes con usuario y libro cargados.
     * @param pageable paginación con límite de resultados
     * @return lista de reseñas recientes
     */
    @Query("SELECT r FROM Resena r JOIN FETCH r.usuario JOIN FETCH r.libro l LEFT JOIN FETCH l.autor ORDER BY r.fechaPublicacion DESC, r.idResena DESC")
    List<Resena> encontrarRecientes(Pageable pageable);
}
