package mx.unam.ciencias.myp.letterbooks.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.unam.ciencias.myp.letterbooks.modelo.Cita;

/**
 * Repositorio de acceso a datos para la entidad Cita.
 * <p>
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 * </p>
 */
@Repository
public interface CitaRepositorio extends JpaRepository<Cita, Integer> {

    /**
     * Busca todas las citas de una reseña dada.
     * @param idResena identificador de la reseña
     * @return lista de citas de la reseña
     */
    @Query("SELECT c FROM Cita c WHERE c.resena.idResena = :idResena")
    List<Cita> encontrarPorResena(@Param("idResena") Integer idResena);
}
