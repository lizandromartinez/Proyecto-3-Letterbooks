package mx.unam.ciencias.myp.letterbooks.servicio;

import mx.unam.ciencias.myp.letterbooks.dto.NuevaResena;
import mx.unam.ciencias.myp.letterbooks.modelo.Libro;
import mx.unam.ciencias.myp.letterbooks.modelo.Resena;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.repositorio.LibroRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.ResenaRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.UsuarioRepositorio;
import mx.unam.ciencias.myp.letterbooks.seguridad.TokenJWT;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio encargado de la lógica de negocio para las reseñas.
 * <p>
 * Esta clase gestiona operaciones de creación y edición de reseñas de libros,
 * aplicando validaciones de identidad, control de autoría y asegurando la
 * actualización síncrona del promedio de calificaciones de los textos.
 * </p>
 */
@Service
public class ResenaServicio {

    /**
     * Repositorio encargado de realizar operaciones de persistencia
     * sobre la entidad Resena en la base de datos.
     */
    private final ResenaRepositorio resenaRepositorio;

    /**
     * Repositorio encargado de realizar operaciones de persistencia
     * y consulta sobre la entidad Libro en la base de datos.
     */
    private final LibroRepositorio libroRepositorio;

    /**
     * Repositorio encargado de realizar búsquedas y validaciones
     * sobre las cuentas de la entidad Usuario en la base de datos.
     */
    private final UsuarioRepositorio usuarioRepositorio;

    /**
     * Componente de seguridad utilizado para decodificar, validar
     * y extraer la información de identidad de los tokens de sesión JWT.
     */
    private final TokenJWT tokenJWT;

    /**
     * Constructor con inyección de dependencias.
     * @param resenaRepositorio repositorio para acceder a las reseñas
     * @param libroRepositorio repositorio para acceder al catálogo de libros
     * @param usuarioRepositorio repositorio para acceder a los datos de cuentas
     * @param tokenJWT utilidad para el procesamiento y lectura de firmas JWT
     */
    public ResenaServicio(ResenaRepositorio resenaRepositorio,
                          LibroRepositorio libroRepositorio,
                          UsuarioRepositorio usuarioRepositorio,
                          TokenJWT tokenJWT) {
        this.resenaRepositorio = resenaRepositorio;
        this.libroRepositorio = libroRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.tokenJWT = tokenJWT;
    }

    /**
     * Crea una nueva reseña en el sistema y actualiza la calificación del libro.
     * <p>
     * Este método:
     * <ul>
     * <li>Extrae la identidad del usuario desde el token JWT de la sesión</li>
     * <li>Valida que tanto el usuario como el libro existan en el sistema</li>
     * <li>Inicializa los contadores de interacción y asigna la fecha actual en formato texto</li>
     * <li>Persiste la reseña y recalcula de forma inmediata el promedio global del libro</li>
     * </ul>
     * </p>
     *
     * @param datos DTO con la información y calificación de la nueva reseña
     * @param token cadena con el token JWT del usuario que realiza la operación
     * @return la entidad reseña recién creada y persistida en el sistema
     * @throws IllegalArgumentException si el usuario firmante o el libro no existen
     */
    @Transactional
    public Resena crearResena(NuevaResena datos, String token) {
        String nombreUsuario = tokenJWT.obtenerNombreUsuario(token);
        
        Usuario usuario = usuarioRepositorio.findByNombreUsuario(nombreUsuario)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
            
        Libro libro = libroRepositorio.findById(datos.getIdLibro())
            .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado."));

        Resena nuevaResena = new Resena();
        nuevaResena.setLibro(libro);
        nuevaResena.setUsuario(usuario);
        nuevaResena.setCalificacionLibro(datos.getCalificacionLibro());
        nuevaResena.setTextoResena(datos.getTextoResena());
        nuevaResena.setCalificacionResena(0);
        nuevaResena.setLikes(0);
        nuevaResena.setReportes(0);
        nuevaResena.setFechaPublicacion(LocalDate.now().toString());

        Resena resenaGuardada = resenaRepositorio.save(nuevaResena);

        Double nuevoPromedio = calcularPromedioLibro(libro.getIdLibro());
        libro.setPromedioCalificacion(nuevoPromedio);
        libroRepositorio.save(libro);

        return resenaGuardada;
    }

    /**
     * Edita una reseña existente aplicando filtros estrictos de derecho de autoría.
     * <p>
     * Este método:
     * <ul>
     * <li>Recupera la reseña solicitada del repositorio por su identificador</li>
     * <li>Verifica que el nombre de usuario del token coincida exactamente con el creador</li>
     * <li>Modifica el texto de la opinión y la puntuación de estrellas asignada</li>
     * <li>Actualiza el valor promedio acumulado de la entidad del libro</li>
     * </ul>
     * </p>
     *
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
        Resena resenaActualizada = resenaRepositorio.save(resena);

        Libro libro = resena.getLibro();
        Double nuevoPromedio = calcularPromedioLibro(libro.getIdLibro());
        libro.setPromedioCalificacion(nuevoPromedio);
        libroRepositorio.save(libro);

        return resenaActualizada;
    }

    /**
     * Calcula el promedio de calificaciones de un libro basado en sus reseñas.
     * <p>
     * Este método recupera la lista completa de críticas asociadas al identificador del
     * libro para computar la media aritmética. En caso de no existir registros, el método
     * normaliza el resultado devolviendo un valor por defecto.
     * </p>
     *
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
     * Recupera todas las reseñas asociadas a un libro específico.
     * <p>
     * Consulta el repositorio utilizando el identificador del libro para
     * devolver la lista de reseñas que serán enviadas al frontend.
     * </p>
     *
     * @param idLibro el identificador único del libro a consultar
     * @return una lista de entidades reseña pertenecientes al libro
     */
    public List<Resena> obtenerResenasPorLibro(Integer idLibro) {
        return resenaRepositorio.findByLibro_IdLibro(idLibro);
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
}
