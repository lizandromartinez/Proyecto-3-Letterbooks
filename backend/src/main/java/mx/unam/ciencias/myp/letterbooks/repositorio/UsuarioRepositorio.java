package mx.unam.ciencias.myp.letterbooks.repositorio;

import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Interfaz para realizar operaciones crudas sobre la tabla Usuario.
 * Extiende de JpaRepository para obtener funcionalidad básica de persistencia.
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario en la base de datos a partir de su nombre de usuario.
     * @param nombreUsuario El nombre de usuario a buscar.
     * @return Un Optional que contiene al usuario si es encontrado.
     */
    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    Optional<Usuario> encontrarPorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);
}
