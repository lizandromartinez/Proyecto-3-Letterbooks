import axios from "axios";

/**
 * Sube una imagen al servidor y retorna su URL pública de acceso.
 * <p>
 * Envía el archivo como multipart/form-data al endpoint de almacenamiento
 * del backend, que lo guarda en el directorio correspondiente según el tipo.
 * </p>
 *
 * @param {File} archivo archivo de imagen seleccionado por el usuario.
 * @param {string} token JWT de autenticación del usuario.
 * @param {string} tipo carpeta destino: "avatares" o "banners". Por defecto "avatares".
 * @returns {Promise<string>} URL pública de la imagen guardada en el servidor.
 */
export async function subirImagen(archivo, token, tipo = "avatares") {
    const formData = new FormData();
    formData.append("archivo", archivo);
    const respuesta = await axios.post(
        `http://localhost:8080/api/almacenamiento/imagen/${tipo}`,
        formData,
        {
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "multipart/form-data"
            }
        }
    );
    return respuesta.data.url;
}
