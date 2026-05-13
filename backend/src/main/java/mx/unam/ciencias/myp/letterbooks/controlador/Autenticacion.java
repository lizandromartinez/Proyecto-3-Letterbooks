package mx.unam.ciencias.myp.letterbooks.controlador;

import mx.unam.ciencias.myp.letterbooks.dto.InicioDeSesion;
import mx.unam.ciencias.myp.letterbooks.servicio.AutenticacionServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa un controlador REST para exponer los endpoints de autenticación.
 * Permite el acceso desde orígenes cruzados para la integración con el frontend.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen (ajustar en producción)
public class Autenticacion {

    @Autowired
    private AutenticacionServicio autenticacionServicio;

    /**
     * Endpoint público para iniciar sesión.
     * @param datosInicioDeSesion Objeto JSON validado que contiene correo y contraseña.
     * @return Respuesta HTTP con el token JWT si es exitoso, o código 401 si falla.
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
}
