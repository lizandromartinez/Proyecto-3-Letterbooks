package mx.unam.ciencias.myp.letterbooks.servicio;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.unam.ciencias.myp.letterbooks.dto.RegistroAutor;
import mx.unam.ciencias.myp.letterbooks.modelo.Autor;
import mx.unam.ciencias.myp.letterbooks.repositorio.AutorRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.UsuarioRepositorio;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.seguridad.TokenJWT;

/**
 * Servicio para la gestión y consulta de autores.
 */
@Service
public class AutorServicio {

    private final AutorRepositorio autorRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final TokenJWT tokenJWT;

    /**
     * Constructor único para la inyección de dependencias.
     * Spring Boot inyectará automáticamente los tres componentes necesarios al arrancar.
     */
    public AutorServicio(AutorRepositorio autorRepositorio, 
                         UsuarioRepositorio usuarioRepositorio, 
                         TokenJWT tokenJWT) {
        this.autorRepositorio = autorRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.tokenJWT = tokenJWT;
    }

    /**
     * Obtiene todos los autores disponibles.
     * @return lista de todos los autores
     */
    @Transactional(readOnly = true)
    public List<Autor> obtenerTodos() {
        return autorRepositorio.findAll();
    }

    /**
     * Busca autores cuyo nombre contenga la cadena dada.
     * @param nombre cadena a buscar
     * @return lista de autores que coinciden
     */
    @Transactional(readOnly = true)
    public List<Autor> buscarPorNombre(String nombre) {
        return autorRepositorio.encontrarPorNombreContiene(nombre);
    }

    /**
     * Obtiene un autor por su ID.
     * @param idAutor identificador del autor
     * @return el autor encontrado
     */
    @Transactional(readOnly = true)
    public Autor obtenerPorId(Integer idAutor) {
        return autorRepositorio.encontrarPorId(idAutor)
            .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado: " + idAutor));
    }

    /**
     * Registra un nuevo autor en la base de datos aplicando reglas de negocio.
     */
    @Transactional
    public Autor registrar(RegistroAutor registro) {
        String nombreLimpio = registro.getNombreAutor().trim();

        if (autorRepositorio.existePorNombre(nombreLimpio)) {
            throw new IllegalArgumentException("El autor '" + nombreLimpio + "' ya se encuentra registrado en el sistema.");
        }

        Autor nuevoAutor = new Autor();
        nuevoAutor.setNombreAutor(nombreLimpio);
        nuevoAutor.setBiografia(registro.getBiografia());
        nuevoAutor.setFechaNacimiento(registro.getFechaNacimiento()); 
        nuevoAutor.setFoto(registro.getFoto());

        return autorRepositorio.save(nuevoAutor);
    }

    /**
     * Modifica un autor existente en el sistema verificando los permisos del usuario.
     */
    @Transactional
    public Autor editar(Integer idAutor, RegistroAutor registro, String token) {
        Autor autor = autorRepositorio.encontrarPorId(idAutor)
                .orElseThrow(() -> new IllegalArgumentException("El autor buscado no existe en la base de datos."));

        String nombreUsuario = tokenJWT.obtenerNombreUsuario(token);
        Usuario usuario = usuarioRepositorio.encontrarPorNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario ejecutor no encontrado."));
        
        boolean esAdmin = usuario.getRol() == Usuario.Rol.admin;

        if (!esAdmin) {
            throw new IllegalArgumentException("No tienes permiso para editar este autor. Solo los administradores del sistema pueden modificar el catálogo de autores.");
        }

        registro.setNombreAutor(registro.getNombreAutor().trim());

        autor.setNombreAutor(registro.getNombreAutor());
        autor.setBiografia(registro.getBiografia());
        autor.setFechaNacimiento(registro.getFechaNacimiento());
        autor.setFoto(registro.getFoto());

        return autorRepositorio.save(autor);
    }
}
