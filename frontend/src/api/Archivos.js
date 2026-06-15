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
    try {
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
        if (!respuesta.data?.url) {
            throw new Error(respuesta.data?.error || "No se recibió la URL de la imagen.");
        }
        return respuesta.data.url;
    } catch (error) {
        if (error.response?.status === 413) {
            throw new Error("La imagen supera el límite de 5MB.");
        }
        throw new Error(error.response?.data?.error || "Error al subir la imagen.");
    }
}
