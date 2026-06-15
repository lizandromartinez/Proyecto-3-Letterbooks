package mx.unam.ciencias.myp.letterbooks.dto;

/**
 * DTO que representa la respuesta del servidor tras un registro exitoso.
 * <p>
 * Expone únicamente los datos públicos del usuario recién creado,
 * omitiendo información sensible como la contraseña o el rol.
 * </p>
 */
public class RegistroRespuesta {

    /* Identificador único del usuario registrado. */
    private Integer idUsuario;

    /* Nombre de usuario elegido durante el registro. */
    private String nombreUsuario;

    /* Correo electrónico del usuario registrado. */
    private String correo;

    /**
     * Construye una respuesta con los datos públicos del usuario registrado.
     *
     * @param idUsuario identificador único del usuario
     * @param nombreUsuario nombre de usuario registrado
     * @param correo correo electrónico del usuario
     */
    public RegistroRespuesta(Integer idUsuario, String nombreUsuario, String correo) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
    }

     /**
     * Obtiene el identificador del usuario.
     *
     * @return identificador único del usuario
     */
    public Integer getIdUsuario() {
	return idUsuario;
    }

     /**
     * Obtiene el nombre de usuario.
     *
     * @return nombre de usuario registrado
     */
    public String getNombreUsuario() {
	return nombreUsuario;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return correo electrónico registrado
     */
    public String getCorreo() {
	return correo;
    }
}
