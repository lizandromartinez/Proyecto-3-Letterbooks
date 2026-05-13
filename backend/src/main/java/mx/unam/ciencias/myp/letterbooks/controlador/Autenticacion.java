package mx.unam.ciencias.myp.letterbooks.controlador;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.servicio.UsuarioServicio;
import mx.unam.ciencias.myp.letterbooks.dto.Registro;
import jakarta.validation.Valid;

/**
 * Controlador encargado de la autenticación y registro de usuarios.
 * <p>
 * Expone endpoints REST bajo la ruta /auth para manejar operaciones
 * relacionadas con autenticación, como el registro de usuarios.
 * </p>
 */
@RestController
@RequestMapping("/autenticacion")
@CrossOrigin(origins = "http://localhost:5173")
public class Autenticacion {
    
    /**
     * Servicio encargado de la lógica de negocio relacionada con usuarios.
     */
    private final UsuarioServicio usuarioServicio;
        
    /**
     * Constructor con inyección de dependencias del servicio de usuarios.
     *
     * @param usuarioServicio servicio que contiene la lógica de negocio de usuarios
     */
    public Autenticacion(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    /**
     * Endpoint para registrar un nuevo usuario.
     * <p>
     * Recibe un objeto {@link Registro} validado, lo envía al servicio
     * de usuarios y retorna el usuario creado.
     * </p>
     *
     * En caso de que ocurra un erro como usuario o correo duplicado,
     * se devuelve una respuesta HTTP 400 con el mensaje de error.
     *
     * @param registro datos del usuario a registrar
     * @return ResponseEntity con el usuario creado o un mensaje de error
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@Valid @RequestBody Registro registro) {	
	try {	    
            Usuario usuario = usuarioServicio.registrar(registro);
            return ResponseEntity.ok(usuario);
	    
	} catch (IllegalArgumentException e) {

            return ResponseEntity
		.badRequest()
		.body(e.getMessage());
	}	
    }    
}
