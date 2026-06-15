package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;

/**
 * Repositorio de acceso a datos para la entidad Usuario.
 * <p>
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 * </p>
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario por su nombre de usuario.
     * @param nombreUsuario nombre único del usuario
     * @return un Optional con el usuario si existe, o vacío si no se encuentra
     */
    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    Optional<Usuario> encontrarPorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    /**
     * Busca un usuario por su correo electrónico.
     * @param correo correo electrónico del usuario
     * @return un Optional con el usuario si existe, o vacío si no se encuentra
     */
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    Optional<Usuario> encontrarPorCorreo(@Param("correo") String correo);

    /**
     * Verifica si existe un usuario con el nombre de usuario dado.
     * @param nombreUsuario nombre de usuario a verificar
     * @return true si existe al menos un usuario con ese nombre, false en caso contrario
     */
    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    boolean existePorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    /**
     * Verifica si existe un usuario con el correo electrónico dado.
     * @param correo correo a verificar
     * @return true si existe al menos un usuario con ese correo, false en caso contrario
     */
    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.correo = :correo")
    boolean existePorCorreo(@Param("correo") String correo);    
}
