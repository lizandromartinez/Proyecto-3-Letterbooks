import React, { createContext, useState } from 'react';
import { tokenEsValido } from '../utilidades/DecodificadorToken';

export const ContextoSesion = createContext();

/**
 * Proveedor del estado global de sesión.
 * El token se lee de localStorage en el init para evitar un frame sin sesión.
 */
export const ProveedorSesion = ({ children }) => {
    const [token, setToken] = useState(() => {
        const guardado = localStorage.getItem('token');
        if (!tokenEsValido(guardado)) {
            localStorage.removeItem('token');
            return null;
        }
        return guardado;
    });

    const iniciarSesion = (nuevoToken) => {
        setToken(nuevoToken);
        localStorage.setItem('token', nuevoToken);
    };

    const cerrarSesion = () => {
        setToken(null);
        localStorage.removeItem('token');
    };

    return (
        <ContextoSesion.Provider value={{ token, iniciarSesion, cerrarSesion }}>
            {children}
        </ContextoSesion.Provider>
    );
};
