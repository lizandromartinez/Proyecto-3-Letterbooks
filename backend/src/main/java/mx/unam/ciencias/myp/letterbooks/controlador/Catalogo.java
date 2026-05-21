package mx.unam.ciencias.myp.letterbooks.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mx.unam.ciencias.myp.letterbooks.modelo.Autor;
import mx.unam.ciencias.myp.letterbooks.modelo.Genero;
import mx.unam.ciencias.myp.letterbooks.modelo.Libro;
import mx.unam.ciencias.myp.letterbooks.servicio.AutorServicio;
import mx.unam.ciencias.myp.letterbooks.servicio.GeneroServicio;
import mx.unam.ciencias.myp.letterbooks.servicio.LibroServicio;

/**
 * Controlador REST para la consulta del catálogo de libros, autores y géneros.
 * <p>
 * Expone endpoints públicos para obtener las listas necesarias
 * al momento de editar el perfil del usuario.
 * </p>
 */
@RestController
@RequestMapping("/api/catalogo")
@CrossOrigin(origins = "*")
public class Catalogo {

    /**
     * Servicio encargado de la lógica relacionada
     * con los libros del sistema.
     */
    @Autowired
    private LibroServicio libroServicio;

    /**
     * Servicio encargado de la lógica relacionada
     * con los autores del sistema.
     */
    @Autowired
    private AutorServicio autorServicio;

    /**
     * Servicio encargado de la lógica relacionada
     * con los géneros literarios del sistema.
     */
    @Autowired
    private GeneroServicio generoServicio;

    /**
     * Obtiene todos los libros disponibles.
     * @return lista de libros
     */
    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> obtenerLibros() {
        return ResponseEntity.ok(libroServicio.obtenerTodos());
    }

    /**
     * Busca libros por título.
     * @param titulo cadena a buscar en el título
     * @return lista de libros que coinciden
     */
    @GetMapping("/libros/buscar")
    public ResponseEntity<List<Libro>> buscarLibros(@RequestParam("titulo") String titulo) {
        return ResponseEntity.ok(libroServicio.buscarPorTitulo(titulo));
    }

    /**
     * Obtiene todos los autores disponibles.
     * @return lista de autores
     */
    @GetMapping("/autores")
    public ResponseEntity<List<Autor>> obtenerAutores() {
        return ResponseEntity.ok(autorServicio.obtenerTodos());
    }

    /**
     * Busca autores por nombre.
     * @param nombre cadena a buscar en el nombre del autor
     * @return lista de autores que coinciden
     */
    @GetMapping("/autores/buscar")
    public ResponseEntity<List<Autor>> buscarAutores(@RequestParam("nombre") String nombre) {
        return ResponseEntity.ok(autorServicio.buscarPorNombre(nombre));
    }

    /**
     * Obtiene todos los géneros disponibles.
     * @return lista de géneros
     */
    @GetMapping("/generos")
    public ResponseEntity<List<Genero>> obtenerGeneros() {
        return ResponseEntity.ok(generoServicio.obtenerTodos());
    }
}
