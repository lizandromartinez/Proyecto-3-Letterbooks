package mx.unam.ciencias.myp.letterbooks.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * Clase que representa un DTO para recibir la información
 * de una nueva reseña, incluyendo sus citas.
 */
public class NuevaResena {

    /* Identificador del libro a reseñar. */
    @NotNull(message = "El ID del libro es obligatorio.")
    private Integer idLibro;

    /* Calificación en estrellas (1 a 5). */
    @NotNull(message = "La calificación es obligatoria.")
    @Min(value = 1, message = "La calificación mínima es 1.")
    @Max(value = 5, message = "La calificación máxima es 5.")
    private Integer calificacionLibro;

    /* Texto de la reseña escrito por el usuario. */
    @NotBlank(message = "La reseña no puede estar vacía.")
    private String textoResena;

    /* Lista de citas opcionales ligadas a la reseña. */
    @Valid
    @Size(max = 3, message = "No se pueden adjuntar más de 3 citas por reseña.")
    private List<NuevaCita> citas;

    /**
     * Obtiene el ID del libro.
     * @return idLibro id del libro.
     */
    public Integer getIdLibro() {
        return idLibro;
    }

    /**
     * Define el ID del libro.
     * @param idLibro id del libro.
     */
    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    /**
     * Obtiene la calificación otorgada al libro.
     * @return calificacionLibro valor del 1 al 5.
     */
    public Integer getCalificacionLibro() {
        return calificacionLibro;
    }

    /**
     * Define la calificación otorgada al libro.
     * @param calificacionLibro valor del 1 al 5.
     */
    public void setCalificacionLibro(Integer calificacionLibro) {
        this.calificacionLibro = calificacionLibro;
    }

    /**
     * Obtiene el texto de la reseña.
     * @return textoResena la reseña del usuario.
     */
    public String getTextoResena() {
        return textoResena;
    }

    /**
     * Define el texto de la reseña.
     * @param textoResena la reseña del usuario.
     */
    public void setTextoResena(String textoResena) {
        this.textoResena = textoResena;
    }

    /**
     * Obtiene la lista de citas adjuntas.
     * @return citas lista de DTOs de citas.
     */
    public List<NuevaCita> getCitas() {
        return citas;
    }

    /**
     * Define la lista de citas adjuntas.
     * @param citas lista de DTOs de citas.
     */
    public void setCitas(List<NuevaCita> citas) {
        this.citas = citas;
    }
}
