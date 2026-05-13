import axios from 'axios';

const URL_BASE = 'http://localhost:8080/auth';

/**
 * Realiza la petición POST para iniciar sesión en el servidor.
 * @param {Object} credenciales DTO con el correo y la contraseña.
 * @return {Promise<Object>} Promesa que resuelve con la respuesta del servidor (token).
 * @throws {Error} Lanza un error con el mensaje del backend si las credenciales fallan.
 */
export const peticionLogin = async (credenciales) => {
    try {
        const respuesta = await axios.post(`${URL_BASE}/login`, credenciales);
        return respuesta.data;
    } catch (error) {
        if (error.response && error.response.data && error.response.data.error) {
            throw new Error(error.response.data.error);
        }
        throw new Error("Error de conexión con el servidor.");
    }
};
