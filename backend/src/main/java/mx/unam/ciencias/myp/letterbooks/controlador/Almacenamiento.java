package mx.unam.ciencias.myp.letterbooks.controlador;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import mx.unam.ciencias.myp.letterbooks.servicio.AlmacenamientoServicio;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para la subida de archivos de imagen.
 * Se utiliza para que el usuario pueda subir imagenes al momento de querer
 * editar su avatar/banner
 */
@RestController
@RequestMapping("/api/almacenamiento")
@CrossOrigin(origins = "*")
public class Almacenamiento {
    
    /**
     * Servicio encargado del almacenamiento
     * de imágenes.
     */
    @Autowired
    private AlmacenamientoServicio almacenamientoServicio;
    
    @Value("${app.upload.dir:./almacenamiento/usuarios}")
    private String uploadDir;

    
    /**
     * Recibe una imagen y la guarda en el servidor.
     *
     * @param tipo tipo de imagen:
     *             {@code avatares} o {@code banners}.
     * @param archivo imagen enviada desde el frontend.
     * @return URL pública de la imagen guardada.
     */
    @PostMapping("/imagen/{tipo}")
    public ResponseEntity<Map<String, String>> subirImagen(
        @PathVariable("tipo") String tipo,
        @RequestParam("archivo") MultipartFile archivo) {

        Map<String, String> respuesta = new HashMap<>();

        try {
            String url = almacenamientoServicio
                .guardarImagen(archivo,tipo);
            respuesta.put("url", url);	  
            return ResponseEntity.ok(respuesta);
	    
	    //Error de validación
        } catch (IllegalArgumentException e) {
            respuesta.put("error", e.getMessage());
            return ResponseEntity
                .badRequest()
                .body(respuesta);
	    
	    //Error del servidor
        } catch (RuntimeException e) {
            respuesta.put(
                "error",
                "Error al guardar la imagen"
            );
            return ResponseEntity
                .internalServerError()
                .body(respuesta);
        }
    }   
}
