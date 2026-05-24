package mx.unam.ciencias.myp.letterbooks.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.unam.ciencias.myp.letterbooks.modelo.Genero;
import mx.unam.ciencias.myp.letterbooks.repositorio.GeneroRepositorio;

/**
 * Servicio para la gestión y consulta de géneros.
 */
@Service
public class GeneroServicio {

    /**
     * Repositorio del género
     **/
    @Autowired
    private GeneroRepositorio generoRepositorio;

    /**
     * Obtiene todos los géneros disponibles.
     * @return lista de todos los géneros
     */
    public List<Genero> obtenerTodos() {
        return generoRepositorio.findAll();
    }

    /**
     * Obtiene un género por su ID.
     * @param idGenero identificador del género
     * @return el género encontrado
     */
    public Genero obtenerPorId(Integer idGenero) {
        return generoRepositorio.findById(idGenero)
            .orElseThrow(() -> new RuntimeException("Género no encontrado: " + idGenero));
    }
}
