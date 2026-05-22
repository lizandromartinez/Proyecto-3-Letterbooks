import axios from 'axios';

const URL_BASE = 'http://localhost:8080/api/resenas';

/**
 * Consulta todas las reseñas asociadas a un libro.
 * @param {number} idLibro identificador del libro.
 * @return {Promise<Array>} lista de reseñas.
 */
export const obtenerResenas = async (idLibro) => {
    const respuesta = await axios.get(`${URL_BASE}/libro/${idLibro}`);
    return respuesta.data;
};

/**
 * Crea una nueva reseña en el sistema.
 * @param {Object} datosResena DTO con idLibro, calificacionLibro y textoResena.
 * @param {string} token token JWT de la sesión activa.
 * @return {Promise<Object>} la reseña creada.
 */
export const crearResena = async (datosResena, token) => {
    const respuesta = await axios.post(URL_BASE, datosResena, {
        headers: { Authorization: `Bearer ${token}` }
    });
    return respuesta.data;
};

/**
 * Edita una reseña existente.
 * @param {number} idResena identificador de la reseña a editar.
 * @param {Object} datosResena DTO con la nueva calificación y texto.
 * @param {string} token token JWT del autor original.
 * @return {Promise<Object>} la reseña actualizada.
 */
export const editarResena = async (idResena, datosResena, token) => {
    const respuesta = await axios.put(`${URL_BASE}/${idResena}`, datosResena, {
        headers: { Authorization: `Bearer ${token}` }
    });
    return respuesta.data;
};

/**
 * Elimina una reseña del sistema.
 * @param {number} idResena identificador de la reseña.
 * @param {string} token token JWT del autor original.
 * @return {Promise<string>} mensaje de éxito.
 */
export const eliminarResena = async (idResena, token) => {
    const respuesta = await axios.delete(`${URL_BASE}/${idResena}`, {
        headers: { Authorization: `Bearer ${token}` }
    });
    return respuesta.data;
};
