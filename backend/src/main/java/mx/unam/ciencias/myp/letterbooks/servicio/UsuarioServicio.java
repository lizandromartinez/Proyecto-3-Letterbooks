package mx.unam.ciencias.myp.letterbooks.servicio;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.repositorio.UsuarioRepositorio;
import mx.unam.ciencias.myp.letterbooks.dto.Registro;

/**
 * Servicio encargado de la lógica de negocio relacionada con usuarios.
 * <p>
 * Esta clase gestiona operaciones como el registro de usuarios,
 * aplicando validaciones, normalización de datos y reglas de negocio.
 * </p>
 */
@Service
public class UsuarioServicio {

    /**
     * Repositorio encargado de realizar operaciones de persistencia
     * sobre la entidad Usuario (consultas, verificaciones y guardado en BD).
     */
    private final UsuarioRepositorio usuarioRepositorio;

    /**
     * Codificador de contraseñas utilizado para transformar contraseñas
     * en texto plano a hashes seguros antes de almacenarlas en la base de datos.
     *
     * Se utiliza típicamente BCryptPasswordEncoder.
     */
    private final PasswordEncoder codificadorContrasenas;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param usuarioRepositorio repositorio para acceder a la base de datos de usuarios
     * @param codificadorContrasenas codificador de contraseñas para almacenar passwords de forma segura
     */
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, PasswordEncoder codificadorContrasenas) {
        this.usuarioRepositorio = usuarioRepositorio;
	this.codificadorContrasenas = codificadorContrasenas;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * <p>
     * Este método:
     * <ul>
     *   <li>Normaliza los datos del registro (trim y lower case en correo)</li>
     *   <li>Codifica la contraseña usando BCrypt</li>
     *   <li>Valida que el nombre de usuario no exista</li>
     *   <li>Valida que el correo no esté registrado</li>
     *   <li>Guarda el usuario en la base de datos</li>
     * </ul>
     * </p>
     *
     * @param registro DTO con los datos del usuario a registrar
     * @return usuario recién creado y persistido en la base de datos
     * @throws IllegalArgumentException si el nombre de usuario o correo ya existen
     */
    public Usuario registrar(Registro registro) {

	registro.setNombreUsuario(registro.getNombreUsuario().trim());
	registro.setCorreo(registro.getCorreo().trim().toLowerCase());
	registro.setContrasena(codificadorContrasenas.encode(registro.getContrasena().trim()));
	
	if (usuarioRepositorio.existsByNombreUsuario(
            registro.getNombreUsuario())) {

            throw new IllegalArgumentException(
                "Ese nombre de usuario ya existe"
            );
        }

        if (usuarioRepositorio.existsByCorreo(
            registro.getCorreo())) {

            throw new IllegalArgumentException(
                "Ese correo ya está registrado"
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(registro.getNombreUsuario());
        usuario.setCorreo(registro.getCorreo());
        usuario.setContrasena(registro.getContrasena());

        return usuarioRepositorio.save(usuario);	
    }
}
