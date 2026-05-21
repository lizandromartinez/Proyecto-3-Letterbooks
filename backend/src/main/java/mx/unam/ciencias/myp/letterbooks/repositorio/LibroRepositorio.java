package mx.unam.ciencias.myp.letterbooks.repositorio;

import mx.unam.ciencias.myp.letterbooks.modelo.Libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestión de libros en la base de datos.
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Integer> {
}
