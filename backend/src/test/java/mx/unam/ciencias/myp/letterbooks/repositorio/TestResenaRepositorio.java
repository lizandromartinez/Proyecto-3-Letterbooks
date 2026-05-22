package mx.unam.ciencias.myp.letterbooks.repositorio;

import mx.unam.ciencias.myp.letterbooks.modelo.Libro;
import mx.unam.ciencias.myp.letterbooks.modelo.Resena;
import mx.unam.ciencias.myp.letterbooks.modelo.Usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de integración para el repositorio de Reseñas.
 * Utiliza @Transactional para garantizar que las inserciones se limpien automáticamente.
 */
@SpringBootTest
@Transactional
public class TestResenaRepositorio {

    @Autowired
    private ResenaRepositorio resenaRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Test
    public void verificarGuardadoYBusquedaPorLibro() {
        // usuario de prueba
        Usuario usuarioPrueba = new Usuario();
        usuarioPrueba.setNombreUsuario("test_c");
        usuarioPrueba.setCorreo("test_c@letterbooks.com");
        usuarioPrueba.setContrasena("password_seguro");
        usuarioPrueba = usuarioRepositorio.save(usuarioPrueba);

        // libro de prueba
        Libro libroPrueba = new Libro();
        libroPrueba.setTitulo("Test de Libro Auxiliar");
        libroPrueba = libroRepositorio.save(libroPrueba);

        // reseña de prueba
        Resena resenaPrueba = new Resena();
        resenaPrueba.setLibro(libroPrueba);
        resenaPrueba.setUsuario(usuarioPrueba);
        resenaPrueba.setCalificacionLibro(5);
        resenaPrueba.setCalificacionResena(0);
        resenaPrueba.setTextoResena("Una excelente lectura de prueba unitaria.");
        resenaPrueba.setFechaPublicacion("2026-05-21");
        resenaPrueba = resenaRepositorio.save(resenaPrueba);

        // método de búsqueda del repositorio
        List<Resena> resultado = resenaRepositorio.findByLibro_IdLibro(libroPrueba.getIdLibro());

        assertFalse(resultado.isEmpty(), "La lista de reseñas no debería estar vacía.");
        assertEquals(1, resultado.size(), "Debería haber exactamente 1 reseña vinculada al libro.");
        assertEquals("Una excelente lectura de prueba unitaria.", resultado.get(0).getTextoResena());
    }
}
