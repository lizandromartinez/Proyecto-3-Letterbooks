package mx.unam.ciencias.myp.letterbooks.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.unam.ciencias.myp.letterbooks.dto.ActualizarPerfil;
import mx.unam.ciencias.myp.letterbooks.dto.Perfil;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;
import mx.unam.ciencias.myp.letterbooks.seguridad.TokenJWT;
import mx.unam.ciencias.myp.letterbooks.servicio.PerfilServicio;
import mx.unam.ciencias.myp.letterbooks.servicio.UsuarioServicio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias del controlador de usuarios.
 */
@WebMvcTest(Usuarios.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestUsuariosControlador {

    /** Simula peticiones HTTP al controlador. */
    @Autowired
    private MockMvc mockMvc;

    /** Mock del servicio de perfiles. */
    @MockBean
    private PerfilServicio perfilServicio;

    /** Mock del servicio de usuarios. */
    @MockBean
    private UsuarioServicio usuarioServicio;

    /** Mock del componente JWT. */
    @MockBean
    private TokenJWT tokenJWT;

    /** Convierte objetos Java a JSON. */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Verifica que se obtenga un perfil correctamente.
     */
    @Test
    public void obtenerPerfilDeberiaRegresar200() throws Exception {

        Perfil perfil = new Perfil();
        perfil.setNombreUsuario("usuario1");

        when(perfilServicio.obtenerPerfilPorUsuario(1))
            .thenReturn(perfil);

        mockMvc.perform(get("/api/usuarios/1/perfil"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombreUsuario")
            .value("usuario1"));
    }

    /**
     * Verifica que se regrese 404 si el perfil no existe.
     */
    @Test
    public void obtenerPerfilDeberiaRegresar404() throws Exception {

        when(perfilServicio.obtenerPerfilPorUsuario(1))
            .thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/usuarios/1/perfil"))
            .andExpect(status().isNotFound());
    }

    /**
     * Verifica que se obtenga un perfil público correctamente.
     */
    @Test
    public void obtenerPerfilPublicoDeberiaRegresar200() throws Exception {

        Perfil perfil = new Perfil();
        perfil.setNombreUsuario("usuario1");

        when(perfilServicio.obtenerPerfilPorNombreUsuario("usuario1"))
            .thenReturn(perfil);

        mockMvc.perform(get("/api/usuarios/perfil/usuario1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombreUsuario")
            .value("usuario1"));
    }

    /**
     * Verifica que un usuario pueda actualizar su propio perfil.
     */
    @Test
    public void actualizarPerfilDeberiaRegresar200() throws Exception {

        ActualizarPerfil datos = new ActualizarPerfil();
        datos.setBiografia("Nueva bio");

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombreUsuario("usuario1");

        Perfil perfil = new Perfil();
        perfil.setNombreUsuario("usuario1");

        when(tokenJWT.obtenerNombreUsuario("token"))
            .thenReturn("usuario1");

        when(usuarioServicio.obtenerPorId(1))
            .thenReturn(usuario);

        when(perfilServicio.actualizarPerfil(1, datos))
            .thenReturn(perfil);

        mockMvc.perform(
            put("/api/usuarios/1/perfil")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos))
        )
            .andExpect(status().isOk());
    }

    /**
     * Verifica que no se pueda modificar un perfil ajeno.
     */
    @Test
    public void actualizarPerfilDeberiaRegresar403() throws Exception {

        ActualizarPerfil datos = new ActualizarPerfil();

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("usuario1");

        when(tokenJWT.obtenerNombreUsuario("token"))
            .thenReturn("otroUsuario");

        when(usuarioServicio.obtenerPorId(1))
            .thenReturn(usuario);

        mockMvc.perform(
            put("/api/usuarios/1/perfil")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos))
        )
            .andExpect(status().isForbidden());
    }
}
