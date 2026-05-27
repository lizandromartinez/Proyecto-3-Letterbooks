package mx.unam.ciencias.myp.letterbooks.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Clase que representa un DTO para recibir la
 * información de una cita adjunta a una reseña.
 */
public class NuevaCita {

    /* Texto de la cita. */
    @NotBlank(message = "El texto de la cita no puede estar vacío.")
    private String texto;

    /* Página opcional de donde se extrajo la cita. */
    private Integer pagina;

    /**
     * Obtiene el texto de la cita.
     * @return texto contenido de la cita.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Define el texto de la cita.
     * @param texto contenido de la cita.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Obtiene la página de la cita.
     * @return pagina número de página.
     */
    public Integer getPagina() {
        return pagina;
    }

    /**
     * Define la página de la cita.
     * @param pagina número de página.
     */
    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }
}
