
/**
 * Componente auxiliar que muestra un mensaje visual cuando
 * una sección de actividad no tiene contenido disponible.
 *
 * Se utiliza en cada tab de la sección de actividad del perfil
 * para indicar al visitante que el usuario aún no tiene registros
 * en esa categoría específica.
 *
 *
 * @param {string} texto mensaje descriptivo a mostrar al usuario.
 * @returns {JSX.Element} contenedor centrado con ícono y mensaje.
 */
function MensajeVacio({ texto }) {
    return (
        <div className="flex flex-col items-center justify-center py-16 gap-3">
            <span className="text-4xl">📚</span>
            <p className="font-inter text-sm text-gray-400 dark:text-gray-500">{texto}</p>
        </div>
    );
}

export default MensajeVacio;
