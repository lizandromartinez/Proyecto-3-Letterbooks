package mx.unam.ciencias.myp.letterbooks.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mx.unam.ciencias.myp.letterbooks.dto.Perfil;
import mx.unam.ciencias.myp.letterbooks.dto.ActualizarPerfil;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.seguridad.TokenJWT;
import mx.unam.ciencias.myp.letterbooks.servicio.PerfilServicio;
import mx.unam.ciencias.myp.letterbooks.servicio.UsuarioServicio;

/**
 * Controlador REST para la gestión de usuarios.
 * <p>
 * Expone los endpoints relacionados con el perfil y la información
 * pública de los usuarios del sistema.
 * </p>
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class Usuarios {

    /**
     * Servicio encargado de la lógica de negocio relacionada
     * con los perfiles de usuario.
     */
    @Autowired
    private PerfilServicio perfilServicio;

    /**
     * Servicio encargado de las operaciones relacionadas
     * con los usuarios del sistema.
     */
    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Componente encargado de generar, leer y validar
     * tokens JWT utilizados para la autenticación.
     */
    @Autowired
    private TokenJWT tokenJWT;

    /**
     * Obtiene el perfil completo de un usuario dado su ID.
     * @param idUsuario identificador del usuario
     * @return ResponseEntity con el DTO del perfil del usuario
     */
    @GetMapping("/{idUsuario}/perfil")
    public ResponseEntity<Perfil> obtenerPerfil(@PathVariable("idUsuario") Integer idUsuario) {
        try {
            Perfil perfil = perfilServicio.obtenerPerfilPorUsuario(idUsuario);
            return ResponseEntity.ok(perfil);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene el perfil público de un usuario dado su nombre de usuario.
     * @param nombreUsuario nombre de usuario a buscar
     * @return ResponseEntity con el DTO del perfil
     */
    @GetMapping("/perfil/{nombreUsuario}")
    public ResponseEntity<Perfil> obtenerPerfilPublico(@PathVariable String nombreUsuario) {
	try {
            Perfil perfil = perfilServicio.obtenerPerfilPorNombreUsuario(nombreUsuario);
            return ResponseEntity.ok(perfil);
	} catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
	}
    }
    
    /**
     * Endpoint para modificar el perfil del usuario autenticado.
     * @param idUsuario identificador del usuario dueño del perfil
     * @param perfilDTO datos nuevos del perfil
     * @param authHeader header de autorización con el token JWT
     * @return ResponseEntity con el perfil actualizado o error
     */
    @PutMapping("/{idUsuario}/perfil")
    public ResponseEntity<?> actualizarPerfil(
        @PathVariable("idUsuario") Integer idUsuario,
        @RequestBody ActualizarPerfil datos,
        @RequestHeader("Authorization") String authHeader) {
        try {
            // Extraemos el token del header
            String token = authHeader.replace("Bearer ", "");

            // Obtenemos el nombre de usuario del token
            String nombreUsuarioToken = tokenJWT.obtenerNombreUsuario(token);

            // Verificamos que el usuario del token es el dueño del perfil
            Usuario usuarioPerfil = usuarioServicio.obtenerPorId(idUsuario);
	    
            if (!nombreUsuarioToken.equals(usuarioPerfil.getNombreUsuario())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para modificar este perfil");
            }

            // Procedemos con la actualización
            Perfil perfilActualizado = perfilServicio.actualizarPerfil(idUsuario, datos);
            return ResponseEntity.ok(perfilActualizado);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
