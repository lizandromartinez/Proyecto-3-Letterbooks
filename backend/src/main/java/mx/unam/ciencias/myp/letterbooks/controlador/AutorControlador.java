package mx.unam.ciencias.myp.letterbooks.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.unam.ciencias.myp.letterbooks.servicio.AutorServicio;
import mx.unam.ciencias.myp.letterbooks.dto.RegistroAutor;
import mx.unam.ciencias.myp.letterbooks.modelo.Autor;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador encargado de recibir las peticiones HTTP para el módulo de autores.
 * Se encarga de exponer los "endpoints" (URLs) para registrar, editar y consultar
 * autores, delegando toda la lógica interna al Servicio.
 */
@RestController
@RequestMapping("/api/autores")
@CrossOrigin(origins = "*")
public class AutorControlador {

    private final AutorServicio autorServicio;

    /**
     * Constructor que conecta el Servicio de autores mediante inyección de dependencias.
     */
    public AutorControlador(AutorServicio autorServicio) {
        this.autorServicio = autorServicio;
    }

    /**
     * Endpoint para registrar o subir un nuevo autor al sistema.
     * Escucha peticiones POST en la URL: /api/autores
     *
     * @param datosFormulario DTO que recibe el JSON de la web con los datos del autor.
     * @return El autor recién creado con su ID asignado.
     */
    @PostMapping
    public ResponseEntity<?> registrarAutor(@Valid @RequestBody RegistroAutor datosFormulario) {
        try {
            Autor autorCreado = autorServicio.registrar(datosFormulario);        
            return new ResponseEntity<>(autorCreado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al registrar el autor.");
        }
    }

    /**
     * Endpoint para editar un autor existente.
     * Escucha peticiones PUT en la URL: /api/autores/{id}
     * Requiere token de autorización ya que la edición está restringida por roles.
     *
     * @param id identificador del autor a editar.
     * @param datosFormulario nuevos datos del autor.
     * @param encabezadoAutorizacion encabezado HTTP con el token JWT de sesión.
     * @return El autor actualizado en la base de datos.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editarAutor(
            @PathVariable("id") Integer id,
            @Valid @RequestBody RegistroAutor datosFormulario,
            @RequestHeader("Authorization") String encabezadoAutorizacion) {
        try {
            String token = extraerToken(encabezadoAutorizacion);
            Autor autorActualizado = autorServicio.editar(id, datosFormulario, token);
            return ResponseEntity.ok(autorActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al editar el autor.");
        }
    }

    /**
     * Endpoint para obtener la lista de todos los autores disponibles.
     * Escucha peticiones GET en la URL: /api/autores
     * Útil para repoblar los selects/dropdowns en los formularios del Frontend.
     *
     * @return Lista completa de autores.
     */
    @GetMapping
    public ResponseEntity<List<Autor>> obtenerTodos() {
        List<Autor> autores = autorServicio.obtenerTodos();
        return ResponseEntity.ok(autores);
    }

    /**
     * Endpoint para consultar el detalle de un autor específico mediante su ID.
     * Escucha peticiones GET en la URL: /api/autores/{id}
     *
     * @param id El identificador único del autor, extraído directamente de la URL.
     * @return La entidad Autor con sus datos correspondientes.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable("id") Integer id) {
        try {
            Autor autor = autorServicio.obtenerPorId(id);        
            return ResponseEntity.ok(autor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
