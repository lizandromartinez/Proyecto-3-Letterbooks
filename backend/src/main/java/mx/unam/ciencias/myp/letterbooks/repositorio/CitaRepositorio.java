package mx.unam.ciencias.myp.letterbooks.repositorio;

import mx.unam.ciencias.myp.letterbooks.modelo.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de acceso a datos para la entidad Cita.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 */
@Repository
public interface CitaRepositorio extends JpaRepository<Cita, Integer> {
}
