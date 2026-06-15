const decodificarPayload = (token) => {
    if (!token) return null;
    try {
        const cargaUtil = token.split('.')[1];
        return JSON.parse(atob(cargaUtil));
    } catch {
        return null;
    }
};

/**
 * Verifica si un token JWT existe y no ha expirado.
 * @param {string|null} token token de sesión.
 * @return {boolean} true si el token es usable.
 */
export const tokenEsValido = (token) => {
    const payload = decodificarPayload(token);
    if (!payload?.exp) return false;
    return payload.exp * 1000 > Date.now();
};

/**
 * Obtiene el token activo desde el contexto o localStorage.
 * @param {string|null} token token del contexto de sesión.
 * @return {string|null} token usable o null.
 */
export const obtenerTokenSesion = (token) => {
    const candidato = token || localStorage.getItem('token');
    return tokenEsValido(candidato) ? candidato : null;
};

/**
 * Extrae el nombre de usuario del payload de un token JWT.
 * @param {string} token token de sesión activo.
 * @return {string|null} nombre de usuario o null si es inválido.
 */
export const obtenerUsuarioDelToken = (token) => {
    const payload = decodificarPayload(token);
    return payload?.nombre_usuario ?? null;
};

/**
 * Extrae el rol del usuario del payload de un token JWT.
 * @param {string} token token de sesión activo.
 * @return {string|null} rol ('admin' o 'usuario') o null si es inválido.
 */
export const obtenerRolDelToken = (token) => {
    const payload = decodificarPayload(token);
    return payload?.rol ?? null;
};

/**
 * Extrae el identificador único del usuario del payload de un token JWT.
 * @param {string} token token de sesión activo.
 * @return {number|null} ID numérico del usuario o null si es inválido.
 */
export const obtenerIdUsuarioDelToken = (token) => {
    const payload = decodificarPayload(token);
    return payload?.id_usuario ?? null;
};
