package mx.unam.ciencias.myp.letterbooks.servicio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

/**
 * Servicio encargado del almacenamiento de imágenes
 * subidas por los usuarios.
 * <p>
 * Permite guardar avatares y banners dentro del
 * servidor generando nombres únicos para evitar
 * conflictos entre archivos.
 */
@Service
public class AlmacenamientoServicio {

    /**
     * Directorio base donde se almacenan las imágenes.
     * <p>
     *
     * Si no existe la propiedad, se utiliza:
     * {@code ./almacenamiento/usuarios}.
     */
    @Value("${app.upload.dir:./almacenamiento/usuarios}")
    private String directorioImagenes;

    /**
     * Guarda una imagen en el servidor.
     *
     * @param archivo archivo enviado desde el frontend.
     * @param tipo tipo de imagen a guardar:
     *             {@code avatares} o {@code banners}.
     * @return URL pública de la imagen guardada.
     */
    public String guardarImagen(MultipartFile archivo, String tipo) {

	//Validar tipo de carpeta permitido.
        if (!tipo.equals("avatares") && !tipo.equals("banners") && !tipo.equals("portadas")) {
            throw new IllegalArgumentException(
                "Tipo de imagen inválido"
            );
        }

	//Validar que el archivo sea una imagen.
        String tipoContenido = archivo.getContentType();

        if (tipoContenido == null || !tipoContenido.startsWith("image/")) {
            throw new IllegalArgumentException(
                "El archivo debe ser una imagen"
            );
        }


        // Validar tamaño máximo de 5MB.
        if (archivo.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException(
                "La imagen no puede superar 5MB"
            );
        }

        try {
            // Crear carpeta correspondiente
            Path dirPath = Paths.get(directorioImagenes, tipo);
            Files.createDirectories(dirPath);

            //Obtenemos extensión del archivo.
            String nombreOriginal = archivo.getOriginalFilename();

            String extension = nombreOriginal.substring(
                            nombreOriginal.lastIndexOf(".")
            );

            // Generarmos nombre único.
            String nombreArchivo =
                    UUID.randomUUID() + extension;

            // ruta completa del archivo
            Path rutaArchivo = dirPath.resolve(nombreArchivo);

            // Guardar archivo en disco.
            Files.copy(
                archivo.getInputStream(),
                rutaArchivo,
                StandardCopyOption.REPLACE_EXISTING
            );

            // Hacemos URL pública.
            return "/almacenamiento/usuarios/" + tipo + "/" + nombreArchivo;

        } catch (IOException e) {
            throw new RuntimeException(
                    "Error al guardar la imagen", e
            );
        }
    }
}
