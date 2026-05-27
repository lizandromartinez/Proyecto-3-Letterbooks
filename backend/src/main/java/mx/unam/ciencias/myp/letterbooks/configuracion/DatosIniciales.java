package mx.unam.ciencias.myp.letterbooks.configuracion;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import mx.unam.ciencias.myp.letterbooks.modelo.Autor;
import mx.unam.ciencias.myp.letterbooks.modelo.Cita;
import mx.unam.ciencias.myp.letterbooks.modelo.Editorial;
import mx.unam.ciencias.myp.letterbooks.modelo.Genero;
import mx.unam.ciencias.myp.letterbooks.modelo.Libro;
import mx.unam.ciencias.myp.letterbooks.modelo.Perfil;
import mx.unam.ciencias.myp.letterbooks.modelo.Resena;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.repositorio.AutorRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.CitaRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.EditorialRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.GeneroRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.LibroRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.PerfilRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.ResenaRepositorio;
import mx.unam.ciencias.myp.letterbooks.repositorio.UsuarioRepositorio;

/**
 * Inserta o actualiza los libros destacados y contenido demo de la landing.
 */
@Component
public class DatosIniciales implements CommandLineRunner {

    private final LibroRepositorio libroRepositorio;
    private final GeneroRepositorio generoRepositorio;
    private final AutorRepositorio autorRepositorio;
    private final EditorialRepositorio editorialRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final PerfilRepositorio perfilRepositorio;
    private final ResenaRepositorio resenaRepositorio;
    private final CitaRepositorio citaRepositorio;
    private final PasswordEncoder codificadorContrasena;

    public DatosIniciales(LibroRepositorio libroRepositorio,
                          GeneroRepositorio generoRepositorio,
                          AutorRepositorio autorRepositorio,
                          EditorialRepositorio editorialRepositorio,
                          UsuarioRepositorio usuarioRepositorio,
                          PerfilRepositorio perfilRepositorio,
                          ResenaRepositorio resenaRepositorio,
                          CitaRepositorio citaRepositorio,
                          PasswordEncoder codificadorContrasena) {
        this.libroRepositorio = libroRepositorio;
        this.generoRepositorio = generoRepositorio;
        this.autorRepositorio = autorRepositorio;
        this.editorialRepositorio = editorialRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.perfilRepositorio = perfilRepositorio;
        this.resenaRepositorio = resenaRepositorio;
        this.citaRepositorio = citaRepositorio;
        this.codificadorContrasena = codificadorContrasena;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Genero romance = guardarGenero("Romance clásico");
        Genero distopia = guardarGenero("Distopía");
        Genero realismoMagico = guardarGenero("Realismo mágico");

        Autor janeAusten = guardarAutor("Jane Austen");
        Autor georgeOrwell = guardarAutor("George Orwell");
        Autor garciaMarquez = guardarAutor("Gabriel García Márquez");

        Editorial penguin = guardarEditorial("Penguin");
        Editorial sudamericana = guardarEditorial("Editorial Sudamericana");

        Libro orgullo = guardarOActualizarLibro(
            "Orgullo y Prejuicio",
            "Una comedia romántica que explora el orgullo, los prejuicios y el amor en la Inglaterra del siglo XIX.",
            "/almacenamiento/usuarios/portadas/orgulloYprejuicio.png",
            432,
            1813,
            "978-0141439518",
            romance,
            janeAusten,
            penguin
        );

        guardarOActualizarLibro(
            "1984",
            "Una distopía sobre un régimen totalitario que controla cada aspecto de la vida mediante la vigilancia y la manipulación de la verdad.",
            "/almacenamiento/usuarios/portadas/1984.png",
            328,
            1949,
            "978-0451524935",
            distopia,
            georgeOrwell,
            penguin
        );

        Libro cienAnos = guardarOActualizarLibro(
            "Cien años de soledad",
            "La saga de la familia Buendía en el mítico pueblo de Macondo, tejiendo realismo mágico y memoria colectiva.",
            "/almacenamiento/usuarios/portadas/cienAnosDeSoledad.png",
            417,
            1967,
            "978-8437604947",
            realismoMagico,
            garciaMarquez,
            sudamericana
        );

        Usuario anaLee = guardarUsuarioDemo();
        guardarPerfilDemo(anaLee, garciaMarquez, realismoMagico, orgullo);
        guardarResenaConCitas(anaLee, cienAnos);
    }

    private Genero guardarGenero(String nombre) {
        return generoRepositorio.encontrarPorNombre(nombre)
            .orElseGet(() -> {
                Genero genero = new Genero();
                genero.setNombreGenero(nombre);
                return generoRepositorio.save(genero);
            });
    }

    private Autor guardarAutor(String nombre) {
        return autorRepositorio.encontrarPorNombre(nombre)
            .orElseGet(() -> {
                Autor autor = new Autor();
                autor.setNombreAutor(nombre);
                return autorRepositorio.save(autor);
            });
    }

    private Editorial guardarEditorial(String nombre) {
        return editorialRepositorio.encontrarPorNombre(nombre)
            .orElseGet(() -> {
                Editorial editorial = new Editorial();
                editorial.setNombreEditorial(nombre);
                return editorialRepositorio.save(editorial);
            });
    }

    private Libro guardarOActualizarLibro(String titulo,
                                          String sinopsis,
                                          String imagen,
                                          int paginas,
                                          int ano,
                                          String isbn,
                                          Genero genero,
                                          Autor autor,
                                          Editorial editorial) {
        Libro libro = libroRepositorio.findByTitulo(titulo).orElseGet(Libro::new);
        libro.setTitulo(titulo);
        libro.setSinopsis(sinopsis);
        libro.setImagen(imagen);
        libro.setPaginas(paginas);
        libro.setAno(ano);
        libro.setIsbn(isbn);
        libro.setGenero(genero);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        if (libro.getReportes() == null) {
            libro.setReportes(0);
        }
        if (libro.getPromedioCalificacion() == null) {
            libro.setPromedioCalificacion(0.0);
        }
        return libroRepositorio.save(libro);
    }

    private Usuario guardarUsuarioDemo() {
        return usuarioRepositorio.encontrarPorNombreUsuario("ana_lee")
            .orElseGet(() -> {
                Usuario usuario = new Usuario();
                usuario.setNombreUsuario("ana_lee");
                usuario.setCorreo("ana.lee@letterbooks.demo");
                usuario.setContrasena(codificadorContrasena.encode("demo1234"));
                return usuarioRepositorio.save(usuario);
            });
    }

    private void guardarPerfilDemo(Usuario usuario, Autor autorFavorito, Genero generoFavorito, Libro libroFavorito) {
        Perfil perfil = perfilRepositorio.encontrarPorUsuario(usuario.getIdUsuario())
            .orElseGet(() -> {
                Perfil nuevo = new Perfil();
                nuevo.setUsuario(usuario);
                return nuevo;
            });

        perfil.setAutor(autorFavorito);
        perfil.setGenero(generoFavorito);
        perfil.setLibro(libroFavorito);
        perfil.setBiografia("Lectora apasionada de clásicos latinoamericanos.");
        perfil.setAvatar("/almacenamiento/usuarios/avatars/ana_lee.png");
        if (perfil.getFechaRegistro() == null) {
            perfil.setFechaRegistro(LocalDate.now().toString());
        }
        if (perfil.getReportes() == null) {
            perfil.setReportes(0);
        }
        perfilRepositorio.save(perfil);
    }

    private void guardarResenaConCitas(Usuario usuario, Libro libro) {
        if (resenaRepositorio.existePorUsuarioYLibro(usuario.getIdUsuario(), libro.getIdLibro())) {
            return;
        }

        Resena resena = new Resena();
        resena.setLibro(libro);
        resena.setUsuario(usuario);
        resena.setCalificacionLibro(5);
        resena.setCalificacionResena(4);
        resena.setLikes(3);
        resena.setReportes(0);
        resena.setTextoResena(
            "Una obra maestra absoluta. García Márquez teje una narrativa tan rica y compleja "
            + "que te sumerge completamente en Macondo. Cada relectura revela nuevas capas de significado."
        );
        resena.setFechaPublicacion(LocalDate.now().toString());
        Resena resenaGuardada = resenaRepositorio.save(resena);

        guardarCita(resenaGuardada,
            "Muchos años después, frente al pelotón de fusilamiento, el coronel Aureliano Buendía "
            + "había de recordar aquella tarde remota...");
        guardarCita(resenaGuardada,
            "La vida no es la que uno vivió, sino la que uno recuerda y cómo la recuerda para contarla.");

        libro.setPromedioCalificacion(5.0);
        libroRepositorio.save(libro);
    }

    private void guardarCita(Resena resena, String texto) {
        Cita cita = new Cita();
        cita.setResena(resena);
        cita.setTexto(texto);
        citaRepositorio.save(cita);
    }
}
