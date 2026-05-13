package mx.unam.ciencias.myp.letterbooks.servicio;

import mx.unam.ciencias.myp.letterbooks.dto.InicioDeSesion;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.repositorio.UsuarioRepositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Clase de prueba para la lógica de negocio de autenticación.
 * Verifica la integración entre el servicio, el repositorio y el cifrado.
 */
@SpringBootTest
@Transactional
public class TestAutenticacionServicio {

    @Autowired
    private AutenticacionServicio servicio;

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private PasswordEncoder codificador;

    /* Crea un usuario de prueba antes de cada test dentro de una transacción. */
    @BeforeEach
    public void prepararDatos() {
        Usuario usuarioTest = new Usuario();
        usuarioTest.setNombreUsuario("test_user_auth");
        usuarioTest.setCorreo("test.auth@letterbooks.com");
        usuarioTest.setContrasena(codificador.encode("password_test_123"));
        usuarioTest.setRol(Usuario.Rol.usuario);
        repositorio.save(usuarioTest);
    }

    /**
     * Prueba que un inicio de sesión con credenciales válidas genera un token.
     */
    @Test
    public void deberiaAutenticarExitosamente() {
        InicioDeSesion datos = new InicioDeSesion();
        datos.setNombreUsuario("test_user_auth");
        datos.setContrasena("password_test_123");

        String token = servicio.autenticarUsuario(datos);
        assertThat(token).isNotNull();
        assertThat(token).startsWith("eyJ"); // Estructura típica de un JWT
    }

    /**
     * Prueba que el sistema rechace contraseñas incorrectas.
     */
    @Test
    public void noDeberiaAutenticarConContrasenaErronea() {
        InicioDeSesion datos = new InicioDeSesion();
        datos.setNombreUsuario("test_user_auth");
        datos.setContrasena("clave_incorrecta");

        assertThrows(IllegalArgumentException.class, () -> {
            servicio.autenticarUsuario(datos);
        });
    }

    /**
     * Prueba que el sistema rechace nombres de usuario que no están en la base de datos.
     */
    @Test
    public void noDeberiaAutenticarUsuarioInexistente() {
        InicioDeSesion datos = new InicioDeSesion();
        datos.setNombreUsuario("usuario_totalmente_inexistente");
        datos.setContrasena("password123");

        assertThrows(IllegalArgumentException.class, () -> {
            servicio.autenticarUsuario(datos);
        });
    }
}
