package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.unam.ciencias.myp.letterbooks.modelo.Perfil;

/**
 * Repositorio de acceso a datos para la entidad Perfil.
 * <p>
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 * </p>
 */
@Repository
public interface PerfilRepositorio extends JpaRepository<Perfil, Integer> {

    /**
     * Busca el perfil asociado a un usuario dado.
     * @param idUsuario identificador del usuario
     * @return un Optional con el perfil si existe, o vacío si no se encuentra
     */
    @Query("SELECT p FROM Perfil p WHERE p.usuario.idUsuario = :idUsuario")
    Optional<Perfil> encontrarPorUsuario(@Param("idUsuario") Integer idUsuario);

    /**
     * Verifica si existe un perfil asociado al usuario dado.
     * @param idUsuario identificador del usuario a verificar
     * @return true si existe un perfil para ese usuario, false en caso contrario
     */
    @Query("SELECT COUNT(p) > 0 FROM Perfil p WHERE p.usuario.idUsuario = :idUsuario")
    boolean existePorUsuario(@Param("idUsuario") Integer idUsuario);
}
