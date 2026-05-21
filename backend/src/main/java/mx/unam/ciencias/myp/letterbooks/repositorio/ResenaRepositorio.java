package mx.unam.ciencias.myp.letterbooks.repositorio;

import mx.unam.ciencias.myp.letterbooks.modelo.Resena;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la gestión de reseñas en la base de datos.
 * Proporciona métodos para almacenar, buscar y actualizar reseñas.
 */
@Repository
public interface ResenaRepositorio extends JpaRepository<Resena, Integer> {

    /**
     * Busca y regresa todas las reseñas asociadas a un libro específico.
     * @param idLibro el identificador único del libro.
     * @return una lista con las reseñas pertenecientes al libro.
     */
    List<Resena> findByLibro_IdLibro(Integer idLibro);
}
