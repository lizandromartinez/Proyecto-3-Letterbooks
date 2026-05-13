/**
 * Valida si una cadena tiene el formato correcto de correo electrónico.
 * @param {string} correo El texto ingresado en el campo de correo.
 * @return {boolean} true si el formato es válido, false en caso contrario.
 */
export const validarCorreo = (correo) => {
    // Expresión regular básica para validar estructura de email
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(correo);
};

/**
 * Valida que la contraseña cumpla con los requisitos mínimos de longitud.
 * @param {string} contrasena El texto ingresado en el campo de contraseña.
 * @return {boolean} true si tiene al menos 6 caracteres, false en caso contrario.
 */
export const validarContrasena = (contrasena) => {
    return contrasena !== null && contrasena.length >= 6;
};
