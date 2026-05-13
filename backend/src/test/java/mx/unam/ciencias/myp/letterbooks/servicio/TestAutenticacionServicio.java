package mx.unam.ciencias.myp.letterbooks.servicio;

import mx.unam.ciencias.myp.letterbooks.dto.InicioDeSesion;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.repositorio.UsuarioRepositorio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Clase de prueba para la lógica de negocio de autenticación.
 * Verifica la integración entre el servicio, el repositorio y el cifrado.
 */
@SpringBootTest
public class TestAutenticacionServicio {

    @Autowired
    private AutenticacionServicio servicio;

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private PasswordEncoder codificador;

    /* Limpia la base de datos y crea un usuario antes de cada test. */
    @BeforeEach
    public void prepararDatos() {
        repositorio.deleteAll();
        Usuario ana = new Usuario();
        ana.setNombreUsuario("ana_test");
        ana.setCorreo("ana@prueba.com");
        // Guardamos la contraseña cifrada
        ana.setContrasena(codificador.encode("secreto123"));
        ana.setRol(Usuario.Rol.usuario);
        repositorio.save(ana);
    }

    /**
     * Prueba que un inicio de sesión con credenciales válidas genera un token.
     */
    @Test
    public void deberiaAutenticarExitosamente() {
        InicioDeSesion datos = new InicioDeSesion();
        datos.setNombreUsuario("ana_test");
        datos.setContrasena("secreto123");

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
        datos.setNombreUsuario("ana_test");
        datos.setContrasena("clave_equivocada");

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
        datos.setNombreUsuario("usuario_inexistente");
        datos.setContrasena("password123");

        assertThrows(IllegalArgumentException.class, () -> {
            servicio.autenticarUsuario(datos);
        });
    }
}
