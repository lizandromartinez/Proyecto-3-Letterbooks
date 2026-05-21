import { resolverUrlImagen } from "../../utilidades/resolverUrlImagen";
import MensajeVacio from "../comunes/MensajeVacio";

/**
 * Componente que muestra la actividad del usuario organizada en tabs.
 * <p>
 * Incluye reseñas likeadas, reseñas calificadas, libros calificados
 * y comentarios likeados. Funciona tanto para el perfil propio
 * como para perfiles públicos ajenos.
 * </p>
 *
 * @param {Object} perfil datos del perfil con las listas de actividad.
 * @param {string} tabActiva ID del tab actualmente seleccionado.
 * @param {Function} setTabActiva función para cambiar el tab activo.
 * @param {string} tituloSeccion título de la sección ("Tu actividad" o "Actividad").
 * @returns {JSX.Element} sección de actividad con tabs navegables.
 */
function ActividadPerfil({ perfil, tabActiva, setTabActiva, tituloSeccion = "Actividad" }) {

    const tabs = [
        { id: "likeadas",    label: "Reseñas likeadas",     count: perfil.resenasLikeadas?.length    || 0 },
        { id: "calificadas", label: "Reseñas calificadas",  count: perfil.resenasCalificadas?.length || 0 },
        { id: "libros",      label: "Libros calificados",   count: perfil.librosCalificados?.length  || 0 },
        { id: "comentarios", label: "Comentarios likeados", count: perfil.comentariosLikeados?.length || 0 },
    ];

    return (
        <div>
            <h2 className="font-cormorant text-2xl font-bold text-navy-letter dark:text-gray-100 mb-4">
                {tituloSeccion}
            </h2>

            {/* Tabs */}
            <div className="flex gap-1 border-b border-gray-200 dark:border-dark-borde mb-6 overflow-x-auto">
                {tabs.map(tab => (
                    <button
                        key={tab.id}
                        onClick={() => setTabActiva(tab.id)}
                        className={`px-4 py-3 font-inter text-sm font-medium whitespace-nowrap transition-all duration-200 border-b-2 cursor-pointer
                            ${tabActiva === tab.id
                                ? "border-gold-button text-gold-button"
                                : "border-transparent text-gray-500 dark:text-gray-400 hover:text-navy-letter dark:hover:text-gray-200"
                            }`}
                    >
                        {tab.label}
                        <span className={`ml-2 px-1.5 py-0.5 rounded-full text-xs
                            ${tabActiva === tab.id
                                ? "bg-gold-button/10 text-gold-button"
                                : "bg-gray-100 dark:bg-dark-borde text-gray-400"
                            }`}>
                            {tab.count}
                        </span>
                    </button>
                ))}
            </div>

            {/* Reseñas likeadas */}
            {tabActiva === "likeadas" && (
                <div className="flex flex-col gap-3">
                    {perfil.resenasLikeadas?.length > 0 ? perfil.resenasLikeadas.map(r => (
                        <div key={r.idResena} className="bg-white dark:bg-dark-borde rounded-xl border border-gray-100 dark:border-white/5 p-5 hover:shadow-md transition-shadow duration-200">
                            <div className="flex items-start justify-between gap-4">
                                <div className="flex-1">
                                    <p className="font-cormorant text-lg font-bold text-navy-letter dark:text-gray-100 mb-1">{r.tituloLibro}</p>
                                    <p className="font-inter text-xs text-gray-400 dark:text-gray-500 mb-3">
														Reseña de <span className="text-gold-button">@{r.autorResena}</span> · {r.fechaLike}
                                    </p>
                                    <p className="font-inter text-sm text-gray-600 dark:text-gray-300 leading-relaxed line-clamp-3">{r.textoResena}</p>
                                </div>
                                <span className="text-red-400 text-xl flex-shrink-0">♥</span>
                            </div>
                        </div>
                    )) : <MensajeVacio texto="Aún no ha dado like a ninguna reseña" />}
                </div>
            )}

            {/* Reseñas calificadas */}
            {tabActiva === "calificadas" && (
                <div className="flex flex-col gap-3">
                    {perfil.resenasCalificadas?.length > 0 ? perfil.resenasCalificadas.map(r => (
                        <div key={r.idResena} className="bg-white dark:bg-dark-borde rounded-xl border border-gray-100 dark:border-white/5 p-5 hover:shadow-md transition-shadow duration-200">
                            <div className="flex items-start justify-between gap-4">
                                <div className="flex-1">
                                    <p className="font-cormorant text-lg font-bold text-navy-letter dark:text-gray-100 mb-1">{r.tituloLibro}</p>
                                    <p className="font-inter text-xs text-gray-400 dark:text-gray-500 mb-3">
														Reseña de <span className="text-gold-button">@{r.autorResena}</span> · {r.fechaCalificacion}
                                    </p>
                                    <p className="font-inter text-sm text-gray-600 dark:text-gray-300 leading-relaxed line-clamp-3">{r.textoResena}</p>
                                </div>
                                <div className="flex items-center gap-1 bg-gold-button/10 px-3 py-1 rounded-full flex-shrink-0">
                                    <span className="text-gold-button text-sm">★</span>
                                    <span className="font-inter font-bold text-gold-button text-sm">{r.calificacion}</span>
                                </div>
                            </div>
                        </div>
                    )) : <MensajeVacio texto="Aún no ha calificado ninguna reseña" />}
                </div>
            )}

            {/* Libros calificados */}
            {tabActiva === "libros" && (
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                    {perfil.librosCalificados?.length > 0 ? perfil.librosCalificados.map(l => (
                        <div key={l.idLibro} className="bg-white dark:bg-dark-borde rounded-xl border border-gray-100 dark:border-white/5 p-4 flex gap-4 hover:shadow-md transition-shadow duration-200">
                            {l.imagen ? (
                                <img src={resolverUrlImagen(l.imagen, null)} alt={l.titulo} className="w-14 h-20 object-cover rounded-lg flex-shrink-0" />
                            ) : (
                                <div className="w-14 h-20 bg-gray-100 dark:bg-dark-fondo rounded-lg flex-shrink-0 flex items-center justify-center">
                                    <span className="text-2xl">📖</span>
                                </div>
                            )}
                            <div className="flex flex-col justify-between flex-1">
                                <div>
                                    <p className="font-cormorant text-base font-bold text-navy-letter dark:text-gray-100 leading-tight">{l.titulo}</p>
                                    <p className="font-inter text-xs text-gray-400 dark:text-gray-500 mt-0.5">{l.autor}</p>
                                </div>
                                <div className="flex items-center gap-1 bg-gold-button/10 px-2 py-0.5 rounded-full w-fit">
                                    <span className="text-gold-button text-xs">★</span>
                                    <span className="font-inter font-bold text-gold-button text-xs">{l.calificacion}/10</span>
                                </div>
                            </div>
                        </div>
                    )) : <div className="col-span-2"><MensajeVacio texto="Aún no ha calificado ningún libro" /></div>}
                </div>
            )}

            {/* Comentarios likeados */}
            {tabActiva === "comentarios" && (
                <div className="flex flex-col gap-3">
                    {perfil.comentariosLikeados?.length > 0 ? perfil.comentariosLikeados.map(c => (
                        <div key={c.idComentario} className="bg-white dark:bg-dark-borde rounded-xl border border-gray-100 dark:border-white/5 p-5 hover:shadow-md transition-shadow duration-200">
                            <p className="font-inter text-xs text-gray-400 dark:text-gray-500 mb-2">
													Comentario en <span className="font-semibold text-navy-letter dark:text-gray-300">{c.tituloLibro}</span> · por <span className="text-gold-button">@{c.autorComentario}</span> · {c.fechaLike}
                            </p>
                            <p className="font-inter text-sm text-gray-600 dark:text-gray-300 leading-relaxed">{c.texto}</p>
                        </div>
                    )) : <MensajeVacio texto="Aún no ha dado like a ningún comentario" />}
                </div>
            )}
        </div>
    );
}

export default ActividadPerfil;
