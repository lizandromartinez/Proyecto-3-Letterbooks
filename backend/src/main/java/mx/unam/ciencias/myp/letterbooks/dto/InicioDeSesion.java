package mx.unam.ciencias.myp.letterbooks.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Clase que representa un DTO para recibir credenciales de inicio de sesión.
 * Incluye validaciones para asegurar la integridad de los datos recibidos.
 */
public class InicioDeSesion {

    /* Correo electrónico del usuario utilizado para iniciar sesión. */
    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "Debe proporcionar un formato de correo válido.")
    private String correo;

    /* Contraseña del usuario utilizada para autenticación. */
    @NotBlank(message = "La contraseña no puede estar vacía.")
    private String contrasena;

    /**
     * Obtiene el correo electrónico ingresado.
     * @return correo cadena con el email.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Define el correo electrónico ingresado.
     * @param correo el correo ingresado por el usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña ingresada.
     * @return contrasena cadena con la contraseña.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Define la contraseña ingresada.
     * @param contrasena la contraseña ingresada por el usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
