package mx.unam.ciencias.myp.letterbooks.servicio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas del servicio de almacenamiento.
 */
public class TestAlmacenamientoServicio {

    /** Servicio de almacenamiento. */
    private AlmacenamientoServicio almacenamientoServicio;

    /** Directorio temporal para pruebas. */
    @TempDir
    Path directorioTemporal;

    /**
     * Configura el servicio antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {

        almacenamientoServicio =
            new AlmacenamientoServicio();

        ReflectionTestUtils.setField(
            almacenamientoServicio,
            "directorioImagenes",
            directorioTemporal.toString()
        );
    }

    /**
     * Verifica que una imagen se guarde correctamente.
     */
    @Test
    public void guardarImagenDeberiaGuardarArchivo() {

        MockMultipartFile archivo =
            new MockMultipartFile(
                "archivo",
                "avatar.png",
                "image/png",
                "imagen".getBytes()
            );

        String ruta =
            almacenamientoServicio.guardarImagen(
                archivo,
                "avatares"
            );

        assertNotNull(ruta);
        assertTrue(ruta.contains("/avatares/"));

        Path carpeta =
            directorioTemporal.resolve("avatares");

        assertTrue(Files.exists(carpeta));
    }

    /**
     * Verifica que falle si el tipo es inválido.
     */
    @Test
    public void guardarImagenDeberiaLanzarExcepcionPorTipoInvalido() {

        MockMultipartFile archivo =
            new MockMultipartFile(
                "archivo",
                "avatar.png",
                "image/png",
                "imagen".getBytes()
            );

        IllegalArgumentException excepcion =
            assertThrows(
                IllegalArgumentException.class,
                () -> almacenamientoServicio.guardarImagen(
                    archivo,
                    "otro"
                )
            );

        assertEquals(
            "Tipo de imagen inválido",
            excepcion.getMessage()
        );
    }

    /**
     * Verifica que falle si el archivo no es imagen.
     */
    @Test
    public void guardarImagenDeberiaLanzarExcepcionSiNoEsImagen() {

        MockMultipartFile archivo =
            new MockMultipartFile(
                "archivo",
                "texto.txt",
                "text/plain",
                "hola".getBytes()
            );

        IllegalArgumentException excepcion =
            assertThrows(
                IllegalArgumentException.class,
                () -> almacenamientoServicio.guardarImagen(
                    archivo,
                    "avatares"
                )
            );

        assertEquals(
            "El archivo debe ser una imagen",
            excepcion.getMessage()
        );
    }

    /**
     * Verifica que falle si la imagen supera 5MB.
     */
    @Test
    public void guardarImagenDeberiaLanzarExcepcionPorTamano() {

        byte[] contenido =
            new byte[6 * 1024 * 1024];

        MockMultipartFile archivo =
            new MockMultipartFile(
                "archivo",
                "imagen.png",
                "image/png",
                contenido
            );

        IllegalArgumentException excepcion =
            assertThrows(
                IllegalArgumentException.class,
                () -> almacenamientoServicio.guardarImagen(
                    archivo,
                    "avatares"
                )
            );

        assertEquals(
            "La imagen no puede superar 5MB",
            excepcion.getMessage()
        );
    }
}
