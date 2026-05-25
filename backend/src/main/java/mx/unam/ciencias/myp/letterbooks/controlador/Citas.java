package mx.unam.ciencias.myp.letterbooks.controlador;

import mx.unam.ciencias.myp.letterbooks.servicio.CitaServicio;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador REST para exponer los endpoints de gestión de citas.
 * Expone rutas para interactuar de forma independiente con las citas publicadas.
 */
@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class Citas {

    private final CitaServicio citaServicio;

    /**
     * Constructor con inyección de dependencias.
     * @param citaServicio servicio para gestionar operaciones de citas.
     */
    public Citas(CitaServicio citaServicio) {
        this.citaServicio = citaServicio;
    }

    /**
     * Endpoint protegido para alternar el "Me gusta" de una cita.
     * Atrapa la excepción de integridad para evitar condiciones de carrera.
     * @param idCita el identificador de la cita.
     * @param encabezadoAutorizacion el encabezado HTTP con el token JWT.
     * @return objeto JSON con el nuevo estado del like (likeActivo: true/false).
     */
    @PostMapping("/{idCita}/like")
    public ResponseEntity<?> alternarLikeCita(
            @PathVariable Integer idCita,
            @RequestHeader("Authorization") String encabezadoAutorizacion) {
        try {
            String token = extraerToken(encabezadoAutorizacion);
            boolean estadoLike = citaServicio.alternarLikeCita(idCita, token);
            return ResponseEntity.ok(Map.of("likeActivo", estadoLike));
            
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Solicitud de like duplicada.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor al procesar el like.");
        }
    }

    /**
     * Extrae de forma segura el token JWT del encabezado HTTP Authorization.
     * @param encabezado el valor crudo del encabezado de la petición.
     * @return la cadena de texto con el token limpio.
     * @throws IllegalArgumentException si el encabezado no existe.
     */
    private String extraerToken(String encabezado) {
        if (encabezado == null || !encabezado.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token de autorización inválido o ausente.");
        }
        return encabezado.substring(7);
    }
}
