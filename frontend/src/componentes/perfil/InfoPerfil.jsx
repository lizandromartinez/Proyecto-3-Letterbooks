/**
 * Componente que muestra la información básica del usuario.
 * <p>
 * Renderiza el nombre de usuario, el handle con @ y la biografía
 * si está disponible.
 * </p>
 *
 * @param {Object} perfil datos del perfil con nombreUsuario y biografia.
 * @returns {JSX.Element} bloque de información personal del usuario.
 */
function InfoPerfil({ perfil }) {
    return (
        <div className="flex flex-col gap-1">
            <h1 className="font-cormorant text-3xl md:text-4xl font-bold text-navy-letter dark:text-gray-100">
                {perfil.nombreUsuario}
            </h1>
            <p className="font-inter text-sm text-gold-button">@{perfil.nombreUsuario}</p>
            {perfil.biografia && (
                <p className="font-inter text-sm text-gray-500 dark:text-gray-400 mt-2 max-w-lg leading-relaxed">
                    {perfil.biografia}
                </p>
            )}
        </div>
    );
}

export default InfoPerfil;
