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
