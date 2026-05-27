/**
 * Extrae el nombre de usuario del payload de un token JWT.
 * @param {string} token token de sesión activo.
 * @return {string|null} nombre de usuario o null si es inválido.
 */
export const obtenerUsuarioDelToken = (token) => {
    if (!token) return null;
    try {
        const cargaUtil = token.split('.')[1];
        const decodificado = JSON.parse(atob(cargaUtil));
        return decodificado.nombre_usuario;
    } catch (error) {
        return null;
    }
};

/**
 * Extrae el rol del usuario del payload de un token JWT.
 * @param {string} token token de sesión activo.
 * @return {string|null} rol ('admin' o 'usuario') o null si es inválido.
 */
export const obtenerRolDelToken = (token) => {
    if (!token) return null;
    try {
        const cargaUtil = token.split('.')[1];
        const decodificado = JSON.parse(atob(cargaUtil));
        return decodificado.rol;
    } catch (error) {
        return null;
    }
};

/**
 * Extrae el identificador único del usuario del payload de un token JWT.
 * @param {string} token token de sesión activo.
 * @return {number|null} ID numérico del usuario o null si es inválido.
 */
export const obtenerIdUsuarioDelToken = (token) => {
    if (!token) return null;
    try {
        const cargaUtil = token.split('.')[1];
        const decodificado = JSON.parse(atob(cargaUtil));
        return decodificado.id_usuario;
    } catch (error) {
        return null;
    }
};
