package mx.unam.ciencias.myp.letterbooks.modelo;

import javax.persistence.*;

/**
 * Clase que representa a un usuario en el sistema.
 * Mapea la tabla "Usuario" de la base de datos MariaDB.
 */
@Entity
@Table(name = "Usuario")

public class Usuario {

    /* ID único del usuario. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /* Correo electrónico único del usuario. */
    @Column(unique = true, nullable = false)
    private String correo;

    /* Contraseña hasheada del usuario. */
    @Column(nullable = false)
    private String contrasena;

    /* Nombre de usuario utilizado para iniciar sesión. */
    @Column(name = "nombre_usuario", unique = true, nullable = false)
    private String nombreUsuario;

    /* Rol asingado al usuario dentro del sistema. */
    @Enumerated(EnumType.STRING)
    private Rol rol;

    /**
     * Enumeración para los tipos de roles.
     */
    public enum Rol {
        admin,
        usuario
    }

    /**
     * Obtiene el id único del usuario.
     * @return idUsuario id entero.
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * Define el id único del usuario.
     * @param idUsuario id del usuario.
     */
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    /**
     * Obtiene el correo electrónico del usuario.
     * @return correo cadena con el email.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Define el correo unico del usuario.
     * @param correo el correo del usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña hasheada del usuario.
     * @return contrasena contraseña hasheada.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Define la contraseña del usuario.
     * @param contrasena el hash de la contraseña.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el nombre de usuario.
     * @return nombreUsuario el nombre del usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    /**
     * Define el nombre de usuario.
     * @param nombreUsuario el nombre de usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    /**
     * Obtiene el rol del usuario (admin o usuario).
     * @return rol valor de la enumeración Rol.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Define el rol del usuario.
     * @param rol valor de la enumeración Rol.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
