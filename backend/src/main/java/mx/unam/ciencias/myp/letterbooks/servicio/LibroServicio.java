package mx.unam.ciencias.myp.letterbooks.servicio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.unam.ciencias.myp.letterbooks.modelo.Libro;
import mx.unam.ciencias.myp.letterbooks.modelo.Autor;
import mx.unam.ciencias.myp.letterbooks.modelo.Genero;
import mx.unam.ciencias.myp.letterbooks.modelo.Editorial;
import mx.unam.ciencias.myp.letterbooks.repositorio.LibroRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.ResenaRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.AutorRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.GeneroRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.EditorialRepositorio;
import mx.unam.ciencias.myp.letterbooks.dto.RegistroLibro;
import mx.unam.ciencias.myp.letterbooks.dto.VistaLibro;

/**
 * Servicio encargado de la lógica de negocio relacionada con los libros.
 * Esta clase conecta los datos que vienen del frontend (DTO) con las relaciones
 * de la base de datos y transforma los resultados en vistas limpias.
 */
@Service
public class LibroServicio {

    private final LibroRepositorio libroRepositorio;
    private final ResenaRepositorio resenaRepositorio;
    private final AutorRepositorio autorRepositorio;
    private final GeneroRepositorio generoRepositorio;
    private final EditorialRepositorio editorialRepositorio;

    /**
     * Constructor con inyección de dependencias.
     * Valida que las llaves foráneas (Autor, Género, Editorial) existan en la base de datos.
     */
    public LibroServicio(LibroRepositorio libroRepositorio,
                         ResenaRepositorio resenaRepositorio,
                         AutorRepositorio autorRepositorio,
                         GeneroRepositorio generoRepositorio, 
                         EditorialRepositorio editorialRepositorio) {
        this.libroRepositorio = libroRepositorio;
        this.resenaRepositorio = resenaRepositorio;
        this.autorRepositorio = autorRepositorio;
        this.generoRepositorio = generoRepositorio;
        this.editorialRepositorio = editorialRepositorio;
    }

    /**
     * Obtiene todos los libros disponibles.
     * @return lista de todos los libros
     */
    @Transactional(readOnly = true)
    public List<Libro> obtenerTodos() {
        return libroRepositorio.findAll();
    }

    /**
     * Busca libros cuyo título contenga la cadena dada.
     * @param titulo cadena a buscar
     * @return lista de libros que coinciden
     */
    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepositorio.encontrarPorTituloContiene(titulo);
    }

    /**
     * Obtiene los libros más populares según su calificación promedio.
     * @param limite cantidad máxima de libros a devolver
     * @return lista de DTOs listos para la landing
     */
    private static final List<String> TITULOS_DESTACADOS = List.of(
        "Cien años de soledad",
        "1984",
        "Orgullo y Prejuicio"
    );

    /**
     * Obtiene los libros destacados de la landing en orden fijo.
     * @param limite cantidad máxima de libros a devolver
     * @return lista de DTOs listos para la landing
     */
    @Transactional(readOnly = true)
    public List<VistaLibro> obtenerPopulares(int limite) {
        int cantidad = Math.max(1, limite);
        return TITULOS_DESTACADOS.stream()
            .map(libroRepositorio::findByTitulo)
            .flatMap(Optional::stream)
            .limit(cantidad)
            .map(this::mapearAVista)
            .collect(Collectors.toList());
    }

    /**
     * Obtiene la entidad Libro por su ID.
     * @param idLibro identificador del libro
     * @return el libro encontrado
     */
    public Libro obtenerEntidadPorId(Integer idLibro) {
        return libroRepositorio.findById(idLibro)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado: " + idLibro));
    }

    /**
     * Registra un nuevo libro en el sistema.
     *
     * @param registro DTO con los datos del formulario de entrada.
     * @return DTO VistaLibro listo para mostrarse en la vista.
     * @throws IllegalArgumentException si algún ID de autor, género o editorial no existe.
     */
    public VistaLibro registrar(RegistroLibro registro) {
        registro.setTitulo(registro.getTitulo().trim());
        
        if (registro.getIsbn() != null) 
            registro.setIsbn(registro.getIsbn().trim());

        Autor autor = autorRepositorio.findById(registro.getIdAutor())
                .orElseThrow(() -> new IllegalArgumentException("El autor seleccionado no existe en la base de datos."));

        Genero genero = generoRepositorio.findById(registro.getIdGenero())
                .orElseThrow(() -> new IllegalArgumentException("El género seleccionado no existe en la base de datos."));

        Editorial editorial = editorialRepositorio.findById(registro.getIdEditorial())
                .orElseThrow(() -> new IllegalArgumentException("La editorial seleccionada no existe en la base de datos."));

        Libro libro = new Libro();
        libro.setTitulo(registro.getTitulo());
        libro.setSinopsis(registro.getSinopsis());
        libro.setImagen(registro.getImagen());
        libro.setPaginas(registro.getPaginas());
        libro.setAno(registro.getAno());
        libro.setIsbn(registro.getIsbn());       
        libro.setAutor(autor);
        libro.setGenero(genero);
        libro.setEditorial(editorial);
        libro.setReportes(0);
        libro.setPromedioCalificacion(0.00);

        Libro libroGuardado = libroRepositorio.save(libro);

        return mapearAVista(libroGuardado);
    }

    /**
     * Busca un libro por su ID y lo prepara para mostrarlo en pantalla.
     *
     * @param idLibro identificador único del libro a buscar.
     * @return El DTO VistaLibro con los nombres de las relaciones ya "aplanados".
     * @throws IllegalArgumentException si el ID del libro no existe.
     */
    @Transactional(readOnly = true)
    public VistaLibro obtenerPorId(Integer idLibro) {
        Libro libro = libroRepositorio.encontrarPorIdConRelaciones(idLibro)
                .orElseThrow(() -> new IllegalArgumentException("El libro buscado no existe en la base de datos."));

        return mapearAVista(libro);
    }

    /**
     * Método auxiliar para pasar los datos de la Entidad Libro al DTO plano (VistaLibro).
     */
    @Transactional(readOnly = true)
    public VistaLibro mapearAVista(Libro libro) {
        VistaLibro vista = new VistaLibro();
        
        vista.setIdLibro(libro.getIdLibro());
        vista.setTitulo(libro.getTitulo());
        vista.setSinopsis(libro.getSinopsis());
        vista.setImagen(libro.getImagen());
        vista.setPaginas(libro.getPaginas());
        vista.setAno(libro.getAno());
        vista.setIsbn(libro.getIsbn());
        vista.setTotalReportes(libro.getReportes());
        
        if (libro.getPromedioCalificacion() != null) {
            vista.setPromedioCalificacion(BigDecimal.valueOf(libro.getPromedioCalificacion()));
        } else {
            vista.setPromedioCalificacion(BigDecimal.ZERO);
        }

        if (libro.getAutor() != null) 
            vista.setNombreAutor(libro.getAutor().getNombreAutor()); 
        
        if (libro.getEditorial() != null) 
            vista.setNombreEditorial(libro.getEditorial().getNombreEditorial());
        
        if (libro.getGenero() != null) 
            vista.setNombreGenero(libro.getGenero().getNombreGenero());

        vista.setTotalResenas(resenaRepositorio.findByLibro_IdLibro(libro.getIdLibro()).size());

        return vista;
    }
}
