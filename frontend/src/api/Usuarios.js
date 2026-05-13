
/**
 * Registra un nuevo usuario en el sistema.
 * <p>
 * Envía los datos del formulario al backend Spring Boot mediante una petición HTTP POST.
 * Maneja la respuesta como texto y la convierte a JSON si es posible.
 * </p>
 *
 * @async
 * @function registrarUsuario
 * @param {Object} formData datos del usuario a registrar
 * @param {string} formData.nombreUsuario nombre de usuario
 * @param {string} formData.correo correo electrónico del usuario
 * @param {string} formData.contrasena contraseña del usuario
 * @param {string} [formData.nombreCompleto] nombre completo del usuario (opcional)
 *
 * @returns {Promise<Object|string>} respuesta del backend (JSON o texto)
 * @throws {Object|string} error devuelto por el backend si la respuesta HTTP no es OK
 */
export async function registrarUsuario(datosFormulario) {

  const respuesta = await fetch(
    "http://localhost:8080/autenticacion/registro",
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
