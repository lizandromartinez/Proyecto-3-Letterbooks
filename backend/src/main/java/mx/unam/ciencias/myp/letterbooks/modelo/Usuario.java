package mx.unam.ciencias.myp.letterbooks.modelo;

import java.util.List;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Clase que representa a un usuario en el sistema.
 * Mapea la tabla "Usuario" de la base de datos MariaDB.
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    /* ID único del usuario. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /* Correo electrónico único del usuario. */
    @JsonIgnore
    @Column(
        name = "correo",
        unique = true,
        nullable = false,
        length = 150
    )
    private String correo;

    /* Nombre de usuario utilizado para iniciar sesión. */
    @Column(
        name = "nombre_usuario",
        unique = true,
        nullable = false,
        length = 50
    )
    private String nombreUsuario;

    /* Contraseña hasheada del usuario. */
    @JsonIgnore
    @Column(
        name = "contrasena",
        nullable = false,
        length = 255
    )
    private String contrasena;

    /* Rol asingado al usuario dentro del sistema. */
    @Enumerated(EnumType.STRING)
    @Column(name = "rol",
	nullable = false,
	columnDefinition = "ENUM('admin','usuario') DEFAULT 'usuario'"
    )
    private Rol rol = Rol.usuario;

    /* Reseñas escritas por el usuario. */
    @OneToMany(mappedBy = "usuario")
    private List<Resena> resenas;

    /* Reseñas a las que dio like el usuario. */
    @OneToMany(mappedBy = "usuario")
    private List<LikesResena> likesResenas;

    /* Comentarios a los que dio like el usuario. */
    @OneToMany(mappedBy = "usuario")
    private List<LikesComentario> likesComentarios;

    /* Reseñas que ha calificado el usuario. */
    @OneToMany(mappedBy = "usuario")
    private List<CalificacionResena> calificacionesResenas;
    
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

    /**
     * Obtiene las reseñas escritas por el usuario.
     * @return resenas lista de reseñas del usuario.
     */
    public List<Resena> getResenas() {
	return resenas;
    }

    /**
     * Define las reseñas escritas por el usuario.
     * @param resenas lista de reseñas del usuario.
     */
    public void setResenas(List<Resena> resenas) {
	this.resenas = resenas;
    }

    /**
     * Obtiene las reseñas a las que el usuario dio like.
     * @return likesResenas lista de likes en reseñas.
     */
    public List<LikesResena> getLikesResenas() {
	return likesResenas;
    }
    
    /**
     * Define las reseñas a las que el usuario dio like.
     * @param likesResenas lista de likes en reseñas.
     */
    public void setLikesResenas(List<LikesResena> likesResenas) {
	this.likesResenas = likesResenas;
    }

    /**
     * Obtiene los comentarios a los que el usuario dio like.
     * @return likesComentarios lista de likes en comentarios.
     */
    public List<LikesComentario> getLikesComentarios() {
	return likesComentarios;
    }
    
    /**
     * Define los comentarios a los que el usuario dio like.
     * @param likesComentarios lista de likes en comentarios.
     */
    public void setLikesComentarios(List<LikesComentario> likesComentarios) {
	this.likesComentarios = likesComentarios;
    }

    /**
     * Obtiene las reseñas calificadas por el usuario.
     * @return calificacionesResenas lista de calificaciones de reseñas.
     */
    public List<CalificacionResena> getCalificacionesResenas() {
	return calificacionesResenas;
    }

    /**
     * Define las reseñas calificadas por el usuario.
     * @param calificacionesResenas lista de calificaciones de reseñas.
     */
    public void setCalificacionesResenas(List<CalificacionResena> calificacionesResenas) {
	this.calificacionesResenas = calificacionesResenas;
    }
}
