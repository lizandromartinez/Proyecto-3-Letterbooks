package mx.unam.ciencias.myp.letterbooks.seguridad;

import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Clase para validar la funcionalidad de TokenJWT.
 * Verifica la correcta generación, lectura y validación de integridad de los tokens de sesión.
 */
@SpringBootTest
public class TestTokenJWT {

    @Autowired
    private TokenJWT componenteToken;

    private Usuario usuarioPrueba;

    /**
     * Configuración inicial antes de cada prueba.
     * Crea un usuario con datos consistentes para las validaciones.
     */
    @BeforeEach
    public void configurar() {
        usuarioPrueba = new Usuario();
        usuarioPrueba.setCorreo("ana.lee@ciencias.unam.mx");
        usuarioPrueba.setNombreUsuario("ana_lee");
        usuarioPrueba.setRol(Usuario.Rol.usuario);
    }

    /**
     * Prueba la generación exitosa de un token y la recuperación de su contenido.
     * Valida que el identificador (subject) coincida con el correo electrónico del usuario.
     */
    @Test
    public void deberiaGenerarYLeerTokenValido() {
        String token = componenteToken.generarToken(usuarioPrueba);
        
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();

        String correoRecuperado = componenteToken.obtenerCorreo(token);
        assertThat(correoRecuperado).isEqualTo(usuarioPrueba.getCorreo());
    }

    /**
     * Prueba la validación de identidad del token.
     * Verifica que el sistema reconozca que el token pertenece al usuario que lo generó.
     */
    @Test
    public void deberiaValidarUsuarioCorrectamente() {
        String token = componenteToken.generarToken(usuarioPrueba);
        boolean esValido = componenteToken.esTokenValido(token, usuarioPrueba);
        
        assertTrue(esValido);
    }

    /**
     * Detecta manipulación de datos (Tampering).
     * Verifica que si se altera un solo carácter del token, la firma sea inválida 
     * y el sistema lo rechace.
     */
    @Test
    public void deberiaRechazarTokenAlterado() {
        String tokenOriginal = componenteToken.generarToken(usuarioPrueba);
        
        // Alteramos el último carácter del token para invalidar la firma criptográfica
        char ultimoChar = tokenOriginal.charAt(tokenOriginal.length() - 1);
        String tokenAlterado = tokenOriginal.substring(0, tokenOriginal.length() - 1) + 
                               (ultimoChar == 'A' ? 'B' : 'A');

        // El componente debe detectar la firma inválida y no validar el token
        try {
            boolean esValido = componenteToken.esTokenValido(tokenAlterado, usuarioPrueba);
            assertFalse(esValido, "Un token con la firma alterada nunca debe ser válido");
        } catch (Exception e) {
            // Excepción de firma inválida (SignatureException)
            assertThat(e).isNotNull();
        }
    }

    /**
     * Prueba que un token generado para un usuario no sea válido para otro.
     * Esto evita ataques de suplantación de identidad.
     */
    @Test
    public void noDeberiaValidarTokenDeOtroUsuario() {
        String tokenAna = componenteToken.generarToken(usuarioPrueba);

        Usuario otroUsuario = new Usuario();
        otroUsuario.setCorreo("intruso@correo.com");

        boolean esValido = componenteToken.esTokenValido(tokenAna, otroUsuario);
        assertFalse(esValido);
    }
}
