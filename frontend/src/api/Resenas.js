import axios from 'axios';
import { obtenerTokenSesion } from '../utilidades/DecodificadorToken';

const URL_BASE = 'http://localhost:8080/api/resenas';

const encabezadoAuth = (token) => {
    const tokenActivo = obtenerTokenSesion(token);
    return tokenActivo ? { Authorization: `Bearer ${tokenActivo}` } : {};
};

/**
 * Consulta las reseñas más recientes para la landing page (público).
 * @param {number} limite cantidad máxima de reseñas.
 * @return {Promise<Array>} lista de reseñas recientes.
 */
export const obtenerResenasRecientes = async (limite = 5, token = null) => {
    const respuesta = await axios.get(`${URL_BASE}/recientes`, {
        params: { limite },
        headers: encabezadoAuth(token)
    });
    return respuesta.data;
};

/**
 * Consulta todas las reseñas asociadas a un libro.
 * @param {number} idLibro identificador del libro.
 * @return {Promise<Array>} lista de reseñas.
 */
export const obtenerResenas = async (idLibro, token) => {
    const respuesta = await axios.get(`${URL_BASE}/libro/${idLibro}`, {
        headers: encabezadoAuth(token)
    });
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

/**
 * Alterna el estado de un "Me gusta" de una reseña.
 * @param {number} idResena id de la reseña.
 * @param {string} token token JWT del usuario activo.
 * @return {Promise<Object>} objeto con el estado actualizado { likeActivo: boolean }.
 */
export const alternarLikeResena = async (idResena, token) => {
    const respuesta = await axios.post(`${URL_BASE}/${idResena}/like`, {}, {
        headers: { Authorization: `Bearer ${token}` }
    });
    return respuesta.data;
};
