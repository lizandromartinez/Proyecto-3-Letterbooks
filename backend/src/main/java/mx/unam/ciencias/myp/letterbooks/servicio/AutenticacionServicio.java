package mx.unam.ciencias.myp.letterbooks.servicio;

import mx.unam.ciencias.myp.letterbooks.dto.InicioDeSesion;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.repositorio.UsuarioRepositorio;
import mx.unam.ciencias.myp.letterbooks.seguridad.TokenJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Clase que representa el servicio que maneja la lógica de negocio para la autenticación de usuarios.
 * Se encarga de validar credenciales y coordinar la generación de tokens.
 */
@Service
public class AutenticacionServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder codificadorContrasena;

    @Autowired
    private TokenJWT tokenJWT;

    /**
     * Valida las credenciales ingresadas y genera un token JWT si son correctas.
     * @param datosInicioDeSesion DTO con el correo y la contraseña en texto plano.
     * @return Un token JWT como cadena de texto para autorizar la sesión.
     * @throws IllegalArgumentException Si las credenciales no coinciden.
     */
    public String autenticarUsuario(InicioDeSesion datosInicioDeSesion) throws IllegalArgumentException {
        Optional<Usuario> usuarioOpt = usuarioRepositorio.encontrarPorCorreo(datosInicioDeSesion.getCorreo().trim());

        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("El correo o la contraseña son incorrectos.");
        }

        Usuario usuario = usuarioOpt.get();

        if (!codificadorContrasena.matches(datosInicioDeSesion.getContrasena(), usuario.getContrasena())) {
            throw new IllegalArgumentException("El correo o la contraseña son incorrectos.");
        }

        return tokenJWT.generarToken(usuario);
    }
}
