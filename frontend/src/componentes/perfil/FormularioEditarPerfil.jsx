import { resolverUrlImagen } from "../../utilidades/resolverUrlImagen";
import avatarDefecto from "../../estilos/img/defecto/avatar.jpg";
import bannerDefecto from "../../estilos/img/defecto/banner.png";

/**
 * Componente que renderiza el formulario de edición del perfil.
 * <p>
 * Permite modificar avatar, banner, biografía, autor favorito,
 * género favorito y libro favorito. Incluye previsualización de
 * imágenes antes de guardar y búsqueda con debounce para autores y libros.
 * </p>
 *
 * @param {Object} perfil datos actuales del perfil para las previsualizaciones.
 * @param {Object} campos objeto con todos los valores de los campos editables.
 * @param {Object} setters objeto con los setters de cada campo editable.
 * @param {Array} autores lista de autores para el selector.
 * @param {Array} generos lista de géneros para el selector.
 * @param {Array} libros lista de libros para el selector.
 * @param {boolean} guardando indica si se está procesando el guardado.
 * @param {Function} onGuardar función que ejecuta el guardado de cambios.
 * @param {Function} onCancelar función que cancela la edición.
 * @returns {JSX.Element} formulario completo de edición de perfil.
 */
function FormularioEditarPerfil({
    perfil,
    campos,
    setters,
    autores,
    generos,
    libros,
    guardando,
    onGuardar,
    onCancelar
}) {
    const {
        biografia, avatar, banner,
        busquedaAutor, busquedaLibro,
        idAutorFavorito, idGeneroFavorito, idLibroFavorito,
        previstaAvatar, previstaBanner
    } = campos;

    const {
        setBiografia, setBusquedaAutor, setBusquedaLibro,
        setIdAutorFavorito, setIdGeneroFavorito, setIdLibroFavorito,
        manejarSeleccionImagen
    } = setters;

    return (
        <div className="py-8">
            <h2 className="font-cormorant text-2xl font-bold text-navy-letter dark:text-gray-100 mb-6">
													   Editar información
            </h2>

            <div className="bg-white dark:bg-dark-borde rounded-2xl border border-gray-100 dark:border-white/5 shadow-sm p-6 md:p-8 flex flex-col gap-6">

                {/* Imágenes */}
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                    {/* Avatar */}
                    <div className="flex flex-col gap-3">
                        <label className="font-inter text-sm font-semibold text-navy-letter dark:text-gray-200">Avatar</label>
                        <img
                            src={previstaAvatar || resolverUrlImagen(perfil.avatar, avatarDefecto)}
                            alt="Preview avatar"
                            className="w-24 h-24 rounded-full object-cover border-2 border-gray-200 dark:border-gray-600"
                        />
                        <label className="flex items-center gap-2 px-4 py-2 rounded-md border border-dashed border-gray-300 dark:border-gray-600 text-sm font-inter text-gray-500 dark:text-gray-400 hover:border-gold-button hover:text-gold-button transition-all cursor-pointer w-fit">
                            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                            </svg>
				      Cambiar avatar
                            <input type="file" accept="image/*" className="hidden" onChange={e => manejarSeleccionImagen(e, "avatar")} />
                        </label>
                    </div>

                    {/* Banner */}
                    <div className="flex flex-col gap-3">
                        <label className="font-inter text-sm font-semibold text-navy-letter dark:text-gray-200">Banner</label>
                        <img
                            src={previstaBanner || resolverUrlImagen(perfil.banner, bannerDefecto)}
                            alt="Preview banner"
                            className="w-full h-24 rounded-lg object-cover border-2 border-gray-200 dark:border-gray-600"
                        />
                        <label className="flex items-center gap-2 px-4 py-2 rounded-md border border-dashed border-gray-300 dark:border-gray-600 text-sm font-inter text-gray-500 dark:text-gray-400 hover:border-gold-button hover:text-gold-button transition-all cursor-pointer w-fit">
                            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                            </svg>
				      Cambiar banner
                            <input type="file" accept="image/*" className="hidden" onChange={e => manejarSeleccionImagen(e, "banner")} />
                        </label>
                    </div>
                </div>

                {/* Biografía */}
                <div className="flex flex-col gap-2">
                    <label className="font-inter text-sm font-semibold text-navy-letter dark:text-gray-200">Biografía</label>
                    <textarea
                        rows={3}
                        value={biografia}
                        onChange={e => setBiografia(e.target.value)}
                        placeholder="Cuéntanos sobre ti..."
                        className="w-full px-4 py-3 rounded-lg border border-gray-200 dark:border-gray-600 bg-white dark:bg-dark-fondo text-navy-letter dark:text-gray-200 font-inter text-sm resize-none focus:outline-none focus:ring-2 focus:ring-gold-button/50 transition-all"
                    />
                </div>

                {/* Favoritos */}
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">

                    {/* Autor favorito */}
                    <div className="flex flex-col gap-2">
                        <label className="font-inter text-sm font-semibold text-navy-letter dark:text-gray-200">Autor favorito</label>
                        <input
                            placeholder="Buscar autor..."
                            value={busquedaAutor}
                            onChange={e => setBusquedaAutor(e.target.value)}
                            className="w-full px-3 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-white dark:bg-dark-fondo text-navy-letter dark:text-gray-200 font-inter text-sm focus:outline-none focus:ring-2 focus:ring-gold-button/50 transition-all"
                        />
                        <select
                            value={idAutorFavorito}
                            onChange={e => setIdAutorFavorito(e.target.value)}
                            className="w-full px-3 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-white dark:bg-dark-fondo text-navy-letter dark:text-gray-200 font-inter text-sm focus:outline-none focus:ring-2 focus:ring-gold-button/50 transition-all"
                        >
                            <option value="">-- Selecciona --</option>
                            {autores.map(a => <option key={a.idAutor} value={a.idAutor}>{a.nombreAutor}</option>)}
                        </select>
                    </div>

                    {/* Género favorito */}
                    <div className="flex flex-col gap-2">
                        <label className="font-inter text-sm font-semibold text-navy-letter dark:text-gray-200">Género favorito</label>
                        <select
                            value={idGeneroFavorito}
                            onChange={e => setIdGeneroFavorito(e.target.value)}
                            className="w-full px-3 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-white dark:bg-dark-fondo text-navy-letter dark:text-gray-200 font-inter text-sm focus:outline-none focus:ring-2 focus:ring-gold-button/50 transition-all mt-8"
                        >
                            <option value="">-- Selecciona --</option>
                            {generos.map(g => <option key={g.idGenero} value={g.idGenero}>{g.nombreGenero}</option>)}
                        </select>
                    </div>

                    {/* Libro favorito */}
                    <div className="flex flex-col gap-2">
                        <label className="font-inter text-sm font-semibold text-navy-letter dark:text-gray-200">Libro favorito</label>
                        <input
                            placeholder="Buscar libro..."
                            value={busquedaLibro}
                            onChange={e => setBusquedaLibro(e.target.value)}
                            className="w-full px-3 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-white dark:bg-dark-fondo text-navy-letter dark:text-gray-200 font-inter text-sm focus:outline-none focus:ring-2 focus:ring-gold-button/50 transition-all"
                        />
                        <select
                            value={idLibroFavorito}
                            onChange={e => setIdLibroFavorito(e.target.value)}
                            className="w-full px-3 py-2 rounded-lg border border-gray-200 dark:border-gray-600 bg-white dark:bg-dark-fondo text-navy-letter dark:text-gray-200 font-inter text-sm focus:outline-none focus:ring-2 focus:ring-gold-button/50 transition-all"
                        >
                            <option value="">-- Selecciona --</option>
                            {libros.map(l => <option key={l.idLibro} value={l.idLibro}>{l.titulo}</option>)}
                        </select>
                    </div>
                </div>

                {/* Botones */}
                <div className="flex gap-3 pt-2">
                    <button
                        onClick={onGuardar}
                        disabled={guardando}
                        className="px-6 py-2 rounded-md bg-gold-button hover:bg-gold-button-hover text-white font-inter font-bold text-sm transition-all duration-200 disabled:opacity-60 cursor-pointer"
                    >
                        {guardando ? "Guardando..." : "Guardar cambios"}
                    </button>
                    <button
                        onClick={onCancelar}
                        className="px-6 py-2 rounded-md border border-gray-300 dark:border-gray-600 text-navy-letter dark:text-gray-200 font-inter font-medium text-sm hover:bg-gray-100 dark:hover:bg-dark-fondo transition-all duration-200 cursor-pointer"
                    >
                         Cancelar
                    </button>
                </div>
            </div>
        </div>
    );
}

export default FormularioEditarPerfil;
