package mx.unam.ciencias.myp.letterbooks.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.unam.ciencias.myp.letterbooks.dto.RegistroAutor;
import mx.unam.ciencias.myp.letterbooks.modelo.Autor;
import mx.unam.ciencias.myp.letterbooks.repositorio.AutorRepositorio;

/**
 * Servicio para la gestión y consulta de autores.
 */
@Service
public class AutorServicio {

    /**
     * Repositorio del autor
     **/
    @Autowired
    private AutorRepositorio autorRepositorio;

    /**
     * Obtiene todos los autores disponibles.
     * @return lista de todos los autores
     */
    public List<Autor> obtenerTodos() {
        return autorRepositorio.findAll();
    }

    /**
     * Busca autores cuyo nombre contenga la cadena dada.
     * @param nombre cadena a buscar
     * @return lista de autores que coinciden
     */
    public List<Autor> buscarPorNombre(String nombre) {
        return autorRepositorio.encontrarPorNombreContiene(nombre);
    }

    /**
     * Obtiene un autor por su ID.
     * @param idAutor identificador del autor
     * @return el autor encontrado
     */
    public Autor obtenerPorId(Integer idAutor) {
        return autorRepositorio.encontrarPorId(idAutor)
            .orElseThrow(() -> new RuntimeException("Autor no encontrado: " + idAutor));
    }

    /**
     * Registra un nuevo autor en la base de datos aplicando reglas de negocio.
     * Valida que no exista un autor con el mismo nombre, limpia espacios en blanco,
     * transforma el DTO {@link RegistroAutor} en la entidad {@link Autor} y la persiste.
     * @param registro DTO con los datos del formulario enviados desde el Frontend
     * @return el autor guardado con su ID asignado por la base de datos
     * @throws IllegalArgumentException si el nombre del autor ya se encuentra registrado
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
     * Este método extrae la identidad del operador mediante su token JWT y restringe
     * la acción de edición en caso de que no cuente con el rol de administrador.
     *
     * @param idAutor ID del autor que se desea modificar.
     * @param registro DTO con los nuevos datos optimizados del formulario.
     * @param token Token JWT del usuario que intenta realizar la operación.
     * @return La entidad {@link Autor} con sus campos actualizados en la base de datos.
     * @throws IllegalArgumentException Si el autor no existe, el usuario no es válido o no tiene permisos.
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
