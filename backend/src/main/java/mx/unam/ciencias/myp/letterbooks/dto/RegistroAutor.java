package mx.unam.ciencias.myp.letterbooks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Representa el formulario de entrada de datos para la vista de subir o registrar un autor.
 * Esta clase actúa como un molde intermedio (DTO) para capturar el texto plano en formato
 * JSON enviado desde el Frontend. El framework Spring Boot, en conjunto con la biblioteca
 * Jackson, orquesta la traducción del JSON de la siguiente manera:
 * - Instancia la clase utilizando el constructor vacío por defecto.
 * - Utiliza los métodos setters de manera automatizada tras bambalinas para inyectar 
 * cada valor del JSON en su atributo correspondiente.
 *
 * Una vez mapeado y validadas sus restricciones mediante Bean Validation, se entrega el objeto 
 * completamente estructurado a la capa de servicio para su posterior procesamiento.
 */
public class RegistroAutor {

    @NotBlank(message = "El nombre del autor no puede estar vacío")
    @Size(max = 150, message = "El nombre del autor no puede superar los 150 caracteres")
    private String nombreAutor;

    @Size(max = 10000, message = "La biografía supera el límite de caracteres permitido")
    private String biografia;

    @Size(max = 10, message = "La fecha de nacimiento debe cumplir exactamente el formato YYYY-MM-DD")
    private String fechaNacimiento;

    @Size(max = 255, message = "La ruta de la foto no puede superar los 255 caracteres")
    private String foto;

    /**
     * Constructor vacío por defecto.
     * Requerido obligatoriamente por la biblioteca Jackson para poder iniciar 
     * el proceso de deserialización y construcción del objeto desde el JSON.
     */
    public RegistroAutor() {}

    /**
     * Obtiene el nombre completo del autor.
     * Invocado por el validador de Spring para verificar la restricción {@code @NotBlank}
     * y por la capa de Servicio para extraer el dato hacia la entidad de persistencia.
     * @return Cadena de texto con el nombre del autor.
     */
    public String getNombreAutor() {
        return this.nombreAutor;
    }

    /**
     * Obtiene la biografía del autor.
     * Invocado por la capa de Servicio para leer la reseña biográfica capturada en el formulario JSON.
     * @return Cadena de texto con la biografía del autor.
     */
    public String getBiografia() {
        return this.biografia;
    }

    /**
     * Obtiene la fecha de nacimiento del autor.
     * Transmitida en formato de cadena plana {@code YYYY-MM-DD} para mantener la simplicidad en el cliente y en db.
     * @return Cadena de texto con la fecha de nacimiento.
     */
    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    /**
     * Obtiene la ruta o URL de la fotografía del autor.
     * Invocado por la capa de Servicio para mapear el recurso multimedia al modelo final.
     * @return Cadena de texto con la ubicación o enlace de la foto.
     */
    public String getFoto() {
        return this.foto;
    }

    /**
     * Define el nombre completo del autor.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "nombreAutor" desde el texto plano del JSON entrante.
     * @param nombreAutor El nombre del autor extraído del JSON del Frontend.
     */
    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    /**
     * Define la biografía del autor.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "biografia" desde el texto plano del JSON entrante.
     * @param biografia La biografía extraída del JSON del Frontend.
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    /**
     * Define la fecha de nacimiento del autor.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "fechaNacimiento" desde el texto plano del JSON entrante.
     * @param fechaNacimiento La fecha (YYYY-MM-DD) extraída del JSON del Frontend.
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Define la ruta o URL de la fotografía del autor.
     * Invocación Automática: Ejecutado por la biblioteca Jackson al leer 
     * la propiedad "foto" desde el texto plano del JSON entrante.
     * @param foto La ubicación de la imagen extraída del JSON del Frontend.
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }
}
