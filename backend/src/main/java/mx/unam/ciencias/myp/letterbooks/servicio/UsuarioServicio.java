package mx.unam.ciencias.myp.letterbooks.servicio;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.repositorio.UsuarioRepositorio;
import mx.unam.ciencias.myp.letterbooks.dto.Registro;
import mx.unam.ciencias.myp.letterbooks.modelo.Perfil;
import mx.unam.ciencias.myp.letterbooks.repositorio.PerfilRepositorio;

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
     * Repositorio encargado de la persistencia y consulta de perfiles de usuario.
     * <p>
     * Se utiliza para crear y guardar el perfil por defecto asociado a cada usuario
     * durante el proceso de registro.
     * </p>
     */
    private final PerfilRepositorio perfilRepositorio;
    
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
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, PasswordEncoder codificadorContrasenas, PerfilRepositorio perfilRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
	this.codificadorContrasenas = codificadorContrasenas;
	this.perfilRepositorio = perfilRepositorio;
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
	
	if (usuarioRepositorio.existePorNombreUsuario(
            registro.getNombreUsuario())) {

            throw new IllegalArgumentException(
                "Ese nombre de usuario ya existe"
            );
        }

        if (usuarioRepositorio.existePorCorreo(
            registro.getCorreo())) {

            throw new IllegalArgumentException(
                "Ese correo ya está registrado"
            );
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(registro.getNombreUsuario());
        usuario.setCorreo(registro.getCorreo());
        usuario.setContrasena(registro.getContrasena());

	Usuario guardado = usuarioRepositorio.save(usuario);	
	perfilRepositorio.save(crearPerfilPorDefecto(guardado));
	
        return guardado;	
    }

    /**
     * Crea un perfil por defecto para un usuario recién registrado.
     * <p>
     * Este método inicializa los valores base del perfil para evitar nulls
     * y garantizar consistencia en la base de datos desde el momento del registro.
     * </p>
     *
     * Valores iniciales:
     * <ul>
     *   <li>Biografía genérica</li>
     *   <li>Avatar por defecto almacenado en frontend/public/estilos/img/defecto</li>
     *   <li>Banner por defecto almacenado en frontend/public/estilos/img/defecto</li>
     *   <li>Fecha de registro actual</li>
     *   <li>Contador de reportes inicializado en 0</li>
     * </ul>
     *
     * @param usuario usuario al que se le asignará el perfil
     * @return entidad Perfil lista para ser persistida
     */
    private Perfil crearPerfilPorDefecto(Usuario usuario) {
	Perfil perfil = new Perfil();
	perfil.setUsuario(usuario);
	perfil.setBiografia("Aquí va tu biografía.");
	perfil.setAutor(null);
	perfil.setGenero(null);
	perfil.setLibro(null);
	// perfil.setAvatar("/estilos/img/defecto/avatar.jpg");
	// perfil.setBanner("/estilos/img/defecto/banner.png");
	perfil.setFechaRegistro(java.time.LocalDate.now().toString());
	perfil.setReportes(0);
	return perfil;
    }

    /**
     * Obtiene un usuario a partir de su identificador único.
     *
     * @param idUsuario identificador del usuario a buscar
     * @return el usuario correspondiente al identificador proporcionado
     * @throws RuntimeException si no existe un usuario con ese identificador
     */
    public Usuario obtenerPorId(Integer idUsuario) {
	return usuarioRepositorio.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + idUsuario));
    }    
}
