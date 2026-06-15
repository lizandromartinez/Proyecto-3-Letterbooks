package mx.unam.ciencias.myp.letterbooks.modelo;

import jakarta.persistence.*;

/**
 * Clase que representa un perfil en el sistema.
 * Mapea la tabla "perfil" de la base de datos MariaDB.
 */
@Entity
@Table(name = "perfil")
public class Perfil {

    /* ID único del perfil. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Integer idPerfil;

    /* Usuario asociado al perfil. */
    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /* Autor favorito del perfil. */
    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

    /* Género favorito del perfil. */
    @ManyToOne
    @JoinColumn(name = "id_genero")
    private Genero genero;

    /* Libro favorito del perfil. */
    @ManyToOne
    @JoinColumn(name = "id_libro")
    private Libro libro;

    /* Biografía del usuario. */
    @Column(name = "biografia", columnDefinition = "TEXT")
    private String biografia;

    /* Ruta de la imagen de avatar del perfil. */
    @Column(name = "avatar", length = 255)
    private String avatar;

    /* Ruta de la imagen de banner del perfil. */
    @Column(name = "banner", length = 255)
    private String banner;

    /* Cantidad de reportes del perfil. */
    @Column(name = "reportes", columnDefinition = "INT DEFAULT 0")
    private Integer reportes = 0;

    /* Fecha de registro del perfil. */
    @Column(name = "fecha_registro", length = 10)
    private String fechaRegistro;

    /**
     * Obtiene el id único del perfil.
     * @return idPerfil id entero.
     */
    public Integer getIdPerfil() {
	return idPerfil;
    }

    /**
     * Define el id único del perfil.
     * @param idPerfil id del perfil.
     */
    public void setIdPerfil(Integer idPerfil) {
	this.idPerfil = idPerfil;
    }

    /**
     * Obtiene el usuario asociado al perfil.
     * @return usuario asociado al perfil.
     */
    public Usuario getUsuario() {
	return usuario;
    }

    /**
     * Define el usuario asociado al perfil.
     * @param usuario usuario asociado.
     */
    public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
    }

    /**
     * Obtiene el autor favorito del perfil.
     * @return autor favorito del perfil.
     */
    public Autor getAutor() {
	return autor;
    }

    /**
     * Define el autor favorito del perfil.
     * @param autor autor favorito.
     */
    public void setAutor(Autor autor) {
	this.autor = autor;
    }

    /**
     * Obtiene el género favorito del perfil.
     * @return genero género favorito del perfil.
     */
    public Genero getGenero() {
	return genero;
    }

    /**
     * Define el género favorito del perfil.
     * @param genero género favorito.
     */
    public void setGenero(Genero genero) {
	this.genero = genero;
    }

    /**
     * Obtiene el libro favorito del perfil.
     * @return libro libro favorito del perfil.
     */
    public Libro getLibro() {
	return libro;
    }

    /**
     * Define el libro favorito del perfil.
     * @param libro libro favorito.
     */
    public void setLibro(Libro libro) {
	this.libro = libro;
    }

    /**
     * Obtiene la biografía del perfil.
     * @return biografia biografía del perfil.
     */
    public String getBiografia() {
	return biografia;
    }

    /**
     * Define la biografía del perfil.
     * @param biografia biografía del perfil.
     */
    public void setBiografia(String biografia) {
	this.biografia = biografia;
    }

    /**
     * Obtiene la ruta del avatar del perfil.
     * @return avatar ruta del avatar.
     */
    public String getAvatar() {
	return avatar;
    }

    /**
     * Define la ruta del avatar del perfil.
     * @param avatar ruta del avatar.
     */
    public void setAvatar(String avatar) {
	this.avatar = avatar;
    }
    
    /**
     * Obtiene la ruta del banner del perfil.
     * @return banner ruta del banner.
     */
    public String getBanner() {
	return banner;
    }

    /**
     * Define la ruta del banner del perfil.
     * @param banner ruta del banner.
     */
    public void setBanner(String banner) {
	this.banner = banner;
    }

    /**
     * Obtiene la cantidad de reportes del perfil.
     * @return reportes cantidad de reportes.
     */
    public Integer getReportes() {
	return reportes;
    }

    /**
     * Define la cantidad de reportes del perfil.
     * @param reportes cantidad de reportes.
     */
    public void setReportes(Integer reportes) {
	this.reportes = reportes;
    }

    /**
     * Obtiene la fecha de registro del perfil.
     * @return fechaRegistro fecha de registro.
     */
    public String getFechaRegistro() {
	return fechaRegistro;
    }

    /**
     * Define la fecha de registro del perfil.
     * @param fechaRegistro fecha de registro.
     */
    public void setFechaRegistro(String fechaRegistro) {
	this.fechaRegistro = fechaRegistro;
    }
}
