package mx.unam.ciencias.myp.letterbooks.servicio;

import mx.unam.ciencias.myp.letterbooks.dto.NuevaCita;
import mx.unam.ciencias.myp.letterbooks.dto.NuevaResena;
import mx.unam.ciencias.myp.letterbooks.dto.VistaCitaReciente;
import mx.unam.ciencias.myp.letterbooks.dto.VistaResenaReciente;
import mx.unam.ciencias.myp.letterbooks.modelo.Cita;
import mx.unam.ciencias.myp.letterbooks.modelo.Libro;
import mx.unam.ciencias.myp.letterbooks.modelo.LikesResena;
import mx.unam.ciencias.myp.letterbooks.modelo.Perfil;
import mx.unam.ciencias.myp.letterbooks.modelo.Resena;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.repositorio.CitaRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.ComentarioRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.LibroRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.LikesResenaRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.PerfilRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.ResenaRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.UsuarioRepositorio;
import mx.unam.ciencias.myp.letterbooks.seguridad.TokenJWT;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de la lógica de negocio para las reseñas.
 * Gestiona operaciones de creación, edición y eliminación de reseñas,
 * vinculando las citas adjuntas mediante relaciones bidireccionales y asegurando
 * la actualización síncrona del promedio de calificaciones del libro.
 */
@Service
public class ResenaServicio {

    private final ResenaRepositorio resenaRepositorio;
    private final LibroRepositorio libroRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    /**
     * Repositorio de citas literarias asociadas a reseñas.
     */
    private final CitaRepositorio citaRepositorio;

    /**
     * Repositorio de comentarios en reseñas.
     */
    private final ComentarioRepositorio comentarioRepositorio;

    /**
     * Repositorio de perfiles de usuario.
     */
    private final PerfilRepositorio perfilRepositorio;

    /**
     * Repositorio de likes en reseñas.
     */
    private final LikesResenaRepositorio likesResenaRepositorio;

    private final TokenJWT tokenJWT;

    /**
     * Constructor con inyección de dependencias.
     * @param resenaRepositorio repositorio para acceder a las reseñas
     * @param libroRepositorio repositorio para acceder al catálogo de libros
     * @param usuarioRepositorio repositorio para acceder a los datos de cuentas
     * @param citaRepositorio repositorio para acceder a las citas
     * @param comentarioRepositorio repositorio para acceder a los comentarios
     * @param perfilRepositorio repositorio para acceder a los perfiles
     * @param likesResenaRepositorio repositorio para acceder a los likes de reseñas
     * @param tokenJWT utilidad para el procesamiento y lectura de firmas JWT
     */
    public ResenaServicio(ResenaRepositorio resenaRepositorio,
                          LibroRepositorio libroRepositorio,
                          UsuarioRepositorio usuarioRepositorio,
                          CitaRepositorio citaRepositorio,
                          ComentarioRepositorio comentarioRepositorio,
                          PerfilRepositorio perfilRepositorio,
                          LikesResenaRepositorio likesResenaRepositorio,
                          TokenJWT tokenJWT) {
        this.resenaRepositorio = resenaRepositorio;
        this.libroRepositorio = libroRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.citaRepositorio = citaRepositorio;
        this.comentarioRepositorio = comentarioRepositorio;
        this.perfilRepositorio = perfilRepositorio;
        this.likesResenaRepositorio = likesResenaRepositorio;
        this.tokenJWT = tokenJWT;
    }

    /**
     * Crea una nueva reseña en el sistema y procesa sus citas asociadas.
     * @param datos DTO con la información y calificación de la nueva reseña
     * @param token cadena con el token JWT del usuario que realiza la operación
     * @return la entidad reseña recién creada y persistida en el sistema
     * @throws IllegalArgumentException si el usuario firmante o el libro no existen
     */
    @Transactional
    public Resena crearResena(NuevaResena datos, String token) {
        String nombreUsuario = tokenJWT.obtenerNombreUsuario(token);
        
        Usuario usuario = usuarioRepositorio.encontrarPorNombreUsuario(nombreUsuario)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
            
        Libro libro = libroRepositorio.findById(datos.getIdLibro())
            .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado."));

        Resena nuevaResena = new Resena();
        nuevaResena.setLibro(libro);
        nuevaResena.setUsuario(usuario);
        nuevaResena.setCalificacionLibro(datos.getCalificacionLibro());
        nuevaResena.setTextoResena(datos.getTextoResena());
        nuevaResena.setFechaPublicacion(LocalDate.now().toString());

        if (datos.getCitas() != null) {
            for (NuevaCita dtoCita : datos.getCitas()) {
                Cita nuevaCita = new Cita();
                nuevaCita.setTexto(dtoCita.getTexto());
                nuevaCita.setPagina(dtoCita.getPagina());
                nuevaResena.agregarCita(nuevaCita);
            }
        }

        Resena resenaGuardada = resenaRepositorio.save(nuevaResena);

        Double nuevoPromedio = calcularPromedioLibro(libro.getIdLibro());
        libro.setPromedioCalificacion(nuevoPromedio);
        libroRepositorio.save(libro);

        return resenaGuardada;
    }

    /**
     * Edita una reseña existente y actualiza sus citas asociadas.
     * @param idResena identificador único de la reseña que se desea modificar
     * @param datos DTO con los nuevos valores de texto y calificación del formulario
     * @param token cadena de autenticación JWT del usuario que solicita el cambio
     * @return la entidad de la reseña con las modificaciones guardadas en el sistema
     * @throws IllegalArgumentException si la reseña no existe o si la sesión no es del autor
     */
    @Transactional
    public Resena editarResena(Integer idResena, NuevaResena datos, String token) {
        Resena resena = resenaRepositorio.findById(idResena)
            .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada."));

        String nombreUsuarioFirma = tokenJWT.obtenerNombreUsuario(token);
        
        if (!resena.getUsuario().getNombreUsuario().equals(nombreUsuarioFirma)) {
            throw new IllegalArgumentException("No tienes permiso para editar esta reseña.");
        }

        resena.setCalificacionLibro(datos.getCalificacionLibro());
        resena.setTextoResena(datos.getTextoResena());

        // Limpieza segura de las citas previas para evitar registros huérfanos
        List<Cita> citasAnteriores = new ArrayList<>(resena.getCitas());
        for (Cita cita : citasAnteriores) {
            resena.removerCita(cita);
        }

        // Incorporación de las nuevas citas asignadas en la edición
        if (datos.getCitas() != null) {
            for (NuevaCita dtoCita : datos.getCitas()) {
                Cita nuevaCita = new Cita();
                nuevaCita.setTexto(dtoCita.getTexto());
                nuevaCita.setPagina(dtoCita.getPagina());
                resena.agregarCita(nuevaCita);
            }
        }

        Resena resenaActualizada = resenaRepositorio.save(resena);

        Libro libro = resena.getLibro();
        Double nuevoPromedio = calcularPromedioLibro(libro.getIdLibro());
        libro.setPromedioCalificacion(nuevoPromedio);
        libroRepositorio.save(libro);

        return resenaActualizada;
    }

    /**
     * Calcula el promedio de calificaciones de un libro basado en sus reseñas.
     * @param idLibro el identificador único del libro a evaluar
     * @return el promedio numérico de calificación obtenido, o cero si no cuenta con aportes
     */
    public Double calcularPromedioLibro(Integer idLibro) {
        List<Resena> resenas = resenaRepositorio.findByLibro_IdLibro(idLibro);
        
        if (resenas.isEmpty())
            return 0.0;

        double suma = 0;
        for (Resena resena : resenas) {
            suma += resena.getCalificacionLibro();
        }

        return suma / resenas.size();
    }

    /**
     * Recupera todas las reseñas asociadas a un libro específico inicializando sus citas.
     * Utiliza una transacción de solo lectura y una consulta estructurada con FETCH JOIN
     * para mitigar el problema de consultas N+1 y prevenir fallos de inicialización perezosa.
     * @param idLibro el identificador único del libro a consultar
     * @return una lista de entidades reseña pertenecientes al libro con sus citas cargadas
     */
    @Transactional(readOnly = true)
    public List<Resena> obtenerResenasPorLibro(Integer idLibro, String token) {
        List<Resena> resenas = resenaRepositorio.encontrarPorLibroConCitas(idLibro);
        marcarLikesActivos(resenas, token);
        return resenas;
    }

    /**
     * Obtiene las reseñas más recientes con datos del libro, usuario, citas y comentarios.
     * @param limite cantidad máxima de reseñas a devolver
     * @param token JWT opcional del usuario autenticado
     * @return lista de DTOs para la landing
     */
    @Transactional(readOnly = true)
    public List<VistaResenaReciente> obtenerRecientes(int limite, String token) {
        int cantidad = Math.max(1, limite);
        List<Resena> resenas = resenaRepositorio.encontrarRecientes(PageRequest.of(0, cantidad));
        List<VistaResenaReciente> resultado = new ArrayList<>();

        for (Resena resena : resenas) {
            VistaResenaReciente vista = new VistaResenaReciente();
            vista.setIdResena(resena.getIdResena());
            vista.setCalificacionLibro(resena.getCalificacionLibro());
            vista.setCalificacionResena(resena.getCalificacionResena());
            vista.setTextoResena(resena.getTextoResena());
            vista.setLikes(resena.getLikes());
            vista.setFechaPublicacion(resena.getFechaPublicacion());
            vista.setTotalComentarios(comentarioRepositorio.encontrarPorResena(resena.getIdResena()).size());

            Usuario usuario = resena.getUsuario();
            if (usuario != null) {
                vista.setNombreUsuario(usuario.getNombreUsuario());
                perfilRepositorio.encontrarPorUsuario(usuario.getIdUsuario())
                    .map(Perfil::getAvatar)
                    .ifPresent(vista::setAvatarUsuario);
            }

            Libro libro = resena.getLibro();
            if (libro != null) {
                vista.setIdLibro(libro.getIdLibro());
                vista.setTituloLibro(libro.getTitulo());
                vista.setImagenLibro(libro.getImagen());
                if (libro.getAutor() != null) {
                    vista.setNombreAutor(libro.getAutor().getNombreAutor());
                }
            }

            List<VistaCitaReciente> citas = citaRepositorio.encontrarPorResena(resena.getIdResena())
                .stream()
                .map(cita -> new VistaCitaReciente(cita.getTexto()))
                .toList();
            vista.setCitas(citas);
            vista.setLikeActivo(usuarioDioLike(resena.getIdResena(), token));
            resultado.add(vista);
        }

        return resultado;
    }

    private void marcarLikesActivos(List<Resena> resenas, String token) {
        Integer idUsuario = obtenerIdUsuarioDesdeToken(token);
        for (Resena resena : resenas) {
            if (idUsuario == null) {
                resena.setLikeActivo(false);
            } else {
                resena.setLikeActivo(
                    likesResenaRepositorio.existePorUsuarioYResena(idUsuario, resena.getIdResena())
                );
            }
        }
    }

    private boolean usuarioDioLike(Integer idResena, String token) {
        Integer idUsuario = obtenerIdUsuarioDesdeToken(token);
        if (idUsuario == null) {
            return false;
        }
        return likesResenaRepositorio.existePorUsuarioYResena(idUsuario, idResena);
    }

    private Integer obtenerIdUsuarioDesdeToken(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }
        try {
            String nombreUsuario = tokenJWT.obtenerNombreUsuario(token);
            return usuarioRepositorio.encontrarPorNombreUsuario(nombreUsuario)
                .map(Usuario::getIdUsuario)
                .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Elimina una reseña existente aplicando filtros estrictos de derecho de autoría.
     * <p>
     * <ul>
     * <li>Recupera la reseña solicitada del repositorio por su identificador</li>
     * <li>Verifica que el nombre de usuario del token coincida exactamente con el creador</li>
     * <li>Elimina el registro de la base de datos de manera definitiva</li>
     * <li>Actualiza en cascada el valor promedio acumulado de la entidad del libro</li>
     * </ul>
     * </p>
     *
     * @param idResena identificador único de la reseña que se desea eliminar
     * @param token cadena de autenticación JWT del usuario que solicita la eliminación
     * @throws IllegalArgumentException si la reseña no existe o si la sesión no es del autor
     */
    @Transactional
    public void eliminarResena(Integer idResena, String token) {
        Resena resena = resenaRepositorio.findById(idResena)
            .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada."));

        String nombreUsuarioFirma = tokenJWT.obtenerNombreUsuario(token);
        
        if (!resena.getUsuario().getNombreUsuario().equals(nombreUsuarioFirma)) {
            throw new IllegalArgumentException("No tienes permiso para eliminar esta reseña.");
        }

        Libro libro = resena.getLibro();
        resenaRepositorio.delete(resena);

        Double nuevoPromedio = calcularPromedioLibro(libro.getIdLibro());
        libro.setPromedioCalificacion(nuevoPromedio);
        libroRepositorio.save(libro);
    }

    /**
     * Alterna (agrega o quita) un like de un usuario sobre una reseña.
     * @param idResena identificador de la reseña a interactuar
     * @param token JWT del usuario que da clic al botón de me gusta
     * @return true si el like se agregó, false si se retiró
     * @throws IllegalArgumentException si el usuario o la reseña no existen
     */
    @Transactional
    public boolean alternarLikeResena(Integer idResena, String token) {
        String nombreUsuario = tokenJWT.obtenerNombreUsuario(token);

        Usuario usuario = usuarioRepositorio.encontrarPorNombreUsuario(nombreUsuario)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        Resena resena = resenaRepositorio.findById(idResena)
            .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada."));

        Optional<LikesResena> likeExistente = likesResenaRepositorio
            .findByUsuario_IdUsuarioAndResena_IdResena(usuario.getIdUsuario(), resena.getIdResena());

        if (likeExistente.isPresent()) {
            likesResenaRepositorio.delete(likeExistente.get());
            resena.setLikes(resena.getLikes() - 1);
            resenaRepositorio.save(resena);
            return false;
        }

        LikesResena nuevoLike = new LikesResena();
        nuevoLike.setUsuario(usuario);
        nuevoLike.setResena(resena);
        nuevoLike.setFecha(LocalDate.now().toString());
        likesResenaRepositorio.save(nuevoLike);

        resena.setLikes(resena.getLikes() + 1);
        resenaRepositorio.save(resena);
        return true;
    }
}
