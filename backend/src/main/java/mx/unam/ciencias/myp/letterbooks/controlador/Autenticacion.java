package mx.unam.ciencias.myp.letterbooks.controlador;

import mx.unam.ciencias.myp.letterbooks.dto.InicioDeSesion;
import mx.unam.ciencias.myp.letterbooks.servicio.AutenticacionServicio;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.servicio.UsuarioServicio;
import mx.unam.ciencias.myp.letterbooks.dto.Registro;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para exponer los endpoints de autenticación y registro.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") 
public class Autenticacion {

    private final AutenticacionServicio autenticacionServicio;
    private final UsuarioServicio usuarioServicio;

    public Autenticacion(AutenticacionServicio autenticacionServicio, UsuarioServicio usuarioServicio) {
        this.autenticacionServicio = autenticacionServicio;
        this.usuarioServicio = usuarioServicio;
    }

    /**
     * Endpoint público para iniciar sesión.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> iniciarSesion(@Valid @RequestBody InicioDeSesion datosInicioDeSesion) {
        Map<String, String> respuesta = new HashMap<>();
        try {
            String token = autenticacionServicio.autenticarUsuario(datosInicioDeSesion);
            respuesta.put("token", token);
            return ResponseEntity.ok(respuesta);
        } catch (IllegalArgumentException e) {
            respuesta.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }
    }

    /**
     * Endpoint para registrar un nuevo usuario.
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
