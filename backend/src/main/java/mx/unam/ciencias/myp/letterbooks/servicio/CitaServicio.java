package mx.unam.ciencias.myp.letterbooks.servicio;

import mx.unam.ciencias.myp.letterbooks.modelo.Cita;
import mx.unam.ciencias.myp.letterbooks.modelo.LikesCita;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.repositorio.CitaRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.LikesCitaRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.UsuarioRepositorio;
import mx.unam.ciencias.myp.letterbooks.seguridad.TokenJWT;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Servicio encargado de la lógica de negocio para las citas.
 * Gestiona operaciones independientes de la reseña, como las interacciones (likes).
 */
@Service
public class CitaServicio {

    private final CitaRepositorio citaRepositorio;
    private final LikesCitaRepositorio likesCitaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final TokenJWT tokenJWT;

    /**
     * Constructor con inyección de dependencias.
     * @param citaRepositorio repositorio de citas.
     * @param likesCitaRepositorio repositorio de likes de citas.
     * @param usuarioRepositorio repositorio de cuentas.
     * @param tokenJWT utilidad para tokens JWT.
     */
    public CitaServicio(CitaRepositorio citaRepositorio,
                        LikesCitaRepositorio likesCitaRepositorio,
                        UsuarioRepositorio usuarioRepositorio,
                        TokenJWT tokenJWT) {
        this.citaRepositorio = citaRepositorio;
        this.likesCitaRepositorio = likesCitaRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.tokenJWT = tokenJWT;
    }

    /**
     * Alterna (Agrega o Quita) un like de un usuario sobre una cita.
     * @param idCita identificador de la cita a interactuar.
     * @param token JWT del usuario que da clic al botón de me gusta.
     * @return true si el like se agregó, false si se retiró.
     * @throws IllegalArgumentException si el usuario o la cita no existen.
     */
    @Transactional
    public boolean alternarLikeCita(Integer idCita, String token) {
        String nombreUsuario = tokenJWT.obtenerNombreUsuario(token);
        
        Usuario usuario = usuarioRepositorio.encontrarPorNombreUsuario(nombreUsuario)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
            
        Cita cita = citaRepositorio.findById(idCita)
            .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada."));

        Optional<LikesCita> likeExistente = likesCitaRepositorio
            .findByUsuario_IdUsuarioAndCita_IdCita(usuario.getIdUsuario(), cita.getIdCita());

        if (likeExistente.isPresent()) {
            likesCitaRepositorio.delete(likeExistente.get());
            cita.setLikes(cita.getLikes() - 1);
            citaRepositorio.save(cita);
            return false; 
        } else {
            LikesCita nuevoLike = new LikesCita();
            nuevoLike.setUsuario(usuario);
            nuevoLike.setCita(cita);
            nuevoLike.setFecha(LocalDate.now().toString());
            likesCitaRepositorio.save(nuevoLike);
            
            cita.setLikes(cita.getLikes() + 1);
            citaRepositorio.save(cita);
            return true; 
        }
    }
}
