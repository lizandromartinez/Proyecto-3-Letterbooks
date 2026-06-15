package mx.unam.ciencias.myp.letterbooks.dto;

/**
 * DTO de cita literaria para la sección de reseñas recientes de la landing.
 */
public class VistaCitaReciente {

    private String texto;

    public VistaCitaReciente() {
    }

    public VistaCitaReciente(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
