import { resolverUrlImagen } from "../../utilidades/resolverUrlImagen";
import avatarDefecto from "../../estilos/img/defecto/avatar.jpg";
import bannerDefecto from "../../estilos/img/defecto/banner.png";

/**
 * Componente que renderiza la cabecera visual del perfil.
 * <p>
 * Muestra el banner de fondo, el avatar superpuesto y los botones
 * de acción opcionales (editar perfil y cerrar sesión).
 * En perfiles ajenos no se pasan botones y la sección queda vacía.
 * </p>
 *
 * @param {Object} perfil datos del perfil con avatar y banner.
 * @param {JSX.Element} [botones] botones de acción opcionales.
 * @returns {JSX.Element} cabecera visual del perfil.
 */
function BannerPerfil({ perfil, botones }) {
    return (
        <>
            {/* Banner */}
            <div className="relative w-full h-48 md:h-56">
                <img
                    src={resolverUrlImagen(perfil.banner, bannerDefecto)}
                    alt="Banner"
                    className="w-full h-full object-cover"
                />
                <div className="absolute inset-0 bg-gradient-to-t from-black/30 to-transparent" />
            </div>

            {/* Avatar y botones */}
            <div className="px-6 md:px-10">
                <div className="relative flex flex-col md:flex-row md:items-end md:justify-between gap-4 pb-6 border-b border-gray-200 dark:border-dark-fondo">
                    <div className="relative -mt-16 md:-mt-20">
                        <img
                            src={resolverUrlImagen(perfil.avatar, avatarDefecto)}
                            alt="Avatar"
                            className="w-28 h-28 md:w-36 md:h-36 rounded-full object-cover border-4 border-white dark:border-dark-borde shadow-lg"
                        />
                    </div>
                    {botones && (
                        <div className="flex gap-3 md:mb-2">
                            {botones}
                        </div>
                    )}
                </div>
            </div>
        </>
    );
}

export default BannerPerfil;
