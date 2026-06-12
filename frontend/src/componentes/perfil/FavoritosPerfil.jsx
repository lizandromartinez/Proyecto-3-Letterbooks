import { resolverUrlImagen } from "../../utilidades/resolverUrlImagen";
import { Link } from 'react-router-dom';

/**
 * Componente que muestra los favoritos literarios del usuario.
 * <p>
 * Renderiza el autor favorito, género favorito y libro favorito
 * con su portada y nombre del autor en una tarjeta de ancho completo.
 * </p>
 *
 * @param {Object} perfil datos del perfil con autorFavorito, generoFavorito,
 *                        libroFavorito, imagenLibroFavorito y autorLibroFavorito.
 * @returns {JSX.Element} sección de favoritos literarios.
 */
function FavoritosPerfil({ perfil }) {
    return (
        <div className="flex flex-col gap-3">

            {/* Autor y Género en fila */}
            <div className="grid grid-cols-2 gap-3">
                <div className="bg-crema-fondo dark:bg-dark-fondo rounded-xl border border-gray-100 dark:border-white/5 p-4">
                    <p className="font-inter text-xs text-gray-400 dark:text-gray-500 uppercase tracking-wider mb-1">Autor favorito</p>
                    <p className="font-cormorant text-lg font-bold text-gold-button">
                        {perfil.autorFavorito !== "Ninguno"
                            ? perfil.autorFavorito
                            : <span className="text-gray-400 text-sm font-inter font-normal">No definido</span>}
                    </p>
                </div>
                <div className="bg-crema-fondo dark:bg-dark-fondo rounded-xl border border-gray-100 dark:border-white/5 p-4">
                    <p className="font-inter text-xs text-gray-400 dark:text-gray-500 uppercase tracking-wider mb-1">Género favorito</p>
                    <p className="font-cormorant text-lg font-bold text-gold-button">
                        {perfil.generoFavorito !== "Ninguno"
                            ? perfil.generoFavorito
                            : <span className="text-gray-400 text-sm font-inter font-normal">No definido</span>}
                    </p>
                </div>
            </div>

            {/* Libro favorito con portada */}
            <div className="bg-crema-fondo dark:bg-dark-fondo rounded-xl border border-gray-100 dark:border-white/5 p-4">
                <div className="flex items-center gap-2 mb-3">
                    <span className="text-gold-button">♥</span>
                    <p className="font-inter text-xs text-gray-400 dark:text-gray-500 uppercase tracking-wider">Libro favorito</p>
                </div>
		{perfil.libroFavorito && perfil.libroFavorito !== "Ninguno" ? (
		    <Link
			to={`/libro/${perfil.idLibro}`}
			className="flex items-start gap-4 group cursor-pointer"
		    >
			<div className="w-16 h-24 bg-gray-200 dark:bg-dark-fondo rounded-lg overflow-hidden flex-shrink-0 group-hover:shadow-md transition-shadow duration-200">
			    {perfil.imagenLibroFavorito ? (
				<img
				    src={resolverUrlImagen(perfil.imagenLibroFavorito, null)}
				    alt={perfil.libroFavorito}
				    className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
				/>
			    ) : (
				<div className="w-full h-full flex items-center justify-center">
				    <span className="text-2xl">📖</span>
				</div>
			    )}
			</div>
			<div className="flex flex-col justify-start gap-1">
			    <p className="font-cormorant text-xl font-bold text-navy-letter dark:text-gray-100 leading-tight group-hover:text-gold-button transition-colors duration-200">
				{perfil.libroFavorito}
			    </p>
			    {perfil.autorLibroFavorito && (
				<p className="font-inter text-sm text-gray-500 dark:text-gray-400">
				    {perfil.autorLibroFavorito}
				</p>
			    )}
			</div>
		    </Link>
		) : (
		    <p className="text-gray-400 font-inter text-sm">No definido</p>
		)}
            </div>
        </div>
    );
}

export default FavoritosPerfil;
