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
     * Prueba que se puede guardar un usuario y recuperarlo por su nombre de usuario.
     */
    @Test
    public void deberiaGuardarYEncontrarUsuarioPorNombreUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("pruebaUser");
        usuario.setCorreo("test@correo.com");
        usuario.setContrasena("hash_seguro");
        usuario.setRol(Usuario.Rol.usuario);

        repositorio.save(usuario);

        Optional<Usuario> encontrado = repositorio.encontrarPorNombreUsuario("pruebaUser");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getCorreo()).isEqualTo("test@correo.com");
    }

    /**
     * Prueba que la búsqueda devuelve un Optional vacío si el nombre de usuario no existe.
     */
    @Test
    public void noDeberiaEncontrarUsuarioInexistente() {
        Optional<Usuario> encontrado = repositorio.encontrarPorNombreUsuario("usuario_fantasma");
        assertThat(encontrado).isEmpty();
    }

    /**
     * Verifica que la base de datos rechace nombres de usuario duplicados.
     * Esto valida que el esquema SQL y la entidad estén sincronizados.
     */
    @Test
    public void deberiaLanzarExcepcionSiElNombreUsuarioEstaDuplicado() {
        Usuario user1 = new Usuario();
        user1.setNombreUsuario("igual");
        user1.setCorreo("user1@correo.com");
        user1.setContrasena("pass");
        repositorio.save(user1);

        Usuario user2 = new Usuario();
        user2.setNombreUsuario("igual"); 
        user2.setCorreo("user2@correo.com"); 
        user2.setContrasena("pass");

        assertThrows(DataIntegrityViolationException.class, () -> {
            repositorio.saveAndFlush(user2);
        });
    }
}
