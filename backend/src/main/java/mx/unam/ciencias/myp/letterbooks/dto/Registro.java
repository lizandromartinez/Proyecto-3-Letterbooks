package mx.unam.ciencias.myp.letterbooks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO que representa la información necesaria para registrar un nuevo usuario.
 * <p>
 * Esta clase se utiliza para transferir los datos de registro desde el cliente
 * hacia el backend, aplicando validaciones básicas de formato y longitud.
 * </p>
 */
public class Registro {

    /**
     * Correo electrónico del usuario.
     * Debe ser válido y no estar vacío.
     */
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo inválido")
    private String correo;

    /**
     * Nombre de usuario del sistema.
     * Debe tener entre 3 y 50 caracteres y no puede estar vacío.
     */
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener al menos 3 caracteres")
    private String nombreUsuario;

    /**
     * Contraseña del usuario.
     * Debe tener entre 6 y 100 caracteres y no puede estar vacía.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena;

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return correo del usuario
     */
    public String getCorreo() {
	return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correo correo del usuario
     */
    public void setCorreo(String correo) {
	this.correo = correo;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return nombre de usuario
     */
    public String getNombreUsuario() {
	return nombreUsuario;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param nombreUsuario nombre de usuario
     */
    public void setNombreUsuario(String nombreUsuario) {
	this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return contraseña del usuario
     */
    public String getContrasena() {
	return contrasena;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contrasena contraseña del usuario
     */
    public void setContrasena(String contrasena) {
	this.contrasena = contrasena;
    }
}
