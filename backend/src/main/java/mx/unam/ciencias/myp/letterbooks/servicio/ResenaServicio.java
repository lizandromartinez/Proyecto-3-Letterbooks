package mx.unam.ciencias.myp.letterbooks.servicio;

import mx.unam.ciencias.myp.letterbooks.dto.NuevaCita;
import mx.unam.ciencias.myp.letterbooks.dto.NuevaResena;
import mx.unam.ciencias.myp.letterbooks.modelo.Cita;
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
import java.util.ArrayList;
import java.util.List;

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
    public List<Resena> obtenerResenasPorLibro(Integer idLibro) {
        return resenaRepositorio.encontrarPorLibroConCitas(idLibro);
    }

    /**
     * Elimina una reseña existente aplicando filtros estrictos de autoría.
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
