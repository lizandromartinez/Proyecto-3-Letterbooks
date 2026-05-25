package mx.unam.ciencias.myp.letterbooks.repositorio;

import mx.unam.ciencias.myp.letterbooks.modelo.LikesCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de acceso a datos para la entidad LikesCita.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y define consultas personalizadas basadas en convenciones de Spring Data JPA.
 */
@Repository
public interface LikesCitaRepositorio extends JpaRepository<LikesCita, Integer> {
    
    /**
     * Busca si un usuario ya le dio like a una cita específica.
     * @param idUsuario id del usuario
     * @param idCita id de la cita
     * @return un Optional con el Like si existe
     */
    Optional<LikesCita> findByUsuario_IdUsuarioAndCita_IdCita(Integer idUsuario, Integer idCita);
}
