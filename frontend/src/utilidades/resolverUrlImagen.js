
/**
 * Resuelve la URL correcta de una imagen según su origen.
 * <p>
 * Si la ruta es nula o corresponde a una imagen por defecto del frontend
 * (rutas que comienzan con "/estilos/"), retorna la imagen de respaldo local.
 * Si la ruta apunta a una imagen subida por el usuario, le agrega el prefijo
 * del servidor backend para formar la URL completa de acceso.
 * </p>
 *
 * @param {string} ruta ruta de la imagen almacenada en la base de datos.
 * @param {string|null} imagenDefecto imagen local a mostrar si no hay ruta válida.
 * @returns {string} URL completa de la imagen a renderizar.
 */
export function resolverUrlImagen(ruta, imagenDefecto) {
    if (!ruta) return imagenDefecto;
    if (ruta.startsWith("/estilos/")) return imagenDefecto;
    return `http://localhost:8080${ruta}`;
}
