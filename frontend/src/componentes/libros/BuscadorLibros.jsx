/**
 * BuscadorLibros - Componente de búsqueda y filtrado para el catálogo de libros.
 * <p>
 * Permite filtrar libros por título, autor, género, editorial, ISBN o año.
 * Sincroniza el estado de búsqueda con los parámetros de la URL para preservar
 * el contexto al navegar entre páginas.
 * </p>
 *
 * @param {string} textoBusqueda texto actual del input de búsqueda.
 * @param {string} criterioBusqueda criterio activo de búsqueda.
 * @param {Function} onTextoCambio función que actualiza el texto de búsqueda.
 * @param {Function} onCriterioCambio función que actualiza el criterio de búsqueda.
 * @param {Function} onLimpiar función que limpia la búsqueda actual.
 * @param {Function} onVerTodos función que resetea todos los filtros.
 * @returns {JSX.Element} barra de búsqueda con selector de criterio.
 */
function BuscadorLibros({
    textoBusqueda,
    criterioBusqueda,
    onTextoCambio,
    onCriterioCambio,
    onLimpiar,
    onVerTodos
}) {
    // Etiquetas descriptivas utilizadas en el placeholder dinámico del buscador.
    const etiquetasCriterio = {
        titulo: 'título',
        autor: 'autor',
        genero: 'género',
        editorial: 'editorial',
        isbn: 'ISBN',
        ano: 'año',
    };

    return (
        <div className="flex items-center gap-2 w-full sm:w-auto">
            <div className="relative flex-1 sm:w-56">
                <input
                    type="text"
                    value={textoBusqueda}
                    onChange={(e) => {
                        const valor = e.target.value;
                        const soloNumeros = criterioBusqueda === 'isbn' || criterioBusqueda === 'ano';
                        if (soloNumeros) {
                            if (/^[0-9]*$/.test(valor)) onTextoCambio(valor);
                        } else {
                            onTextoCambio(valor);
                        }
                    }}
                    placeholder={`Buscar por ${etiquetasCriterio[criterioBusqueda] || criterioBusqueda}...`}
                    className="w-full bg-gray-50 dark:bg-white/5 border border-gray-200 dark:border-white/10 rounded-lg pl-8 pr-3 py-1.5 font-medium text-gray-700 dark:text-gray-300 focus:outline-none focus:ring-1 focus:ring-gold-button/50 transition-all"
                />
                <svg className="absolute left-2.5 top-1/2 -translate-y-1/2 w-3.5 h-3.5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0" />
                </svg>
            </div>

            {textoBusqueda && (
                <>
                    <button
                        onClick={onLimpiar}
                        className="text-gray-400 hover:text-gray-600 dark:hover:text-gray-200 transition-colors flex-shrink-0 cursor-pointer"
                    >
                         ✕
                    </button>
                    <button
                        onClick={onVerTodos}
                        className="text-xs text-gold-button hover:underline flex-shrink-0 cursor-pointer whitespace-nowrap"
                    >
                         Ver todos
                    </button>
                </>
            )}

            <select
                value={criterioBusqueda}
                onChange={(e) => onCriterioCambio(e.target.value)}
                className="bg-gray-50 dark:bg-white/5 border border-gray-200 dark:border-white/10 rounded-lg px-2.5 py-1.5 font-medium text-gray-700 dark:text-gray-300 focus:outline-none flex-shrink-0"
            >
                <option value="titulo">Título</option>
                <option value="autor">Autor</option>
                <option value="genero">Género</option>
                <option value="editorial">Editorial</option>
                <option value="isbn">ISBN</option>
                <option value="ano">Año</option>
            </select>
        </div>
    );
}

export default BuscadorLibros;
