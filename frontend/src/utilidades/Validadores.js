/**
 * Valida si la cadena ingresada como nombre de usuario no está vacía o solo contiene espacios.
 * @param {string} nombreUsuario El texto ingresado en el campo de nombre de usuario.
 * @return {boolean} true si el formato es válido, false en caso contrario.
 */
export const validarNombreUsuario = (nombreUsuario) => {
    return nombreUsuario !== null && nombreUsuario.trim().length > 0;
};

/**
 * Valida que la contraseña cumpla con los requisitos mínimos de longitud.
 * @param {string} contrasena El texto ingresado en el campo de contraseña.
 * @return {boolean} true si tiene al menos 6 caracteres, false en caso contrario.
 */
export const validarContrasena = (contrasena) => {
    return contrasena !== null && contrasena.length >= 6;
};
