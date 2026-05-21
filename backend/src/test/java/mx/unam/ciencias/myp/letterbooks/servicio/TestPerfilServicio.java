package mx.unam.ciencias.myp.letterbooks.servicio;

import mx.unam.ciencias.myp.letterbooks.dto.ActualizarPerfil;
import mx.unam.ciencias.myp.letterbooks.dto.Perfil;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.repositorio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestPerfilServicio {

    /* Repositorio de usuarios. */
    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    /* Repositorio de perfiles. */
    @Mock
    private PerfilRepositorio perfilRepositorio;

    /* Repositorio de libros. */
    @Mock
    private LibroRepositorio libroRepositorio;

    /* Repositorio de autores. */
    @Mock
    private AutorRepositorio autorRepositorio;

    /* Repositorio de géneros. */
    @Mock
    private GeneroRepositorio generoRepositorio;

    /* Repositorio de likes en reseñas. */
    @Mock
    private LikesResenaRepositorio likesResenaRepositorio;

    /* Repositorio de likes en comentarios. */
    @Mock
    private LikesComentarioRepositorio likesComentarioRepositorio;

    /* Repositorio de calificaciones de reseñas. */
    @Mock
    private CalificacionResenaRepositorio calificacionResenaRepositorio;

    /* Repositorio de reseñas. */
    @Mock
    private ResenaRepositorio resenaRepositorio;

    /* Servicio a probar. */
    @InjectMocks
    private PerfilServicio perfilServicio;

    /* Perfil de prueba. */
    private mx.unam.ciencias.myp.letterbooks.modelo.Perfil perfil;

    /* Usuario de prueba. */
    private Usuario usuario;

    /**
     * Inicializa datos de prueba.
     */
    @BeforeEach
    public void setUp() {

        usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombreUsuario("usuario1");

        perfil = new mx.unam.ciencias.myp.letterbooks.modelo.Perfil();
        perfil.setUsuario(usuario);
        perfil.setBiografia("Mi biografía");
        perfil.setAvatar("/avatar.png");
        perfil.setBanner("/banner.png");
    }

    /**
     * Verifica que se obtenga correctamente un perfil por ID.
     */
    @Test
    public void obtenerPerfilPorUsuarioDeberiaRegresarPerfil() {

        when(perfilRepositorio.encontrarPorUsuario(1))
            .thenReturn(Optional.of(perfil));

        when(likesResenaRepositorio.encontrarPorUsuario(1))
            .thenReturn(Collections.emptyList());

        when(calificacionResenaRepositorio.encontrarPorUsuario(1))
            .thenReturn(Collections.emptyList());

        when(resenaRepositorio.encontrarPorUsuario(1))
            .thenReturn(Collections.emptyList());

        when(likesComentarioRepositorio.encontrarPorUsuario(1))
            .thenReturn(Collections.emptyList());

        Perfil resultado = perfilServicio.obtenerPerfilPorUsuario(1);

        assertNotNull(resultado);
        assertEquals("usuario1", resultado.getNombreUsuario());
        assertEquals("Mi biografía", resultado.getBiografia());
        assertEquals("/avatar.png", resultado.getAvatar());
    }

    /**
     * Verifica que se lance excepción si el perfil no existe.
     */
    @Test
    public void obtenerPerfilPorUsuarioDeberiaLanzarExcepcion() {

        when(perfilRepositorio.encontrarPorUsuario(1))
            .thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(
            RuntimeException.class,
            () -> perfilServicio.obtenerPerfilPorUsuario(1)
        );

        assertEquals(
            "Perfil no encontrado para el usuario: 1",
            excepcion.getMessage()
        );
    }

    /**
     * Verifica que se obtenga un perfil por nombre de usuario.
     */
    @Test
    public void obtenerPerfilPorNombreUsuarioDeberiaRegresarPerfil() {

        when(usuarioRepositorio.encontrarPorNombreUsuario("usuario1"))
            .thenReturn(Optional.of(usuario));

        when(perfilRepositorio.encontrarPorUsuario(1))
            .thenReturn(Optional.of(perfil));

        when(likesResenaRepositorio.encontrarPorUsuario(1))
            .thenReturn(Collections.emptyList());

        when(calificacionResenaRepositorio.encontrarPorUsuario(1))
            .thenReturn(Collections.emptyList());

        when(resenaRepositorio.encontrarPorUsuario(1))
            .thenReturn(Collections.emptyList());

        when(likesComentarioRepositorio.encontrarPorUsuario(1))
            .thenReturn(Collections.emptyList());

        Perfil resultado =
            perfilServicio.obtenerPerfilPorNombreUsuario("usuario1");

        assertEquals("usuario1", resultado.getNombreUsuario());
    }

    /**
     * Verifica que los datos del perfil se actualicen correctamente.
     */
    @Test
    public void actualizarPerfilDeberiaActualizarCampos() {

        ActualizarPerfil datos = new ActualizarPerfil();
        datos.setBiografia("Nueva bio");
        datos.setAvatar("/nuevoAvatar.png");
        datos.setBanner("/nuevoBanner.png");

        when(perfilRepositorio.encontrarPorUsuario(1))
            .thenReturn(Optional.of(perfil));

        when(likesResenaRepositorio.encontrarPorUsuario(1))
            .thenReturn(Collections.emptyList());

        when(calificacionResenaRepositorio.encontrarPorUsuario(1))
            .thenReturn(Collections.emptyList());

        when(resenaRepositorio.encontrarPorUsuario(1))
            .thenReturn(Collections.emptyList());

        when(likesComentarioRepositorio.encontrarPorUsuario(1))
            .thenReturn(Collections.emptyList());

        Perfil resultado =
            perfilServicio.actualizarPerfil(1, datos);

        assertEquals("Nueva bio", perfil.getBiografia());
        assertEquals("/nuevoAvatar.png", perfil.getAvatar());
        assertEquals("/nuevoBanner.png", perfil.getBanner());

        verify(perfilRepositorio).save(perfil);

        assertEquals("Nueva bio", resultado.getBiografia());
    }

    /**
     * Verifica que se lance excepción al actualizar un perfil inexistente.
     */
    @Test
    public void actualizarPerfilDeberiaLanzarExcepcionSiNoExistePerfil() {

        ActualizarPerfil datos = new ActualizarPerfil();

        when(perfilRepositorio.encontrarPorUsuario(1))
            .thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(
            RuntimeException.class,
            () -> perfilServicio.actualizarPerfil(1, datos)
        );

        assertEquals(
            "Perfil no encontrado para el usuario: 1",
            excepcion.getMessage()
        );
    }
}
