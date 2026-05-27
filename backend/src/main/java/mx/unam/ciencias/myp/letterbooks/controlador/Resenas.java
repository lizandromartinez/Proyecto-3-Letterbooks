package mx.unam.ciencias.myp.letterbooks.controlador;

import mx.unam.ciencias.myp.letterbooks.dto.NuevaResena;
import mx.unam.ciencias.myp.letterbooks.dto.VistaResenaReciente;
import mx.unam.ciencias.myp.letterbooks.modelo.Resena;
import mx.unam.ciencias.myp.letterbooks.servicio.ResenaServicio;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST para exponer los endpoints de gestión de reseñas.
 * Expone las rutas para consultar, crear, editar y eliminar reseñas de libros,
 * integrando la validación de tokens JWT en las operaciones de escritura.
 */
@RestController
@RequestMapping("/api/resenas")
@CrossOrigin(origins = "*")
public class Resenas {

    /** Servicio que contiene la lógica de negocio de las reseñas. */
    private final ResenaServicio resenaServicio;

    /**
     * Constructor con inyección de dependencias.
     * @param resenaServicio servicio para gestionar operaciones de reseñas
     */
    public Resenas(ResenaServicio resenaServicio) {
        this.resenaServicio = resenaServicio;
    }

    /**
     * Endpoint público para consultar todas las reseñas de un libro.
     * @param idLibro el identificador único del libro
     * @return respuesta HTTP 200 con la lista de reseñas asociadas al libro
     */
    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<List<Resena>> obtenerResenasPorLibro(@PathVariable("idLibro") Integer idLibro) {
        List<Resena> resenas = resenaServicio.obtenerResenasPorLibro(idLibro);
        return ResponseEntity.ok(resenas);
    }

    /**
     * Endpoint público para consultar las reseñas más recientes de la landing.
     * @param limite cantidad máxima de reseñas (por defecto 5)
     * @return respuesta HTTP 200 con la lista de reseñas recientes
     */
    @GetMapping("/recientes")
    public ResponseEntity<List<VistaResenaReciente>> obtenerResenasRecientes(
            @RequestParam(value = "limite", defaultValue = "5") int limite) {
        return ResponseEntity.ok(resenaServicio.obtenerRecientes(limite));
    }

    /**
     * Endpoint protegido para crear una nueva reseña en el sistema con sus citas.
     * @param datosResena el DTO con la información validada del formulario
     * @param encabezadoAutorizacion el encabezado HTTP con el token JWT de sesión
     * @return respuesta HTTP 201 con la entidad creada, o HTTP 400 si hay error de validación
     */
    @PostMapping
    public ResponseEntity<?> crearResena(
            @Valid @RequestBody NuevaResena datosResena,
            @RequestHeader("Authorization") String encabezadoAutorizacion) {
        try {
            String token = extraerToken(encabezadoAutorizacion);
            Resena nuevaResena = resenaServicio.crearResena(datosResena, token);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaResena);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor al crear la reseña.");
        }
    }

    /**
     * Endpoint protegido para editar una reseña existente y sus citas.
     * @param idResena el identificador de la reseña a modificar
     * @param datosResena el DTO con los nuevos datos ingresados
     * @param encabezadoAutorizacion el encabezado HTTP con el token JWT de sesión
     * @return respuesta HTTP 200 con la entidad actualizada, o HTTP 400 si el autor no coincide
     */
    @PutMapping("/{idResena}")
    public ResponseEntity<?> editarResena(
            @PathVariable("idResena") Integer idResena,
            @Valid @RequestBody NuevaResena datosResena,
            @RequestHeader("Authorization") String encabezadoAutorizacion) {
        try {
            String token = extraerToken(encabezadoAutorizacion);
            Resena resenaActualizada = resenaServicio.editarResena(idResena, datosResena, token);
            return ResponseEntity.ok(resenaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor al editar la reseña.");
        }
    }

    /**
     * Endpoint protegido para eliminar una reseña del sistema y sus citas en cascada.
     * @param idResena el identificador único de la reseña a borrar
     * @param encabezadoAutorizacion el encabezado HTTP con el token JWT de sesión
     * @return respuesta HTTP 200 indicando éxito, o HTTP 400 si el autor no coincide
     */
    @DeleteMapping("/{idResena}")
    public ResponseEntity<?> eliminarResena(
            @PathVariable("idResena") Integer idResena,
            @RequestHeader("Authorization") String encabezadoAutorizacion) {
        try {
            String token = extraerToken(encabezadoAutorizacion);
            resenaServicio.eliminarResena(idResena, token);
            return ResponseEntity.ok("Reseña eliminada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor al eliminar la reseña.");
        }
    }

    /**
     * Extrae de forma segura el token JWT del encabezado HTTP Authorization.
     * @param encabezado el valor crudo del encabezado de la petición
     * @return la cadena de texto con el token limpio
     * @throws IllegalArgumentException si el encabezado no existe o su formato es incorrecto
     */
    private String extraerToken(String encabezado) {
        if (encabezado == null || !encabezado.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token de autorización inválido o ausente.");
        }
        return encabezado.substring(7);
    }
}
