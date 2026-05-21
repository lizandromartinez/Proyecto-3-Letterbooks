package mx.unam.ciencias.myp.letterbooks.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.unam.ciencias.myp.letterbooks.modelo.Autor;
import mx.unam.ciencias.myp.letterbooks.repositorio.AutorRepositorio;

/**
 * Servicio para la gestión y consulta de autores.
 */
@Service
public class AutorServicio {

    /**
     * Repositorio del autor
     **/
    @Autowired
    private AutorRepositorio autorRepositorio;

    /**
     * Obtiene todos los autores disponibles.
     * @return lista de todos los autores
     */
    public List<Autor> obtenerTodos() {
        return autorRepositorio.findAll();
    }

    /**
     * Busca autores cuyo nombre contenga la cadena dada.
     * @param nombre cadena a buscar
     * @return lista de autores que coinciden
     */
    public List<Autor> buscarPorNombre(String nombre) {
        return autorRepositorio.encontrarPorNombreContiene(nombre);
    }

    /**
     * Obtiene un autor por su ID.
     * @param idAutor identificador del autor
     * @return el autor encontrado
     */
    public Autor obtenerPorId(Integer idAutor) {
        return autorRepositorio.encontrarPorId(idAutor)
            .orElseThrow(() -> new RuntimeException("Autor no encontrado: " + idAutor));
    }
}
