import React, { createContext, useState } from 'react';

export const ContextoSesion = createContext();

/**
 * Proveedor del estado global de sesión.
 * El token se lee de localStorage en el init para evitar un frame sin sesión.
 */
export const ProveedorSesion = ({ children }) => {
    const [token, setToken] = useState(() => localStorage.getItem('token'));

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
