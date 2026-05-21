import axios from "axios";

const API_URL = "http://localhost:8080/api/usuarios";

/**
 * Obtiene el perfil de un usuario.
 *
 * @param {number} idUsuario ID del usuario.
 * @param {string} token JWT de autenticación.
 * @returns {Promise<Object>} perfil del usuario.
 */
export async function obtenerPerfil(idUsuario, token) {
    const respuesta = await axios.get(
        `${API_URL}/${idUsuario}/perfil`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return respuesta.data;
}

/**
 * Actualiza el perfil de un usuario.
 *
 * @param {number} idUsuario ID del usuario.
 * @param {Object} datos datos actualizados.
 * @param {string} token JWT de autenticación.
 * @returns {Promise<Object>} perfil actualizado.
 */
export async function actualizarPerfil(idUsuario, datos, token) {
    const respuesta = await axios.put(
        `${API_URL}/${idUsuario}/perfil`,
        datos,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return respuesta.data;
}

/**
 * Obtiene el perfil público de un usuario por su nombre de usuario.
 *
 * @param {string} nombreUsuario nombre de usuario a buscar.
 * @returns {Promise<Object>} perfil del usuario.
 */
export async function obtenerPerfilPublico(nombreUsuario) {
    const respuesta = await axios.get(
        `${API_URL}/perfil/${nombreUsuario}`
    );
    return respuesta.data;
}

