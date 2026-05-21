import axios from "axios";

const API_URL = "http://localhost:8080/api/catalogo";

/**
 * Obtiene el listado completo de libros disponibles en el catálogo.
 * Se utiliza para poblar el selector de libro favorito en el formulario
 * de edición de perfil.
 *
 * @param {string} token JWT de autenticación del usuario.
 * @returns {Promise<Array>} lista de libros con sus datos completos.
 */
export async function obtenerLibros(token) {

    const respuesta = await axios.get(
        `${API_URL}/libros`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return respuesta.data;
}

/**
 * Obtiene el listado completo de autores registrados en el sistema.
 * Se utiliza para poblar el selector de autor favorito en el formulario
 * de edición de perfil.
 *
 * @param {string} token JWT de autenticación del usuario.
 * @returns {Promise<Array>} lista de autores con id y nombre.
 */
export async function obtenerAutores(token) {

    const respuesta = await axios.get(
        `${API_URL}/autores`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return respuesta.data;
}

/**
 * Obtiene el listado completo de géneros literarios disponibles.
 * Se utiliza para poblar el selector de género favorito en el formulario
 * de edición de perfil.
 *
 * @param {string} token JWT de autenticación del usuario.
 * @returns {Promise<Array>} lista de géneros con id y nombre.
 */
export async function obtenerGeneros(token) {

    const respuesta = await axios.get(
        `${API_URL}/generos`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return respuesta.data;
}

/**
 * Busca libros cuyo título contenga la cadena proporcionada.
 * Se invoca con debounce desde el campo de búsqueda del formulario
 * de edición de perfil para filtrar resultados en tiempo real.
 *
 * @param {string} titulo cadena parcial o completa del título a buscar.
 * @param {string} token JWT de autenticación del usuario.
 * @returns {Promise<Array>} lista de libros que coinciden con la búsqueda.
 */
export async function buscarLibros(titulo, token) {

    const respuesta = await axios.get(
        `${API_URL}/libros/buscar?titulo=${titulo}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return respuesta.data;
}

/**
 * Busca autores cuyo nombre contenga la cadena proporcionada.
 * Se invoca con debounce desde el campo de búsqueda del formulario
 * de edición de perfil para filtrar resultados en tiempo real.
 *
 * @param {string} nombre cadena parcial o completa del nombre a buscar.
 * @param {string} token JWT de autenticación del usuario.
 * @returns {Promise<Array>} lista de autores que coinciden con la búsqueda.
 */
export async function buscarAutores(nombre, token) {

    const respuesta = await axios.get(
        `${API_URL}/autores/buscar?nombre=${nombre}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return respuesta.data;
}
