package mx.unam.ciencias.myp.letterbooks.servicio;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.dto.Perfil;
import mx.unam.ciencias.myp.letterbooks.dto.ActualizarPerfil;
import mx.unam.ciencias.myp.letterbooks.repositorio.CalificacionResenaRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.LikesComentarioRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.LikesResenaRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.PerfilRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.ResenaRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.LibroRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.AutorRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.GeneroRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.UsuarioRepositorio;
// import mx.unam.ciencias.myp.letterbooks.modelo.Libro;
// import mx.unam.ciencias.myp.letterbooks.modelo.Autor;
// import mx.unam.ciencias.myp.letterbooks.modelo.Genero;

/**
 * Servicio para la gestión y consulta del perfil de un usuario.
 * <p>
 * Se encarga de obtener los datos del perfil desde los repositorios
 * y construir el DTO correspondiente para su uso en el frontend.
 * </p>
 */
@Service
public class PerfilServicio {

    /* Repositorio del usuario asociado al perfil. */
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    /* Repositorio de perfiles (acceso a datos de perfil). */
    @Autowired
    private PerfilRepositorio perfilRepositorio;

    /* Repositorio de libro. */
    @Autowired
    private LibroRepositorio libroRepositorio;

    /* Repositorio de autor. */
    @Autowired
    private AutorRepositorio autorRepositorio;

    /* Repositorio de genero. */
    @Autowired
    private GeneroRepositorio generoRepositorio;
    
    /* Repositorio de likes en reseñas. */
    @Autowired
    private LikesResenaRepositorio likesResenaRepositorio;

    /* Repositorio de likes en comentarios. */
    @Autowired
    private LikesComentarioRepositorio likesComentarioRepositorio;

    /* Repositorio de calificaciones de reseñas. */
    @Autowired
    private CalificacionResenaRepositorio calificacionResenaRepositorio;

    /* Repositorio de reseñas. */
    @Autowired
    private ResenaRepositorio resenaRepositorio;

    /**
     * Obtiene el perfil completo de un usuario dado su ID.
     * @param idUsuario identificador del usuario
     * @return DTO con los datos del perfil del usuario
     * @throws RuntimeException si no existe un perfil para ese usuario
     */
    @Transactional
    public Perfil obtenerPerfilPorUsuario(Integer idUsuario) {
	mx.unam.ciencias.myp.letterbooks.modelo.Perfil perfil =
            perfilRepositorio.encontrarPorUsuario(idUsuario)
		.orElseThrow(() -> new RuntimeException("Perfil no encontrado para el usuario: " + idUsuario));
	
	return construirDTO(perfil, idUsuario);
    }

    @Transactional
    public Perfil obtenerPerfilPorNombreUsuario(String nombreUsuario) {
	Usuario usuario = usuarioRepositorio.encontrarPorNombreUsuario(nombreUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + nombreUsuario));
	return obtenerPerfilPorUsuario(usuario.getIdUsuario());
    }

    /**
     * Construye el DTO de perfil a partir de la entidad Perfil y el ID del usuario.
     * @param perfil entidad Perfil obtenida de la base de datos
     * @param idUsuario identificador del usuario
     * @return DTO con todos los datos del perfil
     */
    private Perfil construirDTO(mx.unam.ciencias.myp.letterbooks.modelo.Perfil perfil, Integer idUsuario) {
        Perfil dto = new Perfil();

        // Datos que vienen en los campos del perfil
        dto.setBiografia(perfil.getBiografia());
        dto.setAvatar(perfil.getAvatar());
        dto.setBanner(perfil.getBanner());
        dto.setFechaRegistro(perfil.getFechaRegistro());

	if (perfil.getUsuario() != null)
	    dto.setNombreUsuario(perfil.getUsuario().getNombreUsuario());		
	
        // Autor, género y libro favorito
	dto.setLibroFavorito(perfil.getLibro() != null ? perfil.getLibro().getTitulo() : "Ninguno");	
	dto.setAutorFavorito(perfil.getAutor() != null ? perfil.getAutor().getNombreAutor() : "Ninguno");
	dto.setGeneroFavorito(perfil.getGenero() != null ? perfil.getGenero().getNombreGenero() : "Ninguno");
	
        // Reseñas likeadas
        dto.setResenasLikeadas(construirResenasLikeadas(idUsuario));
        // Reseñas calificadas
        dto.setResenasCalificadas(construirResenasCalificadas(idUsuario));
        // Libros calificados
        dto.setLibrosCalificados(construirLibrosCalificados(idUsuario));
        // Comentarios likeados
        dto.setComentariosLikeados(construirComentariosLikeados(idUsuario));

        return dto;
    }

    /**
     * Construye la lista de reseñas a las que el usuario ha dado like.
     * @param idUsuario identificador del usuario
     * @return lista de DTOs de reseñas likeadas
     */
    private List<Perfil.ResenaLikeDTO> construirResenasLikeadas(Integer idUsuario) {
        return likesResenaRepositorio.encontrarPorUsuario(idUsuario)
            .stream()
            .map(like -> {
            Perfil.ResenaLikeDTO resenaLikeDTO = new Perfil.ResenaLikeDTO();
            resenaLikeDTO.setIdResena(like.getResena().getIdResena());
            resenaLikeDTO.setTituloLibro(like.getResena().getLibro().getTitulo());
            resenaLikeDTO.setAutorResena(like.getResena().getUsuario().getNombreUsuario());
            resenaLikeDTO.setTextoResena(like.getResena().getTextoResena());
            resenaLikeDTO.setFechaLike(like.getFecha());
            return resenaLikeDTO;
        })
            .collect(Collectors.toList());
    }

    /**
     * Construye la lista de reseñas que el usuario ha calificado.
     * @param idUsuario identificador del usuario
     * @return lista de DTOs de reseñas calificadas
     */
    private List<Perfil.ResenaCalificadaDTO> construirResenasCalificadas(Integer idUsuario) {
        return calificacionResenaRepositorio.encontrarPorUsuario(idUsuario)
            .stream()
            .map(calificacion -> {
            Perfil.ResenaCalificadaDTO resenaCalificadaDTO = new Perfil.ResenaCalificadaDTO();
            resenaCalificadaDTO.setIdResena(calificacion.getResena().getIdResena());
            resenaCalificadaDTO.setTituloLibro(calificacion.getResena().getLibro().getTitulo());
            resenaCalificadaDTO.setAutorResena(calificacion.getResena().getUsuario().getNombreUsuario());
            resenaCalificadaDTO.setTextoResena(calificacion.getResena().getTextoResena());
            resenaCalificadaDTO.setCalificacion(calificacion.getCalificacion());
            resenaCalificadaDTO.setFechaCalificacion(calificacion.getFecha());
            return resenaCalificadaDTO;
        })
            .collect(Collectors.toList());
    }

    /**
     * Construye la lista de libros calificados por el usuario a través de sus reseñas.
     * @param idUsuario identificador del usuario
     * @return lista de DTOs de libros calificados
     */
    private List<Perfil.LibroCalificadoDTO> construirLibrosCalificados(Integer idUsuario) {
        return resenaRepositorio.encontrarPorUsuario(idUsuario)
            .stream()
            .filter(resena -> resena.getCalificacionLibro() != null)
            .map(resena -> {
            Perfil.LibroCalificadoDTO libroCalificadoDTO = new Perfil.LibroCalificadoDTO();
            libroCalificadoDTO.setIdLibro(resena.getLibro().getIdLibro());
            libroCalificadoDTO.setTitulo(resena.getLibro().getTitulo());
            libroCalificadoDTO.setAutor(resena.getLibro().getAutor().getNombreAutor());
            libroCalificadoDTO.setImagen(resena.getLibro().getImagen());
            libroCalificadoDTO.setCalificacion(resena.getCalificacionLibro());
            return libroCalificadoDTO;
        })
            .collect(Collectors.toList());
    }

    /**
     * Construye la lista de comentarios a los que el usuario ha dado like.
     * @param idUsuario identificador del usuario
     * @return lista de DTOs de comentarios likeados
     */
    private List<Perfil.ComentarioLikeDTO> construirComentariosLikeados(Integer idUsuario) {
        return likesComentarioRepositorio.encontrarPorUsuario(idUsuario)
            .stream()
            .map(like -> {
            Perfil.ComentarioLikeDTO comentarioLikeDTO = new Perfil.ComentarioLikeDTO();
            comentarioLikeDTO.setIdComentario(like.getComentario().getIdComentario());
            comentarioLikeDTO.setTexto(like.getComentario().getTexto());
            comentarioLikeDTO.setAutorComentario(like.getComentario().getUsuario().getNombreUsuario());
            comentarioLikeDTO.setTituloLibro(like.getComentario().getResena().getLibro().getTitulo());
            comentarioLikeDTO.setFechaLike(like.getFecha());
            return comentarioLikeDTO;
        })
            .collect(Collectors.toList());
    }

    /**
     * Actualiza los campos editables del perfil de un usuario.
     * Este método permite modificar información personal del perfil,
     * incluyendo biografía, avatar, banner y libro, género y autor favorito
     *
     * @param idUsuario identificador del usuario dueño del perfil
     * @param datos DTO con los nuevos datos del perfil a actualizar
     * @return DTO del perfil actualizado
     * @throws RuntimeException si el perfil no existe o si alguno de los
     *         identificadores de libro, autor o género no corresponde
     *         a una entidad válida
     */
    @Transactional
    public Perfil actualizarPerfil(Integer idUsuario, ActualizarPerfil datos) {
	mx.unam.ciencias.myp.letterbooks.modelo.Perfil perfil =
            perfilRepositorio.encontrarPorUsuario(idUsuario)
		.orElseThrow(() -> new RuntimeException("Perfil no encontrado para el usuario: " + idUsuario));

	if (datos.getBiografia() != null)
            perfil.setBiografia(datos.getBiografia());
	if (datos.getAvatar() != null)
            perfil.setAvatar(datos.getAvatar());
	if (datos.getBanner() != null)
            perfil.setBanner(datos.getBanner());
	if (datos.getIdAutorFavorito() != null)
            perfil.setAutor(autorRepositorio.encontrarPorId(datos.getIdAutorFavorito())
		.orElseThrow(() -> new RuntimeException("Autor no encontrado")));
	if (datos.getIdGeneroFavorito() != null)
            perfil.setGenero(generoRepositorio.encontrarPorId(datos.getIdGeneroFavorito())
		.orElseThrow(() -> new RuntimeException("Género no encontrado")));
	if (datos.getIdLibroFavorito() != null)
            perfil.setLibro(libroRepositorio.encontrarPorId(datos.getIdLibroFavorito())
		.orElseThrow(() -> new RuntimeException("Libro no encontrado")));

	perfilRepositorio.save(perfil);
	return construirDTO(perfil, idUsuario);
    }
}
