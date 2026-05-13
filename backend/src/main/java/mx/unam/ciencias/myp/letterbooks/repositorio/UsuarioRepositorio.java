package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
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
     * No se puede cambiar al español por Spring     
     * @param nombreUsuario nombre único del usuario
     * @return un Optional con el usuario si existe, o vacío si no se encuentra
     */
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    /**
     * Busca un usuario por su correo electrónico.
     * No se puede cambiar al español por Spring
     * @param correo correo electrónico del usuario
     * @return un Optional con el usuario si existe, o vacío si no se encuentra
     */
    Optional<Usuario> findByCorreo(String correo);

    /**
     * Verifica si existe un usuario con el nombre de usuario dado.
     * No se puede cambiar al español por Spring
     * @param nombreUsuario nombre de usuario a verificar
     * @return true si existe al menos un usuario con ese nombre, false en caso contrario
     */
    boolean existsByNombreUsuario(String nombreUsuario);

    /**
     * Verifica si existe un usuario con el correo electrónico dado.
     * No se puede cambiar al español por Spring
     * @param correo correo a verificar
     * @return true si existe al menos un usuario con ese correo, false en caso contrario
     */
    boolean existsByCorreo(String correo);    
}
