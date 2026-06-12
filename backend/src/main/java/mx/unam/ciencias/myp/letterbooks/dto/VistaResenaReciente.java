package mx.unam.ciencias.myp.letterbooks.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO aplanado de reseña reciente para la landing page.
 */
public class VistaResenaReciente {

    private Integer idResena;
    private Integer idLibro;
    private String tituloLibro;
    private String nombreAutor;
    private String imagenLibro;
    private String nombreUsuario;
    private String avatarUsuario;
    private String fechaPublicacion;
    private Integer calificacionLibro;
    private Integer calificacionResena;
    private String textoResena;
    private Integer likes;
    private Integer totalComentarios;
    private Boolean likeActivo = false;
    private List<VistaCitaReciente> citas = new ArrayList<>();

    public Integer getIdResena() {
        return idResena;
    }

    public void setIdResena(Integer idResena) {
        this.idResena = idResena;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getImagenLibro() {
        return imagenLibro;
    }

    public void setImagenLibro(String imagenLibro) {
        this.imagenLibro = imagenLibro;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getAvatarUsuario() {
        return avatarUsuario;
    }

    public void setAvatarUsuario(String avatarUsuario) {
        this.avatarUsuario = avatarUsuario;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Integer getCalificacionLibro() {
        return calificacionLibro;
    }

    public void setCalificacionLibro(Integer calificacionLibro) {
        this.calificacionLibro = calificacionLibro;
    }

    public Integer getCalificacionResena() {
        return calificacionResena;
    }

    public void setCalificacionResena(Integer calificacionResena) {
        this.calificacionResena = calificacionResena;
    }

    public String getTextoResena() {
        return textoResena;
    }

    public void setTextoResena(String textoResena) {
        this.textoResena = textoResena;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getTotalComentarios() {
        return totalComentarios;
    }

    public void setTotalComentarios(Integer totalComentarios) {
        this.totalComentarios = totalComentarios;
    }

    public Boolean getLikeActivo() {
        return likeActivo;
    }

    public void setLikeActivo(Boolean likeActivo) {
        this.likeActivo = likeActivo;
    }

    public List<VistaCitaReciente> getCitas() {
        return citas;
    }

    public void setCitas(List<VistaCitaReciente> citas) {
        this.citas = citas;
    }
}
