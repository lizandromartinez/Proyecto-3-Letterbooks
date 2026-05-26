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
     * @param datosFormulario DTO que recibe el JSON de la web. La anotación {@code @Valid} 
     * activa las reglas como {@code @NotBlank} o {@code @Min} declaradas en el DTO.
     * @return El libro recién creado transformado en VistaLibro.
     */
    @PostMapping
    public ResponseEntity<VistaLibro> registrarLibro(@Valid @RequestBody RegistroLibro datosFormulario) {

        VistaLibro libroCreado = libroServicio.registrar(datosFormulario);        
	return new ResponseEntity<VistaLibro>(libroCreado, HttpStatus.CREATED);
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
    
}
