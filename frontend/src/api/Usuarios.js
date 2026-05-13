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

/**
 * Registra un nuevo usuario en el sistema.
 * <p>
 * Envía los datos del formulario al backend Spring Boot mediante una petición HTTP POST.
 * Maneja la respuesta como texto y la convierte a JSON si es posible.
 * </p>
 *
 * @async
 * @function registrarUsuario
 * @param {Object} datosFormulario datos del usuario a registrar
 * @returns {Promise<Object|string>} respuesta del backend (JSON o texto)
 * @throws {Object|string} error devuelto por el backend si la respuesta HTTP no es OK
 */
export async function registrarUsuario(datosFormulario) {

  const respuesta = await fetch(
    `${URL_BASE}/registro`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(datosFormulario)
    }
  );

  const texto = await respuesta.text();

  let datos;

  try {
    datos = JSON.parse(texto);
  } catch {
    datos = texto;
  }

  if (!respuesta.ok) {
    throw datos;
  }

  return datos;
}
