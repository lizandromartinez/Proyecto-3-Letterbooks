package mx.unam.ciencias.myp.letterbooks.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.unam.ciencias.myp.letterbooks.servicio.LibroServicio;
import mx.unam.ciencias.myp.letterbooks.dto.RegistroLibro;
import mx.unam.ciencias.myp.letterbooks.dto.VistaLibro;

import jakarta.validation.Valid;

/**
 * Controlador encargado de recibir las peticiones HTTP para el módulo de libros.
 * Se encarga de exponer los "endpoints" (URLs) para subir libros y ver sus detalles,
 * delegando toda la lógica interna al Servicio.
 */
@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*")
public class LibroControlador {

    private final LibroServicio libroServicio;

    /**
     * Constructor que conecta el Servicio de libros mediante inyección de dependencias.
     */
    public LibroControlador(LibroServicio libroServicio) {
        this.libroServicio = libroServicio;
    }

    /**
     * Endpoint para registrar o subir un nuevo libro al sistema.
     * Escucha peticiones POST en la URL: /api/libros
     *
     * @param datosFormulario DTO que recibe el JSON de la web.
     * @param encabezadoAutorizacion encabezado HTTP con el token JWT de sesión.
     * @return El libro recién creado transformado en VistaLibro.
     */
    @PostMapping
    public ResponseEntity<?> registrarLibro(
            @Valid @RequestBody RegistroLibro datosFormulario,
            @RequestHeader("Authorization") String encabezadoAutorizacion) {
        try {
            String token = extraerToken(encabezadoAutorizacion);
            VistaLibro libroCreado = libroServicio.registrar(datosFormulario, token);        
            return new ResponseEntity<>(libroCreado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al registrar el libro.");
        }
    }

    /**
     * Endpoint para editar un libro existente.
     * Escucha peticiones PUT en la URL: /api/libros/{id}
     *
     * @param id identificador del libro a editar.
     * @param datosFormulario nuevos datos del libro.
     * @param encabezadoAutorizacion encabezado HTTP con el token JWT de sesión.
     * @return El libro actualizado en formato VistaLibro.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editarLibro(
            @PathVariable("id") Integer id,
            @Valid @RequestBody RegistroLibro datosFormulario,
            @RequestHeader("Authorization") String encabezadoAutorizacion) {
        try {
            String token = extraerToken(encabezadoAutorizacion);
            VistaLibro libroActualizado = libroServicio.editar(id, datosFormulario, token);
            return ResponseEntity.ok(libroActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al editar el libro.");
        }
    }

    /**
     * Endpoint para consultar el detalle de un libro específico mediante su ID.
     * Escucha peticiones GET en la URL: /api/libros/{id} (ejemplo: /api/libros/42)
     *
     * @param id El identificador único del libro, extraído directamente de la URL.
     * @return El DTO VistaLibro con los datos del libro.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VistaLibro> obtenerPorId(@PathVariable("id") Integer id) {
        VistaLibro libroVista = libroServicio.obtenerPorId(id);        
        return ResponseEntity.ok(libroVista);
    }

    /**
     * Extrae de forma segura el token JWT del encabezado HTTP Authorization.
     */
    private String extraerToken(String encabezado) {
        if (encabezado == null || !encabezado.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token de autorización inválido o ausente.");
        }
        return encabezado.substring(7);
    }
}
