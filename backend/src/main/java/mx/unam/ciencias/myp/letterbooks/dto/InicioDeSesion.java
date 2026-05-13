package mx.unam.ciencias.myp.letterbooks.dto;

import javax.validation.constraints.NotBlank;

/**
 * Clase que representa un DTO para recibir credenciales de inicio de sesión.
 * Incluye validaciones para asegurar la integridad de los datos recibidos.
 */
public class InicioDeSesion {

    /* Nombre de usuario utilizado para iniciar sesión. */
    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    private String nombreUsuario;

    /* Contraseña del usuario utilizada para autenticación. */
    @NotBlank(message = "La contraseña no puede estar vacía.")
    private String contrasena;

    /**
     * Obtiene el nombre de usuario ingresado.
     * @return nombreUsuario cadena con el nombre de usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Define el nombre de usuario ingresado.
     * @param nombreUsuario el nombre de usuario ingresado por el usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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
