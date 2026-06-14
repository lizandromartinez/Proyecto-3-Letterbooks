import React, { useContext, useEffect, useState } from 'react';
import { ContextoSesion } from '../../../contexto/Sesion';
import { obtenerPerfil } from '../../../api/Perfil';
import { obtenerIdUsuarioDelToken, obtenerUsuarioDelToken } from '../../../utilidades/DecodificadorToken';
import { resolverUrlImagen } from '../../../utilidades/resolverUrlImagen';
import EnlaceRuta from '../EnlaceRuta';
import avatarDefecto from '../../../estilos/img/defecto/avatar.jpg';

/**
 * Enlace circular con la imagen de perfil del usuario autenticado.
 * @param {string|null} avatarUrl ruta de avatar externa para actualización inmediata.
 * @param {function} onClick callback opcional al hacer clic (ej. cerrar menú móvil).
 */
const EnlaceAvatarPerfil = ({ avatarUrl, onClick }) => {
    const { token } = useContext(ContextoSesion);
    const [avatar, setAvatar] = useState(null);

    useEffect(() => {
        if (avatarUrl !== undefined) {
            setAvatar(avatarUrl);
            return;
        }

        const idUsuario = obtenerIdUsuarioDelToken(token);
        if (!idUsuario || !token) return;

        obtenerPerfil(idUsuario, token)
            .then((perfil) => setAvatar(perfil?.avatar ?? null))
            .catch(() => setAvatar(null));
    }, [token, avatarUrl]);

    const nombreUsuario = obtenerUsuarioDelToken(token) ?? 'Usuario';
    const src = resolverUrlImagen(avatar, avatarDefecto);

    return (
        <EnlaceRuta
            to="/perfil"
            onClick={onClick}
            aria-label="Ir a mi perfil"
            className="inline-block"
        >
            <img
                src={src}
                alt={nombreUsuario}
                className="w-10 h-10 rounded-full object-cover border-2 border-gold-button/20 hover:border-gold-button hover:scale-105 transition-all duration-200"
            />
        </EnlaceRuta>
    );
};

export default EnlaceAvatarPerfil;
