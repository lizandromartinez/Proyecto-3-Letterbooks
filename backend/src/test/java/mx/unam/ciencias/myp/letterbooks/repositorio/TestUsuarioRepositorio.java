package mx.unam.ciencias.myp.letterbooks.repositorio;

import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas de integración para el repositorio de Usuarios.
 * Utiliza la base de datos real configurada en el proyecto.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestUsuarioRepositorio {

    @Autowired
    private UsuarioRepositorio repositorio;

    /**
     * Prueba que se puede guardar un usuario y recuperarlo por su correo.
     */
    @Test
    public void deberiaGuardarYEncontrarUsuarioPorCorreo() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("pruebaUser");
        usuario.setCorreo("test@correo.com");
        usuario.setContrasena("hash_seguro");
        usuario.setRol(Usuario.Rol.usuario);

        repositorio.save(usuario);

        Optional<Usuario> encontrado = repositorio.encontrarPorCorreo("test@correo.com");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombreUsuario()).isEqualTo("pruebaUser");
    }

    /**
     * Prueba que la búsqueda devuelve un Optional vacío si el correo no existe.
     */
    @Test
    public void noDeberiaEncontrarUsuarioInexistente() {
        Optional<Usuario> encontrado = repositorio.encontrarPorCorreo("fantasma@correo.com");
        assertThat(encontrado).isEmpty();
    }

    /**
     * Verifica que la base de datos rechace correos duplicados.
     * Esto valida que el esquema SQL y la entidad estén sincronizados.
     */
    @Test
    public void deberiaLanzarExcepcionSiElCorreoEstaDuplicado() {
        Usuario user1 = new Usuario();
        user1.setNombreUsuario("user1");
        user1.setCorreo("igual@correo.com");
        user1.setContrasena("pass");
        repositorio.save(user1);

        Usuario user2 = new Usuario();
        user2.setNombreUsuario("user2");
        user2.setCorreo("igual@correo.com"); 
        user2.setContrasena("pass");

        assertThrows(DataIntegrityViolationException.class, () -> {
            repositorio.saveAndFlush(user2);
        });
    }
}
