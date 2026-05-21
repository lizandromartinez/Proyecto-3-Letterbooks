package mx.unam.ciencias.myp.letterbooks.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.unam.ciencias.myp.letterbooks.modelo.Libro;
import mx.unam.ciencias.myp.letterbooks.repositorio.LibroRepositorio;

/**
 * Servicio para la gestión y consulta de libros.
 */
@Service
public class LibroServicio {

    /**
     * Repositorio del libro
     **/
    @Autowired
    private LibroRepositorio libroRepositorio;

    /**
     * Obtiene todos los libros disponibles.
     * @return lista de todos los libros
     */
    public List<Libro> obtenerTodos() {
        return libroRepositorio.findAll();
    }

    /**
     * Busca libros cuyo título contenga la cadena dada.
     * @param titulo cadena a buscar
     * @return lista de libros que coinciden
     */
    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepositorio.encontrarPorTituloContiene(titulo);
    }

    /**
     * Obtiene un libro por su ID.
     * @param idLibro identificador del libro
     * @return el libro encontrado
     */
    public Libro obtenerPorId(Integer idLibro) {
        return libroRepositorio.encontrarPorId(idLibro)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado: " + idLibro));
    }
}
