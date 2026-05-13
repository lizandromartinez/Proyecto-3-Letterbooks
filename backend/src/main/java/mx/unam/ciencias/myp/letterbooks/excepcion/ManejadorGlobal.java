package mx.unam.ciencias.myp.letterbooks.excepcion;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para la aplicación.
 * <p>
 * Esta clase intercepta excepciones lanzadas por los controladores REST
 * y permite centralizar el manejo de errores, especialmente los de validación.
 * </p>
 */
@RestControllerAdvice
public class ManejadorGlobal {

    /**
     * Maneja las excepciones de validación de argumentos en las peticiones.
     * <p>
     * Se ejecuta cuando un objeto marcado con @Valid falla las validaciones
     * de anotaciones como @NotBlank, @Email, @Size, etc.
     * </p>
     *
     * @param ex excepción lanzada cuando falla la validación de un argumento
     * @return ResponseEntity con un mapa donde:
     *         <ul>
     *           <li>clave = nombre del campo con error</li>
     *           <li>valor = mensaje de error asociado</li>
     *         </ul>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>manejarErroresValidacion(MethodArgumentNotValidException ex) {

	Map<String, String> errores = new HashMap<>();

        ex.getBindingResult()
            .getFieldErrors()
            .forEach(error -> {errores.put(
                error.getField(),
                error.getDefaultMessage()
            );
        });

        return ResponseEntity
            .badRequest()
            .body(errores);
    }
}
