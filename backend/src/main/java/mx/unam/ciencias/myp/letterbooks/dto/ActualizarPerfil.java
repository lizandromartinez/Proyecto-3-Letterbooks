package mx.unam.ciencias.myp.letterbooks.dto;

/**
 * DTO para la actualización de los datos editables del perfil.
 */
public class ActualizarPerfil {

    /* URL del nuevo avatar. */
    private String avatar;

    /* URL del nuevo banner. */
    private String banner;

    /* Nueva biografía. */
    private String biografia;

    /* ID del nuevo autor favorito. */
    private Integer idAutorFavorito;

    /* ID del nuevo género favorito. */
    private Integer idGeneroFavorito;

    /* ID del nuevo libro favorito. */
    private Integer idLibroFavorito;

    /**
     * Obtiene la URL del avatar del perfil.
     *
     * @return URL de la imagen de avatar
     */
    public String getAvatar() {
	return avatar;
    }

    /**
     * Establece la URL del avatar del perfil.
     *
     * @param avatar nueva URL del avatar
     */
    public void setAvatar(String avatar) {
	this.avatar = avatar;
    }

    /**
     * Obtiene la URL del banner del perfil.
     *
     * @return URL de la imagen de banner
     */
    public String getBanner() {
	return banner;
    }

    /**
     * Establece la URL del banner del perfil.
     *
     * @param banner nueva URL del banner
     */
    public void setBanner(String banner) {
	this.banner = banner;
    }

    /**
     * Obtiene la biografía del usuario.
     *
     * @return texto de la biografía
     */
    public String getBiografia() {
	return biografia;
    }

    /**
     * Establece la biografía del usuario.
     *
     * @param biografia nueva biografía del perfil
     */
    public void setBiografia(String biografia) {
	this.biografia = biografia;
    }

    /**
     * Obtiene el identificador del autor favorito seleccionado.
     *
     * @return ID del autor favorito
     */
    public Integer getIdAutorFavorito() {
	return idAutorFavorito;
    }

    /**
     * Establece el identificador del autor favorito.
     *
     * @param idAutorFavorito ID del nuevo autor favorito
     */
    public void setIdAutorFavorito(Integer idAutorFavorito) {
	this.idAutorFavorito = idAutorFavorito;
    }

    /**
     * Obtiene el identificador del género favorito seleccionado.
     *
     * @return ID del género favorito
     */
    public Integer getIdGeneroFavorito() {
	return idGeneroFavorito;
    }

    /**
     * Establece el identificador del género favorito.
     *
     * @param idGeneroFavorito ID del nuevo género favorito
     */
    public void setIdGeneroFavorito(Integer idGeneroFavorito) {
	this.idGeneroFavorito = idGeneroFavorito;
    }

    /**
     * Obtiene el identificador del libro favorito seleccionado.
     *
     * @return ID del libro favorito
     */
    public Integer getIdLibroFavorito() {
	return idLibroFavorito;
    }
    
    /**
     * Establece el identificador del libro favorito.
     *
     * @param idLibroFavorito ID del nuevo libro favorito
     */
    public void setIdLibroFavorito(Integer idLibroFavorito) {
	this.idLibroFavorito = idLibroFavorito;
    }    
}
