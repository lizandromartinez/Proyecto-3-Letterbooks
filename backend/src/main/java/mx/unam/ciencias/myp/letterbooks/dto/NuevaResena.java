package mx.unam.ciencias.myp.letterbooks.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Clase que representa un DTO para recibir la información
 * de una nueva reseña desde la interfaz de usuario.
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
    @NotBlank(message = "El texto de la reseña no puede estar vacío.")
    private String textoResena;

    /**
     * Obtiene el ID del libro.
     * @return idLibro identificador del libro.
     */
    public Integer getIdLibro() {
        return idLibro;
    }

    /**
     * Define el ID del libro.
     * @param idLibro identificador del libro.
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
}
